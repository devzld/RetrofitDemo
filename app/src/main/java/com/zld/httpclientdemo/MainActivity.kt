package com.zld.httpclientdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.zld.netlib.HttpBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HttpBuilder("/api/data/Android/10/1")
                .params("ky","123")
                .path("")
                .success {
                    Logger.i("main", it)
                }
                .error {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
                .get()
    }


}
