package Codtech;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandler {
    public static void main(String[] args) {


        System.out.println("Made by Subhrangsu Samadder ");


        // Create a new file

        File myFile = new File("ExampleFile.txt");
        try {
            myFile.createNewFile();
            System.out.println("File created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // //writing the file

            File myFile1 = new File("ExampleFile.txt");
            try {
                FileWriter fileWriter = new FileWriter("ExampleFile.txt");
                fileWriter.write("This is a sentence for example!");
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        //     //Reading a file

            File myFile2 = new File("ExampleFile.txt");
        try {
            Scanner sc = new Scanner(myFile2);
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                System.out.println(line);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Deleting a file

        File myFile3 = new File("ExampleFile.txt");
        if(myFile3.delete()){
            System.out.println("I have deleted: " + myFile3.getName());
        }
        else{
            System.out.println("Some problem occurred while deleting the file");
        }

    }

}