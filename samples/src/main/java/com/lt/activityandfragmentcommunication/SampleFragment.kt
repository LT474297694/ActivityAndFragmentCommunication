package com.lt.activityandfragmentcommunication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lt.activityandfragmentcommunication.base.BaseFragment
import com.lt.frame.manager.FunctionManager
import kotlinx.android.synthetic.main.fragment_sample.*

/**
 * Created by L.T on 2018/3/22.
 * qq:474297694
 * 功能: SampleFragment
 */
class SampleFragment : BaseFragment() {
    //定义接口名称 和我们平时使用接口定义接口一个意思
    companion object {
        val functionName1 = javaClass.name + "function1"
        val functionName2 = javaClass.name + "function2"
        val functionName3 = javaClass.name + "function3"
        val functionName4 = javaClass.name + "function4"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_sample, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListener()
    }

    //当前Framgent需要绑定的接口名称
    override fun bindFunctionNames() =
            arrayOf(functionName1, functionName2, functionName3, functionName4)

    private fun initListener() {
        //调用接口 只需传入名字和参数
        btFunc1.setOnClickListener {
            FunctionManager.invokeFunc(functionName1)
        }
        btFunc2.setOnClickListener {
            FunctionManager.invokeFunc(functionName2, "我是Fragment传递的参数")
        }
        btFunc3.setOnClickListener {
            val str = FunctionManager.invokeFunc(functionName3, String::class.java)
            Toast.makeText(context, "Fragment获得返回值:$str", Toast.LENGTH_LONG).show()
        }
        btFunc4.setOnClickListener {
            val str = FunctionManager.invokeFunc(functionName4, "我是Fragment传递的参数", String::class.java)
            Toast.makeText(context, "Fragment获得返回值:$str", Toast.LENGTH_LONG).show()
        }
    }

}
