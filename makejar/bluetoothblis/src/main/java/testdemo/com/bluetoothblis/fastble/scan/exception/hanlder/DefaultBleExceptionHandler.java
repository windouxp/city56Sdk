package testdemo.com.bluetoothblis.fastble.scan.exception.hanlder;

import android.content.Context;
import android.util.Log;

import testdemo.com.bluetoothblis.fastble.scan.exception.ConnectException;
import testdemo.com.bluetoothblis.fastble.scan.exception.GattException;
import testdemo.com.bluetoothblis.fastble.scan.exception.InitiatedException;
import testdemo.com.bluetoothblis.fastble.scan.exception.OtherException;
import testdemo.com.bluetoothblis.fastble.scan.exception.TimeoutException;

public class DefaultBleExceptionHandler extends BleExceptionHandler {

    private static final String TAG = "BleExceptionHandler";
    private Context context;

    public DefaultBleExceptionHandler(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    protected void onConnectException(ConnectException e) {
        Log.e(TAG, e.getDescription());
    }

    @Override
    protected void onGattException(GattException e) {
        Log.e(TAG, e.getDescription());
    }

    @Override
    protected void onTimeoutException(TimeoutException e) {
        Log.e(TAG, e.getDescription());
    }

    @Override
    protected void onInitiatedException(InitiatedException e) {
        Log.e(TAG, e.getDescription());
    }

    @Override
    protected void onOtherException(OtherException e) {
        Log.e(TAG, e.getDescription());
    }
}
