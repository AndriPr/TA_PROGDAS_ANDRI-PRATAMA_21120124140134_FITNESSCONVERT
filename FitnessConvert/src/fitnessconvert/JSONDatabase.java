package database;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONDatabase {
    private static final String DATABASE_PATH = "database.json";

    public static JSONObject loadDatabase() throws IOException {
        File file = new File(DATABASE_PATH);
        if (!file.exists()) {
            JSONObject db = new JSONObject();
            db.put("users", new JSONArray());
            db.put("history", new JSONArray());
            saveDatabase(db);
        }
        try (FileReader reader = new FileReader(DATABASE_PATH)) {
            StringBuilder data = new StringBuilder();
            int i;
            while ((i = reader.read()) != -1) {
                data.append((char) i);
            }
            return new JSONObject(data.toString());
        }
    }

    public static void saveDatabase(JSONObject database) throws IOException {
        try (FileWriter writer = new FileWriter(DATABASE_PATH)) {
            writer.write(database.toString(4));
        }
    }
}
