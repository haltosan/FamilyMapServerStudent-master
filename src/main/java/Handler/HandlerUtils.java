package Handler;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import Result.ClearResult;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class HandlerUtils {

    private static final Gson gson = new Gson();

    public static void sendServerError(HttpExchange exchange, String message) throws IOException {
        String json = gson.toJson(new ClearResult("Error: " + message, false)); //clear result is used just because it doesn't have extra data members
        sendResult(exchange, HttpURLConnection.HTTP_BAD_REQUEST, json);
    }

    public static void sendNotAuthorized(HttpExchange exchange, String message) throws IOException {
        String json = gson.toJson(new ClearResult("Error: " + message, false));
        sendResult(exchange, HttpURLConnection.HTTP_BAD_REQUEST, json);
    }

    public static void sendUserError(HttpExchange exchange, String message) throws IOException {
        String json = gson.toJson(new ClearResult("Error: " + message, false));
        sendResult(exchange, HttpURLConnection.HTTP_BAD_REQUEST, json);
    }

    public static void sendSuccess(HttpExchange exchange, String json) throws IOException {
        sendResult(exchange, HttpURLConnection.HTTP_OK, json);
    }

    public static void sendResult(HttpExchange exchange, int status, String json) throws IOException {
        OutputStream responseBody = exchange.getResponseBody();
        byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8); //terrible way of doing this but I need to sleep
        exchange.sendResponseHeaders(status, jsonBytes.length);
        responseBody.write(jsonBytes);
        responseBody.close();
    }

    public static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    public static AuthToken authorization(HttpExchange exchange, Database db) throws IOException {
        Headers requestHeaders = exchange.getRequestHeaders();
        if(!requestHeaders.containsKey("Authorization")){
            System.out.print("No authorization");
            sendNotAuthorized(exchange, "No authorization.");
            db.closeConnection(false);
            return null;
        }

        AuthTokenDAO authTokenDAO;
        AuthToken authToken;
        try {
            authTokenDAO = new AuthTokenDAO(db.getConnection());
            authToken = authTokenDAO.findFromToken(requestHeaders.getFirst("Authorization"));
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            HandlerUtils.sendServerError(exchange, "Authorization mechanism failed.");
            db.closeConnection(false);
            return null;
        }

        return authToken;
    }
}
