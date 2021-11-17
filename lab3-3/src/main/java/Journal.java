import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Journal {
    public List<List<Integer>> students;
    Random random;

    public Journal(){
        students = List.of(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        random = new Random();
    }

    public synchronized void putMark(int student){
        students.get(student).add(random.nextInt(101));
    }
}
