package testdemo.com.bluetoothblis;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import testdemo.com.bluetoothblis.fastble.scan.BleManager;
import testdemo.com.bluetoothblis.fastble.scan.conn.BleCharacterCallback;
import testdemo.com.bluetoothblis.fastble.scan.conn.BleGattCallback;
import testdemo.com.bluetoothblis.fastble.scan.data.ScanResult;
import testdemo.com.bluetoothblis.fastble.scan.exception.BleException;
import testdemo.com.bluetoothblis.fastble.scan.scan.ListScanCallback;


/**
 * Created by chenzhuo on 2017/11/7.
 */

public class SendOrderNumForBindHelper {
    String TAG = "SendOrderNumForBindHelper";
    private Context c;
    private BluetoothResultListener bluetoothResult;
    private static final String seuiec = "0000ffe0-0000-1000-8000-00805f9b34fb";
    private static final String t1111 = "0000ffe1-0000-1000-8000-00805f9b34fb";
    private static final String t2222 = "0000fxe1-0100-1031-8061-00805f7b34fb";
    private static final String t3333 = "0000ffe1-0000-1000-8000-00805f1b34fb";
    private static final String t4444 = "0000fxc1-0000-1030-8200-00805f9b34fb";
    private static final String t5555 = "0000fhe1-0020-1300-8060-00805g5b34fb";
    private static final String t6666 = "0000xxe1-0030-1010-8100-00805f5b34fb";
    private static final String t7777= "0000bve1-0000-1000-8000-00805f9b34fb";
    private static final String t8888 = "0000fxg1-0020-1000-8000-00805f9b34fb";
    private static final String t9999 = "0000fst1-0020-1010-8000-00805f9b34fb";
    private List<String> list1 = new ArrayList<String>();
    private List<OrderBind.ListBoxesBean> mParamsList = new ArrayList<OrderBind.ListBoxesBean>();
    private OrderBind mOb = new OrderBind();
    private BindResult mResult = new BindResult();
    private List<DeviceSerch.DataBean> mBoxList = new ArrayList<DeviceSerch.DataBean>();
    BleManager bleManager;
    DeviceSerch.DataBean dataBean;
    private BluetoothAdapter mBluetoothAdapter = null;
    private StringBuilder stringBuilder = new StringBuilder();
    private String ordernum;

    public SendOrderNumForBindHelper(Context c, BluetoothResultListener bluetoothResult,   String ordernum) {
        this.c = c;
        this.bluetoothResult = bluetoothResult;


        if(ordernum.length()!=13 &&ordernum.length()!=12 ){

            Toast.makeText(c,"运单号必须为13位",Toast.LENGTH_LONG).show();
            return;
        }
//        try {
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append(int2String(ordernum.substring(0, 1)));
//            stringBuilder.append(int2String(ordernum.substring(1, 3)));
//            stringBuilder.append(int2String(ordernum.substring(3, 5)));
//            stringBuilder.append(int2String(ordernum.substring(5, 7)));
//            stringBuilder.append(int2String(ordernum.substring(7, 9)));
//            stringBuilder.append(int2String(ordernum.substring(9, 11)));
//            stringBuilder.append(int2String(ordernum.substring(11, 13)));
//            ordernum = stringBuilder.toString();
//        }catch (Exception e ){
//            e.printStackTrace();
//        }
        Log.d(TAG, "SendOrderNumForBindHelper: ordernum.length()  "+ordernum.length());
        if(ordernum.length()==13) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(int2String(ordernum.substring(0, 1)));
            stringBuilder.append(int2String(ordernum.substring(1, 3)));
            stringBuilder.append(int2String(ordernum.substring(3, 5)));
            stringBuilder.append(int2String(ordernum.substring(5, 7)));
            stringBuilder.append(int2String(ordernum.substring(7, 9)));
            stringBuilder.append(int2String(ordernum.substring(9, 11)));
            stringBuilder.append(int2String(ordernum.substring(11, 13)));
            this.ordernum = stringBuilder.toString();
        }
        if(ordernum.length()==12){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("00");
            stringBuilder.append(int2String(ordernum.substring(0, 2)));
            stringBuilder.append(int2String(ordernum.substring(2, 4)));
            stringBuilder.append(int2String(ordernum.substring(4, 6)));
            stringBuilder.append(int2String(ordernum.substring(6, 8)));
            stringBuilder.append(int2String(ordernum.substring(8, 10)));
            stringBuilder.append(int2String(ordernum.substring(10, 12)));
            this.ordernum  = stringBuilder.toString();
        }


        bleManager = new BleManager(c);
        init();
    }

    private void init() {
//        if (ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions((Activity) c, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                    1);
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) c,
//                    Manifest.permission.READ_CONTACTS)) {
//                Toast.makeText(c, "6.0系统请求搜索蓝牙权限", Toast.LENGTH_SHORT).show();
//            }
//        }
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }
    }

    public void sendOrderNumForBind(final String code) {

        Observable.timer(30, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        bluetoothResult.connecting();
                        bleManager.scanDevice(new ListScanCallback(1000 * 10) {
                            @Override
                            public void onScanning(ScanResult result) {
                                if (result.getDevice().getName() != null && result.getDevice().getName().contains(code)) {
                                    bleManager.cancelScan();
                                    bleManager.connectDevice(result, true, new BleGattCallback() {
                                        @Override
                                        public void onNotFoundDevice() {
                                            bluetoothResult.notFoundDevice();
                                        }

                                        @Override
                                        public void onFoundDevice(ScanResult scanResult) {

                                        }

                                        @Override
                                        public void onConnectSuccess(BluetoothGatt gatt, int status) {
                                            gatt.discoverServices();

                                            Observable.timer(1, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new Action1<Long>() {
                                                        @Override
                                                        public void call(Long aLong) {
                                                            bluetoothResult.connectsuccess();
                                                        }
                                                    });


                                        }

                                        @Override
                                        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                                            super.onServicesDiscovered(gatt, status);
                                            if (status == BluetoothGatt.GATT_SUCCESS) {
                                                BluetoothGattService service = gatt.getService(UUID
                                                        .fromString(seuiec));
                                                if (service == null) {
                                                    bleManager.closeBluetoothGatt();
                                                    bluetoothResult.connectFail("连接失败");
                                                    return;
                                                }
                                                waiteaa77();
                                            }
                                        }

                                        @Override
                                        public void onConnectFailure(BleException exception) {
                                            bluetoothResult.connectFail(exception.toString());
                                        }
                                    });
                                }

                            }

                            @Override
                            public void onScanComplete(ScanResult[] results) {

                            }
                        });

                    }
                });

    }

    private void waiteaa77() {

        Observable.timer(1, TimeUnit.NANOSECONDS).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.notify(seuiec, t1111, new BleCharacterCallback() {

                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        byte[] b = characteristic.getValue();
                        if (ByteUtils.bytes2HexString(b).startsWith("aa77") && ByteUtils.bytes2HexString(b).endsWith("ef")) {

//                            rc6();
//                            write59();

                            rc9();


                        }
                    }

                    @Override
                    public void onFailure(BleException exception) {
                        bluetoothResult.connectFail(exception.toString());
                    }
                });

            }
        });
    }
    void rc9() {

        Observable.timer(1, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.writeDevice(seuiec, t1111, ByteUtils.hexString2Bytes("CC99010102EF"), new BleCharacterCallback() {
                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        waitedd88();
                    }

                    @Override
                    public void onFailure(BleException exception) {

                    }
                });

            }
        });
    }




    void waitedd88() {
        Observable.timer(1, TimeUnit.NANOSECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.notify(seuiec, t1111, new BleCharacterCallback() {
                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        byte[] b = characteristic.getValue();
                        String str = ByteUtils.bytes2HexString(b);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(str);
                        Log.d(TAG, "onSuccess: 监听dd88的 :" + stringBuilder.toString());
                        if (stringBuilder.toString().startsWith("dd88") && stringBuilder.toString().endsWith("ef")) {

                            if (b[3] == 0x01) {
                                stringBuilder.setLength(0);

//                                rc61();
                                write59();
                            } else {

                            }

                        }

                    }

                    @Override
                    public void onFailure(BleException exception) {

                    }
                });

            }
        });


    }

    private void rc6() {

        Observable.timer(1, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        bleManager.writeDevice(seuiec, t1111, ByteUtils.hexString2Bytes("CC66010102EF"), new BleCharacterCallback() {
                            @Override
                            public void onSuccess(BluetoothGattCharacteristic characteristic) {

                            }

                            @Override
                            public void onFailure(BleException exception) {
                                rc7();
                                bluetoothResult.connectFail(exception.toString());
                            }
                        });

                    }
                });
    }
    private void rc7() {

        Observable.timer(1, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        bleManager.writeDevice(seuiec, t1111, ByteUtils.hexString2Bytes("CC77010102EF"), new BleCharacterCallback() {
                            @Override
                            public void onSuccess(BluetoothGattCharacteristic characteristic) {

                            }

                            @Override
                            public void onFailure(BleException exception) {

                                bluetoothResult.connectFail(exception.toString());
                            }
                        });

                    }
                });
    }

  private void write59() {

        Observable.timer(500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.writeDevice(seuiec, t1111, ByteUtils.hexString2Bytes("AA551C1234AB59" + ordernum + "05EF"), new BleCharacterCallback() {
                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        waitedd66();
                    }

                    @Override
                    public void onFailure(BleException exception) {bluetoothResult.connectFail(exception.toString());
                    }
                });

            }
        });

    }


      String time ;
    void waitedd66() {

        Observable.timer(1,TimeUnit.NANOSECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.notify(seuiec, t1111, new BleCharacterCallback() {
                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        byte[] b = characteristic.getValue();
                        if (ByteUtils.bytes2HexString(b).startsWith("dd66") && ByteUtils.bytes2HexString(b).endsWith("ef")) {
                             if (b[3] == 0x01) {

                                String str = ByteUtils.bytes2HexString(b);
                                String year = Integer.parseInt(str.substring(8, 12), 16) + "";
                                String mouth = (Integer.parseInt(str.substring(12, 14), 16) + "").length()==1?"0"+(Integer.parseInt(str.substring(12, 14), 16) + "")
                                        :(Integer.parseInt(str.substring(12, 14), 16) + "");
                                String day = (Integer.parseInt(str.substring(14, 16), 16) + "").length()==1?"0"+(Integer.parseInt(str.substring(14, 16), 16) + "")
                                        :(Integer.parseInt(str.substring(14, 16), 16) + "");

                                String hour = (Integer.parseInt(str.substring(16, 18), 16) + "").length()==1?"0"+(Integer.parseInt(str.substring(16, 18), 16) + "")
                                        :(Integer.parseInt(str.substring(16, 18), 16) + "");

                                String min = (Integer.parseInt(str.substring(18, 20), 16) + "").length()==1?"0"+(Integer.parseInt(str.substring(18, 20), 16) + "")
                                        :(Integer.parseInt(str.substring(18, 20), 16) + "");
                                time = year+mouth+day+hour+min;
                                bluetoothResult.returnBindDate(time);
                                bluetoothResult.onSendOrderForBindSuccess();
                                bleManager.closeBluetoothGatt();

                             } else {

                            }

                        }
                    }

                    @Override
                    public void onFailure(BleException exception) {
                        bluetoothResult.connectFail(exception.toString());

                    }
                });

            }
        });


    }

    private static String int2String(String str) {
        int i = Integer.parseInt(str);

        String hex = Integer.toHexString(i);

        if (hex.length() == 1) {
            hex = "0" + hex;
        }

        return hex;
    }





}
