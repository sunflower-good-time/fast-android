package com.yixia.fastandroid.page.home.viewmodel

import android.app.Application
import com.yixia.base.arch.viewmodel.BaseViewModel
import com.yixia.fastandroid.page.home.model.source.NewsSource
/**
 * @author : zhoujianfeng
 * CreatData: 2021/9/13
 * desc:
 */
 class NewsViewModel(application: Application) :BaseViewModel(application)  {
    var mArticleSource: NewsSource = NewsSource()

    var age:Int = 10

 }