package eis.exceptions;

/**
 * This action is thrown if an attempt to perform an action has failed. An
 * action could fail if the action is not supported by the environment, if it
 * has wrong parameters, or if it cannot be executed because of the state of the
 * environment.
 * 
 * @author tristanbehrens
 * 
 */
public class ActException extends EnvironmentInterfaceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9092788356656103070L;

	/** No type. */
	public static final int NOTSPECIFIC = 0;

	/** if the agent is not registered */
	public static final int NOTREGISTERED = 1;

	/** if the agent has no entities */
	public static final int NOENTITIES = 2;

	/** if there is a wrong entity */
	public static final int WRONGENTITY = 3;

	/** if the action is not supported by the type */
	public static final int NOTSUPPORTEDBYENVIRONMENT = 4;

	/** if the action is not supported by the type */
	public static final int NOTSUPPORTEDBYTYPE = 5;

	/** if the action is not supported by the type */
	public static final int NOTSUPPORTEDBYENTITY = 6;

	/** if the syntax is wrong */
	// public static final int WRONGSYNTAX = 7;

	/** if the action cannot be executed */
	public static final int FAILURE = 7;

	/** the type */
	private int type = NOTSPECIFIC;

	/**
	 * @param string
	 *            the error message
	 */
	public ActException(String string) {
		super(string);
	}

	/**
	 * @param message
	 *            the error message
	 * @param cause
	 *            the cause (possibly null)
	 */
	public ActException(String message, Throwable cause) {
		super(message);

		this.initCause(cause);

	}

	/**
	 * @param string
	 *            the error message
	 * @param type
	 *            the type of error
	 */
	public ActException(String string, int type) {
		super(string);

		setType(type);

	}

	public ActException(int type) {
		super("");

		setType(type);

	}

	/**
	 * @param message
	 *            the error message
	 * @param type
	 *            the type of error
	 */
	public ActException(int type, String message) {
		super(message);

		setType(type);

	}

	/**
	 * 
	 * @param type
	 *            type of the error
	 * @param message
	 *            the error message
	 * @param cause
	 *            the cause of the error
	 */
	public ActException(int type, String message, Throwable cause) {
		super(message);

		setType(type);
		initCause(cause);

	}

	/**
	 * @return type
	 */
	public int getType() {

		return type;

	}

	/**
	 * @param type
	 *            the new type of the error
	 */
	public void setType(int type) {

		if (type < 0 || type > 7) {

			assert false : "Type \"" + type + "\" not supported.";
			type = 0;

		}

		this.type = type;

	}

	public String toString() {

		String ret = "";

		String strType = "";
		if (type == NOTSPECIFIC) {
			strType = "not specific";
		} else if (type == NOTREGISTERED) {
			strType = "not registered";
		} else if (type == NOENTITIES) {
			strType = "no entities";
		} else if (type == WRONGENTITY) {
			strType = "wrong entity";
		} else if (type == NOTSUPPORTEDBYENVIRONMENT) {
			strType = "not supported by environment";
		} else if (type == NOTSUPPORTEDBYTYPE) {
			strType = "not supported by type";
		} else if (type == NOTSUPPORTEDBYENTITY) {
			strType = "not supported by entity";
		} else if (type == FAILURE) {
			strType = "failure";
		}

		ret += "ActException type=\"" + strType + "\"";

		if (getMessage() != null)
			ret += " message=\"" + getMessage() + "\"";

		if (getCause() != null)
			ret += " cause=\"" + getCause() + "\"";

		return ret;

	}

}
