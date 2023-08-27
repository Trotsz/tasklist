package services;

import entities.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandlingService {
    public static List<Task> readAllLines(BufferedReader br) throws IOException {
        List<Task> tasks = new ArrayList<>();

        String line = br.readLine();

        while(line != null) {
            tasks.add(new Task(line));
            line = br.readLine();
        }

        br.close();
        return tasks;
    }

    public static void write(BufferedWriter bw, List<Task> tasks) throws IOException {
        for(Task task: tasks) {
            String text = task.getText().split(":").length > 1 ? task.getText().split(":")[1] : task.getText();
            bw.write((tasks.indexOf(task) + 1) + ": " + text.strip());
            bw.newLine();
        }
        bw.close();
    }
}
