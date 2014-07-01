package zhou.floatlayout;

import android.util.Log;

/**
 * 日志类 说明: 对新版的启用多参数的方式
 * <br><br>
 * <h1>方法v,d,i,w,e: </h1>
 * <p>
 * <h2>主要参数 message...:</h2>
 * 可以为null. 会以所在类名为TAG,方法名为Message.<br>
 * 例:Logger.d();Logger.d(null);
 * <br>
 * 传入一个参数使用默认TAG,<br>
 * 传入2个参数第一个为TAG,第二个为Message<br>
 * 传入多个参数的话则 以 msg[1]=msg[2],msg[3]=msg[4]的格式输出<br>
 * </p>
 * <br><br>
 * <h1>方法dd,ii,ww,ee: </h1>
 * <p2>
 *     主要使用了string.formater方法.<br>
 *     可以直接使用object类型 不需要{@code String}<br>
 *     例:Logger.dd("rooted: %b,count:%d", rooted,count);
 * </p2>
 * <p>
 *  更多方法 见{@link Logger.L}
 * </p>
 */
public class Logger {

    /** 锁，是否关闭Log日志输出 */
    public static boolean DEBUG = true;
	public static String TAG = "XY";

	public static void Debug(boolean debugOn){
        DEBUG = debugOn;
	}

	public static boolean Debug(){
        return DEBUG;
	}

	/** 新的日志打印工具
     * @param msg
     * <p>
     * 可以为null. 会以所在类名为TAG,方法名为Message.<br>
     * 传入一个参数使用默认TAG,<br>
     * 传入2个参数第一个为TAG,第二个为Message<br>
     * 传入多个参数的话则 以 msg[1]=msg[2],msg[3]=msg[4]的格式输出<br>
     * <p>
     */
    public static void v(String... msg){
        if (DEBUG) {
            if (msg == null || msg.length == 0) {
                L.v(null);
            } else if (msg.length == 2) {
                Log.v(msg[0], msg[1]);
            } else if (msg.length == 1) {
                Log.v(TAG, msg[0]);
            } else{
                StringBuilder sb = new StringBuilder(msg.length * 7);
                sb.append('[');
                for (int i = 1; i < msg.length; i++) {
                    sb.append(msg[i]);
                    if(i%2 ==1) {
                        sb.append(" = ");
                    } else {
                        sb.append(", ");
                    }
                }
                sb.append(']');
                Log.v(msg[0], sb.toString());
            }
        }
    }

    /**
     * 兼容处理
     *
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    /**
     * 兼容处理
     * 
     * @param msg
     */
    public static void d(String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }

    /**
     * 兼容处理
     *
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    /** 新的日志打印工具
     * @param msg
     * <p>
     * 可以为null. 会以所在类名为TAG,方法名为Message.<br>
     * 传入一个参数使用默认TAG,<br>
     * 传入2个参数第一个为TAG,第二个为Message<br>
     * 传入多个参数的话则 以 msg[1]=msg[2],msg[3]=msg[4]的格式输出<br>
     * <p>
     */
    public static void d(String... msg){
        if (DEBUG) {
            if (msg == null || msg.length == 0) {
                L.d(null);
            } else if (msg.length == 2) {
                Log.d(msg[0], msg[1]);
            } else if (msg.length == 1) {
                Log.d(TAG, msg[0]);
            } else {
                StringBuilder sb = new StringBuilder(msg.length * 7);
                sb.append('[');
                for (int i = 1; i < msg.length; i++) {
                    sb.append(msg[i]);
                    if (i % 2 == 1) {
                        sb.append(" = ");
                    } else {
                        sb.append(", ");
                    }
                }
                sb.append(']');
                Log.d(msg[0], sb.toString());
            }
        }
    }

    /** 新的日志打印工具
     * @param msg
     * <p>
     * 可以为null. 会以所在类名为TAG,方法名为Message.<br>
     * 传入一个参数使用默认TAG,<br>
     * 传入2个参数第一个为TAG,第二个为Message<br>
     * 传入多个参数的话则 以 msg[1]=msg[2],msg[3]=msg[4]的格式输出<br>
     * <p>
     */
    public static void i(String... msg){
        if (DEBUG) {
            if (msg == null || msg.length == 0) {
                L.i(null);
            } else if (msg.length == 2) {
                Log.i(msg[0], msg[1]);
            } else if (msg.length == 1) {
                Log.i(TAG, msg[0]);
            } else{
                StringBuilder sb = new StringBuilder(msg.length * 7);
                sb.append('[');
                for (int i = 1; i < msg.length; i++) {
                    sb.append(msg[i]);
                    if(i%2 ==1) {
                        sb.append(" = ");
                    } else {
                        sb.append(", ");
                    }
                }
                sb.append(']');
                Log.i(msg[0], sb.toString());
            }
        }
    }

    /** 新的日志打印工具
     * @param msg
     * <p>
     * 可以为null. 会以所在类名为TAG,方法名为Message.<br>
     * 传入一个参数使用默认TAG,<br>
     * 传入2个参数第一个为TAG,第二个为Message<br>
     * 传入多个参数的话则 以 msg[1]=msg[2],msg[3]=msg[4]的格式输出<br>
     * <p>
     */
    public static void w(String... msg){
        if (DEBUG) {
            if (msg == null || msg.length == 0) {
                L.w(null);
            } else if (msg.length == 2) {
                Log.w(msg[0], msg[1]);
            } else if (msg.length == 1) {
                Log.w(TAG, msg[0]);
            } else{
                StringBuilder sb = new StringBuilder(msg.length * 7);
                sb.append('[');
                for (int i = 1; i < msg.length; i++) {
                    sb.append(msg[i]);
                    if(i%2 ==1) {
                        sb.append(" = ");
                    } else {
                        sb.append(", ");
                    }
                }
                sb.append(']');
                Log.w(msg[0], sb.toString());
            }
        }
    }

    /** 新的日志打印工具
     * @param msg
     * <p>
	 * 可以为null. 会以所在类名为TAG,方法名为Message.<br>
	 * 传入一个参数使用默认TAG,<br>
	 * 传入2个参数第一个为TAG,第二个为Message<br>
	 * 传入多个参数的话则 以 msg[1]=msg[2],msg[3]=msg[4]的格式输出<br>
	 * <p>
	 */
    public static void e(String... msg) {
        if (DEBUG) {
            if (msg == null || msg.length == 0) {
                Logger.ee(null);
            } else if (msg.length == 2) {
                Log.e(msg[0], msg[1]);
            } else if (msg.length == 1) {
                Log.e(TAG, msg[0]);
            } else{
                StringBuilder sb = new StringBuilder(msg.length * 7);
                sb.append('[');
                for (int i = 1; i < msg.length; i++) {
                    sb.append(msg[i]);
                    if(i%2 ==1) {
                        sb.append(" = ");
                    } else {
                        sb.append(", ");
                    }
                }
                sb.append(']');
                Log.e(msg[0], sb.toString());
            }
        }
	}

	public static void dd(Object s1, Object... args) {
        L.log(Log.DEBUG, null, s1, args);
    }

    public static void ii(Object s1, Object... args) {
        L.log(Log.INFO, null, s1, args);
    }

    public static void ww(Object s1, Object... args) {
        L.log(Log.WARN, null, s1, args);
    }

    public static void ee(Throwable t, Object s1, Object... args) {
        L.log(Log.ERROR, t, s1, args);
    }

    /**
     * 使用了formatter的日志打印.
     * @param t 异常
     */
    public static void ee(Throwable t) {
        L.log(Log.ERROR, t, null);
    }


    /**
     * Convenience class for logging. Logs the given parameters plus the calling
     * class and line as a tag and the calling method's name
     */
    public static class L {

        public static void v(Throwable t) {
            log(Log.VERBOSE, t, null);
        }

        public static void v(Object s1, Object... args) {
            log(Log.VERBOSE, null, s1, args);
        }

        public static void v(Throwable t, Object s1, Object... args) {
            log(Log.VERBOSE, t, s1, args);
        }

        public static void d(Throwable t) {
            log(Log.DEBUG, t, null);
        }

        public static void d(Throwable t, Object s1, Object... args) {
            log(Log.DEBUG, t, s1, args);
        }

        public static void i(Throwable t) {
            log(Log.INFO, t, null);
        }

        public static void i(Throwable t, Object s1, Object... args) {
            log(Log.INFO, t, s1, args);
        }

        public static void w(Throwable t) {
            log(Log.WARN, t, null);
        }

        public static void w(Throwable t, Object s1, Object... args) {
            log(Log.WARN, t, s1, args);
        }

        public static void e(Object s1, Object... args) {
            log(Log.ERROR, null, s1, args);
        }

        static void log(final int pType, final Throwable t, final Object s1, final Object... args) {
            if (pType == Log.ERROR || Debug()) {
                StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
                String fullClassName = stackTraceElement.getClassName();
                if("com.yiutil.tools.Logger".endsWith(fullClassName)){
                    stackTraceElement = Thread.currentThread().getStackTrace()[5];
                    fullClassName = stackTraceElement.getClassName();
                }
                final String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
                final int lineNumber = stackTraceElement.getLineNumber();
                final String method = stackTraceElement.getMethodName();

                final String tag = className + ":" + lineNumber;

                final StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(method);
                stringBuilder.append("(): ");

                if (s1 != null) {
                    final String message = (args == null) ? s1.toString() : String.format((String) s1, args);
                    stringBuilder.append(message);
                }

                switch (pType) {
                case Log.VERBOSE:
                    if (t != null) {
                        Log.v(tag, stringBuilder.toString(), t);
                    } else {
                        Log.v(tag, stringBuilder.toString());
                    }
                    break;

                case Log.DEBUG:
                    if (t != null) {
                        Log.d(tag, stringBuilder.toString(), t);
                    } else {
                        Log.d(tag, stringBuilder.toString());
                    }
                    break;

                case Log.INFO:
                    if (t != null) {
                        Log.i(tag, stringBuilder.toString(), t);
                    } else {
                        Log.i(tag, stringBuilder.toString());
                    }
                    break;

                case Log.WARN:
                    if (t != null) {
                        Log.w(tag, stringBuilder.toString(), t);
                    } else {
                        Log.w(tag, stringBuilder.toString());
                    }
                    break;

                case Log.ERROR:
                    if (t != null) {
                        Log.e(tag, stringBuilder.toString(), t);
                    } else {
                        Log.e(tag, stringBuilder.toString());
                    }
                    break;
                }
            }
        }
    }
}