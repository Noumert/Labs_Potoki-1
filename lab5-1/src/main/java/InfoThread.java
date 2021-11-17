import java.util.List;

public class InfoThread extends Thread{
    private final List<SMOQueue> smoQueues;
    private final int customers;


    public InfoThread(List<SMOQueue> smoQueues, int customers){
        this.smoQueues = smoQueues;
        this.customers = customers;
    }

    public synchronized void showInfo(){
        smoQueues.forEach(smoQueue -> {
            System.out.println("Avr length: " + smoQueue.getAvrLength() + " Fail Chance: " + smoQueue.getFailChance());
        });
        System.out.println();
    }

    private boolean testIsFinish() {
        int i = 0;
        for (SMOQueue s : smoQueues) {
            if (s.ok.get() == customers) {
                i++;
            }
        }
        return i == smoQueues.size();
    }

    @Override
    public void run() {
        do{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            showInfo();
        }while (!testIsFinish());
    }
}
