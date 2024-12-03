import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Uber {
    private final Semaphore waitingDemocrats;
    private final Semaphore waitingRepublicans;
    private int democratCount;
    private int republicCount;
    private final ReentrantLock lock;

    public Uber() {
        waitingDemocrats = new Semaphore(0);
        waitingRepublicans = new Semaphore(0);
        democratCount = 0;
        republicCount = 0;
        lock = new ReentrantLock();
    }

    public void callUber(PassengerType type) {
        type.accept(new PassengerType.Visitor<Uber, Void>() {
            @Override
            public Void visitDemocrat(Uber uber) throws InterruptedException {
                lock.lock();
                String name = Thread.currentThread().getName();
                democratCount++;
                if(democratCount == 4) {
                    waitingDemocrats.release(3);
                    democratCount -= 4;
                    System.out.printf("Thread: %s 4 democrat taking uber%n", name);
                    lock.unlock();
                } else if(democratCount == 2 && republicCount>=2) {
                    waitingDemocrats.release(1);
                    waitingRepublicans.release(2);
                    democratCount -= 2;
                    republicCount -= 2;
                    System.out.printf("Thread: %s 2 rep and 2 dem taking uber%n", name);
                    lock.unlock();
                } else {
                    System.out.printf("Thread: %s waiting for next passengers%n", name);
                    lock.unlock();
                    waitingDemocrats.acquire();
                    System.out.printf("Thread: %s thread starting after wait%n", name);
                }
                return null;
            }

            @Override
            public Void visitRepublican(Uber uber) throws InterruptedException {
                lock.lock();
                String name = Thread.currentThread().getName();
                republicCount++;
                if(republicCount == 4) {
                    waitingRepublicans.release(3);
                    republicCount -= 3;
                    System.out.printf("Thread: %s 4 republicans taking uber%n", name);
                    lock.unlock();
                } else if(republicCount == 2 && democratCount >= 2) {
                    waitingRepublicans.release();
                    waitingDemocrats.release(2);
                    democratCount -= 2;
                    republicCount -= 2;
                    System.out.printf("Thread: %s 2 rep and 2 dem taking uber%n", name);
                    lock.unlock();
                } else {
                    System.out.printf("Thread: %s waiting for next passengers%n", name);
                    lock.unlock();
                    waitingRepublicans.acquire();
                    System.out.printf("Thread: %s thread starting after wait%n", name);
                }
                return null;
            }
        }, null);
    }

    public void startRide() {
        String name = Thread.currentThread().getName();
        System.out.printf("Thread: %s thread starting ride%n", name);
    }
}
