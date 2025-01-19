package SharedBathroomStall;

public class User implements Runnable {
    private int id;

    public User(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            int stallNumber = Bathroom.acquireStall();
            Thread.sleep(1000); //simulate some sleep time to demonstration purposes
            System.out.println("User " + id + " is using stall " + stallNumber);
            useStall(stallNumber);// Simulate time spent in the bathroom
            Bathroom.releaseStall(stallNumber); //user leaves
            System.out.println("User " + id + " has left stall " + stallNumber);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Simulate stall usage with sleep
    private void useStall(int stallNumber) {
        try {
            int usageTime = (int) (Math.random() * 2000) + 3000;
            Thread.sleep(usageTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
