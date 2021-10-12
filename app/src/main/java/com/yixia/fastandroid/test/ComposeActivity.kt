package com.yixia.fastandroid.test

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

/**
 * @author : zhoujianfeng
 * CreatData: 2021/9/15
 * desc:
 */
class ComposeActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            showView()
        }
    }


    @Composable
    fun showView() {

    }

}