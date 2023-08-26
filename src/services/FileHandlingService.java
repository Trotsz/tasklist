package services;

import entities.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandlingService {
    public static List<Task> readAllLines(BufferedReader br) throws IOException {
        List<Task> files = new ArrayList<>();

        String line = br.readLine();

        while(line != line) {
            files.add(new Task(line));
            line = br.readLine();
        }

        br.close();
        return files;
    }

    public static void write(BufferedWriter bw, List<Task> tasks, int counter) throws IOException {
        for(Task task: tasks) {
            bw.write(counter + ": " + task.getText());
            bw.newLine();
            counter++;
        }
        bw.close();
    }
}
