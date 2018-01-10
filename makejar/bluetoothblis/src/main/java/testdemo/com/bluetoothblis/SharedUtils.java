package testdemo.com.bluetoothblis;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedUtils {
	

	private Context ctx;

	public SharedUtils(Context ctx){
		this.ctx = ctx;
	}
	public  void putBoolean(String key, boolean value) {
		SharedPreferences sp = ctx.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}

	public  boolean getBoolean(String key, boolean defValue) {
		SharedPreferences sp = ctx.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
	}
	//保存数据
	public void saveXML(String key, String Value){
		SharedPreferences share = ctx.getSharedPreferences("egtcookies", 0);
		Editor edt = share.edit();
		edt.putString(key, Value);
		edt.commit();
	}
	//获取数据
	public String readXML(String key){
		String result = null;
		
		SharedPreferences share = ctx.getSharedPreferences("egtcookies", 0);
		result = share.getString(key, "");
		return result;
	}

    //获取数据
    public String readXML(String key,String def){
        String result = null;

        SharedPreferences share = ctx.getSharedPreferences("egtcookies", 0);
        result = share.getString(key, def);
        return result;
    }

	//存储的sharedpreferences文件名
	private static final String FILE_NAME = "save_file_name";

	/**
	 * 保存数据到文件
	 * @param context
	 * @param key
	 * @param data
	 */
	public static void saveData(Context context, String key,Object data){

		String type = data.getClass().getSimpleName();
		SharedPreferences sharedPreferences = context
				.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();

		if ("Integer".equals(type)){
			editor.putInt(key, (Integer)data);
		}else if ("Boolean".equals(type)){
			editor.putBoolean(key, (Boolean)data);
		}else if ("String".equals(type)){
			editor.putString(key, (String)data);
		}else if ("Float".equals(type)){
			editor.putFloat(key, (Float)data);
		}else if ("Long".equals(type)){
			editor.putLong(key, (Long)data);
		}

		editor.commit();
	}

	/**
	 * 从文件中读取数据
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static Object getData(Context context, String key, Object defValue){

		String type = defValue.getClass().getSimpleName();
		SharedPreferences sharedPreferences = context.getSharedPreferences
				(FILE_NAME, Context.MODE_PRIVATE);

		//defValue为为默认值，如果当前获取不到数据就返回它
		if ("Integer".equals(type)){
			return sharedPreferences.getInt(key, (Integer)defValue);
		}else if ("Boolean".equals(type)){
			return sharedPreferences.getBoolean(key, (Boolean)defValue);
		}else if ("String".equals(type)){
			return sharedPreferences.getString(key, (String)defValue);
		}else if ("Float".equals(type)){
			return sharedPreferences.getFloat(key, (Float)defValue);
		}else if ("Long".equals(type)){
			return sharedPreferences.getLong(key, (Long)defValue);
		}

		return null;
	}
//	public  boolean isCookId() {
//		SharedPreferences preference = ctx.getSharedPreferences("egtcookies", 0);
//
//		if (preference.getString("UserName", "") != null//用户名
//		&& !preference.getString("UserName", "").equals("")
//		&& !preference.getString("UserName", "").equals("null")&&preference.getString("pwd", "") != null
//				&& !preference.getString("pwd", "").equals("")
//				&& !preference.getString("pwd", "").equals("null")) {
//		return true;
//		} else {
//		return false;
//		}
//		}
//	public void clearCookie() {
//		SharedPreferences preference = ctx.getSharedPreferences("egtcookies", 0);
//		Editor edt = preference.edit();
//		  edt.remove("UserName");
//		  edt.remove("pwd");
//		  edt.commit();
//		   }

}
