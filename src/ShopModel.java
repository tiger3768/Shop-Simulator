import java.util.ArrayList;
import java.util.Formatter;

/**
 * <h2>Shop Model</h2>
 * This class is used to implement a shop.
 *
 */
public class ShopModel {

	private ArrayList<CustomerGroup> groups; //To keep track of the groups currently in the shop
	private ArrayList<CustomerGroup> history; //To keep track of groups that arrived at the shop
	private int nextId; //To provide incoming group with a unique identifier
	private int numServed; //To keep track of the customers served
	private int lostBusiness; //To keep track of customers lost
	private int spacesAvailable; //To define the maximum space in the shop
	private int numGroups; //To keep track of the number of groups currently in the shop
	
	/**
	 * Constructor for the ShopModel class
	 * @param max to define the maximum serving space in the shop
	 */
	public ShopModel(int max) {
		this.spacesAvailable = max;
		this.lostBusiness = 0;
		this.numServed = 0;
		groups = new ArrayList<>();
		history = new ArrayList<>();
		nextId = 0;
		numGroups = 0;
	}
	public ShopModel() {
		groups = new ArrayList<>();
		history = new ArrayList<>();
		nextId = 0;
		numGroups = 0;
	}
	
	/**
	 * Getter method for numServed
	 * @return int numServed
	 */
	public int getNumberServed() {
		return numServed;
	}
	
	/**
	 * Getter method for lostBusiness
	 * @return int lostBusiness
	 */
	public int getLostBusiness() {
		return lostBusiness;
	}
	
	/**
	 * This method is used to add a CustomerGroup to the groups list.
	 * This method also keeps track of numGroups and spacesAvailable.
	 * @param g represents the group entering the shop
	 */
	public void addGroup(CustomerGroup g) {
		groups.add(g);
		numGroups = numGroups + 1;
		spacesAvailable -= g.getNumberInGroup();
	}
	
	/**
	 * This method is used to log the groups that arrived at the shop.
	 * @param g represents the group arriving at the shop
	 */
	public void logGroup(CustomerGroup g) {
		history.add(g);
	}
	
	/**
	 * Used to return a unique identifier for a customer group.
	 * @return int nextId
	 */
	public int getNextId() {
		return nextId++;
	}
	
	/**
	 * Used to format the groups that are present in the shop to the formatter object.
	 * @param output It is the Formatter object used to store statistics 
	 */
	public void showGroups(Formatter output) {
		output.format("\nThe following groups are in the shop:\n");
		output.format("=====================================\n");
		for(CustomerGroup g : groups) {
			output.format("Group %s (%s people) arrived at t = %s\n", 
					g.getId(), g.getNumberInGroup(), g.getArrivalTime());
		}
	}
	
	/**
	 * Used to format the groups that arrived at the shop to the formatter object.
	 * @param output It is the Formatter object used to store statistics 
	 */
	public void showLog(Formatter output) {
		output.format("\nThe following groups are in the history/log:\n");
		output.format("============================================\n");
		for(CustomerGroup g : history) {
			output.format("Group %s (%s people) arrived at t = %s\n", 
					g.getId(), g.getNumberInGroup(), g.getArrivalTime());
		}
	}
	
	/**
	 * This method is used to check weather the incoming group can enter the shop or not.
	 * In case the group can't enter the shop, the lostBusiness value is increased by the
	 * number of people present in the group.
	 * @param t the arriving time
	 * @param g the arriving group details
	 * @return boolean
	 */
	public boolean canEnter(int t, CustomerGroup g) {
		if(spacesAvailable >= g.getNumberInGroup()) return true;
		lostBusiness += g.getNumberInGroup();
		return false;
	}
	
	/**
	 * This method is used to print the arrival of group to collect items.
	 * @param time the time at which the group arrived
	 * @param g the group that arrived
	 */
	public void collectItems(int time,  CustomerGroup g) {
		System.out.printf("t = %s: Purchases collected by Group %s\n", time, g.getId());
	}
	
	/**
	 * This method is used to print the arrival of group to pay for the items.
	 * It also increases the numServed (number of served customers).
	 * @param time the time at which the group arrived
	 * @param g the group that arrived
	 */
	public void pay(int time, CustomerGroup g) {
		numServed += g.getNumberInGroup();
		System.out.printf("t = %s: Group %s customer has paid\n", time, g.getId());
	}
	
	/**
	 * This method is used to print that the group has left the shop.
	 * It removes the group from the groups list,
	 * it also increases the spacesAvailable value and decreases the numGroups value.
	 * @param timetime the time at which the group left
	 * @param g the group that left
	 */
	public void leave(int time, CustomerGroup g) {
		System.out.printf("t = %s: Group %s leaves the shop\n", time, g.getId());
		groups.remove(g);
		numGroups = numGroups - 1;
		spacesAvailable += g.getNumberInGroup();
	}
}
