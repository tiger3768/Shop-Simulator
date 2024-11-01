package StoreSimulation;

import java.util.HashMap;
import java.util.Map;

public class Customer implements Runnable{
	
	private int id, patience;
	private Integer[] listOfItems;
	private Map<Integer, Integer> cart;
	private Store store;
	private String name;
	
	public Customer(int id, Integer[] listOfItems, Store store, int patience, String name) {
		this.id = id;
		this.listOfItems = listOfItems;
		this.store = store;
		this.patience = patience;
		this.cart = new HashMap<>();
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public int getPatience() {
		return patience;
	}

	public Integer[] getListOfItems() {
		return listOfItems;
	}

	public Map<Integer, Integer> getCart() {
		return cart;
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public void run() {
		if(store.enter(this)) {
			store.browse(this);
			store.checkout(this);
			store.exit(this);
		}
	}
	
}
