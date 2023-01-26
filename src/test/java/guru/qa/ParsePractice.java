package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ParsePractice {

    ClassLoader classLoader = ParsePractice.class.getClassLoader();

    @Test
    void zipParseTest() throws Exception {

        try (
                InputStream resource = classLoader.getResourceAsStream("homework.zip");
                ZipInputStream zis = new ZipInputStream(resource)
        ) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {

                if (entry.getName().contains("xls")) {
                    XLS content = new XLS(zis);
                    assertThat(content.excel.getSheetAt(0).getRow(5).getCell(1).getStringCellValue()).contains("Junio");
                } else if (entry.getName().contains("pdf")) {
                    PDF content = new PDF(zis);
                    assertThat(content.text).contains("REMEMBER THESE SHORTCUTS");
                } else if (entry.getName().contains("csv")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = reader.readAll();
                    assertThat(content.get(2)[1]).contains("Marzo");
                }
            }
        }
    }


}


