package eis.eis2java.environment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import eis.EIDefaultImpl;
import eis.eis2java.handlers.ActionHandler;
import eis.eis2java.handlers.DefaultActionHandler;
import eis.eis2java.handlers.DefaultPerceptHandler;
import eis.eis2java.handlers.PerceptHandler;
import eis.exceptions.ActException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.Parameter;
import eis.iilang.Percept;

/**
 * Base implementation for environments that want to work with automated percept
 * and action discovery in EIS2Java.
 * 
 * 
 * 
 * @author Lennard de Rijk
 * @author M.P. Korstanje * 11-02-2011: MP - Implemented synchronization feature
 *         to work with asynchronous entities.
 */
public abstract class AbstractEnvironment extends EIDefaultImpl {
	private static final long serialVersionUID = 1L;
	/** Map of entity names to objects representing those entities */
	private final Map<String, Object> entities = new HashMap<String, Object>();
	/** Maps an entity to an action handler */
	private final Map<String, PerceptHandler> perceptHandlers = new HashMap<String, PerceptHandler>();
	/** Maps a Class to a map of action names and methods */
	private final Map<String, ActionHandler> actionHandlers = new HashMap<String, ActionHandler>();

	/**
	 * Couples a name to an entity and parses it's annotations for percepts and
	 * actions.
	 * 
	 * @param name
	 *            The name of the entity.
	 * @param entity
	 *            The entity itself.
	 * @param <T>
	 *            the type of the entity
	 * @throws EntityException
	 *             if the entity could not be added.
	 */
	public final <T> void registerEntity(String name, T entity) throws EntityException {
		this.registerEntity(name, entity, new DefaultActionHandler(entity), new DefaultPerceptHandler(entity));
	}

	/**
	 * Couples a name to an entity and parses it's annotations for percepts and
	 * actions using the specified handlers.
	 * 
	 * @param name
	 *            The name of the entity.
	 * @param entity
	 *            The entity itself.
	 * @param actionHandler
	 *            the associated action handler.
	 * @param <T>
	 *            the type of the entity
	 * 
	 * @param perceptHandler
	 *            the associated percept handler.
	 * @throws EntityException
	 *             if the entity could not be added.
	 */
	public final <T> void registerEntity(String name, T entity, ActionHandler actionHandler,
			PerceptHandler perceptHandler) throws EntityException {
		actionHandlers.put(name, actionHandler);
		perceptHandlers.put(name, perceptHandler);

		entities.put(name, entity);
		addEntity(name);
	}

	/**
	 * Couples a name to an entity and parses it's annotations for percepts and
	 * actions.
	 * 
	 * @param <T>
	 *            the type of the entity
	 * 
	 * @param name
	 *            The name of the entity.
	 * @param type
	 *            The type of entity
	 * @param entity
	 *            The entity itself.
	 * @throws EntityException
	 *             if the entity could not be added.
	 */
	public final <T> void registerEntity(String name, String type, T entity) throws EntityException {
		this.registerEntity(name, type, entity, new DefaultActionHandler(entity), new DefaultPerceptHandler(entity));
	}

	/**
	 * Couples a name to an entity and parses it's annotations for percepts and
	 * actions using the specified handlers.
	 * <p>
	 * Your environment must be able to handle
	 * {@link #getAllPercepts(String, String...)} and
	 * {@link #getAllPerceptsFromEntity(String)} when this is called.
	 * 
	 * 
	 * @param name
	 *            The name of the entity.
	 * @param type
	 *            The type of entity
	 * @param entity
	 *            The entity itself.
	 * @param actionHandler
	 *            the associated action handler.
	 * @param <T>
	 *            the type of the entity
	 * 
	 * @param perceptHandler
	 *            the associated percept handler.
	 * @throws EntityException
	 *             if the entity could not be added.
	 */

	public final <T> void registerEntity(String name, String type, T entity, ActionHandler actionHandler,
			PerceptHandler perceptHandler) throws EntityException {
		actionHandlers.put(name, actionHandler);
		perceptHandlers.put(name, perceptHandler);

		entities.put(name, entity);
		addEntity(name, type);
	}

	@Override
	public final void deleteEntity(String name) throws EntityException, RelationException {
		super.deleteEntity(name);
		entities.remove(name);
		actionHandlers.remove(name);
		perceptHandlers.remove(name);
	}

	/**
	 * Retrieve an entity for a given name.
	 * 
	 * @param <T>
	 *            The class of entity to return.
	 * @param name
	 *            The name of the entity.
	 * @return the entity behind this name
	 */
	@SuppressWarnings("unchecked")
	public final <T> T getEntity(String name) {
		return (T) entities.get(name);
	}

	@Override
	protected final LinkedList<Percept> getAllPerceptsFromEntity(String name)
			throws PerceiveException, NoEnvironmentException {

		PerceptHandler handler = perceptHandlers.get(name);

		if (handler == null) {
			throw new PerceiveException("Entity with name " + name + " has no handler");
		}

		return handler.getAllPercepts();
	}

	@Override
	protected final boolean isSupportedByEntity(Action action, String name) {
		Object entity = getEntity(name);
		ActionHandler handler = actionHandlers.get(entity);
		return handler.isSupportedByEntity(action);
	}

	@Override
	protected final Percept performEntityAction(String name, Action action) throws ActException {

		ActionHandler handler = actionHandlers.get(name);

		if (handler == null) {
			throw new ActException(ActException.FAILURE, "Entity with name " + name + " has no handler");
		}

		return handler.performAction(action);

	}

	@Override
	public void reset(Map<String, Parameter> parameters) throws ManagementException {
		super.reset(parameters);
		for (PerceptHandler handler : perceptHandlers.values()) {
			handler.reset();
		}
		for (ActionHandler handler : actionHandlers.values()) {
			handler.reset();
		}
	}
}
