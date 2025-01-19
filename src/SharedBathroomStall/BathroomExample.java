package SharedBathroomStall;

public class BathroomExample {

    public static void main(String[] args) {
        Bathroom.initializeStalls(); // Initialize the bathroom stalls

        for (int i = 1; i <= Bathroom.MAX_USERS; i++) {
            String userType = (i % 2 == 0) ? "Student" : "Employee";
            Thread userThread = new Thread(new User(i), userType);
            userThread.start();

            try {
                Thread.sleep(100); // Simulate delay between user arrivals
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
