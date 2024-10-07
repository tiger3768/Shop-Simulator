import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;

/**
 * <h1>Shop Simulator</h1>
 *
 */
public class Demo {
	private static Formatter output;
	
	/**
	 * This method is used to initialize the Formatter.
	 * @param filename This is the name of the file, in which data is to be stored
	 * @exception FileNotFoundException on incorrect file name
	 */
	public static void openFile(String filename)
    {
        {
            try
            {
                output = new Formatter(filename);
            }
            catch(FileNotFoundException fileNotFoundException)
            {
                System.err.println("Error");
            }
        }
    }
	/**
	 * This is the main method which works as the driver code for the shop simulation.
	 * Here, it first initialize the ShopModel class, followed by the Simulator class.
	 * It uses ArrayList to initialize the events list in the Simulator class.
	 * @param args Unused
	 */
	public static void main(String[] args) {
		
		openFile("statistics.txt"); //invoking openFile method to get the Formatter object
		ShopModel model = new ShopModel(8); 
		Simulator simulator = new Simulator(model);
		ArrayList<Event> eventQueue = new ArrayList<>();
		eventQueue.add(new ArrivalEvent(0));
		simulator.initialize(eventQueue);
		simulator.run(20); //Running the simulation for 20 time units
		output.format("Statistics\n");
		output.format("==========\n");
		output.format("\nThe number of people served = %s\n", model.getNumberServed());
		output.format("The lost business = %s people\n", model.getLostBusiness());
		model.showGroups(output);
		model.showLog(output);
		output.flush(); //writing buffered output to the file
		output.close(); //closing and saving the changes made to the file
		
	}
}
