package eis.exceptions;

/**
 * This exception is thrown if an attempt to manipulate the
 * agents-entities-relation has failed.
 * 
 * @author tristanbehrens
 * @modified W.Pasman 14mar13 new constructor allowing exception chaining.
 * 
 */
public class RelationException extends EnvironmentInterfaceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 176485209025703866L;

	/**
	 * @param string
	 *            the error message
	 */
	public RelationException(String string) {
		super(string);
	}

	/**
	 * @param string
	 *            the error message
	 * @param cause
	 *            the cause of the error
	 */
	public RelationException(String string, Exception cause) {
		super(string, cause);
	}

}
