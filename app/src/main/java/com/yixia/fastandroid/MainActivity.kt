package com.yixia.fastandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.yixia.fastandroid.bean.NewsChannelsBean
import com.yixia.fastandroid.net.NewsApi
import com.yixia.network.ApiBase
import com.yixia.network.errorhandler.ExceptionHandle
import com.yixia.network.observer.BaseObserver
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.txt_test).setOnClickListener {
            NewsApi.getInstance().getNewsChannels(
                object : BaseObserver<NewsChannelsBean?>() {
                    override fun onNext(t: NewsChannelsBean) {
                        Log.d("tagger","ssssss ->>>>>"+t.toString())
                    }

                    override fun onError(e: ExceptionHandle.ResponeThrowable?) {
                        Log.d("tagger","ssssss ->>>>>"+e?.code)
                    }
                })
        }
    }
}