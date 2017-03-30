package zhang.snapply.speechtest.Util;

import android.app.Application;
import android.content.Context;

/**
 * Created by Snapply Zhang on 2017/3/28.
 */

public class MainContext extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
