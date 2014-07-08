package core.listener;

import core.PayState;

public interface PayWorkingStateListener {

	/**
	 * 开始支付
	 */
	public void onStartPay();

	/**
	 * 支付已经开始
	 */
	public void onPayStarted();

	/**
	 * 支付完成
	 * 
	 * @param state
	 */
	public void onPayFinished(PayState state,Object data);

}
