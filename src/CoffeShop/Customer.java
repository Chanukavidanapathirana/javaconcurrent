package CoffeShop;

public class Customer implements  Runnable{
    private CoffeeShop coffeeShop;
    private String order;

    public Customer(CoffeeShop coffeeShop, String order){
        this.coffeeShop = coffeeShop;
        this.order = order;
    }

    public void run(){
           coffeeShop.placeOrder(order);
    }
}
