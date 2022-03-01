package Handler;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.ClearRequest;
import Result.ClearResult;
import Service.ClearService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;

public class ClearHandler implements HttpHandler {

    private Database db;

    public ClearHandler(Database db){
        this.db = db;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();

        if(!exchange.getRequestMethod().equalsIgnoreCase("post")){
            System.out.println("Bad method");
            failResponse(exchange, "Bad method");
            db.closeConnection(false);
            return;
        }

        InputStream body = exchange.getRequestBody();
        String requestData = readString(body);
        System.out.println("-- /clear --");
        ClearRequest request = gson.fromJson(requestData, ClearRequest.class);
        ClearService service = null;
        try {
            service = new ClearService(request, db.getConnection());
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            failResponse(exchange, "Service faulted.");
            db.closeConnection(false);
            return;
        }

        service.execute();
        ClearResult result = service.getResult();
        if(!result.success){
            System.out.print("Service failed: ");
            System.out.println(result.message);
            failResponse(exchange, "Service failed.");
            db.closeConnection(false);
            return;
        }

        OutputStream responseBody = exchange.getResponseBody();
        String json = gson.toJson(result);
        byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8); //terrible way of doing this but I need to sleep
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonBytes.length);
        responseBody.write(jsonBytes);
        responseBody.close();
        System.out.println("Clear success");
        db.closeConnection(true);
    }

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private void failResponse(HttpExchange exchange, String message) throws IOException {
        Gson gson = new Gson();
        OutputStream responseBody = exchange.getResponseBody();
        String json = gson.toJson(new ClearResult("Error: " + message, false));
        byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8); //terrible way of doing this but I need to sleep
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonBytes.length);
        responseBody.write(jsonBytes);
        responseBody.close();
    }
}
