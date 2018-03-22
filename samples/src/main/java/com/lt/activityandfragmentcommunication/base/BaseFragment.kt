package com.lt.activityandfragmentcommunication.base

import android.content.Context
import android.support.v4.app.Fragment

/**
 * Created by L.T on 2018/3/22.
 * qq:474297694
 * 功能: BaseFragment
 */
open class BaseFragment : Fragment() {
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            context.setFragmentFunction(this)
        }
    }

    //需要绑定的函数名称
    open fun bindFunctionNames(): Array<String>? = arrayOf()
}