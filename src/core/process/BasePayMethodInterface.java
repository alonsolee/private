package core.process;

import android.os.Handler;
import core.listener.PayWorkingStateListener;

public abstract class BasePayMethodInterface {

	protected boolean isPayMethodEnable = false;

	protected boolean isUserCanceled = false;

	protected PayWorkingStateListener mWorkingStateListener;
	
	protected volatile boolean isPayStarted = false;

	public abstract void startPay(Object data);

	public abstract void cancelPay();
	
	public abstract void invokePayRequest();
	
	public abstract boolean checkChargeInfo();
	
	protected Object payData;
	
	protected Handler mHandler;

	public boolean isPayMethodEnable() {
		return isPayMethodEnable;
	}

	public void setPayMethodEnable(boolean isPayMethodEnable) {
		this.isPayMethodEnable = isPayMethodEnable;
	}

	public boolean isUserCanceled() {
		return isUserCanceled;
	}

	public void setUserCanceled(boolean isUserCanceled) {
		this.isUserCanceled = isUserCanceled;
	}

	public void setWorkingStateListener(
			PayWorkingStateListener mWorkingStateListener) {
		this.mWorkingStateListener = mWorkingStateListener;
	}

	public PayWorkingStateListener getWorkingStateListener() {
		return mWorkingStateListener;
	}
	
	public void setPayStarted(boolean isPayStarted) {
		this.isPayStarted = isPayStarted;
	}
	
	public boolean isPayStarted() {
		return isPayStarted;
	}
	
	public Object getPayData() {
		return payData;
	}
	
	public void setPayData(Object payData) {
		this.payData = payData;
	}

}
