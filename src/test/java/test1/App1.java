package test1;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class App1 {

    public static byte[] readFileByBytes(String fileName) throws IOException {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(fileName));
            out = new ByteArrayOutputStream();
            byte[] tempbytes = new byte[in.available()];
            for (int i = 0; (i = in.read(tempbytes)) != -1;) {
                out.write(tempbytes, 0, i);
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return out.toByteArray();
    }
    public static void main(String[] args) {
        // Java执行JavaScript脚本的步骤：
        // 第一步，创建ScriptEngineManager对象
        // 第二步，获取ScriptEngine对象
        // 第三步，ScriptEngine对象调用eval方法执行脚本

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByName("javascript");
        try {


//             2.读取外部脚本执行
//            engine.eval(new FileReader("https://tonejs.github.io/Midi/"));
            System.out.println("===============================================");
//            FileReader fileReader = new FileReader("src\\test\\resources\\html\\midi.html");
            String content = new String(Files.readAllBytes(Paths.get("src\\test\\resources\\html\\midi.html")));
            System.out.println(content);

            engine.eval(content);
            System.out.println("===============================================");


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }
}
