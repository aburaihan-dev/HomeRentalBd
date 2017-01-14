package io.github.arsrabon.m.homerentalbd;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by msrabon on 1/14/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(getBaseContext());
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}
