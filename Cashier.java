package StoreSimulation;

import java.util.Map;

public class Cashier{
    private int cashierId;
    private boolean busy;
    private String cashierName;

    public Cashier(int cashierId, String cashierName) {
        this.cashierId = cashierId;
        this.cashierName = cashierName;
        this.busy = false;
    }

    public int getCashierId() {
        return cashierId;
    }

    public boolean isBusy() {
        return busy;
    }

    public void checkout(Customer c, Store s) {
        busy = true;
        double totalPrice = 0.0;
        Map<Integer, Integer> cart = c.getCart();
        StringBuilder log = new StringBuilder();
        log.append("Receipt\n=============================\n");
        log.append("Checkout done by: ").append(cashierName).append("\n");
        log.append("Checkout details for Customer: ").append(c.getName()).append("\n");
        log.append("Items purchased:\n");
        for(Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            int itemId = entry.getKey();
            int quantity = entry.getValue();
            Item item = s.getItemById(itemId);
            double price = item.getPrice();
            double itemTotal = price * quantity;
            totalPrice += itemTotal;
            log.append("Item: ").append(item.getName())
               .append(", Quantity: ").append(quantity)
               .append(", Price per item: ").append(price)
               .append(", Total: ").append(itemTotal).append("\n");
        }

        log.append("Total Price: ").append(totalPrice).append("\n\n");
        log.append("Thank You For Your Purchase!\n=============================\n");
        s.generateReceipt(log.toString(), c.getId());
        busy = false;
    }
}
