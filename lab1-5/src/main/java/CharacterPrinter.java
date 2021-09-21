public class CharacterPrinter extends Thread {
    char symbol;

    public CharacterPrinter(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public void run() {
        for (int j = 0; j < 50; j++) {
            for (int i = 0; i < 20; i++) {
                System.out.print(symbol);
            }
            System.out.println();
        }
    }
}
