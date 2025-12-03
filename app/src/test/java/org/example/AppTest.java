package org.example;

import by.gstu.generator.App;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    @Test
    public void testAppExists() {
        // Просто проверяем что класс можно создать
        App app = new App();
        assertNotNull(app);
    }
}