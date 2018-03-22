# ActivityAndFragmentCommunication
简单的Activity与Fragment通信小demo

废弃传统的定义接口实现接口的方式降低耦合便于维护
具体实现在library中

使用方式:
添加library依赖
```
allprojects {
	repositories {
		...
		maven { url 'https://www.jitpack.io' }
	}
}
```
```
dependencies {
        compile 'com.github.LT474297694:ActivityAndFragmentCommunication:v1.0.0'
}
```

1.在BaseFragment和BaseActivity中添加部分代码
```kotlin
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
```

2.在Fragment中定义函数名称 并添加需要绑定的函数名称

```kotlin
class SampleFragment : BaseFragment() {
    //定义接口名称 和我们平时使用接口定义接口一个意思
    companion object {
        val functionName1 = javaClass.name + "function1"
        val functionName2 = javaClass.name + "function2"
        val functionName3 = javaClass.name + "function3"
        val functionName4 = javaClass.name + "function4"
    }
    //当前Framgent需要绑定的接口名称
    override fun bindFunctionNames() =
            arrayOf(functionName1, functionName2, functionName3, functionName4)
```
3.在Activity中重写bindFunctions() 添加对应的函数实现(同时传入函数名称作为标识)
```
    override fun bindFunctions() =
            arrayOf(object : FunctionNoParameterNoResult(SampleFragment.functionName1) {
                override fun function() {
                    Toast.makeText(this@MainActivity, "无参无返回值", Toast.LENGTH_LONG).show()
                }
            }, object : FuncitonOnlyParameter<String>(SampleFragment.functionName2) {
                override fun function(t: String?) {
                    Toast.makeText(this@MainActivity, "有参无返回值:$t", Toast.LENGTH_LONG).show()
                }
            }, object : FuncitonOnlyResult<String>(SampleFragment.functionName3) {

                override fun function(): String? = "Activity给你的数据"

            }, object : FunctionHaveParameterHaveResult<String, String>(SampleFragment.functionName4) {
                override fun function(t: String?): String? {
                    Toast.makeText(this@MainActivity, "有参有返回值:$t", Toast.LENGTH_SHORT).show()
                    return "Activity给你的数据"
                }
            })
```
4.在Fragment中调用对应函数实现通信
```
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
```