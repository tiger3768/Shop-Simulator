# Shop Simulation Using Multithreading

**Tech Used:** Java

This project uses Java to simulate the working of a shop environment with multithreaded components.

## Components

The simulation is divided into the following main components:

1. **Customer** (`Runnable`)
2. **Cashier**
3. **Store**
4. **Items**

## Flow

Each **Customer** follows this workflow:

1. **Enter**: Attempts to enter the store.
2. **Browse**: Browses available items and creates a shopping list.
3. **Checkout**: Proceeds to checkout if a cashier is available. If all cashiers are busy, the customer waits.
4. **Exit**: Leaves the store after checkout or if their patience expires.

## Key Feature: Patience Meter

- Each customer has a **patience value** which determines the maximum time they will wait:
  - To **enter** the store if it's at capacity.
  - To **checkout** if all cashiers are busy.
- If the waiting period exceeds their patience, the customer will leave the store without shopping.

---

