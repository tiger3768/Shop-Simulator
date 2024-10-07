/**
 * <h2>Leave Event</h2>
 * This class is used to implement the event when a customer leaves the shop.
 *
 */
public class LeaveEvent extends Event{

	private CustomerGroup customerGroup;
	
	/**
	 * Constructor for the Leave Event class 
	 * @param time used to keep track of the time the group left
	 * @param group used to keep track of the group that left
	 */
	public LeaveEvent(int time, CustomerGroup group) {
		super(time);
		this.customerGroup = group;
	}
	
	/**
	 * This method is used to process the event.
	 * The shopModel invokes the leave method in the ShopModel class.
	 * @param shopModel It is the reference to the ShopModel object
	 * @param scheduler It is the reference to the Simulator object
	 */
	@Override
	public void process(ShopModel shopModel, IScheduler scheduler) {
		shopModel.leave(getTime(), customerGroup);
	}
	
}
