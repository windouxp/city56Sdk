
package testdemo.com.bluetoothblis.fastble.scan.conn;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;

import testdemo.com.bluetoothblis.fastble.scan.data.ScanResult;
import testdemo.com.bluetoothblis.fastble.scan.exception.BleException;


public abstract class BleGattCallback extends BluetoothGattCallback {

    public abstract void onNotFoundDevice();

    public abstract void onFoundDevice(ScanResult scanResult);

    public abstract void onConnectSuccess(BluetoothGatt gatt, int status);

    public abstract void onConnectFailure(BleException exception);

}