package CoffeShop;

import java.util.LinkedList;
import java.util.Queue;

public class CoffeeShop {

    private boolean allOrdersPlaced = false; // Flag to indicate all customer threads are done
    private Queue<String> orderQueue = new LinkedList<>();
    private int capacity;

    public CoffeeShop(int capacity){
        this.capacity = capacity;
    }

    public boolean isAllOrdersPlaced() {
        return allOrdersPlaced;
    }

    public void setAllOrdersPlaced(boolean allOrdersPlaced) {
        this.allOrdersPlaced = allOrdersPlaced;
    }
    public Queue<String> getOrderQueue() {
        return orderQueue;
    }

    public synchronized void placeOrder(String order) {
        try {
            Thread.sleep(1000); //simulate sometime
            while (orderQueue.size() == capacity) { //queue is full
                System.out.println("Queue is full. Waiting to place order " + order);
                this.wait();
            }
            orderQueue.add(order);
            System.out.println("Order successfully placed: " + order );
            this.notifyAll();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public synchronized void prepareOrder(){
        try{
            while(orderQueue.isEmpty() && !(allOrdersPlaced)){ // when the queue is empty and still customers are coming(customer thread running) we put barista thread to wait state
                System.out.println("Waiting for a order");
                this.wait();
            }
            String order = orderQueue.poll();
            if(order != null) {
                System.out.println(order + " Order successfully prepared by " + Thread.currentThread().getName());
            }
                this.notifyAll();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
