package by.gstu.generator;

import com.github.javafaker.Faker;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Random;
import java.util.Locale;

/**
 * Generates field values based on type.
 */
public class FieldGenerator {
    private final Faker faker;
    private final Random random = new Random();
    private final EmailValidator emailValidator = EmailValidator.getInstance();

    public FieldGenerator() {
        this(new Locale("ru", "RU")); // русская локаль по умолчанию
    }

    public FieldGenerator(Locale locale) {
        this.faker = new Faker(locale);
    }

    /**
     * Generates value for a field like "id:int(1,100000)".
     * @param fieldDef Definition string.
     * @return Generated value.
     */
    public String generate(String fieldDef) {
        System.out.println("DEBUG FieldGenerator: processing fieldDef = '" + fieldDef + "'");

        if (fieldDef == null || fieldDef.trim().isEmpty()) {
            return "";
        }

        String[] parts = fieldDef.split(":");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid field definition, expected 'name:type(params)': " + fieldDef);
        }

        String name = parts[0].trim();
        String typeWithParams = parts[1].trim();
        String type;
        String params = "";

        // Парсим тип и параметры
        if (typeWithParams.contains("(") && typeWithParams.endsWith(")")) {
            int parenIndex = typeWithParams.indexOf("(");
            type = typeWithParams.substring(0, parenIndex).trim();
            params = typeWithParams.substring(parenIndex + 1, typeWithParams.length() - 1).trim();
        } else {
            type = typeWithParams;
        }

        System.out.println("DEBUG: name='" + name + "', type='" + type + "', params='" + params + "'");

        try {
            switch (type.toLowerCase()) {
                case "int":
                case "integer":
                    // Обработка int(1,100000)
                    String[] range = params.split(",");
                    int min = 0;
                    int max = 100;

                    if (range.length >= 1 && !range[0].trim().isEmpty()) {
                        min = Integer.parseInt(range[0].trim());
                    }
                    if (range.length >= 2 && !range[1].trim().isEmpty()) {
                        max = Integer.parseInt(range[1].trim());
                    }

                    // Корректируем если min > max
                    if (min > max) {
                        int temp = min;
                        min = max;
                        max = temp;
                    }

                    int value = min + random.nextInt(max - min + 1);
                    System.out.println("DEBUG: generated int: " + value + " (min=" + min + ", max=" + max + ")");
                    return String.valueOf(value);

                case "name":
                    String fullName = faker.name().fullName();
                    System.out.println("DEBUG: generated name: " + fullName);
                    return fullName;

                case "email":
                    String email;
                    int attempts = 0;
                    do {
                        email = faker.internet().emailAddress();
                        attempts++;
                        if (attempts > 10) {
                            email = "test@example.com"; // fallback
                            break;
                        }
                    } while (!emailValidator.isValid(email));
                    System.out.println("DEBUG: generated email: " + email);
                    return email;

                default:
                    throw new IllegalArgumentException("Unknown field type: " + type);
            }
        } catch (Exception e) {
            System.err.println("ERROR generating field '" + fieldDef + "': " + e.getMessage());
            e.printStackTrace();
            return "ERROR";
        }
    }
}