package CoffeShop;

public class Barista implements Runnable {
    private CoffeeShop coffeeShop;

    public Barista(CoffeeShop coffeeShop) {
        this.coffeeShop = coffeeShop;
    }

    public void run() {
        while (!(coffeeShop.getOrderQueue().isEmpty())|| !(coffeeShop.isAllOrdersPlaced())){ //If the queue is not empty and customers still coming(customer threads still creating and running) preparing the orders
            try {
                coffeeShop.prepareOrder();
                Thread.sleep(1000); //simulateSome Task
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
}
