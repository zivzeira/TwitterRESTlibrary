package twitter.base.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Sergii Zhuk
 *         Date: 25.06.2014
 *         Time: 0:09
 */
public final class APIProperties {


	private static final String GENERAL_PREFS_ID = "general_prefs";
	private static final String ACCESS_TOKEN = "access_token";



	private static SharedPreferences getPrefs(Context context) {
		return context.getSharedPreferences(GENERAL_PREFS_ID, Context.MODE_PRIVATE);
	}

	public static void setAccessToken(Context context, String token) {
		getPrefs(context).edit().putString(ACCESS_TOKEN, token).apply();
	}

	public static String getAccessToken(Context context) {
		return getPrefs(context).getString(ACCESS_TOKEN, null);
	}

}
