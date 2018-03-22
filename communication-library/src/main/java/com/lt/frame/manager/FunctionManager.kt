package com.lt.frame.manager

import com.lt.frame.Function
import com.lt.frame.FunctionNoParameterNoResult
import com.lt.frame.exception.FunctionException
import com.lt.frame.function.FuncitonOnlyResult
import com.lt.frame.function.FunctionHaveParameterHaveResult
import com.lt.frame.function.FunctionOnlyParameter
import java.util.*

/**
 * Created by L.T on 2018/3/22.
 * qq:474297694
 * 功能: FunctionManager 设置获取对应Function
 */
object FunctionManager {

    private val M_FUNCTION_ONLY_PARAMETER: HashMap<String?, FunctionOnlyParameter<Any?>>
            by lazy { HashMap<String?, FunctionOnlyParameter<Any?>>() }
    private val M_FUNCTION_ONLY_RESULT: HashMap<String?, FunctionOnlyResult<*>>
            by lazy { HashMap<String?, FunctionOnlyResult<*>>() }
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
                is FunctionOnlyResult<*> -> M_FUNCTION_ONLY_RESULT.put(it.funcName, it)
                is FunctionOnlyParameter<*> -> M_FUNCTION_ONLY_PARAMETER.put(it.funcName, it as FunctionOnlyParameter<Any?>)
                is FunctionHaveParameterHaveResult<*, *> -> mFunctionHaveParameterHaveResult.put(it.funcName, it as FunctionHaveParameterHaveResult<Any?, *>)
                is FunctionNoParameterNoResult -> mFunctionNoParameterNoResult.put(it.funcName, it)
            }
        }
        return this
    }


    fun invokeFunc(functionName: String?) {
        if (functionName.isNullOrEmpty()) return
        val noParameterNoResultFunc = mFunctionNoParameterNoResult[functionName] ?:
                throw FunctionException("Not Find This Function:$functionName")
        noParameterNoResultFunc.function()
    }

    fun <T> invokeFunc(functionName: String?, resultClass: Class<T>?): T? {
        if (functionName.isNullOrEmpty()) return null
        val onlyResultFunc = M_FUNCTION_ONLY_RESULT[functionName] ?:
                throw FunctionException("Not Find This Function:$functionName")

        return if (resultClass != null) {
            resultClass.cast(onlyResultFunc.function())
        } else {
            onlyResultFunc.function() as T
        }
    }

    fun <T> invokeFunc(functionName: String?, data: T?) {
        if (functionName.isNullOrEmpty()) return
        val onlyParameterFunc = M_FUNCTION_ONLY_PARAMETER[functionName] ?:
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