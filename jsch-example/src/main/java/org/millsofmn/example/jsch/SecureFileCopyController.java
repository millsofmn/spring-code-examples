package org.millsofmn.example.jsch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@RestController
public class SecureFileCopyController {

    @Autowired
    ScpSessionFactory factory;

    @RequestMapping("/")
    public String index(){
        return "Hello";
    }

    @RequestMapping("/scp")
    public String scf(){

        String fileName1 = "reporting.bed";
        String fileName2 = "target.bed";

        try (ScpClient session = factory.getScpClient()) {

            StringBuilder sb = new StringBuilder();

            File myFile = session.getFile(fileName1, "reporting.bed");
            try(BufferedReader br = new BufferedReader(new FileReader(myFile))){

                String currentLine = null;
                while((currentLine = br.readLine()) != null){
                    sb.append(currentLine).append("<br>");
                }

            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Hello from me";
    }
}
