package testdemo.com.bluetoothblis;

import java.util.List;

public class OrderBind {
	@Override
	public String toString() {
		return "OrderBind{" +
				"token='" + token + '\'' +
				", orderNumber='" + orderNumber + '\'' +
				", listBoxes=" + listBoxes +
				'}';
	}

	/** 绑定时要上传的json
	 * token : token
	 * orderNumber : 20161019004
	 * receiverId : 8
	 * listBoxes : [{"boxCode":"包装箱03","deviceCode":"箱002"},{"boxCode":"包装箱04","deviceCode":"箱002"}]
	 */

	private String token;
	private String orderNumber;

	/**
	 * boxCode : 包装箱03
	 * deviceCode : 箱002
	 */

	private List<ListBoxesBean> listBoxes;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}



	public List<ListBoxesBean> getListBoxes() {
		return listBoxes;
	}

	public void setListBoxes(List<ListBoxesBean> listBoxes) {
		this.listBoxes = listBoxes;
	}

	public static class ListBoxesBean {

		private String deviceCode;

		public ListBoxesBean( String deviceCode) {

			this.deviceCode = deviceCode;
		}

		@Override
		public String toString() {
			return "ListBoxesBean{" +
					"deviceCode='" + deviceCode + '\'' +
					'}';
		}

		public String getDeviceCode() {
			return deviceCode;
		}

		public void setDeviceCode(String deviceCode) {
			this.deviceCode = deviceCode;
		}
	}


}