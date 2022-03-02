package Handler;

import Result.ClearResult;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class HandlerUtils {
    public static void sendFail(HttpExchange exchange, String message) throws IOException {
        Gson gson = new Gson();
        OutputStream responseBody = exchange.getResponseBody();
        String json = gson.toJson(new ClearResult("Error: " + message, false)); //clear result is used just because it doesn't have extra data members
        byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8); //terrible way of doing this but I need to sleep
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonBytes.length);
        responseBody.write(jsonBytes);
        responseBody.close();
    }

    public static void sendSuccess(HttpExchange exchange, String json) throws IOException {
        OutputStream responseBody = exchange.getResponseBody();
        byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonBytes.length);
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
}
