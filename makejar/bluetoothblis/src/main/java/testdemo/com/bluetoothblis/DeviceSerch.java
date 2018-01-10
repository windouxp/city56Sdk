package testdemo.com.bluetoothblis;


public class DeviceSerch {


	/**
	 * result : 1
	 * message : 加载成功
	 * data : {"code":"713087","CrruDate":"2017-04-03 19:39:00","power":"1","door":"1","threshold":"2~8","address":"","alarm":1,"temperature":"-77.0"}
	 */

	private String result;
	private String message;
	private DataBean data;

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

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public static class DataBean {
		@Override
		public String toString() {
			return "DataBean{" +
					"code='" + code + '\'' +
					", CrruDate='" + CrruDate + '\'' +
					", power='" + power + '\'' +
					", door='" + door + '\'' +
					", threshold='" + threshold + '\'' +
					", address='" + address + '\'' +
					", alarm='" + alarm + '\'' +
					", temperature='" + temperature + '\'' +
					'}';
		}

		/**
		 * code : 713087
		 * CrruDate : 2017-04-03 19:39:00
		 * power : 1
		 * door : 1
		 * threshold : 2~8
		 * address :
		 * alarm : 1
		 * temperature : -77.0
		 */

		private String code;
		private String CrruDate;
		private String power;
		private String door;
		private String threshold;
		private String address;
		private String alarm;
		private String temperature;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getCrruDate() {
			return CrruDate;
		}

		public void setCrruDate(String CrruDate) {
			this.CrruDate = CrruDate;
		}

		public String getPower() {
			return power;
		}

		public void setPower(String power) {
			this.power = power;
		}

		public String getDoor() {
			return door;
		}

		public void setDoor(String door) {
			this.door = door;
		}

		public String getThreshold() {
			return threshold;
		}

		public void setThreshold(String threshold) {
			this.threshold = threshold;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getAlarm() {
			return alarm;
		}

		public void setAlarm(String alarm) {
			this.alarm = alarm;
		}

		public String getTemperature() {
			return temperature;
		}

		public void setTemperature(String temperature) {
			this.temperature = temperature;
		}
	}
}
