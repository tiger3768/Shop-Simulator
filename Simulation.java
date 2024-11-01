package StoreSimulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Simulation {
    private static final Random random = new Random();
    private static final String[] itemNames = {"Apples", "Bananas", "Milk", "Bread", "Cheese", "Eggs", "Juice", "Chicken", "Rice", "Pasta"};
    private static final String[] customerNames = {"Alice", "Bob", "Charlie", "Diana", "Edward", "Fiona", "George", "Hannah", "Ian", "Julia"};
    private static final String[] cashierNames = {"Kevin", "Liam", "Mason", "Nora", "Olivia", "Sophia", "Ethan", "Chloe"};

    public static void main(String[] args) throws InterruptedException {
        int storeCapacity = random.nextInt(16) + 5;
        int numItems = random.nextInt(9) + 2;
        Vector<Integer> availableStock = new Vector<>();
        HashMap<Integer, Item> itemDetails = new HashMap<>();
        
        for (int i = 0; i < numItems; i++) {
            availableStock.add(random.nextInt(16) + 5);
            double price = random.nextDouble() * 50 + 10;
            String itemName = itemNames[random.nextInt(itemNames.length)];
            itemDetails.put(i, new Item(i, price, itemName));
        }

        List<Cashier> cashiers = new ArrayList<>();
        int numCashiers = random.nextInt(4) + 2;
        for (int i = 0; i < numCashiers; i++) {
            String cashierName = cashierNames[random.nextInt(cashierNames.length)];
            cashiers.add(new Cashier(i + 1, cashierName));
        }

        Store store = new Store(storeCapacity, availableStock, cashiers, itemDetails);

        int numCustomers = random.nextInt(48) + 3;
        List<Thread> customerThreads = new ArrayList<>();

        for (int i = 0; i < numCustomers; i++) {
            int customerId = i + 1;
            int patience = random.nextInt(100) + 1;
            Integer[] shoppingList = new Integer[random.nextInt(5) + 1];
            for (int j = 0; j < shoppingList.length; j++) {
                shoppingList[j] = random.nextInt(numItems);
            }
            String customerName = customerNames[random.nextInt(customerNames.length)];
            Customer customer = new Customer(customerId, shoppingList, store, patience, customerName);
            Thread customerThread = new Thread(customer);
            customerThreads.add(customerThread);
        }
        
        for(Thread thread : customerThreads) thread.start();
        for(Thread thread : customerThreads) thread.join();
        
        store.closeLog();
    }
}
