package core.process.yeepay;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import android.text.TextUtils;
import core.PayState;
import core.exception.ChargeException;
import core.log.LLog;
import core.process.BasePayMethodInterface;
import core.utils.ALDigestUtil;

public class YeepayProcessor extends BasePayMethodInterface {

//	private YeepayChargeInfo payData;
	private final String TAG = this.getClass().getSimpleName();

	// 易宝充值URL
	private final String YEEPAY_RECHARGE_URL = "https://www.yeepay.com/app-merchant-proxy/command.action";
	// 易宝充值连接管理器
	private HttpsUtils httpsUtil;

	@Override
	public void startPay(Object data) {
		// TODO Auto-generated method stub
		if (data == null) {
			throw new ChargeException("charge info should not null!");
		}

		try {
			payData = data;
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
		if (!checkChargeInfo()) {
			if (mWorkingStateListener != null) {
				mWorkingStateListener.onPayFinished(
						PayState.LOOVEE_PAY_FINISHED_ILLEGAL_ARGUMENT,
						payData);
			}
			LLog.d(TAG, "illegal argument!force stop pay request...");
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

		setPayStarted(false);
		if (mWorkingStateListener != null) {
			mWorkingStateListener
					.onPayFinished(PayState.LOOVEE_PAY_FINISHED_USER_CANCEL,
							payData);
		}
	}

	@Override
	public void invokePayRequest() {
		// TODO Auto-generated method stub
		if (mWorkingStateListener != null) {
			mWorkingStateListener.onStartPay();
		}

		invokeYeepayRecharge((YeepayChargeInfo) payData);
		LLog.d(TAG, "processing pay request: " + payData.toString());
	}

	@Override
	public boolean checkChargeInfo() {
		// TODO Auto-generated method stub

		if (TextUtils.isEmpty(((YeepayChargeInfo) payData).getCbURL())) {
			return false;
		}
		return true;
	}

	private void invokePayResponse(int code) {
		LLog.d(TAG, TAG + " pay request successed!");
		if (mWorkingStateListener != null) {
			((YeepayChargeInfo)payData).setResponseCode(code);
			mWorkingStateListener.onPayFinished(
					PayState.LOOVEE_PAY_FINISHED_SUCCESS, payData);
		}
		setPayStarted(false);
	}

	/**
	 * 请求易宝充值
	 * 
	 * @param yeepayUrl
	 *            易宝充值url。如果为空则用缺省
	 * @param info
	 *            充值卡信息，一定要设定我们自己接收易宝回调的服务器地址
	 */
	public void invokeYeepayRecharge(String yeepayUrl,
			final YeepayChargeInfo info) {

		if (TextUtils.isEmpty(info.getCbURL())) {
			throw new ChargeException("server callback url should not null.");
		}

		if (TextUtils.isEmpty(yeepayUrl)) {
			// 使用缺省的易宝充值url
			yeepayUrl = YEEPAY_RECHARGE_URL;
		}
		httpsUtil = null;
		httpsUtil = new HttpsUtils();
		httpsUtil.doRecharge(info, yeepayUrl);
	}

	/**
	 * 用缺省易宝URL执行充值
	 * 
	 * @param info
	 *            充值卡信息，一定要设定我们自己接收易宝回调的服务器地址
	 */
	public void invokeYeepayRecharge(final YeepayChargeInfo info) {

		if (TextUtils.isEmpty(info.getCbURL())) {
			throw new ChargeException("server callback url should not null.");
		}
		httpsUtil = null;
		httpsUtil = new HttpsUtils();
		httpsUtil.doRecharge(info, YEEPAY_RECHARGE_URL);
	}

	/**
	 * 此类用于执行易宝https充值
	 * 
	 * @author Alonso Lee
	 * 
	 */
	public class HttpsUtils {

		private Executor threadPool;

		private X509TrustManager xtm = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain,
					String authType) {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		private HostnameVerifier hnv = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		public HttpsUtils() {
			SSLContext sslContext = null;
			threadPool = Executors.newCachedThreadPool();
			try {
				sslContext = SSLContext.getInstance("TLS");
				X509TrustManager[] xtmArray = new X509TrustManager[] { xtm };
				sslContext.init(null, xtmArray,
						new java.security.SecureRandom());

			} catch (GeneralSecurityException gse) {
				if (mWorkingStateListener != null) {
					mWorkingStateListener.onPayFinished(
							PayState.LOOVEE_PAY_FINISHED_UNSUPPORT,
							payData);
				}
				setPayStarted(false);

			}
			if (sslContext != null) {
				HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
						.getSocketFactory());
			}

			HttpsURLConnection.setDefaultHostnameVerifier(hnv);
		}

		/**
		 * 执行充值
		 * 
		 * @param info
		 *            用户填入的充值信息
		 * @param urlPath
		 *            支付地址
		 * @return
		 */
		public void doRecharge(final YeepayChargeInfo info, final String urlPath) {

			threadPool.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					StringBuffer json = new StringBuffer();
					URL url = null;
					HttpsURLConnection urlCon = null;
					BufferedReader in = null;
					try {

						url = new URL(urlPath);
						urlCon = (HttpsURLConnection) url.openConnection();

						urlCon.setReadTimeout(30000);
						urlCon.setConnectTimeout(30000);
						urlCon.setDoOutput(true);
						urlCon.setDoInput(true);

						String[] arg = new String[20];
						for (int i = 0; i < 20; i++) {
							arg[i] = "";
						}

						// 业务类型
						arg[0] = info.getMd();
						// 商户id
						arg[1] = info.getMerId();
						// 订单号
						arg[2] = /* order */info.getOrderNumber();
						// 充值金额
						arg[3] = info.getMoney();
						// 校验金额开关
						arg[4] = info.getVerifyAmt();
						// 回调接口
						arg[8] = info.getCbURL();
						// 充值卡面额
						arg[10] = info.getCardAmt();
						// 充值卡号
						arg[11] = info.getCardNo();
						// 充值卡密码
						arg[12] = info.getCardPwd();
						// 充值渠道
						arg[13] = info.getFrpId();
						//
						arg[14] = info.getNeedResponse();

						String content = "p0_Cmd=" + arg[0] + "&p1_MerId="
								+ arg[1] + "&p2_Order=" + arg[2] + "&p3_Amt="
								+ arg[3] + "&p4_verifyAmt=" + arg[4]
								+ "&p8_Url=" + arg[8] + "&pa7_cardAmt="
								+ arg[10] + "&pa8_cardNo=" + arg[11]
								+ "&pa9_cardPwd=" + arg[12] + "&pd_FrpId="
								+ arg[13] + "&pr_NeedResponse=" + arg[14];

						String ou = "";
						for (String s : arg) {
							ou += s;
						}
						content += "&hmac="
								+ ALDigestUtil
										.hmacSign(ou,
												"d4z7YU5h4J0QzZot4lWi7lXt344755n1SCjqa6EpD2q109675x5413y976mp");

						LLog.d(TAG,
								"yeepay charge request url : " + url.toString());
						LLog.d(TAG, "yeepay charge request content : "
								+ content);

						DataOutputStream outStream = new DataOutputStream(
								urlCon.getOutputStream());
						outStream.writeBytes(content);
						outStream.flush();
						outStream.close();

						in = new BufferedReader(new InputStreamReader(urlCon
								.getInputStream()));
						String line;
						while ((line = in.readLine()) != null) {
							json.append(line);
						}
						String msg = json.toString();
						LLog.d(TAG, "yeepay charge response : " + msg);
						int code = Integer.parseInt(msg.substring(
								msg.indexOf("r1_Code=") + 8,
								msg.indexOf("r6_Order")));

						if (isUserCanceled) {
							LLog.d(TAG, "user cancelled pay request.ignore...");
						} else {
							invokePayResponse(code);
						}

					} catch (MalformedURLException mue) {
						mue.printStackTrace();
						LLog.e(TAG,
								TAG + " pay request failed: " + mue.toString());
						if (mWorkingStateListener != null) {
							mWorkingStateListener.onPayFinished(
									PayState.LOOVEE_PAY_FINISHED_COMM,
									payData);
						}
						setPayStarted(false);

					} catch (IOException ioe) {
						ioe.printStackTrace();
						LLog.e(TAG,
								TAG + " pay request failed: " + ioe.toString());
						if (mWorkingStateListener != null) {
							mWorkingStateListener.onPayFinished(
									PayState.LOOVEE_PAY_FINISHED_COMM,
									payData);
						}
						setPayStarted(false);

					} catch (Exception e) {
						e.printStackTrace();
						LLog.e(TAG,
								TAG + " pay request failed: " + e.toString());
						if (mWorkingStateListener != null) {
							mWorkingStateListener.onPayFinished(
									PayState.LOOVEE_PAY_FINISHED_UNKOWN_ERROR,
									payData);
						}
						setPayStarted(false);

					} finally {
						if (null != in) {
							try {
								in.close();
								in = null;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						if (null != urlCon) {
							urlCon.disconnect();
							urlCon = null;
						}
					}

				}
			});

		}
	}

}
