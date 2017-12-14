package eis.exceptions;

/**
 * This exception is thrown when querying fails.
 * 
 * 
 */
public class QueryException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4100088707624572939L;

	/**
	 * @param string
	 *            the error message
	 */
	public QueryException(String string) {
		super(string);
	}

	/**
	 * @param string
	 *            the error message
	 * @param cause
	 *            the cause of the exception
	 */
	public QueryException(String string, Exception cause) {
		super(string, cause);
	}

}
