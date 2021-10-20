import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Journal {
    public List<List<Integer>> students;

    public Journal(){
        students = List.of(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public synchronized void putMark(int student){
        Random random = new Random();
        students.get(student).add(random.nextInt(101));
    }
}
