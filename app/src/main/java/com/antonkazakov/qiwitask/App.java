package com.antonkazakov.qiwitask;

import android.app.Application;
import android.content.Context;

/**
 * @author Anton Kazakov
 * @date 31.07.17.
 */

public class App extends Application {

    /**
     * Application context
     */
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
