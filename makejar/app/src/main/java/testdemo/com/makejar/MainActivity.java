package testdemo.com.makejar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import newcity56.ObHelper;
import newcity56.OnBindResultListener;
import newcity56.TransportDeviceBean;

public class MainActivity extends AppCompatActivity implements OnBindResultListener {// BluetoothResultListener,
    String TAG = "MainActivity";
    private Handler mHandler;
    private ObHelper helper;
    private Button mBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn = (Button) findViewById(R.id.conn_btn);
        Log.d(TAG, "onCreate: ");
//        OrderbindHelper sendOrderNumForBindHelper = new OrderbindHelper(MainActivity.this,this);
//        sendOrderNumForBindHelper.connectDevice("355");
        mHandler = new Handler();
        helper = new ObHelper(this, this,mHandler);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.cnDeviceBle("903796");
            }
        });
//        OrderSignHelper orderSignHelper = new OrderSignHelper(this,this);
//        orderSignHelper.connectDevice("959");

//        OrderbindHelper orderbindHelper = new OrderbindHelper(MainActivity.this,this);
//        orderbindHelper.connectDevice("153");

//        SendOrderNumForBindHelper sendOrderNumForBindHelper= new SendOrderNumForBindHelper(MainActivity.this,this,"1111111111111");
//        sendOrderNumForBindHelper.sendOrderNumForBind("3706");

//        SendOrderNumForSignHelper sendOrderNumForSignHelper = new SendOrderNumForSignHelper(MainActivity.this,this,"2222222222222");
//        sendOrderNumForSignHelper.sendOrderNumForSign("3706");
//
//        OrderSignHelper orderSignHelper= new OrderSignHelper(MainActivity.this,this);
//        orderSignHelper.connectDevice("3778");
    }


//    @Override
//    public void connectFail(String str) {
//        Log.d(TAG, "connectFail: "+str);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this,"connectFail",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    @Override
//    public void connectTimeout() {
//        Log.d(TAG, "connectTimeout: ");
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this,"connectTimeout",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    @Override
//    public void connecting() {
//        Log.d(TAG, "connecting: ");
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this,"connecting",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    @Override
//    public void connectsuccess() {
//        Log.d(TAG, "connectsuccess: ");
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this,"connectsuccess",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    @Override
//    public void notFoundDevice() {
//        Log.d(TAG, "notFoundDevice: ");
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this,"notFoundDevice",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    @Override
//    public void serviceFound() {
//        Log.d(TAG, "serviceFound: ");
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this,"serviceFound",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    @Override
//    public void returnBindDate(final String str) {
//        Log.d(TAG, "returnBindDate: "+str);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this,"returnBindDate"+str,Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    @Override
//    public void returnSignDate(final String date) {
//        Log.d(TAG, "returnSignDate: "+date);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this,"returnSignDate"+date,Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    @Override
//    public void returnTempthreshold(final String str) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this,"returnTempthreshold"+str,Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    @Override
//    public void returnDataForBind(final List<DeviceSerch.DataBean> mBoxList) {
//        Log.d(TAG, "returnDataForBind: "+mBoxList.toString());
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this,"returnDataForBind"+mBoxList.toString(),Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    @Override
//    public void returnDatForSign(final List<DetailInfo.DataBean.MonitorsBean> bleListForAdapter) {
//        Log.d(TAG, "returnDatForSign: "+bleListForAdapter.toString());
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this,"returnDatForSign"+bleListForAdapter.toString(),Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    @Override
//    public void onSendOrderForBindSuccess() {
//        Log.d(TAG, "onSendOrderForBindSuccess: ");
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this,"onSendOrderForBindSuccess",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    @Override
//    public void onSendOrderForSignSuccess() {
//        Log.d(TAG, "onSendOrderForSignSuccess: ");
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this,"onSendOrderForSignSuccess",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    @Override
    public void onBegin() {
        Log.e("onBegin", "初始化操作,eg:show diag()");
    }

    @Override
    public void onSucceed(List<TransportDeviceBean> mBoxList) {
        if(mBoxList!=null&&mBoxList.size()>0){
            Log.e("onSucceed",mBoxList.toString());
        }
    }

    @Override
    public void onFail(int erroCode) {
        Log.e("erroCode", erroCode+"");
//        helper.cnDeviceBle("796");
    }

    @Override
    public void onFinaly() {
        Log.e("onFinaly", "流程结束,eg:diaglog.dissmiss");

    }
}
