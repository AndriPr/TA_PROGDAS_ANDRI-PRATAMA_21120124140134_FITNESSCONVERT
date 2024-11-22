package controller;

import org.json.JSONArray;
import org.json.JSONObject;
import database.JSONDatabase;

import java.io.IOException;

public class UserController {
    public static boolean login(String username, String password) throws IOException {
        JSONObject db = JSONDatabase.loadDatabase();
        JSONArray users = db.getJSONArray("users");
        for (Object obj : users) {
            JSONObject user = (JSONObject) obj;
            if (user.getString("username").equals(username) && 
                user.getString("password").equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static boolean signUp(String username, String password) throws IOException {
        JSONObject db = JSONDatabase.loadDatabase();
        JSONArray users = db.getJSONArray("users");
        for (Object obj : users) {
            JSONObject user = (JSONObject) obj;
            if (user.getString("username").equals(username)) {
                return false; // Username sudah ada
            }
        }
        JSONObject newUser = new JSONObject();
        newUser.put("username", username);
        newUser.put("password", password);
        users.put(newUser);
        JSONDatabase.saveDatabase(db);
        return true;
    }
}
