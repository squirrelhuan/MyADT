package com.huan.myadt.test.compiler;

/*import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;*/


public class Test {
	public static String main() throws Exception {
       /* //Java 源代码
        String sourceStr = "public class Hello{public String sayHello (String name){return \"Hello,\" + name + \"!\";}}";
        sourceStr ="System.out.println(\"ni hao\");";
        // 类名及文件名
        String clsName = "Hello";
        // 方法名
        String methodName = "sayHello";
        String result = "空" ;
        // 当前编译器
        JavaCompiler cmp = ToolProvider.getSystemJavaCompiler();
        if(cmp==null){
        	System.out.println("null");
        }else{
        	System.out.println("111");
        }
        //Java 标准文件管理器
        StandardJavaFileManager fm = cmp.getStandardFileManager(null,null,null);
        //Java 文件对象
        JavaFileObject jfo = new StringJavaObject(clsName,sourceStr);
        // 编译参数，类似于javac <options> 中的options
        List<String> optionsList = new ArrayList<String>();
        // 编译文件的存放地方，注意：此处是为Eclipse 工具特设的
        optionsList.addAll(Arrays.asList("-d","./bin"));
        // 要编译的单元
        List<JavaFileObject> jfos = Arrays.asList(jfo);
        // 设置编译环境
        JavaCompiler.CompilationTask task = cmp.getTask(null, fm, null,optionsList,null,jfos);
        // 编译成功
        if(task.call()){
            // 生成对象
            Object obj = Class.forName(clsName).newInstance();
            Class<? extends Object> cls = obj.getClass();
            // 调用sayHello 方法
            Method m = cls.getMethod(methodName, String.class);
            result = (String) m.invoke(obj, "Dynamic Compilation");
            System.out.println(result);
        }
		return result;*/
        return "";
    }
}
