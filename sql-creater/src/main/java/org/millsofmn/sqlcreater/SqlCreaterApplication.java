package org.millsofmn.sqlcreater;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.google.common.base.Strings;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SqlCreaterApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SqlCreaterApplication.class, args);

        String path = "/Users/m108491/Downloads/NMPAN_CPT_Codes.csv";

        List<CptRecord> cptRecords = read(path);

    }

    private static String SQL =
            "update dbo.panel_gene set cpt_code = ''{0}'' " +
            "where id = (\n" +
            "   select pg.id\n" +
            "   from dbo.panel_gene pg\n" +
            "   join dbo.gene g on pg.gene_id = g.id\n" +
            "   join dbo.panel pan on pg.panel_id = pan.id\n" +
            "   join dbo.project_panel pp on pan.id = pp.mnemonic_id\n" +
            "   join dbo.project pro on pp.project_id = pro.id\n" +
            "   where pro.project_id_number = ''{1}''\n" +
            "   and pan.test_mnemonic = ''{2}''\n" +
            "   and (g.gene_symbol = ''{3}'' or pg.gene_symbol = ''{3}'')\n" +
            ")\n" +
            "GO;\n";

    private static List<CptRecord> read(String path) throws IOException {
        Reader in = new FileReader(path);

        Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);

        List<CptRecord> cptRecords = new ArrayList<>();

        for (CSVRecord record : records){
            CptRecord cptRecord = new CptRecord();
            cptRecord.setProjectId(String.valueOf(parseProjectNumber(record.get("NGS#"))));
            cptRecord.setMasterPanel(record.get("Master Panel Mnemonic"));
            cptRecord.setGeneSymbol(record.get("Gene Symbol"));
            cptRecord.setCptCode(record.get("CPT Code"));

            cptRecords.add(cptRecord);

            String sql = MessageFormat.format(
                    SQL,
                    cptRecord.getCptCode(),
                    cptRecord.getProjectId(),
                    cptRecord.getMasterPanel(),
                    cptRecord.getGeneSymbol());

            System.out.println(sql);
        }

        return cptRecords;
    }

    public static Integer parseProjectNumber(String projectNumber) {
        Integer projectIdNumber = null;
        String projectNumberString = projectNumber.replaceAll("[^0-9]", "");

        if (!Strings.isNullOrEmpty(projectNumberString)) {
            projectIdNumber = Integer.valueOf(projectNumberString);
        }
        return projectIdNumber;
    }

}
