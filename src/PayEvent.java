/**
 * <h2>Pay Event</h2>
 * This class is used to implement the event when a customer pays for the item.
 *
 */
public class PayEvent extends Event{

	private CustomerGroup customerGroup;
	
	/**
	 * Constructor for the Pay Event class 
	 * @param time used to keep track of the time the group arrived to pay for the item
	 * @param group used to keep track of the group that arrived to pay for the item
	 */
	public PayEvent(int time, CustomerGroup group) {
		super(time);
		this.customerGroup = group;
	}
	
	/**
	 * This method is used to process the event.
	 * The shopModel first invokes the pay method in the ShopModel class,
	 * it then proceeds to schedule a Leave Event 'n' unit times after the current event.
	 * @param shopModel It is the reference to the ShopModel object
	 * @param scheduler It is the reference to the Simulator object
	 */
	@Override
	public void process(ShopModel shopModel, IScheduler scheduler) {
		shopModel.pay(getTime(), customerGroup);
		scheduler.schedule(new LeaveEvent(getTime() + 1, customerGroup));
	}
}
