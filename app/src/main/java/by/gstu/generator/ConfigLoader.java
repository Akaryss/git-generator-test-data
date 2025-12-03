package by.gstu.generator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Загружает конфигурацию из app.properties.
 */
public class ConfigLoader {
    private final Properties properties = new Properties();

    public ConfigLoader(String configPath) throws IOException {
        InputStream is = ConfigLoader.class.getClassLoader().getResourceAsStream(configPath);

        if (is == null) {
            throw new FileNotFoundException("Config file not found in classpath: " + configPath);
        }

        try (is) {
            properties.load(is);
            System.out.println("DEBUG ConfigLoader: Loaded " + properties.size() + " properties");
        }
    }

    public int getRowsCount() {
        String value = properties.getProperty("generator.rows.count", "10");
        return Integer.parseInt(value);
    }

    public String getOutputFormat() {
        return properties.getProperty("output.format", "CSV").toUpperCase();
    }

    public String[] getFields() {
        String fieldsStr = properties.getProperty("generator.fields", "");
        System.out.println("DEBUG ConfigLoader: Raw fields string = '" + fieldsStr + "'");

        if (fieldsStr.isBlank()) {
            return new String[]{"id:int(1,100)", "name:name", "email:email"};
        }

        // Правильный парсинг с учётом скобок
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        int bracketDepth = 0;

        for (char c : fieldsStr.toCharArray()) {
            if (c == '(') {
                bracketDepth++;
                current.append(c);
            } else if (c == ')') {
                bracketDepth--;
                current.append(c);
            } else if (c == ',' && bracketDepth == 0) {
                // Только запятые вне скобок разделяют поля
                fields.add(current.toString().trim());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }

        // Добавляем последнее поле
        if (current.length() > 0) {
            fields.add(current.toString().trim());
        }

        System.out.println("DEBUG ConfigLoader: Parsed " + fields.size() + " fields: " + fields);
        return fields.toArray(new String[0]);
    }
}