/**
 * <h2>Collect Items Event</h2>
 * This class is used to implement the event when a customer collects item from the shop.
 *
 */
public class CollectItemsEvent extends Event{
	
	private CustomerGroup customerGroup;
	/**
	 * Constructor for the CollectItemsEvent class.
	 * @param time used to keep track of the time the group arrived to collect the item
	 * @param group used to keep track of the group that arrived to collect the item
	 */
	public CollectItemsEvent (int time, CustomerGroup group) {
		super(time);
		this.customerGroup = group;
	}
	
	/**
	 * This method is used to process the event.
	 * The shopModel first invokes the collectItems method in the ShopModel class,
	 * it then proceeds to schedule a Pay Event 'n' unit times after the current event.
	 * @param shopModel It is the reference to the ShopModel object
	 * @param scheduler It is the reference to the Simulator object
	 */
	@Override
	public void process(ShopModel shopModel, IScheduler scheduler) {
		shopModel.collectItems(getTime(), customerGroup);
		scheduler.schedule(new PayEvent(getTime() + 4, customerGroup));
	}
	
}
