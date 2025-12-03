package by.gstu.generator;

import java.io.IOException;
import java.util.List;

/**
 * Точка входа в приложение.
 */
public class App {
    public static void main(String[] args) {
        try {
            // Просто имя файла — он будет найден в classpath (src/main/resources)
            ConfigLoader config = new ConfigLoader("app.properties");
            DataGenerator generator = new DataGenerator(config);

            List<List<String>> data = generator.generateData();
            String output = generator.formatData(data);

            System.out.println(output);

        } catch (IOException e) {
            System.err.println("Error loading config: " + e.getMessage());
            e.printStackTrace();
        }
    }
}