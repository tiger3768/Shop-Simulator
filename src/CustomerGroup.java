/**
 * <h2>Customer Group</h2>
 * This is the entity class for a customer group.
 */
public class CustomerGroup {

	private int id;
	private int numberInGroup;
	private int arrivalTime;
	
	/**
	 * Constructor for the customer group.
	 * @param id the unique identifier for the group
	 * @param number the number of people in the group
	 * @param time the arrival time of the group
	 */
	public CustomerGroup(int id, int number, int time) {
		this.id = id;
		numberInGroup = number;
		arrivalTime = time;
	}
	
	/**
	 * getter method for Id
	 * @return int id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * getter method for number of people in the group
	 * @return int numberInGroup
	 */
	public int getNumberInGroup() {
		return numberInGroup;
	}
	
	/**
	 * getter method for arrival time
	 * @return int arrivalTime
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}
	
	/**
	 * To string method for the CustomerGroup class.
	 */
	@Override
	public String toString() {
		return "CustomerGroup [id=" + id + ", numberInGroup=" + numberInGroup + ", arrivalTime=" + arrivalTime + "]";
	}
}
