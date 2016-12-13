package com.lp.ttpod.util; 
 
import android.os.Environment;
import android.util.Log; 
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale; 

/** 717219917@qq.com  2016/12/14 0:13 */
public class L {
    private L() { 
        throw new UnsupportedOperationException("对象不能被初始化");
    }

    private static final String TAG = "L";

    /*** log是否需要打印 */
    public static boolean isDebug = true;

    /*** log.w是否可以写入本地,默认:true可写 */
    public static boolean isWrite = true;// 默认打开 写日志

    /***** 默认目录(内存卡下) */
    public static final String DEFAULT_DIR = "andlp";

    /** App父目录(用默认目录) */
    private static String APP_DIR = DEFAULT_DIR;

    /** 运行日志目录(父目录之下目录) */
    public static final String LOG_DIR = "log";


    public static void i(String msg) {
        if (isDebug) {

            StackTraceElement caller = getCallerStackTraceElement();
            String stack = generateTag(caller);
            log(stack , msg, stack);
        }

    }

    public static void d(String msg) {
        if (isDebug) {
            StackTraceElement caller = getCallerStackTraceElement();
            String stack = generateTag(caller);
            log(stack , msg, stack);
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            StackTraceElement caller = getCallerStackTraceElement();
            String stack = generateTag(caller);
            log(stack, msg, stack);
        }

    }

    public static void v(String msg) {
        if (isDebug) {
            StackTraceElement caller = getCallerStackTraceElement();
            String stack = generateTag(caller);
            log(stack , msg, stack);
            // Log.v(TAG + tag, msg);

        }

    }

    public static void i(String tag, String msg) {
        if (isDebug) {

            StackTraceElement caller = getCallerStackTraceElement();
            String stack = generateTag(caller);
            log(tag, msg, stack);

            // Log.i(tag1 + tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            StackTraceElement caller = getCallerStackTraceElement();
            String stack = generateTag(caller);
            log(tag , msg, stack);
            // Log.i(tag1 + tag, msg);


        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            StackTraceElement caller = getCallerStackTraceElement();
            String stack = generateTag(caller);
            log(tag , msg, stack);
            // Log.i(tag1 + tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            StackTraceElement caller = getCallerStackTraceElement();
            String stack = generateTag(caller);
            log(tag , msg, stack);
        }
    }

    /**栈处理,为定位行数*/
    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s():%d"; // 占位符,类名 方法名 行数
        String callerClazzName = caller.getClassName(); // 获取到类名
//		callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(),
                caller.getLineNumber()); // 替换
        return tag;
    }
    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }


    /***** 设置app父目录 */
    public static void setAppDir(String appDir) {
        APP_DIR = appDir;
    }

    /***** 获取app父目录 */
    public static String getAppDir() {
        return APP_DIR;
    }

    /**  创建父目录下的子目录并返回 路径
     * @param dir 子目录名称
     * @return 子目录路径
     */
    public static String getOrCreateChildDir(String dir) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File f = new File(Environment.getExternalStorageDirectory()
                    + String.format("/%s/%s/", APP_DIR, dir));
            if (f.mkdirs() || f.isDirectory()) {
                return f.getPath();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**  写日志 */
    public static void w(String msg) {
        if (isWrite) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd",
                    Locale.CHINA);
            String logName = String.format("%s/lp_%s.txt",
                    getOrCreateChildDir(LOG_DIR), formatter.format(new Date()));

            w(msg, logName);
        }

    }

    /***** 输出信息 到指定目录中 参数为内容和路径 */
    public static void w(String msg, String fileName) {

        if (isWrite) {
            StringBuilder sb = new StringBuilder();
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "[yyyy-MM-dd HH:mm:ss]  ", Locale.CHINA);

            Date firstDate = new Date(System.currentTimeMillis());
            String str = formatter.format(firstDate);
            sb.append(str);
            sb.append(msg).append("\n");

            try {

                File files = new File(fileName);
                FileOutputStream fileOutputStream = new FileOutputStream(files,
                        true);
                fileOutputStream.write(sb.toString().getBytes());
                fileOutputStream.close();

            } catch (Exception e) {
            }
        }

    }

    /********* 写异常txt 到指定目录中 */
    public static void w(Exception e) {

        if (isWrite) {
            StringWriter writer = new StringWriter();
            PrintWriter pw = new PrintWriter(writer, true);
            e.printStackTrace(pw);
            w(writer.toString());
        }

    }

    public static void log(Throwable ex) {
        if (isWrite) {
            StringWriter writer = new StringWriter();
            PrintWriter pw = new PrintWriter(writer, true);
            ex.printStackTrace(pw);
            w(writer.toString());
        }
    }
    //log4k
    public static void log(String tag, String str,String stack) {
        if (!tag.contains(stack)) {//如果不相同(打印了自定义log),需要先打印堆栈
            Log.i(tag, "栈------------>" + stack);
        } else {
            //  Log.i(tag,"栈------------>");
        }
        int index = 0;  // 当前位置
        int max = 3800; // 需要截取的最大长度,别用4000
        String sub;     // 进行截取操作的string
        while (index < str.length()) {

            if (str.length() < max) {  // 如果长度比最大长度小
                max = str.length();    // 最大长度设为length,全部截取完成.
                sub = str.substring(index, max);
            } else {
                sub = str.substring(index, max);
            }
            Log.i(tag, sub);           // 进行输出
            index = max;
            max += 3800;
        }
    }
}
