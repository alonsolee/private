package core.log;

import android.util.Log;

public class LLog {

	public static boolean debugEnable = true;

	public static void d(String tag, String msg) {
		if (debugEnable) {
			Log.d(tag, "[ " + msg + " ]");
			;
		}
	}

	public static void w(String tag, String msg) {
		if (debugEnable) {
			Log.w(tag, "[ " + msg + " ]");
			;
		}
	}

	public static void e(String tag, String msg) {
		if (debugEnable) {
			Log.e(tag, "[ " + msg + " ]");
		}
	}

	public static void i(String tag, String msg) {
		if (debugEnable) {
			Log.i(tag, "[ " + msg + " ]");
		}
	}

}
