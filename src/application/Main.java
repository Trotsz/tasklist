package application;

import services.*;
import entities.Task;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

public class Main {
    public static void main(String [] args) {
        Locale.setDefault(Locale.US);

        FileSystemView view = FileSystemView.getFileSystemView();
        File homeTaskDir = new File(view.getHomeDirectory().toString() + "\\Tasks");

        BufferedReader br = null;
        BufferedWriter bw = null;

        while(true) {
            try(Scanner sc = new Scanner(System.in)) {
                UIService.clearScreen();

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

                if(option == 'Q') {
                    System.exit(0);
                } else if(option != 'C' && option != 'E'){
                    throw new InputMismatchException("The option '" + option + "' does not exist.");
                }

                System.out.print("Name of the file you want to create/edit: ");
                String fileName = sc.nextLine();

                File targetFile = new File(homeTaskDir + "\\" + fileName + ".txt");

                if(option == 'E' && !targetFile.exists()) {
                    throw new FileNotFoundException("You cannot edit a file that does not exist.");
                } else if(option == 'C' && targetFile.exists()) {
                    throw new FileAlreadyExistsException("A file with that name already exists.");
                }

                UIService.clearScreen();

                List<Task> tasks = new ArrayList<>();

                if(option == 'E') {
                    br = new BufferedReader(new FileReader(targetFile));
                    tasks = FileHandlingService.readAllLines(br);
                }

                bw = new BufferedWriter(new FileWriter(targetFile, false));

                UIService.printLines(tasks);

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
                } else if(option == 'C') {
                    while(true) {
                        System.out.println("Quit (Q)");
                        System.out.print("Insert the line number you want to check: ");
                        String lStr = sc.nextLine();

                        if(lStr.equalsIgnoreCase("Q")) break;

                        int l = Integer.parseInt(lStr);

                        String line = tasks.get(l-1).getText();
                        tasks.get(l-1).setText(line + " | \u001B[32mDONE\u001B[0m");

                        UIService.clearScreen();
                        UIService.printLines(tasks);
                    }
                } else if(option == 'D') {
                    while(true) {
                        System.out.println("Quit(Q)");
                        System.out.print("Insert the line number you want to remove: ");
                        String lStr = sc.nextLine();

                        if(lStr.equalsIgnoreCase("Q")) break;

                        int l = Integer.parseInt(lStr);
                        tasks.remove(l-1);

                        UIService.clearScreen();
                        UIService.printLines(tasks);
                    }
                }

                FileHandlingService.write(bw, tasks);
            } catch(FileNotFoundException e) {
                System.out.println("File not found!");
            } catch(IOException e) {
                System.out.println("Error: " + e.getMessage());
            } catch(Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
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