package twitter.base.utils;

import android.util.Log;

public class MyLogger {

	public static final String ORGANIC = "zivtwitt";
	public static boolean debug = true;


	public static void info(Class clazz, String msg) {
		if (debug) Log.i(ORGANIC ,clazz.getName() +": " + msg);
	}
	
	public static void info(Object obj, String msg) {
		if (debug) Log.i(ORGANIC ,obj.getClass().getName() +": " + msg);
	}
	
	public static void warning(Object obj, String msg) {
		if (debug) Log.i(ORGANIC ,obj.getClass().getName() +": " + msg);
	}
	
	public static void warning(Object obj, String msg, Throwable e) {
		if (debug) Log.e(ORGANIC ,obj.getClass().getName() +": " + msg + " -Error Message:"+e.getMessage());
	}
	
	public static void error(Object obj, String msg, Throwable e) {
		if (debug) Log.e(ORGANIC ,obj.getClass().getName() +": " + msg + " -Error Message:"+e.getMessage(),e);
	}
	
	public static void error(Class clazz, String msg, Throwable e) {
		if (debug) Log.e(ORGANIC ,clazz.getName() +": " + msg + " -Error Message:"+e.getMessage(),e);
	}
	
	
	public static void debug(String msg) {
		if (debug) Log.d(ORGANIC, msg );
	}

}
