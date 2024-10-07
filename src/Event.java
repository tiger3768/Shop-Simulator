import java.util.Random;

/**
 * <h2>Event</h2>
 * This is the abstract class for all the events (i.e Arrival, Collect, Pay & Leave).
 *
 */
public abstract class Event {
	
	private int time;
	private static Random generator = new Random(1); //Use of seed 1
	
	/**
	 * Constructor for the Event class
	 * @param time used to keep track of the time unit the event occurred
	 */
	public Event(int time) {
		this.time = time;
	}
	public Event() {}
	
	/**
	 * getter method for time
	 * @return int time
	 */
	public int getTime() {
		return this.time;
	}
	
	/**
	 * getter method for generator
	 * @return Random generator
	 */
	public Random getGenerator() {
		return generator;
	}
	
	/**
	 * Abstract method, used to process the event, to be overridden by the child class.
	 * @param shopModel 
	 * @param scheduler
	 */
	public abstract void process(ShopModel shopModel, IScheduler scheduler);
	
	
}
