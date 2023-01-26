package guru.qa;

import guru.qa.model.BookJson;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;


public class JsonParseTest {
    ClassLoader classLoader = JsonParseTest.class.getClassLoader();
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void jsonParse() throws Exception {

        try (
                InputStream resource = classLoader.getResourceAsStream("homework.json");
                InputStreamReader reader = new InputStreamReader(resource)
        ) {

            BookJson[] bookJson = mapper.readValue(reader, BookJson[].class);

            assertThat(bookJson[0].author).contains("Schildt");
            assertThat(bookJson[0].amount).isEqualTo(10);
            assertThat(bookJson[1].language).isEqualTo("C++");
            assertThat(bookJson[1].edition).isEqualTo("sixth");
        }
    }
}

