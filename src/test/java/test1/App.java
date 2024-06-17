package test1;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // Java执行JavaScript脚本的步骤：
        // 第一步，创建ScriptEngineManager对象
        // 第二步，获取ScriptEngine对象
        // 第三步，ScriptEngine对象调用eval方法执行脚本

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
        try {
            // 1.直接执行脚本
            // 1.直接执行脚本
            String s = "color > 10 && color < 20";
            s = s.replace("color","9");
            engine.eval("print("+s+");");
            System.out.println("===============================================");

//             2.读取外部脚本执行
//            engine.eval(new FileReader("C:\\Users\\65708\\Desktop\\config.js"));
            engine.eval(new FileReader("https://tonejs.github.io/Midi/"));
            System.out.println("===============================================");

            // 3.将Java对象传递到JavaScript引擎中,js可以调用Java对象的方法
            List<String> list = Arrays.asList("element1", "element2", "element3");
            engine.put("javaList", list);
            String script = "print('the size of javaList is ' + javaList.size());\n" +
                    "print('javaList elements:');" +
                    "for (i in javaList) {\n" +
                    "print(javaList[i]);\n" +
                    "}";
            engine.eval(script);
            System.out.println("===============================================");

            // 4.Java调用JavaScript中的方法或JavaScript对象的方法
            String scriptHasMethod = "function sayHello(name){\n" +
                    "print('this is a global function.hello, '+name)\n" +
                    "}\n" +
                    "var testObject = new Object();\n" +
                    "testObject.testMethod = function (param) {\n" +
                    "print('this is a method of a object, the input param is '+param)\n" +
                    "}";
            engine.eval(scriptHasMethod);

            // 调用JavaScript中的方法
            Invocable env = (Invocable) engine;
            // 返回的结果
            Object result = env.invokeFunction("sayHello", "syseal");
            System.out.println("===============================================");

            // 调用JavaScript对象的方法
            // 1 获取JavaScript对象
            Object testObject = engine.get("testObject");
            // 2 调用对象的方法
            env.invokeMethod(testObject, "testMethod", "JavaCode");

        } catch (ScriptException  | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
