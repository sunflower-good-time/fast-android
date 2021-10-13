package com.yixia.fastandroid

import android.os.Bundle
import android.util.Log
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.yixia.base.arch.view.BaseActivity
import com.yixia.fastandroid.databinding.ActivityMainBinding
import com.yixia.fastandroid.page.home.viewmodel.NewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val _uiState = MutableStateFlow(true)

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.mArticleSource.response()?.observe(this, Observer {
            Log.d("tagger", "tagger------> $it")
            Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
        })


        /**
         * 网络请求
         */
        mBinding.txtHttp.setOnClickListener {
            viewModel.mArticleSource.request()?.value = ""
        }


        /**
         * Flow
         */
        mBinding.txtFlow.setOnClickListener {
            val flower = flow {
                emit("哈哈哈哈哈")
            }.map {
                "${it}你是谁"
            }.catch {
                it.message?.let { it1 -> Log.d("tagger", it1) }
            }.flowOn(Dispatchers.IO)

            lifecycleScope.launch {
//                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    flower.collect {
                        Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                    }
//                }
            }
        }

        /**
         * StateFlow
         */
        mBinding.txtStateflow.setOnClickListener {
            lifecycleScope.launch {
                delay(4000)
                _uiState.value = false
            }
        }

        lifecycleScope.launch {
            _uiState.collect {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }


        mBinding.txtAnimation.setOnClickListener {
            mBinding.animationView.startAnimation(getTranslateAnimation(1000))
            mBinding.animationView1.startAnimation(getTranslateAnimation(1500))
            mBinding.animationView2.startAnimation(getTranslateAnimation(2000))
            mBinding.animationView3.startAnimation(getTranslateAnimation(2400))
            mBinding.animationView4.startAnimation(getTranslateAnimation(2800))
            mBinding.animationView5.startAnimation(getTranslateAnimation(3100))
            mBinding.animationView6.startAnimation(getTranslateAnimation(3600))
        }
    }

    fun getTranslateAnimation(duration:Long):TranslateAnimation {
        val translateAnimation1 = TranslateAnimation(0f,
            windowManager.defaultDisplay.width.toFloat()-100,0f,-windowManager.defaultDisplay.height.toFloat()+150)
        translateAnimation1.duration = duration;//动画持续的时间为10s
        translateAnimation1.isFillEnabled = true;//使其可以填充效果从而不回到原地
        translateAnimation1.fillAfter = true;//不回到起始位置
        return translateAnimation1;
    }

    override fun getLastNonConfigurationInstance(): Any? {
        return super.getLastNonConfigurationInstance()
    }


    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return super.onRetainCustomNonConfigurationInstance()
    }

}