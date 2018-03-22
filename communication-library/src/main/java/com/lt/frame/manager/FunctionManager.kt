package com.lt.frame.manager

import com.lt.frame.Function
import com.lt.frame.FunctionNoParameterNoResult
import com.lt.frame.exception.FunctionException
import com.lt.frame.function.FuncitonOnlyParameter
import com.lt.frame.function.FuncitonOnlyResult
import com.lt.frame.function.FunctionHaveParameterHaveResult

/**
 * Created by L.T on 2018/3/22.
 * qq:474297694
 * 功能: FunctionManager 设置获取对应Function
 */
object FunctionManager {

    private val mFuncitonOnlyParameter: HashMap<String?, FuncitonOnlyParameter<Any?>>
            by lazy { HashMap<String?, FuncitonOnlyParameter<Any?>>() }
    private val mFuncitonOnlyResult: HashMap<String?, FuncitonOnlyResult<*>>
            by lazy { HashMap<String?, FuncitonOnlyResult<*>>() }
    private val mFunctionHaveParameterHaveResult: HashMap<String?, FunctionHaveParameterHaveResult<Any?, *>>
            by lazy { HashMap<String?, FunctionHaveParameterHaveResult<Any?, *>>() }
    private val mFunctionNoParameterNoResult: HashMap<String?, FunctionNoParameterNoResult>
            by lazy { HashMap<String?, FunctionNoParameterNoResult>() }

    fun addFunc(vararg function: Function?): FunctionManager {
        function.forEach {
            if (it == null)
                throw FunctionException("function isNull")
            if (it.funcName.isNullOrEmpty())
                throw FunctionException("function name isNullOrEmpty")
            when (it) {
                is FuncitonOnlyResult<*> -> mFuncitonOnlyResult.put(it.funcName, it)
                is FuncitonOnlyParameter<*> -> mFuncitonOnlyParameter.put(it.funcName, it as FuncitonOnlyParameter<Any?>)
                is FunctionHaveParameterHaveResult<*, *> -> mFunctionHaveParameterHaveResult.put(it.funcName, it as FunctionHaveParameterHaveResult<Any?, *>)
                is FunctionNoParameterNoResult -> mFunctionNoParameterNoResult.put(it.funcName, it)
            }
        }
        return this
    }



    fun invokeFunc(functionName: String?) {
        if (functionName.isNullOrEmpty()) return
        val noParameterNoResultFunc = mFunctionNoParameterNoResult[functionName]
        noParameterNoResultFunc?.apply { function() }
    }

    fun <T> invokeFunc(functionName: String?, resultClass: Class<T>?): T? {
        if (functionName.isNullOrEmpty()) return null
        val onlyResultFunc = mFuncitonOnlyResult[functionName] ?:
                throw FunctionException("Not Find This Function:$functionName")

        return if (resultClass != null) {
            resultClass.cast(onlyResultFunc.function())
        } else {
            onlyResultFunc.function() as T
        }
    }

    fun <T> invokeFunc(functionName: String?, data: T?) {
        if (functionName.isNullOrEmpty()) return
        val onlyParameterFunc = mFuncitonOnlyParameter[functionName] ?:
                throw FunctionException("Not Find This Function:$functionName")
        onlyParameterFunc.function(data)
    }

    fun <T, R> invokeFunc(functionName: String?, data: T?, resultClass: Class<R>?): R? {
        if (functionName.isNullOrEmpty()) return null
        val haveParameterHaveResultFunc = mFunctionHaveParameterHaveResult[functionName] ?:
                throw FunctionException("Not Find This Function:$functionName")

        return if (resultClass != null) {
            resultClass.cast(haveParameterHaveResultFunc.function(data))
        } else {
            haveParameterHaveResultFunc.function(data) as R
        }
    }
}