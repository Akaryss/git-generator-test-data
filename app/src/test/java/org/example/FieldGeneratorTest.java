package org.example;

import by.gstu.generator.FieldGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FieldGeneratorTest {

    @Test
    void testGenerateInt() {
        FieldGenerator generator = new FieldGenerator();
        String result = generator.generate("id:int(1,10)");

        assertNotNull(result);
        int value = Integer.parseInt(result);
        assertTrue(value >= 1 && value <= 10);
    }

    @Test
    void testGenerateName() {
        FieldGenerator generator = new FieldGenerator();
        String result = generator.generate("full_name:name");

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testGenerateEmail() {
        FieldGenerator generator = new FieldGenerator();
        String result = generator.generate("email:email");

        assertNotNull(result);
        assertTrue(result.contains("@"));
        assertTrue(result.contains("."));
    }
}