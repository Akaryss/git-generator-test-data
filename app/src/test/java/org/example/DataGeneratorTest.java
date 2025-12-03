package org.example;

import by.gstu.generator.ConfigLoader;
import by.gstu.generator.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DataGeneratorTest {
    private ConfigLoader config;
    private DataGenerator generator;

    @BeforeEach
    void setUp() throws IOException {
        // Можно создать тестовый конфиг в памяти
        config = new ConfigLoader("app.properties");
        generator = new DataGenerator(config);
    }

    @Test
    void testGenerateData() {
        List<List<String>> data = generator.generateData();

        assertNotNull(data);
        assertTrue(data.size() > 1); // header + at least one row
        assertEquals(config.getRowsCount() + 1, data.size());
    }

    @Test
    void testFormatDataCSV() {
        List<List<String>> data = generator.generateData();
        String formatted = generator.formatData(data);

        assertNotNull(formatted);
        assertTrue(formatted.contains(","));
        assertTrue(formatted.contains("\n"));
    }
}