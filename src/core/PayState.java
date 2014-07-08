package core;

public enum PayState {

	/* 用户取消充值操作 */
	LOOVEE_PAY_FINISHED_USER_CANCEL,
	/* 成功提交充值 */
	LOOVEE_PAY_FINISHED_SUCCESS,
	/* 通信错误 */
	LOOVEE_PAY_FINISHED_COMM,
	/* 不支持该充值方式 */
	LOOVEE_PAY_FINISHED_UNSUPPORT,
	/* 充值未知错误 */
	LOOVEE_PAY_FINISHED_UNKOWN_ERROR,
	/* 充值超时 */
	LOOVEE_PAY_FINISHED_TIME_OUT,
}
