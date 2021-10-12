package com.yixia.source_compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;
import com.yixia.source_annotation.NetworkSource;
import com.yixia.source_compiler.utils.ProcessorConfig;
import com.yixia.source_compiler.utils.ProcessorUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * 同学们注意：编码此类，记住就是一个字（细心，细心，细心），出了问题debug真的不好调试
 */

// AutoService则是固定的写法，加个注解即可
// 通过auto-service中的@AutoService可以自动生成AutoService注解处理器，用来注册
// 用来生成 META-INF/services/javax.annotation.processing.Processor 文件
@AutoService(Processor.class)

// 注解处理器接收的参数
@SupportedOptions({ProcessorConfig.OPTIONS, ProcessorConfig.APT_PACKAGE})

public class SourceProcessor extends AbstractProcessor {

    // 操作Element的工具类（类，函数，属性，其实都是Element）
    private Elements elementTool;

    // type(类信息)的工具类，包含用于操作TypeMirror的工具方法
    private Types typeTool;

    // Message用来打印 日志相关信息
    private Messager messager;

    // 文件生成器， 类 资源 等，就是最终要生成的文件 是需要Filer来完成的
    private Filer filer;

    private String options; // 各个模块传递过来的模块名 例如：app order personal
    private String aptPackage; // 各个模块传递过来的目录 用于统一存放 apt生成的文件

    // 仓库一 Path  缓存一
    // Map<"personal", List<RouterBean>>
//    private Map<String, List<RouterBean>> mAllPathMap = new HashMap<>(); // 目前是一个

    // 仓库二 Group 缓存二
    // Map<"personal", "ARouter$$Path$$personal.class">

    // 做初始化工作，就相当于 Activity中的 onCreate函数一样的作用

    // 临时map存储，用来存放被@Parameter注解的属性集合，生成类文件时遍历
    // key:类节点, value:被@Parameter注解的属性集合
    private Map<TypeElement, List<Element>> tempParameterMap = new HashMap<>();


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(NetworkSource.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        elementTool = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        typeTool = processingEnvironment.getTypeUtils();

        // 只有接受到 App壳 传递过来的书籍，才能证明我们的 APT环境搭建完成
        options = processingEnvironment.getOptions().get(ProcessorConfig.OPTIONS);
        aptPackage = processingEnvironment.getOptions().get(ProcessorConfig.APT_PACKAGE);
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>>>>>>>>>>>>>> options:" + options);
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>>>>>>>>>>>>>> aptPackage:" + aptPackage);
        if (options != null && aptPackage != null) {
            messager.printMessage(Diagnostic.Kind.NOTE, "APT 环境搭建完成....");
        } else {
            messager.printMessage(Diagnostic.Kind.NOTE, "APT 环境有问题，请检查 options 与 aptPackage 为null...");
        }
    }

    /**
     * 相当于main函数，开始处理注解
     * 注解处理器的核心方法，处理具体的注解，生成Java文件
     *
     * @param set              使用了支持处理注解的节点集合
     * @param roundEnvironment 当前或是之前的运行环境,可以通过该对象查找的注解。
     * @return true 表示后续处理器不会再处理（已经处理完成）
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set.isEmpty()) {
            messager.printMessage(Diagnostic.Kind.NOTE, "并没有发现 被@ARouter注解的地方呀");
            return false; // 没有机会处理
        }

        if (!ProcessorUtils.isEmpty(set)) {

            // 获取所有被 @Parameter 注解的 元素（属性）集合
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(NetworkSource.class);
            for (Element element : elements) {
                // 字段节点的上一个节点 类节点==Key
                // 注解在属性的上面，属性节点父节点 是 类节点
                TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

                // enclosingElement == Personal_MainActivity == key

                if (tempParameterMap.containsKey(enclosingElement)) {
                    tempParameterMap.get(enclosingElement).add(element);
                } else { // 没有key Personal_MainActivity
                    List<Element> fields = new ArrayList<>();
                    fields.add(element);
                    tempParameterMap.put(enclosingElement, fields); // 加入缓存
                }
            }

            // 判断是否有需要生成的类文件
            if (ProcessorUtils.isEmpty(tempParameterMap)) return true;

            TypeElement activityType = elementTool.getTypeElement(ProcessorConfig.INetWork);

            for (TypeElement typeElement : tempParameterMap.keySet()) {
                List<Element> elements1 = tempParameterMap.get(typeElement);
                for (Element element : elements1) {


                    try {

                        String simplName = element.getSimpleName().toString();
                        String s1 = element.asType().toString();
                        messager.printMessage(Diagnostic.Kind.NOTE, simplName +" < ------>" +s1);


                        JavaFileObject jfo = processingEnv.getFiler().createSourceFile(aptPackage+".NewTestOverSource", typeElement);
                        try (Writer writer = jfo.openWriter()) {
                            writer.write(
                                    "package "+aptPackage+";\n" +
                                    "\n" +
                                    "import androidx.annotation.NonNull;\n" +
                                    "import androidx.annotation.Nullable;\n" +
                                    "\n" +
                                    "import com.yixia.base.arch.model.IRetrofitDataSource;\n" +
                                    "import com.yixia.base.arch.net.NetApi;\n" +
                                    "import com.yixia.fastandroid.bean.NewsChannelsBean;\n" +
                                    "import com.yixia.fastandroid.net.NewsApiInterface;\n" +
                                    "\n" +
                                    "import io.reactivex.Observable;\n" +
                                    "\n" +
                                    "public class NewTestOverSource extends IRetrofitDataSource<String, NewsChannelsBean> {\n" +
                                    "    @NonNull\n" +
                                    "    @Override\n" +
                                    "    public Observable<NewsChannelsBean> getCall(@Nullable String request) {\n" +
                                    "        return NetApi.getInstance().getRef().create(NewsApiInterface.class)."+simplName+"();\n" +
                                    "    }\n" +
                                    "}\n");
                            writer.flush();
                        }
                    } catch (Exception e) {
                        messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage() + "\r\n");
                    }
                }
            }
        }

        return false; // 坑：必须写返回值，表示处理@ARouter注解完成
    }
}
