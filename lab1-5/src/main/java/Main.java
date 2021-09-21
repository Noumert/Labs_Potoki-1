public class Main {
    static volatile boolean isPrinted = false;

    public static void main(String[] args) throws InterruptedException {
        //async
        CharacterPrinter characterPrinter1 = new CharacterPrinter('-');
        CharacterPrinter characterPrinter2 = new CharacterPrinter('|');
        characterPrinter1.start();
        characterPrinter2.start();

        characterPrinter1.join();
        characterPrinter2.join();

        System.out.println("00000000000000000000000000000000000000000000000000000000");
        //sync
        String lock = "lock";

        SyncCharacterPrinter syncCharacterPrinter1 = new SyncCharacterPrinter('-', lock);
        syncCharacterPrinter1.setName("-");
        SyncCharacterPrinter syncCharacterPrinter2 = new SyncCharacterPrinter('|', lock);
        syncCharacterPrinter2.setName("|");
        syncCharacterPrinter1.start();
        syncCharacterPrinter2.start();
    }
}
