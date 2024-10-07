import java.util.ArrayList;

/**
 * <h2>Simulator class</h2>
 * This class is used to implement the Simulator .
 * It contains an events queue to keep track of all the events,
 * a clock variable to store the time unit,
 * and a ShopModel object.
 */
public class Simulator implements IScheduler{
	
	private ArrayList<Event> events;
	private int clock;
	private ShopModel model;
	
	/**
	 * Constructor for the Simulator class
	 * Initializes time to 0
	 * @param model the ShopModel object
	 */
	public Simulator(ShopModel model) {
		this.clock = 0;
		this.model = model;
	}
	
	/**
	 * Used to initialize the events queue.
	 * @param events ArrayList<Event> queue
	 */
	public void initialize(ArrayList<Event> events) {
		this.events = events;
	}
	
	/**
	 * This method is used to run the simulation.
	 * It first check if the events queue is empty or not initialized yet,
	 * in case of no exceptions, it fetches the head of the queue and the time unit of the 
	 * event, it then runs a loop till the clock time is less than or equal to the stop time.
	 * It processes the event, again removes the head of the events queue and updates the 
	 * current clock time, till the breaking condition is invoked.
	 * @param stopTime denotes the max time unit limit to run the simulation 
	 */
	public void run(int stopTime) {     
        if ((events == null)|| events.isEmpty()) return;
        Event e = events.remove(0);
        clock = e.getTime();
        while (clock <= stopTime) {
            e.process(model, this);
            e = events.remove(0);
            clock = e.getTime();
        }
    }
	
	/**
	 * This method is used to schedule an event to the event queue.
	 * It finds the correct position for the event according to its time, and enqueues it.
	 * @param event denotes the event to be scheduled
	 */
	@Override
	public void schedule(Event event) {
		int i = 0;
		for( ; i < events.size(); i++) if(events.get(i).getTime() > event.getTime()) break;
		events.add(i, event);
	}
}
