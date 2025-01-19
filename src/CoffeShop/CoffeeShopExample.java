package CoffeShop;

public class CoffeeShopExample {
    
    public static void main(String[] args){
        int numberOfCustomers = 100;
        int numberOfBaristas = 5;
        CoffeeShop coffeeShop = new CoffeeShop(8);
        Thread[]  customers = new Thread[numberOfCustomers];
        Thread[]  baristas = new Thread[numberOfBaristas];

        //creating customer threads
        for(int i = 0; i < numberOfCustomers; i++){

        customers[i] = new Thread(new Customer(coffeeShop, "Customer "+i));
        }

        //creating baristas thread
        for(int i = 0; i < numberOfBaristas; i++) {

            baristas[i] = new Thread(new Barista(coffeeShop), "Barista " + i);
        }

        for(Thread barista: baristas ){
            barista.setPriority(Thread.MAX_PRIORITY); //set the barista threads priority into max priority to demonstrate fair scenario
            barista.start();
        }
        for(Thread customer: customers ){
            try {
                Thread.sleep(900);
                customer.setPriority(Thread.MIN_PRIORITY);//set the customer thread priority into min priority to demonstrate fair scenario
                customer.start();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        for (Thread customer: customers){
            try {
                customer.join(); // wait until all customers are completing their orders
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        coffeeShop.setAllOrdersPlaced(true);

        for (Thread barista: baristas){
            try {
                barista.join(); //wait until all baristas finish their works
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
