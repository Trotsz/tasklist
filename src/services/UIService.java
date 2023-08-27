package services;

import entities.Task;
import java.util.List;

public class UIService {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printLines(List<Task> tasks) {
        for(Task task : tasks) {
            System.out.println(task.getText());
        }
    }
}
