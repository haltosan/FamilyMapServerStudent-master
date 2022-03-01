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
import java.net.HttpURLConnection;
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

        try{
            if(exchange.getRequestMethod().equalsIgnoreCase("post")){
                InputStream body = exchange.getRequestBody();
                String requestData = readString(body);
                System.out.println("-- /clear --");
                System.out.println(requestData);
                ClearRequest request = gson.fromJson(requestData, ClearRequest.class);
                ClearService service = new ClearService(request, connection);
                service.execute();
                ClearResult result = service.getResult();
                if(result == null){
                    //fail
                }
            }
            else{
                //fail
            }

        }
        catch (IOException exception){
            exception.printStackTrace();

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getRequestBody().close();
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
