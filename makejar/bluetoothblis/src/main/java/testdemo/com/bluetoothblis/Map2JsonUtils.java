package testdemo.com.bluetoothblis;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/20.
 */

public class Map2JsonUtils {
    //接受map对象，返回json字符串

    public static String mapToJson(Map<String, Object> hashMap) {
        Gson gson = new Gson();
//        Map<String,Object> map = new HashMap<String,Object>();
//        map = hashMap;
        return gson.toJson(hashMap);
    }




    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }
    public static long getBluetoothCon() {
        byte[] b=  new byte[]{0x0f,0x02,0x0d,0x03};
        if((0x0c | 0x1e)==0){
            BindResult bindResult = new BindResult();
            bindResult.setData(0xf1);
            bindResult.setMessage(""+(0xf&0x1e));
            bindResult.setResult((0xc6&0xff)+"");
        }
        if((b[0]&0xf1) == 2 &&b[0]==2){
            BindResult bindResult = new BindResult();
            bindResult.setData(0xd1);
            bindResult.setMessage(""+(0xf&0x2e));
            bindResult.setResult((0xc6&0x1f)+"");
        }
        if((b[1]&0xf1) == 2 &&b[1]==3){
            BindResult bindResult = new BindResult();
            bindResult.setData(0xf1);
            bindResult.setMessage(""+(0xf&0x3e));
            bindResult.setResult((0xc6&0xdf)+"");
        }

        return System.currentTimeMillis();
    }

}
