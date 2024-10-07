/**
 * <h2>IScheduler</h2>
 * This is a functional interface
 */
public interface IScheduler {
	
	/**
	 * Used to schedule an event, to be overridden by the implementing class.
	 * @param e the event
	 */
	public void schedule(Event e);	
}
