package eis.exceptions;

/**
 * This exception is thrown if something unexpected happens when attempting to
 * add or remove an entity.
 * 
 * @author tristanbehrens
 * 
 */
public class EntityException extends EnvironmentInterfaceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3853560667774374410L;

	public EntityException(String string) {
		super(string);
	}

}
