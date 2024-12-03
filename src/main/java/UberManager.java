import java.util.Random;

public class UberManager {
    Uber uber = new Uber();
    Random random = new Random();
    public void testRepublican(String name) {
        new Thread(() -> {
            long sTime = random.nextInt(50) * 100;
            try {
                Thread.sleep(sTime);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            uber.callUber(PassengerType.REPUBLICAN);
        }, name).start();
    }

    public void testDemocrat(String name) {
        new Thread(() -> {
            long sTime = random.nextInt(50) * 100;
            try {
                Thread.sleep(sTime);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            uber.callUber(PassengerType.DEMOCRAT);
        }, name).start();
    }
}
