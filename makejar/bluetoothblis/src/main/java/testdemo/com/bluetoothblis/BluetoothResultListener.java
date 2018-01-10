package testdemo.com.bluetoothblis;

import java.util.List;

/**
 * Created by chenzhuo on 2017/11/6.
 */

public interface BluetoothResultListener {

    void connectFail(String str);

    void connectTimeout();

    void connecting();

    void connectsuccess();

    void notFoundDevice();

    void serviceFound();

    void returnBindDate(String str);

    void returnSignDate(String date);

    void returnTempthreshold(String str);

    void returnDataForBind(List<DeviceSerch.DataBean> mBoxList);

    void returnDatForSign(List<DetailInfo.DataBean.MonitorsBean> bleListForAdapter);

    void onSendOrderForBindSuccess();

    void onSendOrderForSignSuccess();
}
