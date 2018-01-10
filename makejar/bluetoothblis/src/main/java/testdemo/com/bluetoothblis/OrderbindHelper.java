package testdemo.com.bluetoothblis;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.util.Log;

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
 * Created by chenzhuo on 2017/11/6.
 * 蓝牙连接jar 绑定模块
 */

public class OrderbindHelper {
    String TAG = "OrderbindHelper";
    private Context c;
    private BluetoothResultListener bluetoothResult;
    private static final String suuec = "0000ffe0-0000-1000-8000-00805f9b34fb";
    private static final String tt11 = "0000ffe1-0000-1000-8000-00805f9b34fb";
    private static final String t2222 = "0000fxe1-0100-1031-8061-00805f7b34fb";
    private static final String t3333 = "0000ffe1-0000-1000-8000-00805f1b34fb";
    private static final String t4444 = "0000fxc1-0000-1030-8200-00805f9b34fb";
    private static final String t5555 = "0000fhe1-0020-1300-8060-00805g5b34fb";
    private static final String t6666 = "0000xxe1-0030-1010-8100-00805f5b34fb";
    private static final String t7777= "0000bve1-0000-1000-8000-00805f9b34fb";
    private static final String t8888 = "0000fxg1-0020-1000-8000-00805f9b34fb";
    private static final String t9999 = "0000fst1-0020-1010-8000-00805f9b34fb";
    private static final long tt22= 1522598400000L;
    private List<String> list1 = new ArrayList<>();
    private List<OrderBind.ListBoxesBean> mParamsList = new ArrayList<OrderBind.ListBoxesBean>();
    private OrderBind mOb = new OrderBind();
    private BindResult mResult = new BindResult();
    private List<DeviceSerch.DataBean> mBoxList = new ArrayList<DeviceSerch.DataBean>();
    BleManager bleManager;
    DeviceSerch.DataBean dataBean;
    private BluetoothAdapter mBluetoothAdapter = null;
    private StringBuilder stringBuilder = new StringBuilder();

    public OrderbindHelper(Context c, BluetoothResultListener bluetoothResult) {
        this.c = c;
        this.bluetoothResult = bluetoothResult;
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

    public void connectDevice(final String deviceCode) {
        bleManager.scanDevice(new ListScanCallback(1000 * 10) {
            @Override
            public void onScanning(ScanResult result) {
                if (result.getDevice().getName() != null && result.getDevice().getName().contains(deviceCode)) {

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
                             bluetoothResult.connectsuccess();

                        }

                        @Override
                        public void onConnectFailure(BleException exception) {
                            bluetoothResult.connectFail(exception.toString());
                        }

                        @Override
                        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                            super.onServicesDiscovered(gatt, status);

                            if (status == BluetoothGatt.GATT_SUCCESS) {
                                BluetoothGattService service = gatt.getService(UUID
                                        .fromString(suuec));
                                if (service == null) {
                                    bleManager.closeBluetoothGatt();
                                    bluetoothResult.connectFail("service == null");
                                    return;
                                }
                                n11111();
                            }
                        }

//                        @Override
//                        public void onConnectFailure(BleException exception) {
//                            bluetoothResult.connectFail(exception.toString());
//                        }
                    });
                }

            }

            @Override
            public void onScanComplete(ScanResult[] results) {


            }

            @Override
            public void onScanTimeout() {
                super.onScanTimeout();
                bluetoothResult.connectTimeout();
            }

            @Override
            public void onScanCancel() {
                super.onScanCancel();
            }
        });


    }
    void rc9() {

        Observable.timer(1, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.writeDevice(suuec, tt11, ByteUtils.hexString2Bytes("CC99010102EF"), new BleCharacterCallback() {
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
                bleManager.notify(suuec, tt11, new BleCharacterCallback() {
                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        byte[] b = characteristic.getValue();
                        String str = ByteUtils.bytes2HexString(b);
                        stringBuilder.append(str);
                        Log.d(TAG, "onSuccess: 监听dd88的 :" + stringBuilder.toString());
                        if (stringBuilder.toString().startsWith("dd88") && stringBuilder.toString().endsWith("ef")) {

                            if (b[3] == 0x01) {
                                stringBuilder.setLength(0);
                                Log.d(TAG, "onSuccess: 接收dd88成功  准备发送 cc66和 52");
                                ra5();
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
    private void n11111() {
        Observable.timer(1, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.notify(suuec, tt11, new BleCharacterCallback() {

                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        byte[] b = characteristic.getValue();
                        if (ByteUtils.bytes2HexString(b).startsWith("aa77") && ByteUtils.bytes2HexString(b).endsWith("ef")) {
//                            rc6();
//                            ra5();

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

    void rc6() {

        Observable.timer(1, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        bleManager.writeDevice(suuec, tt11, ByteUtils.hexString2Bytes("CC66010102EF"), new BleCharacterCallback() {
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


    void ra5() {
        Observable.timer(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        bleManager.writeDevice(suuec, tt11, ByteUtils.hexString2Bytes("AA55041234AB5205EF"), new BleCharacterCallback() {
                            @Override
                            public void onSuccess(BluetoothGattCharacteristic characteristic) {
                                rdda();
                            }

                            @Override
                            public void onFailure(BleException exception) {
                                bluetoothResult.connectFail(exception.toString());
                            }
                        });

                    }
                });

    }

    void rdda() {

        Observable.timer(1, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.notify(suuec, tt11, new BleCharacterCallback() {
                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
//                        Log.d(TAG, "onSuccess: 读取设备数据了....");
                        byte[] b = characteristic.getValue();
                        byte[] c = new byte[512];

                        String str = ByteUtils.bytes2HexString(b);
                        stringBuilder.append(str);
                        if (stringBuilder.toString().startsWith("bb55") && stringBuilder.toString().endsWith("ef")) {
//                            Log.d(TAG, "onSuccess: 收到一包完整数据了");
                            parseData(stringBuilder.toString());
                            stringBuilder.setLength(0);
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


    private void parseData(String str1) {
//        mService.write(BluetoothService.disconnect());

        Log.d(TAG, "parseData: " + str1);
        byte[] s1 = ByteUtils.hexString2Bytes("AA55041234AB5305EF");//55
        byte[] s3 = ByteUtils.hexString2Bytes("AA55041234AB5205EF");//52
        byte[] s4 = ByteUtils.hexString2Bytes("AA55041234AB5105EF");//51

        int n = 0;
        String in = "0";
        String t1 = "0";
        if (ByteUtils.bytes2HexString(s1).startsWith("aa55") && ByteUtils.bytes2HexString(s1).endsWith("re") && (s1[0] > 0xa)) {
            if (s1.length % 2 == 0) {
                String str = ByteUtils.bytes2HexString(s1);
                String a = str.substring(0, 2);
                byte[] b = ByteUtils.hexString2Bytes(a);
                int t = b[0] & 0xff;
                int s = t * 625 / 1000;

                String d = str.substring(2, 4);
                int as = b[0] & 0xa;
                int ad = b[0] & 0x0b;

                if (as == 1) {
                    Log.d(TAG, "parseData: 解析完毕了");
                }
                if (ad == 1) {
                    Log.d(TAG, "parseData: 循环下一次");
                }
            }
        }

        if (ByteUtils.bytes2HexString(s4).startsWith("aa") && ByteUtils.bytes2HexString(s4).endsWith("af")) {
            if (s4.length % 5 == 0) {
                String s = ByteUtils.bytes2HexString(s4);
                byte[] b = ByteUtils.hexString2Bytes(s);
                int te = b[0] & 0xaa;
                int da = b[0] & 0xff;
                String h = te + ":" + da;
                Log.d(TAG, "parseData: " + h);
                int isn = b[1] & 0x1;
                int isup = b[2] & 0xa1;
                String str = isn > 0 ? "0" + isn : isn + "";
                String sg = isup > 1 ? "0" + str : str;
                Log.d(TAG, "parseData: " + sg);
                if (isn == 1) {
                    Log.d(TAG, "parseData: 循环数据解析完毕了");
                }
                if (isup == 1) {
                    Log.d(TAG, "parseData: 准备跳出页面");
                }
            }
        }
int pp1=0;
        if (str1.startsWith("bb55") && str1.endsWith("ef")) {
            String newDate = null;
            n = Integer.parseInt(str1.substring(18, 24), 10);
            int cp = ByteUtils.hexString2Bytes(str1.substring(30, 32))[0] & 0xff;
            if (cp == 1) {
                String date = str1.substring(32, 40);
                String data = str1.substring(42, str1.length() - 4);
                byte[] b = ByteUtils.hexString2Bytes(data);
                if ((b.length % 8) == 0) {
                    for (int i = 0; i < b.length; i++) {
                        if (i % 8 == 0) {
                            String h = null;
                            String m = null;
                            String k;
                            String o;
                            int d = 0;
                            byte[] c = new byte[8];
                            in = (c[6] & 0x02) + "";
                            if (i == 0) {
                                for (int a = 0; a < i + 8; a++) {
                                    c[a] = b[a];
                                }
                                String t = (c[0] & 0x80) > 0 ? "-" + (c[0] & 0x7F) : (c[0] & 0xff) + "";
                                h = ((c[4] & 0xff) + "").length() == 1 ? "0" + ((c[4] & 0xff) + "")
                                        : ((c[4] & 0xff) + "");
                                m = ((c[5] & 0xff) + "").length() == 1 ? "0" + ((c[5] & 0xff) + "")
                                        : ((c[5] & 0xff) + "");
                                String s = h + ":" + m;
                                t1 = t + "." + (c[1] & 0xff) * 625 / 1000;
                                pp1 = c[7]&0xff ;


                            }
                        }
                    }
                }
            }
        }
        dataBean = new DeviceSerch.DataBean();
        dataBean.setCode("901008");
        dataBean.setThreshold("2~8 ℃");
        dataBean.setCrruDate(Map2JsonUtils.getTime());
        dataBean.setCode(n + "");
        dataBean.setAddress("无位置信息");
        dataBean.setPower(pp1+"");
        dataBean.setTemperature(t1);
        mBoxList.add(dataBean);
        bluetoothResult.returnDataForBind(mBoxList);

        Observable.timer(1, TimeUnit.NANOSECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                rc7();
            }
        });


    }


    void rc7() {

        Observable.timer(200, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.writeDevice(suuec, tt11, ByteUtils.hexString2Bytes("CC77010102EF"), new BleCharacterCallback() {
                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        Log.d(TAG, "onSuccess: 发送cc77成功 ");
                        n2();
                    }

                    @Override
                    public void onFailure(BleException exception) {
                    }
                });

            }
        });

    }




    void n2() {

        Observable.timer(1, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.notify(suuec, tt11, new BleCharacterCallback() {

                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        byte[] b = characteristic.getValue();
                        String highTem;
                        String lowTem;
                        if (ByteUtils.bytes2HexString(b).startsWith("dd77") && ByteUtils.bytes2HexString(b).endsWith("ef")) {
                            try {
                                if (b[3] == 0x01) {

                                    int h = b[8] & 0xff;
                                    int hd = b[9] & 0xff;
                                    if (h > 127) {
                                        int a = h - 128;
                                        highTem = ("-" + a + "." + hd);
                                    } else {
                                        highTem = (h + "." + hd);
                                    }
                                    int l = b[6] & 0xff;
                                    int ld = b[7] & 0xff;
                                    if (l > 127) {
                                        int b1 = l - 128;
                                        lowTem = ("-" + b1 + "." + ld);
                                    } else {
                                        lowTem = (l + "." + ld);
                                    }
//                                    sutils.saveXML("highTem", (b[8] + "") + ("." + b[9]));
//                                    sutils.saveXML("lowTem", (b[6] + "") + ("." + b[7]));
//                                    dataBean.setThreshold(sutils.readXML("lowTem", "0") + "~" + sutils.readXML("highTem", "0"));
                                    bluetoothResult.returnTempthreshold(lowTem + "~" + highTem);
                                }
                                rdis();
                                Observable.timer(5, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
                                    @Override
                                    public void call(Long aLong) {
                                        bleManager.closeBluetoothGatt();

                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
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



    void rdis() {

        Observable.timer(500,TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.writeDevice(suuec, tt11, ByteUtils.hexString2Bytes("AA55041234AB5305EF"), new BleCharacterCallback() {
                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {

                    }

                    @Override
                    public void onFailure(BleException exception) {

                    }
                });

//                bleManager.closeBluetoothGatt();
            }
        });



    }


}
