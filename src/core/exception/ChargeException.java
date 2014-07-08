/**
 * 
 */
package core.exception;

/**
 * @author alonso lee
 * 
 */
public class ChargeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4271456829277315934L;

	public ChargeException(String detailMessage) {
		super(detailMessage);
	}

	public ChargeException(Throwable throwable) {
		super(throwable);
	}

	public ChargeException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
