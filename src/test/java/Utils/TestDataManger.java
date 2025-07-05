package Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.*;
public class TestDataManger {

    private static final String TEST_DATA_FILE_PATH = "src/test/resources/testdata/Data.json";
    private static Map<String, Map<String, Object>> allTestData;

    static {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(Paths.get(TEST_DATA_FILE_PATH).toFile());
            Type type = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
            allTestData = gson.fromJson(reader, type);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load test data JSON: " + e.getMessage());
        }
    }

    // Generic getter: returns String value by testCaseId and key
    public static String getValue(String testCaseId, String key) {
        Object value = allTestData.get(testCaseId).get(key);
        return value != null ? value.toString() : null;
    }

    // Returns List<String> for array values like productList
    public static List<String> getListFromJson(String testCaseId, String key) {
        try {
            Object value = allTestData.get(testCaseId).get(key);
            System.out.println("Product list fetched from JSON: " + key + " → " + value);
            if (value instanceof List<?>) {
                return (List<String>) value; // safe cast due to Gson structure
            } else if (value != null) {
                // Try parsing the raw JSON string into List<String> if it's a string
                Gson gson = new Gson();
                return gson.fromJson(gson.toJson(value), new TypeToken<List<String>>() {}.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    // ✅ Optionally get full Map<String, Object> per test case
    public static Map<String, Object> getTestDataMap(String testCaseId) {
        return allTestData.get(testCaseId);
    }

    public static Map<String, Object> getMapFromJson(String testCaseId, String key) {
        try {
            Object value = allTestData.get(testCaseId).get(key);
            if (value instanceof Map) {
                Gson gson = new Gson();
                String json = gson.toJson(value);
                return gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }
    public static Map<String, Object> getMapFromJson(String testCaseId) {
        return allTestData.getOrDefault(testCaseId, Collections.emptyMap());
    }
}
