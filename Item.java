package StoreSimulation;

public class Item {
    private int id;
    private String name;
    private double price;

    public Item(int id, double price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }
    
    public String getName() {
    	return name;
    }
}

