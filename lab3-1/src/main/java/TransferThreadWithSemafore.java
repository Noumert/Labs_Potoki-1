import java.util.concurrent.Semaphore;

class TransferThreadWithSemafore extends Thread {
    private BankI bank;
    private int fromAccount;
    private int maxAmount;
    private static final int REPS = 1000;
    Semaphore semaphore;

    public TransferThreadWithSemafore(BankI b, int from, int max,Semaphore semaphore){
        bank = b;
        fromAccount = from;
        maxAmount = max;
        this.semaphore = semaphore;
    }
    @Override
    public void run(){
        while (true) {
            try {
                semaphore.acquire();
            for (int i = 0; i < REPS; i++) {
                int toAccount = (int) (bank.size() * Math.random());
                int amount = (int) (maxAmount * Math.random()/REPS);
                bank.transfer(fromAccount, toAccount, amount);
            }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        }
    }
}