package testdemo.com.bluetoothblis;

import android.util.Log;




/**
 * Created by chenzhuo on 2017/12/13.
 */

public class Application extends android.app.Application {
    String TAG = "Application";
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate: ");


    }
}
