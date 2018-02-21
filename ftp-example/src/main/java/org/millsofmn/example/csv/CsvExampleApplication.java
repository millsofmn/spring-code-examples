package org.millsofmn.example.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvExampleApplication {
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext ctx = SpringApplication.run(CsvExampleApplication.class, args);

        System.out.println("Hello World from Spring Boot CSV Example Application!");

        CSVPrinter csvPrinter = new CSVPrinter(new StringBuilder(), CSVFormat.RFC4180);

        for(int i = 0; i < 100; i++){
            List<String> record = new ArrayList<>();
            record.add(RandomStringUtils.randomAlphanumeric(16));
            record.add(RandomStringUtils.randomAlphanumeric(16));
            record.add(RandomStringUtils.randomAlphanumeric(16));
            record.add(RandomStringUtils.randomAlphanumeric(16));
            record.add(RandomStringUtils.randomAscii(16));

            csvPrinter.printRecord(record);
        }


        String result = ((StringBuilder)csvPrinter.getOut()).toString();
        System.out.println(result);
    }
}
