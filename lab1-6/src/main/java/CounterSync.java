public class CounterSync {
    int count = 0;
    public synchronized void increment(){count++;}
    public synchronized void decrement(){count--;}
}
