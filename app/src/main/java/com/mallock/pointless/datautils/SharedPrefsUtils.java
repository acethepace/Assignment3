package com.mallock.pointless.datautils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Utility class for Shared Preferences
 * Created by Tiny on 3/10/2016.
 */
public class SharedPrefsUtils {

    public static String getStringDataByKey(Context context, String key) {
        final SharedPreferences preferences = context.getSharedPreferences("trakrPrefs",
                Context.MODE_PRIVATE);

        return preferences.getString(key, null);
    }

    public static boolean getBooleanDataByKey(Context context, String key) {
        return getBooleanDataByKey(context, key, false);
    }

    public static boolean getBooleanDataByKey(Context context, String key, boolean defaultValue) {
        final SharedPreferences preferences = context.getSharedPreferences("trakrPrefs",
                Context.MODE_PRIVATE);

        return preferences.getBoolean(key, defaultValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        final SharedPreferences.Editor editor = context.getSharedPreferences("trakrPrefs",
                Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void putString(Context context, String key, String value) {
        final SharedPreferences.Editor editor = context.getSharedPreferences("trakrPrefs",
                Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void putInt(Context context, String key, Integer value) {
        final SharedPreferences.Editor editor = context.getSharedPreferences("trakrPrefs",
                Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void putLong(Context context, String key, Long value) {
        final SharedPreferences.Editor editor = context.getSharedPreferences("trakrPrefs",
                Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.apply();
    }
    public static void putFloat(Context context, String key, Float value) {
        final SharedPreferences.Editor editor = context.getSharedPreferences("trakrPrefs",
                Context.MODE_PRIVATE).edit();
        editor.putFloat(key, value);
        editor.apply();
    }

}
