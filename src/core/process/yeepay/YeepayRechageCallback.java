/**
 * 
 */
package core.process.yeepay;

/**
 * 易宝充值，提交订单回调
 * 
 * @author alonso lee
 * 
 */
public interface YeepayRechageCallback {

	public void yeepaySubmitRechargeOrderEvent(int code, String order);
}
