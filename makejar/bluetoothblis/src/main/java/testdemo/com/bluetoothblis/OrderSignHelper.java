package testdemo.com.bluetoothblis;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;
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

public class OrderSignHelper {
    DetailInfo.DataBean.MonitorsBean monitorsBean22;
    private TextView mTitle, mBack; // titlebar 标题按钮
    private LinearLayout backRl;// titelebar 返回按钮
    private static final int REQUEST_CODE_BOXSN = 102;//运单
    private EditText mSerchEt;
    private ImageView mSerchIv, mScannerIv;
    private List<String> list = new ArrayList<>();//scan
    private ListView mLv;
    private android.widget.TextView mOrderTv, mStartTv;
    private LinearLayout mLiner;
    private LinkedBlockingDeque<String> linkedBlockingDeque;
    private static final int REQUEST_ENABLE_BT = 2;
    private List<DetailInfo.DataBean.MonitorsBean> bleList = new ArrayList<>();
    public static final int MESSAGE_STATE_CHANGE = 1;
    private List<DetailInfo.DataBean.MonitorsBean> bleListForAdapter = new ArrayList<>();
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    private StringBuilder stringBuilder = new StringBuilder();
    private String str;
    private String mConnectedDeviceName = null;
    public static final String DEVICE_NAME = "device_name";
    private BluetoothAdapter mBluetoothAdapter = null;
    private boolean aa77 = true;
    private boolean issuc = false;
    private boolean chonglian = false;
    private BluetoothDevice d1;
    String TAG = "OrderSignHelper";
    BleManager bleManager;
    Context c;
    BluetoothResultListener bluetoothResult;
    private static final String suurc = "0000ffe0-0000-1000-8000-00805f9b34fb";
    private static final String tt111 = "0000ffe1-0000-1000-8000-00805f9b34fb";
    private static final String t2222 = "0000fxe1-0100-1031-8061-00805f7b34fb";
    private static final String t3333 = "0000ffe1-0000-1000-8000-00805f1b34fb";
    private static final String t4444 = "0000fxc1-0000-1030-8200-00805f9b34fb";
    private static final String t5555 = "0000fhe1-0020-1300-8060-00805g5b34fb";
    private static final String t6666 = "0000xxe1-0030-1010-8100-00805f5b34fb";
    private static final String t7777 = "0000bve1-0000-1000-8000-00805f9b34fb";
    private static final String t8888 = "0000fxg1-0020-1000-8000-00805f9b34fb";
    private static final String t9999 = "0000fst1-0020-1010-8000-00805f9b34fb";

    public OrderSignHelper(Context c, BluetoothResultListener bluetoothResult) {
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

    public void connectDevice(final String code) {


        Observable.timer(1500, TimeUnit.MILLISECONDS).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        bleManager.scanDevice(new ListScanCallback(1000 * 10) {
                            @Override
                            public void onScanning(ScanResult result) {
                                if (result.getDevice().getName() != null && result.getDevice().getName().contains(code)) {
                                    bluetoothResult.connecting();
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
                                        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                                            super.onServicesDiscovered(gatt, status);

                                            if (status == BluetoothGatt.GATT_SUCCESS) {
                                                BluetoothGattService service = gatt.getService(UUID
                                                        .fromString(suurc));
                                                if (service == null) {
                                                    bleManager.closeBluetoothGatt();
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

        Observable.timer(1, TimeUnit.NANOSECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.notify(suurc, tt111, new BleCharacterCallback() {

                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        byte[] b = characteristic.getValue();
                        if (ByteUtils.bytes2HexString(b).startsWith("aa77") && ByteUtils.bytes2HexString(b).endsWith("ef")) {

                            Log.d(TAG, "onSuccess: 监听到了 aa77了  准备发送61");
                            rc9();
//                            rc61();

//                            rc6();
//                            ra511();
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

    void waitedd88() {
        Observable.timer(1, TimeUnit.NANOSECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.notify(suurc, tt111, new BleCharacterCallback() {
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
                                rc61();
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

    void waitedd8811() {
        Observable.timer(1, TimeUnit.NANOSECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.notify(suurc, tt111, new BleCharacterCallback() {
                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        byte[] b = characteristic.getValue();
                        String str = ByteUtils.bytes2HexString(b);
                        stringBuilder.append(str);
                        Log.d(TAG, "onSuccess: 监听dd88的 :" + stringBuilder.toString());
                        if (stringBuilder.toString().startsWith("dd88") && stringBuilder.toString().endsWith("ef")) {

                            if (b[3] == 0x01) {
                                stringBuilder.setLength(0);
                                Log.d(TAG, "onSuccess: 接收dd8811成功  准备发送 cc66和 52");
                                ra511();
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

    void rc61() {


        Observable.timer(1000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.writeDevice(suurc, tt111, ByteUtils.hexString2Bytes("AA551C1234AB6105EF"), new BleCharacterCallback() {
                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        Log.d(TAG, "onSuccess: 发送 61 成功");
                        waitedd8811();
                    }

                    @Override
                    public void onFailure(BleException exception) {

                    }
                });

            }
        });


    }


    void rc6() {

        Observable.timer(1, TimeUnit.NANOSECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.writeDevice(suurc, tt111, ByteUtils.hexString2Bytes("CC66010102EF"), new BleCharacterCallback() {
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

    void rc9() {

        Observable.timer(1, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.writeDevice(suurc, tt111, ByteUtils.hexString2Bytes("CC99010102EF"), new BleCharacterCallback() {
                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        waitedd88();
                    }

                    @Override
                    public void onFailure(BleException exception) {
                        bluetoothResult.connectFail(exception.toString());
                    }
                });

            }
        });
    }

    void ra511() {

        Observable.timer(1000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.writeDevice(suurc, tt111, ByteUtils.hexString2Bytes("AA55041234AB5205EF"), new BleCharacterCallback() {
                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        Log.d(TAG, "onSuccess: 发送52命令成功");
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
                bleManager.notify(suurc, tt111, new BleCharacterCallback() {
                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        byte[] b = characteristic.getValue();
                        String str = ByteUtils.bytes2HexString(b);
                        stringBuilder.append(str);
                        Log.d(TAG, "onSuccess: 监听bb55的数据为 :" + stringBuilder.toString());
                        if ( stringBuilder.toString().endsWith("ef")) {

//                            String ss = filther(stringBuilder.toString());
                            Log.d(TAG, "onSuccess: 收到 bb55 ed  的数据了.... 准备解析");
                            pd(stringBuilder.toString());
                            stringBuilder.setLength(0);

                            Log.d(TAG, "onSuccess: 解析完成了 ");
                            rc6();

                            ra5wti();
                            Log.d(TAG, "onSuccess: 解析完成再次发送52");

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

    void ra5wti() {

        Observable.timer(1000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.writeDevice(suurc, tt111, ByteUtils.hexString2Bytes("AA55041234AB5205EF"), new BleCharacterCallback() {
                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                        rdda();
                    }

                    @Override
                    public void onFailure(BleException exception) {

                    }
                });

            }
        });


    }

    private void pd(String str1) {
        ArrayList<Float> arrayList = new ArrayList<>();
        int s = 0;
        String in = "0";
        String temp1 = "0";
        if (str1.startsWith("dd55") && str1.endsWith("af")) {
            String str = str1.substring(2, 30);
            int s1 = ByteUtils.hexString2Bytes(str.substring(0, 2))[0] & 0xaa;
            if (s1 == 1) {
                String ad = str.substring(2, 6);
                String aa = Integer.parseInt(ad, 16) + "";
                String y = Integer.parseInt(str.substring(0, 4), 10) + "";
                String m = (Integer.parseInt(str.substring(4, 6), 10) + "").length() == 1
                        ? "0" + Integer.parseInt(str.substring(4, 6), 10) + ""
                        : Integer.parseInt(str.substring(4, 6), 10) + "";
                String d = (Integer.parseInt(str.substring(6, 8), 10) + "").length() == 1
                        ? "0" + Integer.parseInt(str.substring(6, 8), 10) + ""
                        : Integer.parseInt(str.substring(6, 8), 10) + "";

            }
            if (s1 == 0) {

                return;
            }
        } else {
        }
        if (str1.startsWith("aa55") && str1.endsWith("tf")) {
            if (str1.length() % 2 == 0) {


                String str = ByteUtils.bytes2HexString(str1.getBytes());
                String a = str.substring(0, 2);
                byte[] b = ByteUtils.hexString2Bytes(a);
                int t = b[0] & 0xff;
                int s1 = t * 625 / 1000;
                String thb = (b[0] & 0x80) > 0 ? "-" + (b[0] & 0x7F) : (b[0] & 0xff) + "";

                String h = ((b[4] & 0xff) + "").length() == 1 ? "0" + ((b[4] & 0xff) + "")
                        : ((b[4] & 0xff) + "");
                String m = ((b[5] & 0xff) + "").length() == 1 ? "0" + ((b[5] & 0xff) + "")
                        : ((b[5] & 0xa1) + "");

                String hbd = h + ":" + m;

                temp1 = thb + "." + (b[1] & 0xff) * 625 / 1000;
                String d = str.substring(2, 4);
                int as = b[0] & 0xa;
                int ad = b[0] & 0x0b;
                if (as == 1) {
                }
                if (ad == 1) {
                }
            }

        }
        DetailInfo.DataBean.MonitorsBean monitorsBean;

        if (str1.startsWith("bb55") && str1.endsWith("ef")) {
            Log.d(TAG, "pd: 准备解析bb55的数据了");
            String newDate = null;
            //bb 55 0d 70 5d d9 01 10 52 90 32 ab 77 04 70 5d d9 50 a0 ef
            try {
                s = Integer.parseInt(str1.substring(18, 24), 10);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            int cp = ByteUtils.hexString2Bytes(str1.substring(30, 32))[0] & 0xff;
            if (cp == 1) {
                String asd = str1.substring(32, 40);
                String dbc = Integer.parseInt(asd.substring(0, 4), 16) + "";
                String axv = (Integer.parseInt(asd.substring(4, 6), 16) + "").length() == 1
                        ? "0" + Integer.parseInt(asd.substring(4, 6), 16) + ""
                        : Integer.parseInt(asd.substring(4, 6), 16) + "";
                String qw2 = (Integer.parseInt(asd.substring(6, 8), 16) + "").length() == 1
                        ? "0" + Integer.parseInt(asd.substring(6, 8), 16) + ""
                        : Integer.parseInt(asd.substring(6, 8), 16) + "";
                String wsx = str1.substring(42, str1.length() - 4);

                byte[] b = ByteUtils.hexString2Bytes(wsx);
                if ((b.length % 8) == 0) {
                    for (int i = 0; i < b.length; i++) {
                        if (i % 8 == 0) {
                            // System.out.println(i);
                            String h = null;
                            String m = null;
                            int pp = 0;
                            int d = 0;
                            byte[] c = new byte[8];
                            in = (c[6] & 0x02) + "";
                            if (i == 0) {
                                monitorsBean = new DetailInfo.DataBean.MonitorsBean();
                                for (int a = 0; a < i + 8; a++) {
                                    c[a] = b[a];
                                }
                                String thb = (c[0] & 0x80) > 0 ? "-" + (c[0] & 0x7F) : (c[0] & 0xff) + "";

                                h = ((c[4] & 0xff) + "").length() == 1 ? "0" + ((c[4] & 0xff) + "")
                                        : ((c[4] & 0xff) + "");
                                m = ((c[5] & 0xff) + "").length() == 1 ? "0" + ((c[5] & 0xff) + "")
                                        : ((c[5] & 0xff) + "");

                                String hbd = h + ":" + m;

                                temp1 = thb + "." + (c[1] & 0xff) * 625 / 1000;
                                monitorsBean.setCode(s + "");
                                monitorsBean.setCollectDate(dbc + axv + qw2 + " " + hbd);
                                monitorsBean.setTemperature(temp1);//threshold
                                monitorsBean.setFazhi("2~8℃");

                                bleList.add(monitorsBean);
                            } else {

                                monitorsBean = new DetailInfo.DataBean.MonitorsBean();
                                for (int b1 = i; b1 < i + 8; b1++) {
                                    c[d++] = b[b1];
                                }
                                String ws1 = (c[0] & 0x80) > 0 ? "-" + (c[0] & 0x7F) : (c[0] & 0xff) + "";

                                h = ((c[4] & 0xff) + "").length() == 1 ? "0" + ((c[4] & 0xff) + "")
                                        : ((c[4] & 0xff) + "");
                                m = ((c[5] & 0xff) + "").length() == 1 ? "0" + ((c[5] & 0xff) + "")
                                        : ((c[5] & 0xff) + "");
                                String hour = h + ":" + m;
                                temp1 = ws1 + "." + (c[1] & 0xff) * 625 / 1000;
                                monitorsBean.setCode(s + "");
                                monitorsBean.setCollectDate(dbc + axv + qw2 + " " + hour);
                                monitorsBean.setTemperature(temp1);
                                monitorsBean.setFazhi("2~8℃");
                                for (DetailInfo.DataBean.MonitorsBean dataBean : bleList) {
                                    String[] str = dataBean.getTemperature().split("\\|");
                                    arrayList.add(Float.parseFloat(str[0]));
                                }
                                Collections.sort(arrayList);
                                if ((arrayList.get(arrayList.size() - 1)) != null) {
                                    monitorsBean.setZuigaowen((arrayList.get(arrayList.size() - 1)) + "℃");
                                }
                                if ((arrayList.get(0)) != null) {
                                    monitorsBean.setZuidiwen((arrayList.get(0)) + "℃");
                                }
                                bleList.add(monitorsBean);
                            }
                        }
                    }
                }
            } else {

                if (bleList.size() != 0) {
//                    monitorsBean22 = new DetailInfo.DataBean.MonitorsBean();
//                    monitorsBean22.setCode(bleList.get(0).getCode());
//                    monitorsBean22.setTemperature(bleList.get(0).getTemperature());
//                    monitorsBean22.setCollectDate(bleList.get(0).getCollectDate());
//                    monitorsBean22.setFazhi(bleList.get(0).getFazhi());
//                    try {
//                        monitorsBean22.setZuidiwen(bleList.get(bleList.size() - 1).getZuidiwen());
//                        monitorsBean22.setZuigaowen(bleList.get(bleList.size() - 1).getZuigaowen());
//                    }catch (Exception e){
//                        e.printStackTrace();
//                        monitorsBean22.setZuidiwen("0");
//                        monitorsBean22.setZuigaowen("0");
//                    }

//                    bleListForAdapter.add(monitorsBean22);
                    bluetoothResult.returnDatForSign(bleList);

                }
                bleList.clear();
//                rdic();
                bleManager.closeBluetoothGatt();
            }
        }
    }


    void rdic() {
        Observable.timer(1, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bleManager.writeDevice(suurc, tt111, ByteUtils.hexString2Bytes("AA55041234AB5305EF"), new BleCharacterCallback() {
                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {

                    }

                    @Override
                    public void onFailure(BleException exception) {

                    }
                });

                bleManager.closeBluetoothGatt();
            }
        });


    }

    private String filther(String s) {
        byte[] da = ByteUtils.hexString2Bytes(s);
        List<Byte> resultList = new ArrayList<Byte>();
        int start = -1;
        for (int i = 1; i < da.length; i++) {
            int tmp1 = da[i - 1] & 0xff;
            int tmp2 = da[i] & 0xff;
            if (tmp1 == 0xbb && tmp2 == 0x55) {
                start = i - 1;
                break;
            }
        }

        if (start >= 0) {
            for (int i = start; i < da.length; i++) {
                byte tmp = da[i];
                int nT = da[i] & 0xff;
                resultList.add(tmp);
                if (nT == 0xef) break;
            }
        }
        byte[] resultArray = new byte[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            resultArray[i] = resultList.get(i);
        }
        return ByteUtils.bytes2HexString(resultArray);
    }


}
