package testdemo.com.bluetoothblis;

public class BindResult {


	/** 绑定成功返回的json
	 * result : 1
	 * message : 加载成功
	 * data : 9
	 */

	private String result;
	private String message;
	private int data;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}
}
