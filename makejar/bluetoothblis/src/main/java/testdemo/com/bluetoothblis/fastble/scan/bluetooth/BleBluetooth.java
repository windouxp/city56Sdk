package testdemo.com.bluetoothblis.fastble.scan.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import testdemo.com.bluetoothblis.fastble.scan.conn.BleConnector;
import testdemo.com.bluetoothblis.fastble.scan.conn.BleGattCallback;
import testdemo.com.bluetoothblis.fastble.scan.data.ScanResult;
import testdemo.com.bluetoothblis.fastble.scan.exception.BleException;
import testdemo.com.bluetoothblis.fastble.scan.exception.ConnectException;
import testdemo.com.bluetoothblis.fastble.scan.scan.MacScanCallback;
import testdemo.com.bluetoothblis.fastble.scan.scan.NameScanCallback;
import testdemo.com.bluetoothblis.fastble.scan.scan.PeriodScanCallback;
import testdemo.com.bluetoothblis.fastble.scan.utils.BleLog;


public class BleBluetooth {

    private static final String CONNECT_CALLBACK_KEY = "connect_key";
    public static final String READ_RSSI_KEY = "rssi_key";

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_SCANNING = 1;
    private static final int STATE_CONNECTING = 2;
    private static final int STATE_CONNECTED = 3;
    private static final int STATE_SERVICES_DISCOVERED = 4;

    private int connectionState = STATE_DISCONNECTED;
    private Context context;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothGatt bluetoothGatt;
    private Handler handler = new Handler(Looper.getMainLooper());
    private HashMap<String, BluetoothGattCallback> callbackHashMap = new HashMap<>();
    private PeriodScanCallback periodScanCallback;


    public BleBluetooth(Context context) {
        this.context = context = context.getApplicationContext();
        BluetoothManager bluetoothManager = (BluetoothManager) context
                .getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
    }


    public BleConnector newBleConnector() {
        return new BleConnector(this);
    }


    public boolean isInScanning() {
        return connectionState == STATE_SCANNING;
    }

    public boolean isConnectingOrConnected() {
        return connectionState >= STATE_CONNECTING;
    }

    public boolean isConnected() {
        return connectionState >= STATE_CONNECTED;
    }

    public boolean isServiceDiscovered() {
        return connectionState == STATE_SERVICES_DISCOVERED;
    }


    private void addConnectGattCallback(BleGattCallback callback) {
        callbackHashMap.put(CONNECT_CALLBACK_KEY, callback);
    }

    public void addGattCallback(String uuid, BluetoothGattCallback callback) {
        callbackHashMap.put(uuid, callback);
    }

    public void removeConnectGattCallback() {
        callbackHashMap.remove(CONNECT_CALLBACK_KEY);
    }

    public void removeGattCallback(String key) {
        callbackHashMap.remove(key);
    }

    public void clearCallback() {
        callbackHashMap.clear();
    }

    public BluetoothGattCallback getGattCallback(String uuid) {
        if (TextUtils.isEmpty(uuid))
            return null;
        return callbackHashMap.get(uuid);
    }

    public boolean startLeScan(PeriodScanCallback callback) {
        this.periodScanCallback = callback;
        callback.setBleBluetooth(this).notifyScanStarted();
        boolean success = bluetoothAdapter.startLeScan(callback);
        if (success) {
            connectionState = STATE_SCANNING;
        } else {
            callback.removeHandlerMsg();
        }
        return success;
    }

    public void cancelScan() {
        if (periodScanCallback != null && connectionState == STATE_SCANNING)
            periodScanCallback.notifyScanCancel();
    }

    public void stopScan(BluetoothAdapter.LeScanCallback callback) {
        if (callback instanceof PeriodScanCallback) {
            ((PeriodScanCallback) callback).removeHandlerMsg();
        }
        bluetoothAdapter.stopLeScan(callback);
        if (connectionState == STATE_SCANNING) {
            connectionState = STATE_DISCONNECTED;
        }
    }

    public synchronized BluetoothGatt connect(ScanResult scanResult,
                                              boolean autoConnect,
                                              BleGattCallback callback) {
        BleLog.i("connect name: " + scanResult.getDevice().getName()
                + "\nmac: " + scanResult.getDevice().getAddress()
                + "\nautoConnect: " + autoConnect);
        addConnectGattCallback(callback);
        return scanResult.getDevice().connectGatt(context, autoConnect, coreGattCallback);
    }

    public boolean scanNameAndConnect(String name, long time_out, final boolean autoConnect, final BleGattCallback callback) {
        return scanNameAndConnect(name, time_out, autoConnect, false, callback);
    }

    public boolean scanNameAndConnect(String name, long time_out, final boolean autoConnect, boolean fuzzy, final BleGattCallback callback) {
        if (TextUtils.isEmpty(name)) {
            if (callback != null) {
                callback.onNotFoundDevice();
            }
            return false;
        }
        return startLeScan(new NameScanCallback(name, time_out, fuzzy) {

            @Override
            public void onDeviceFound(final ScanResult scanResult) {
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onFoundDevice(scanResult);
                        }
                        connect(scanResult, autoConnect, callback);
                    }
                });
            }

            @Override
            public void onDeviceNotFound() {
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onNotFoundDevice();
                        }
                    }
                });
            }
        });
    }

    public boolean scanNameAndConnect(String[] names, long time_out, final boolean autoConnect, final BleGattCallback callback) {
        return scanNameAndConnect(names, time_out, autoConnect, false, callback);
    }

    public boolean scanNameAndConnect(String[] names, long time_out, final boolean autoConnect, boolean fuzzy, final BleGattCallback callback) {
        if (names == null || names.length < 1) {
            if (callback != null) {
                callback.onNotFoundDevice();
            }
            return false;
        }
        return startLeScan(new NameScanCallback(names, time_out, fuzzy) {

            @Override
            public void onDeviceFound(final ScanResult scanResult) {
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onFoundDevice(scanResult);
                        }
                        connect(scanResult, autoConnect, callback);
                    }
                });
            }

            @Override
            public void onDeviceNotFound() {
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onNotFoundDevice();
                        }
                    }
                });
            }
        });
    }

    public boolean scanMacAndConnect(String mac, long time_out, final boolean autoConnect, final BleGattCallback callback) {
        if (TextUtils.isEmpty(mac)) {
            if (callback != null) {
                callback.onNotFoundDevice();
            }
            return false;
        }
        return startLeScan(new MacScanCallback(mac, time_out) {

            @Override
            public void onDeviceFound(final ScanResult scanResult) {
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onFoundDevice(scanResult);
                        }
                        connect(scanResult, autoConnect, callback);
                    }
                });
            }

            @Override
            public void onDeviceNotFound() {
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onNotFoundDevice();
                        }
                    }
                });
            }
        });
    }

    public boolean refreshDeviceCache() {
        try {
            final Method refresh = BluetoothGatt.class.getMethod("refresh");
            if (refresh != null) {
                final boolean success = (Boolean) refresh.invoke(getBluetoothGatt());
                BleLog.i("refreshDeviceCache, is success:  " + success);
                return success;
            }
        } catch (Exception e) {
            BleLog.i("exception occur while refreshing device: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public void closeBluetoothGatt() {
        if (bluetoothGatt != null) {
            bluetoothGatt.disconnect();
        }

        if (bluetoothGatt != null) {
            refreshDeviceCache();
        }

        if (bluetoothGatt != null) {
            bluetoothGatt.close();
        }
    }

    public void enableBluetoothIfDisabled() {
        if (!isBlueEnable()) {
            enableBluetooth();
        }
    }

    public boolean isBlueEnable() {
        return bluetoothAdapter.isEnabled();
    }

    public void enableBluetooth() {
        bluetoothAdapter.enable();
    }

    public void disableBluetooth() {
        bluetoothAdapter.disable();
    }

    private void runOnMainThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            handler.post(runnable);
        }
    }

    public Context getContext() {
        return context;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    public BluetoothGatt getBluetoothGatt() {
        return bluetoothGatt;
    }

    public int getConnectionState() {
        return connectionState;
    }

    private BleGattCallback coreGattCallback = new BleGattCallback() {

        @Override
        public void onNotFoundDevice() {
            BleLog.i("BleGattCallback：onNotFoundDevice ");
        }

        @Override
        public void onFoundDevice(ScanResult scanResult) {
            BleLog.i("BleGattCallback：onFoundDevice ");
        }

        @Override
        public void onConnectSuccess(BluetoothGatt gatt, int status) {
            BleLog.i("BleGattCallback：onConnectSuccess ");

            bluetoothGatt = gatt;
            Iterator iterator = callbackHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object call = entry.getValue();
                if (call instanceof BleGattCallback) {
                    ((BleGattCallback) call).onConnectSuccess(gatt, status);
                }
            }
        }

        @Override
        public void onConnectFailure(BleException exception) {
            BleLog.i("BleGattCallback：onConnectFailure ");

            closeBluetoothGatt();
            bluetoothGatt = null;
            Iterator iterator = callbackHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object call = entry.getValue();
                if (call instanceof BleGattCallback) {
                    ((BleGattCallback) call).onConnectFailure(exception);
                }
            }
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            BleLog.i("BleGattCallback：onConnectionStateChange "
                    + '\n' + "status: " + status
                    + '\n' + "newState: " + newState
                    + '\n' + "thread: " + Thread.currentThread().getId());

            if (newState == BluetoothGatt.STATE_CONNECTED) {
                connectionState = STATE_CONNECTED;
                onConnectSuccess(gatt, status);

            } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                connectionState = STATE_DISCONNECTED;
                onConnectFailure(new ConnectException(gatt, status));

            } else if (newState == BluetoothGatt.STATE_CONNECTING) {
                connectionState = STATE_CONNECTING;
            }

            Iterator iterator = callbackHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object call = entry.getValue();
                if (call instanceof BluetoothGattCallback) {
                    ((BluetoothGattCallback) call).onConnectionStateChange(gatt, status, newState);
                }
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            BleLog.i("BleGattCallback：onServicesDiscovered ");

            connectionState = STATE_SERVICES_DISCOVERED;
            Iterator iterator = callbackHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object call = entry.getValue();
                if (call instanceof BluetoothGattCallback) {
                    ((BluetoothGattCallback) call).onServicesDiscovered(gatt, status);
                }
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            BleLog.i("BleGattCallback：onCharacteristicRead ");

            Iterator iterator = callbackHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object call = entry.getValue();
                if (call instanceof BluetoothGattCallback) {
                    ((BluetoothGattCallback) call).onCharacteristicRead(gatt, characteristic, status);
                }
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            BleLog.i("BleGattCallback：onCharacteristicWrite ");

            Iterator iterator = callbackHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object call = entry.getValue();
                if (call instanceof BluetoothGattCallback) {
                    ((BluetoothGattCallback) call).onCharacteristicWrite(gatt, characteristic, status);
                }
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            BleLog.i("BleGattCallback：onCharacteristicChanged ");

            Iterator iterator = callbackHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object call = entry.getValue();
                if (call instanceof BluetoothGattCallback) {
                    ((BluetoothGattCallback) call).onCharacteristicChanged(gatt, characteristic);
                }
            }
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            BleLog.i("BleGattCallback：onDescriptorRead ");

            Iterator iterator = callbackHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object call = entry.getValue();
                if (call instanceof BluetoothGattCallback) {
                    ((BluetoothGattCallback) call).onDescriptorRead(gatt, descriptor, status);
                }
            }
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            BleLog.i("BleGattCallback：onDescriptorWrite ");

            Iterator iterator = callbackHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object call = entry.getValue();
                if (call instanceof BluetoothGattCallback) {
                    ((BluetoothGattCallback) call).onDescriptorWrite(gatt, descriptor, status);
                }
            }
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            BleLog.i("BleGattCallback：onReliableWriteCompleted ");

            Iterator iterator = callbackHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object call = entry.getValue();
                if (call instanceof BluetoothGattCallback) {
                    ((BluetoothGattCallback) call).onReliableWriteCompleted(gatt, status);
                }
            }
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            BleLog.i("BleGattCallback：onReadRemoteRssi ");

            Iterator iterator = callbackHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object call = entry.getValue();
                if (call instanceof BluetoothGattCallback) {
                    ((BluetoothGattCallback) call).onReadRemoteRssi(gatt, rssi, status);
                }
            }
        }
    };
}
