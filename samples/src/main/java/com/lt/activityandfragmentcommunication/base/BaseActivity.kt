package com.lt.activityandfragmentcommunication.base

import android.support.v7.app.AppCompatActivity
import com.lt.frame.Function
import com.lt.frame.manager.FunctionManager

/**
 * Created by L.T on 2018/3/22.
 * qq:474297694
 * 功能: BaseActivity
 */

open class BaseActivity : AppCompatActivity() {


    fun setFragmentFunction(fragment: BaseFragment) {
        //fragment需要bind的FunctionNames
        val bindFunctionNames = fragment.bindFunctionNames()
        //获得当前需要绑定的fragment的function
        bindFunctions()?.let {
            val bindFunction = it.filter { function ->
                bindFunctionNames?.find { function.funcName.equals(it) } != null
            }
            FunctionManager.addFunc(*bindFunction.toTypedArray())
        }
    }

    //需要绑定的函数子类实现
    open fun bindFunctions(): Array<Function>? = arrayOf()

}