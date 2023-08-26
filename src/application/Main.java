package application;

import services.FileHandlingService;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.*;
import entities.Task;

public class Main {
    public static void main(String [] args) {
        Locale.setDefault(Locale.US);
        FileSystemView view = FileSystemView.getFileSystemView();
        File homeTaskDir = new File(view.getHomeDirectory().toString() + "\\Tasks");

        BufferedReader br = null;
        BufferedWriter bw = null;

        while(true) {
            try(Scanner sc = new Scanner(System.in)) {
                char option;

                if(!homeTaskDir.exists()) {
                    boolean result = homeTaskDir.mkdir();
                    if(!result) throw new Exception("There was an error while creating the folder");
                }

                for(File file : homeTaskDir.listFiles()) {
                    System.out.println(file.getName());
                }

                System.out.println("Create(C) | Edit(E) | Quit(Q)");
                option = sc.nextLine().toUpperCase().charAt(0);

                boolean append = true;

                if(option == 'C') {
                    append = false;
                } else if(option == 'E') {
                    append = true;
                } else if(option == 'Q') {
                    System.exit(0);
                } else {
                    throw new InputMismatchException("The option '" + option + "' does not exist.");
                }

                System.out.print("Name of the file you want to create/edit: ");
                String fileName = sc.nextLine();

                bw = new BufferedWriter(new FileWriter(homeTaskDir + "\\" + fileName + ".txt", append));
                br = new BufferedReader(new FileReader(homeTaskDir + "\\" + fileName + ".txt"));

                List<Task> tasks = FileHandlingService.readAllLines(br);

                int counter = 1;

                for(Task task : tasks) {
                    System.out.println(task.getText());
                    counter++;
                }

                System.out.println("Write(W) | Check(C) | Delete(D) | Back(B)");
                option = sc.nextLine().toUpperCase().charAt(0);

                if(option == 'W') {
                    while(true) {
                        System.out.println("Quit (Q)");
                        System.out.print("Task: ");
                        String text = sc.nextLine();

                        if(text.equalsIgnoreCase("Q")) break;

                        tasks.add(new Task(text));
                    }

                    // FIXME: IT'S NOT APPENDING TASK NUMBERS PROPERLY

                    FileHandlingService.write(bw, tasks, counter);
                }

            } catch(FileNotFoundException e) {
                System.out.println("File not found!");
            } catch(IOException e) {
                System.out.println("Error: " + e.getMessage());
            } catch(Exception e) {
                System.out.println("Error:" + e.getMessage());
                break;
            } finally {
                try {
                    if(br != null) br.close();
                    if(bw != null) bw.close();
                } catch(IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
}