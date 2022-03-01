package Handler;

import Request.ClearRequest;
import Result.ClearResult;
import Service.ClearService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

public class ClearHandler implements HttpHandler {

    private final Connection connection;

    public ClearHandler(Connection connection){
        this.connection = connection;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();

        if(exchange.getRequestMethod().equalsIgnoreCase("post")){
            InputStream body = exchange.getRequestBody();
            String requestData = readString(body);
            System.out.println("-- /clear --");
            ClearRequest request = gson.fromJson(requestData, ClearRequest.class);
            ClearService service = new ClearService(request, connection);
            service.execute();
            System.out.println("what is wrong with you child");
            ClearResult result = service.getResult();
            System.out.println(result.message);
            System.out.println(result.success);
            if(!result.success){
                System.out.println("Fail"); //todo: fail
            }
            System.out.println("why are we here...");
            OutputStream responseBody = exchange.getResponseBody();
            String json = gson.toJson(result);
            byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8); //terrible way of doing this but i need to sleep
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonBytes.length);
            responseBody.write(jsonBytes);
            responseBody.close();
            System.out.println("Response sent");
        }
        else{
            System.out.println("Fail"); //todo: fail
        }
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
}
