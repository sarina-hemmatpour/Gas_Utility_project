package com.example.gasutilityproject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.example.gasutilityproject.StaticFields.ShPKey;
import com.example.gasutilityproject.system.uiTools.Theme;

public class AppLoader extends Application {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static int diameter;
    public static int widthScreen;
    public static int heightScreen;
    public static int mySDK = Build.VERSION.SDK_INT;

    public static Handler handler;

    public static Typeface regularTypeface;
    public static Typeface lightTypeface;
    public static Typeface mediumTypeface;

//    public static SQL sql;
    public static String account = "";
    private static Context context;

    public static Context getAppContext() {
        return AppLoader.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppLoader.context = getApplicationContext();
        sharedPreferences = AppLoader.getAppContext().getSharedPreferences(ShPKey.MAIN_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        account = AppLoader.sharedPreferences.getString(ShPKey.ACCOUNT, "");
        handler = new Handler(Looper.getMainLooper());
        Theme.getScreenPX();

        lightTypeface = Typeface.createFromAsset(AppLoader.getAppContext().getAssets(), "iransans_light.ttf");
        regularTypeface = Typeface.createFromAsset(AppLoader.getAppContext().getAssets(), "dana_regular.ttf");
        mediumTypeface = Typeface.createFromAsset(AppLoader.getAppContext().getAssets(), "dana_medium.ttf");

    }
}
