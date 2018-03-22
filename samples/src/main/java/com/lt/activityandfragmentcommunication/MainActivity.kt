package com.lt.activityandfragmentcommunication

import android.os.Bundle
import android.widget.Toast
import com.lt.activityandfragmentcommunication.base.BaseActivity
import com.lt.frame.FunctionNoParameterNoResult
import com.lt.frame.function.FunctionOnlyParameter
import com.lt.frame.function.FunctionOnlyResult
import com.lt.frame.function.FunctionHaveParameterHaveResult


/**
 * Created by L.T on 2018/3/22.
 * qq:474297694
 * 功能:MainActivity
 */
class MainActivity : BaseActivity() {

    val fragment by lazy { SampleFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        supportFragmentManager.beginTransaction().add(R.id.rootView, fragment).commit()
    }

    override fun bindFunctions() =
            arrayOf(object : FunctionNoParameterNoResult(SampleFragment.functionName1) {
                override fun function() {
                    Toast.makeText(this@MainActivity, "无参无返回值", Toast.LENGTH_LONG).show()
                }
            }, object : FunctionOnlyParameter<String>(SampleFragment.functionName2) {
                override fun function(t: String?) {
                    Toast.makeText(this@MainActivity, "有参无返回值:$t", Toast.LENGTH_LONG).show()
                }
            }, object : FunctionOnlyResult<String>(SampleFragment.functionName3) {

                override fun function(): String? = "Activity给你的数据"

            }, object : FunctionHaveParameterHaveResult<String, String>(SampleFragment.functionName4) {
                override fun function(t: String?): String? {
                    Toast.makeText(this@MainActivity, "有参有返回值:$t", Toast.LENGTH_SHORT).show()
                    return "Activity给你的数据"
                }
            })
}
