/**
 * 
 */
package core;

import java.util.HashMap;

import core.listener.PayWorkingStateListener;
import core.log.LLog;
import core.process.BasePayMethodInterface;
import core.process.yeepay.YeepayChargeInfo;
import core.process.yeepay.YeepayProcessor;

/**
 * 支付功能入口
 * 
 * @author alonso lee
 * 
 */
public class LooveePayProcesser {

	private volatile static LooveePayProcesser instance;

	private HashMap<String, BasePayMethodInterface> payProcessers;

	private final String TAG = this.getClass().getSimpleName();

	private BasePayMethodInterface curPayMethod;

	public static LooveePayProcesser getInstance() {
		if (instance == null) {
			synchronized (LooveePayProcesser.class) {
				if (instance == null) {
					instance = new LooveePayProcesser();
				}
			}
		}
		return instance;
	}

	public LooveePayProcesser() {
		// TODO Auto-generated constructor stub
		payProcessers = new HashMap<String, BasePayMethodInterface>();
		payProcessers.put(YeepayChargeInfo.class.getSimpleName(),
				new YeepayProcessor());
	}

	public void addPayProcessor(Class<?> chargeInfo, Class<?> processor) {
		if (!payProcessers.containsKey(chargeInfo.getSimpleName())) {
			LLog.d(TAG, "add new pay processor " + processor.getSimpleName());
		} else {
			LLog.i(TAG, "pay processor " + processor.getSimpleName()
					+ " has been added.ignore");
		}
	}

	public void startPay(Object chargeInfo, PayWorkingStateListener listener) {
		if (chargeInfo == null) {
			LLog.e(TAG, "chargeInfo should not null!");
			return;
		}

		if (!payProcessers.containsKey(chargeInfo.getClass().getSimpleName())) {
			LLog.e(TAG, "no such pay processor to handle pay request "
					+ chargeInfo.getClass().getSimpleName());
			return;
		}

		if (curPayMethod != null) {
			LLog.w(TAG, curPayMethod.getClass().getSimpleName()
					+ " is processing pay request "
					+ curPayMethod.getPayData().toString());
			return;
		}

		curPayMethod = payProcessers.get(chargeInfo.getClass().getSimpleName());
		curPayMethod.setWorkingStateListener(listener);
		curPayMethod.startPay(chargeInfo);
	}

	public void startPayForce(Object chargeInfo,
			PayWorkingStateListener listener) {
		if (chargeInfo == null) {
			LLog.e(TAG, "chargeInfo should not null!");
			return;
		}

		if (!payProcessers.containsKey(chargeInfo.getClass().getSimpleName())) {
			LLog.e(TAG, "no such pay processor to handle pay request "
					+ chargeInfo.getClass().getSimpleName());
			return;
		}

		if (curPayMethod != null) {
			LLog.w(TAG, curPayMethod.getClass().getSimpleName()
					+ " is processing pay request "
					+ curPayMethod.getPayData().toString()
					+ " force cancel it ");
			curPayMethod.cancelPay();
		}

		curPayMethod = payProcessers.get(chargeInfo.getClass().getSimpleName());
		curPayMethod.setWorkingStateListener(listener);
		curPayMethod.startPay(chargeInfo);
	}

	public void cancelPay() {
		if (curPayMethod != null) {
			curPayMethod.cancelPay();
			curPayMethod = null;
		}
	}

	public void writeDebug(boolean enableDebug) {
		LLog.debugEnable = enableDebug;
	}

}
