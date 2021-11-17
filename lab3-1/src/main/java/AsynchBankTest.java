public class AsynchBankTest {
    public static final int NACCOUNTS = 200;
    public static final int INITIAL_BALANCE = 10000;
    public static void main(String[] args) {
//        BankI b = new Bank1(NACCOUNTS, INITIAL_BALANCE);
//        int i;
//        for (i = 0; i < NACCOUNTS; i++){
//            TransferThread t = new TransferThread(b, i,
//                    INITIAL_BALANCE);
//            t.setPriority(Thread.NORM_PRIORITY + i % 2);
//            t.start () ;
//        }
        BankI b = new Bank2(NACCOUNTS, INITIAL_BALANCE);
        int i;
        for (i = 0; i < NACCOUNTS; i++){
            TransferThread t = new TransferThread(b, i,
                    INITIAL_BALANCE);
            t.setPriority(Thread.NORM_PRIORITY + i % 2);
            t.start () ;
        }
//        BankI b = new Bank3(NACCOUNTS, INITIAL_BALANCE);
//        int i;
//        Semaphore semaphore = new Semaphore(1);
//        for (i = 0; i < NACCOUNTS; i++){
//            TransferThreadWithSemafore t = new TransferThreadWithSemafore(b, i,
//                    INITIAL_BALANCE,semaphore);
//            t.setPriority(Thread.NORM_PRIORITY + i % 2);
//            t.start () ;
//        }

    }
}