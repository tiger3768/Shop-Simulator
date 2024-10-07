/**
 * <h2>Arrival Event</h2>
 * This class is used to implement a group arriving at the shop.
 * It extends the Event class.
 *
 */
public class ArrivalEvent extends Event{
	/**
	 * Contructor for the ArrivalEvent class
	 * @param time used to keep track of the time at which the event occurred
	 */
	ArrivalEvent(int time){
		super(time);
	}
	
	/**
	 * This method is used to process the event.
	 * The id fetches a unique identifier for the current group,
	 * shopModel first logs the arrival of the group,
	 * then it checks if there is space for all the people in the group to enter the shop,
	 * if there is enough space, the Simulator schedules CollectItemsEvent and adds the
	 * current group to the groups queue in the ShopModel object, it also schedules a 
	 * Collect Items Event 'm' unit times after the current event,
	 * otherwise, it prints the failure message.
	 * Regardless of the condition, the Simulator then schedules another arrival 'n' unit
	 * times after the current event.
	 * @param shopModel It is the reference to the ShopModel object
	 * @param scheduler It is the reference to the Simulator object
	 */
	@Override
	public void process(ShopModel shopModel, IScheduler scheduler) {
		
		int groupSize = getGenerator().nextInt(1, 4);
		int id = shopModel.getNextId();
		CustomerGroup customerGroup = new CustomerGroup(id, groupSize, getTime());
		System.out.printf("t = %s: Group %s <%s people> arrived at the shop\n",
				getTime(), id, groupSize);
		shopModel.logGroup(customerGroup);
		if(shopModel.canEnter(getTime(), customerGroup)) {
			int n = getGenerator().nextInt(3, 9);
			scheduler.schedule(new CollectItemsEvent(getTime() + n, customerGroup));
			System.out.printf("t = %s: Group %s <%s> enters the shop\n",
					getTime(), id, groupSize);
			shopModel.addGroup(customerGroup);
		}
		else {
			System.out.printf("t = %s: Group %s leaves as there is insufficient room for the group\n",
					getTime(), id, groupSize);
		}
		scheduler.schedule(new ArrivalEvent(getTime() + 2));
	}
}
