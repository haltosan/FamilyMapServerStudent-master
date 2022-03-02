package Handler;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.ClearRequest;
import Result.ClearResult;
import Service.ClearService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;

import java.io.*;

public class ClearHandler implements HttpHandler {

    private final Database db;

    public ClearHandler(Database db){
        this.db = db;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();

        if(!exchange.getRequestMethod().equalsIgnoreCase("post")){
            System.out.println("Bad method");
            HandlerUtils.sendFail(exchange, "Bad method.");
            db.closeConnection(false);
            return;
        }

        InputStream body = exchange.getRequestBody();
        String requestData = HandlerUtils.readString(body);
        System.out.println("-- /clear --");
        ClearRequest request = gson.fromJson(requestData, ClearRequest.class);
        ClearService service;
        try {
            service = new ClearService(request, db.getConnection());
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            HandlerUtils.sendFail(exchange, "Service faulted.");
            db.closeConnection(false);
            return;
        }

        service.execute();
        ClearResult result = service.getResult();
        if(!result.success){
            System.out.print("Service failed: ");
            System.out.println(result.message);
            HandlerUtils.sendFail(exchange, "Service failed.");
            db.closeConnection(false);
            return;
        }

        String json = gson.toJson(result);
        HandlerUtils.sendSuccess(exchange, json);
        System.out.println("Clear success");
        db.closeConnection(true);
    }
}
