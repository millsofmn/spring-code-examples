package org.millsofmn.example.ftp;

import java.io.File;
import java.io.IOException;

public class WriteFile {
    public static void main(String[] args){
        try {
            File file = new File("src\\main\\java\\org\\millsofmn\\example\\ftp\\bar.txt");

            if(file.createNewFile()){
                System.out.println("File is created");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
