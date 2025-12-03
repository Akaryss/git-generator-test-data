package org.example;

import by.gstu.generator.ConfigLoader;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class ConfigLoaderTest {

    @Test
    void testConfigLoader() throws IOException {
        ConfigLoader config = new ConfigLoader("app.properties");

        assertNotNull(config);
        assertTrue(config.getRowsCount() > 0);
        assertNotNull(config.getOutputFormat());
        assertTrue(config.getFields().length > 0);
    }

    @Test
    void testGetFields() throws IOException {
        ConfigLoader config = new ConfigLoader("app.properties");
        String[] fields = config.getFields();

        assertNotNull(fields);
        assertTrue(fields.length >= 1);
        for (String field : fields) {
            assertTrue(field.contains(":"));
        }
    }
}
