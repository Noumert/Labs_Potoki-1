public class SyncCharacterPrinter extends Thread {
    private final char symbol;
    private final String lock;


    public SyncCharacterPrinter(char symbol, String lock) {
        this.symbol = symbol;
        this.lock = lock;

    }

    @Override
    public void run() {
        for (int j = 0; j < 50; j++) {
            for (int i = 0; i < 20; i++) {
                synchronized (lock) {
                    if (Main.isPrinted && symbol == '|') {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (!Main.isPrinted && symbol == '-') {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.print(symbol);

                    Main.isPrinted = !Main.isPrinted;

                    lock.notify();

                }

            }
            System.out.println();
        }
    }
}

