package by.gstu.generator;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates test data based on config.
 */
public class DataGenerator {
    private final ConfigLoader config;
    private final FieldGenerator fieldGenerator = new FieldGenerator();

    public DataGenerator(ConfigLoader config) {
        this.config = config;
    }

    /**
     * Generates N rows of data.
     * @return List of rows, each row is a list of field values.
     */
    public List<List<String>> generateData() {
        List<List<String>> data = new ArrayList<>();
        String[] fields = config.getFields();
        int rows = config.getRowsCount();

        // Header
        List<String> header = new ArrayList<>();
        for (String field : fields) {
            header.add(field.split(":")[0]);
        }
        data.add(header);

        // Data rows
        for (int i = 0; i < rows; i++) {
            List<String> row = new ArrayList<>();
            for (String field : fields) {
                row.add(fieldGenerator.generate(field));
            }
            data.add(row);
        }
        return data;
    }

    /**
     * Formats data to CSV or JSON.
     * @param data Generated data.
     * @return Formatted string.
     */
    public String formatData(List<List<String>> data) {
        String format = config.getOutputFormat();
        if ("CSV".equalsIgnoreCase(format)) {
            StringBuilder sb = new StringBuilder();
            for (List<String> row : data) {
                sb.append(String.join(",", row)).append("\n");
            }
            return sb.toString();
        } else if ("JSON".equalsIgnoreCase(format)) {
            StringBuilder sb = new StringBuilder("[\n");
            List<String> headers = data.get(0);
            for (int i = 1; i < data.size(); i++) {
                sb.append("  {");
                List<String> row = data.get(i);
                for (int j = 0; j < headers.size(); j++) {
                    sb.append("\"").append(headers.get(j)).append("\": \"").append(row.get(j)).append("\"");
                    if (j < headers.size() - 1) sb.append(", ");
                }
                sb.append("}");
                if (i < data.size() - 1) sb.append(",\n");
            }
            sb.append("\n]");
            return sb.toString();
        }
        throw new IllegalArgumentException("Unknown format: " + format);
    }
}