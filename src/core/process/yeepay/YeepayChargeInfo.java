package core.process.yeepay;

import core.data.ChargInfo;

public class YeepayChargeInfo extends ChargInfo {

	// 充值卡面额
	private String cardAmt = "";
	// 充值卡卡号
	private String cardNo = "";
	// 充值卡密码
	private String cardPwd = "";
	// 业务类型
	private final String md = "ChargeCardDirect";
	// 商户编号
	private final String merId = "10011679671";
	// 是否较验订单金额
	private final String verifyAmt = "true";
	// 启用应答机制
	private final String needResponse = "1";

	/****************************************************** 支付通道 *********************************************************************/
	// 骏网一卡通
	public static final String JUNNET = "JUNNET";
	// 盛大卡
	public static final String SNDACARD = "SNDACARD";
	// 神州行
	public static final String SZX = "SZX";
	// 征途卡
	public static final String ZHENGTU = "ZHENGTU";
	// Q币卡
	public static final String QQCARD = "QQCARD";
	// 联通卡
	public static final String UNICOM = "UNICOM";
	// 久游卡
	public static final String JIUYOU = "JIUYOU";
	// 易宝e卡通
	public static final String YPCARD = "YPCARD";
	// 网易卡
	public static final String NETEASE = "NETEASE";
	// 完美卡
	public static final String WANMEI = "WANMEI";
	// 搜狐卡
	public static final String SOHU = "SOHU";
	// 电信卡
	public static final String TELECOM = "TELECOM";
	// 纵游一卡通
	public static final String ZONGYOU = "ZONGYOU";
	// 天下一卡通
	public static final String TIANXIA = "TIANXIA";
	// 天宏一卡通
	public static final String TIANHONG = "TIANHONG";

	/**
	 * @return the cardAmt
	 */
	public String getCardAmt() {
		return cardAmt;
	}

	/**
	 * @param cardAmt
	 *            the cardAmt to set
	 */
	public void setCardAmt(String cardAmt) {
		this.cardAmt = cardAmt;
	}

	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo
	 *            the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @return the cardPwd
	 */
	public String getCardPwd() {
		return cardPwd;
	}

	/**
	 * @param cardPwd
	 *            the cardPwd to set
	 */
	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}

	/**
	 * @return the md
	 */
	public String getMd() {
		return md;
	}

	/**
	 * @return the merId
	 */
	public String getMerId() {
		return merId;
	}

	/**
	 * @return the verifyAmt
	 */
	public String getVerifyAmt() {
		return verifyAmt;
	}

	/**
	 * @return the needResponse
	 */
	public String getNeedResponse() {
		return needResponse;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		// return super.toString();
		return "[ cardAmt " + cardAmt + " frpId " + frpId + " money " + money
				+ " ]";
	}

}
