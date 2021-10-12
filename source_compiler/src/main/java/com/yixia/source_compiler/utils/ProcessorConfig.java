package com.yixia.source_compiler.utils;

public interface ProcessorConfig {

    // @ARouter注解 的 包名 + 类名
    String AROUTER_PACKAGE =  "com.yixia.source_annotation.NetworkSource";

    // 接收参数的TAG标记
    String OPTIONS = "moduleName"; // 同学们：目的是接收 每个module名称
    String APT_PACKAGE = "packageNameForAPT"; // 同学们：目的是接收 包名（APT 存放的包名）


    String INetWork = "com.yixia.source_annotation.INetWork";
    String PATH_METHOD_NAME = "getCall";

}
