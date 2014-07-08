package core.data;

public class ChargInfo {

	// 订单号
	protected String orderNumber = "";
	// 充值id号
	protected String productid = "";
	// 充值金额
	protected String money = "";
	// 充值渠道
	protected String frpId = "";
	// 服务器的接收充值结果的回调地址
	protected String cbURL = "";
	
	protected int responseCode;

	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber
	 *            the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @return the productid
	 */
	public String getProductid() {
		return productid;
	}

	/**
	 * @param productid
	 *            the productid to set
	 */
	public void setProductid(String productid) {
		this.productid = productid;
	}

	/**
	 * @return the money
	 */
	public String getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(String money) {
		this.money = money;
	}

	/**
	 * @return the frpId
	 */
	public String getFrpId() {
		return frpId;
	}

	/**
	 * @param frpId
	 *            the frpId to set
	 */
	public void setFrpId(String frpId) {
		this.frpId = frpId;
	}

	/**
	 * @return the cbURL
	 */
	public String getCbURL() {
		return cbURL;
	}

	/**
	 * @param cbURL
	 *            the cbURL to set
	 */
	public void setCbURL(String cbURL) {
		this.cbURL = cbURL;
	}

	/**
	 * @return the responseCode
	 */
	public int getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	
	
}
