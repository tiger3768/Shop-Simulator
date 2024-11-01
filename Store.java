package StoreSimulation;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Store {
    private final ReentrantLock entryLock = new ReentrantLock(true);
    private final Condition spaceAvailable = entryLock.newCondition();
    private final ReentrantLock checkoutLock = new ReentrantLock(true);
    private final Condition cashierAvailable = checkoutLock.newCondition();

    private int capacity;
    private int peopleInsideStore;
    private Vector<Integer> itemAvailability;
    private List<Cashier> cashiers;
    private HashMap<Integer, Item> itemDetails;
    private long simulationStartTime;
    private int totalServedCustomers = 0;
    private int lostBusinessDueToSpace = 0;
    private int lostBusinessDueToCashier = 0;
    
    private BufferedWriter logWriter, receiptWriter;

    public Store(int capacity, Vector<Integer> itemAvailability, List<Cashier> cashiers, HashMap<Integer, Item> itemDetails) {
        this.capacity = capacity;
        this.peopleInsideStore = 0;
        this.cashiers = cashiers;
        this.itemAvailability = itemAvailability;
        this.itemDetails = itemDetails;
        this.simulationStartTime = System.currentTimeMillis();
        try {
            this.logWriter = new BufferedWriter(new FileWriter("src\\StoreSimulation\\store_log.txt", false));
            logWriter.write("Store Simulation Log\n====================\n");
        } catch (IOException e) {
            System.err.println("Error initializing log file: " + e.getMessage());
        }
    }

    private long getRelativeTime() {
        return System.currentTimeMillis() - simulationStartTime;
    }

    public boolean enter(Customer c) {
        entryLock.lock();
        try {
            long waitTime = c.getPatience() * 1L; 
            long startTime = System.currentTimeMillis();
            
            while (peopleInsideStore + 1 > capacity) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime >= waitTime) {
                    lostBusinessDueToSpace++;
                    log("Customer " + c.getId() + " gave up waiting to enter due to store capacity limits after " + getRelativeTime() + " ms.\n");
                    return false;
                }

                spaceAvailable.await(waitTime - elapsedTime, TimeUnit.MILLISECONDS);
            }

            peopleInsideStore += 1;
            log("Customer " + c.getId() + " entered the store at " + getRelativeTime() + " ms. People inside now: " + peopleInsideStore + ".\n");
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
        } finally {
            entryLock.unlock(); 
        }
        return false;
    }

    public void browse(Customer c) {
        for (Integer item : c.getListOfItems()) {
            if (itemAvailability.get(item) > 0) {
                itemAvailability.set(item, itemAvailability.get(item) - 1);
                c.getCart().put(item, c.getCart().getOrDefault(item, 0) + 1);
            }
        }
        log("Customer " + c.getId() + " browsed and added items to cart.\n");
    }

    public void checkout(Customer c) {
        Cashier availableCashier;
        long waitTime = c.getPatience() * 10L;
        long startTime = System.currentTimeMillis();

        checkoutLock.lock();
        try {
            while (allCashiersBusy()) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime >= waitTime) {
                    lostBusinessDueToCashier++;
                    log("Customer " + c.getId() + " gave up waiting for a cashier after " + getRelativeTime() + " ms.\n");
                    return;
                }
                cashierAvailable.await(waitTime - elapsedTime, TimeUnit.MILLISECONDS);
            }

            availableCashier = getAvailableCashier();
            log("Customer " + c.getId() + " is being checked out by cashier " + availableCashier.getCashierId() + " at " + getRelativeTime() + " ms.\n");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        } finally {
            cashierAvailable.signalAll();
            checkoutLock.unlock();
        }

        availableCashier.checkout(c, this);
        totalServedCustomers++;
        log("Cashier " + availableCashier.getCashierId() + " completed checkout for Customer " + c.getId() + ".\n");
    }

    private boolean allCashiersBusy() {
        return cashiers.stream().allMatch(Cashier::isBusy);
    }

    private Cashier getAvailableCashier() {
        return cashiers.stream().filter(cashier -> !cashier.isBusy()).findFirst().orElse(null);
    }

    public Item getItemById(int itemId) {
        return itemDetails.get(itemId);
    }

    public void exit(Customer g) {
    	entryLock.lock();
        peopleInsideStore -= 1;
        log("Customer " + g.getId() + " exited the store at " + getRelativeTime() + " ms. People inside now: " + peopleInsideStore + ".\n");
		spaceAvailable.signalAll();
		entryLock.unlock();
    }

    private void log(String message) {
        try {
            logWriter.write(message);
            logWriter.flush();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
    
    public void generateReceipt(String logData, Integer customerId) {
        try {
        	receiptWriter = new BufferedWriter(new FileWriter(String.format("src\\StoreSimulation\\receipt_%d.txt", customerId), false));
            receiptWriter.write(logData);
            receiptWriter.close();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    public void closeLog() {
        try {
        	log("\n");
            log("Total customers served: " + totalServedCustomers + "\n");
            log("Business lost due to store space: " + lostBusinessDueToSpace + "\n");
            log("Business lost due to cashier unavailability: " + lostBusinessDueToCashier + "\n");
            logWriter.close();
        } catch (IOException e) {
            System.err.println("Error closing log file: " + e.getMessage());
        }
    }
}


