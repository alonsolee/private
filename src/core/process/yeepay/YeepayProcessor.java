package core.process.yeepay;

import core.PayState;
import core.exception.ChargeException;
import core.log.LLog;
import core.process.BasePayMethodInterface;

public class YeepayProcessor extends BasePayMethodInterface {

	private YeepayChargeInfo mYeepayChargeInfo;
	private final String TAG = this.getClass().getSimpleName();

	@Override
	public void startPay(Object data) {
		// TODO Auto-generated method stub
		if (data == null) {
			throw new ChargeException("charge info should not null!");
		}

		try {
			mYeepayChargeInfo = (YeepayChargeInfo) data;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ChargeException("charge info type error!");
		}

		if (isPayStarted()) {
			LLog.d(TAG, this.getClass().getSimpleName()
					+ " is processing pay request.ignore...");
			return;
		}

		LLog.d(TAG, this.getClass().getSimpleName()
				+ " start processing pay request!");
		setPayStarted(true);
		invokePayRequest();
	}

	@Override
	public void cancelPay() {
		// TODO Auto-generated method stub
		if (isPayStarted()) {
			setUserCanceled(true);
			LLog.d(TAG, "user cancel yeepay request,stop");
		}

		if (mWorkingStateListener != null) {
			mWorkingStateListener
					.onPayFinished(PayState.LOOVEE_PAY_FINISHED_USER_CANCEL,
							mYeepayChargeInfo);
		}
	}

	@Override
	public void invokePayRequest() {
		// TODO Auto-generated method stub
		if (mWorkingStateListener != null) {
			mWorkingStateListener.onStartPay();
		}

		LLog.d(TAG, "processing pay request: " + mYeepayChargeInfo.toString());
	}

	@Override
	public void checkChargeInfo() {
		// TODO Auto-generated method stub
		
	}

}
