package com.yixia.fastandroid

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.yixia.base.arch.view.BaseActivity
import com.yixia.fastandroid.databinding.ActivityMainBinding
import com.yixia.fastandroid.page.home.viewmodel.NewsViewModel
import com.yixia.fastandroid.test.FunInterface
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>(){

    var age = 10;
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.mArticleSource.response()?.observe(this, Observer {
            Log.d("tagger", "tagger------> $it")
        })
        mBinding.txtTest.setOnClickListener {
//            viewModel.mArticleSource.request()?.postValue("")
            viewModel.age ++
            age ++
            Log.d("tagger",">>>>>>>" + viewModel.age + "sssssss" + age)

        }
//
//        lifecycleScope.launch {
//
//        }


    }

    fun test(funInterface: FunInterface):String {
        Log.d("tagger",funInterface.testOver());
        return funInterface.testOver()
    }

    override fun getLastNonConfigurationInstance(): Any? {
        return super.getLastNonConfigurationInstance()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return super.onRetainCustomNonConfigurationInstance()
    }

}