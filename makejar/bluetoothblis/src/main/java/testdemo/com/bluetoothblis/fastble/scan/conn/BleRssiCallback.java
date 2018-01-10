package testdemo.com.bluetoothblis.fastble.scan.conn;


public abstract class BleRssiCallback extends BleCallback {
    public abstract void onSuccess(int rssi);
}