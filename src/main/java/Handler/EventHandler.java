package Handler;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import Result.EventResult;
import Service.EventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class EventHandler implements HttpHandler {

    private final Database db;

    public EventHandler(Database db) {
        this.db = db;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {


        Gson gson = new Gson();

        if(!exchange.getRequestMethod().equalsIgnoreCase("get")){
            System.out.println("Bad method");
            HandlerUtils.sendServerError(exchange, "Bad method.");
            db.closeConnection(false);
            return;
        }

        Headers requestHeaders = exchange.getRequestHeaders();
        if(!requestHeaders.containsKey("Authorization")){
            System.out.print("No authorization");
            HandlerUtils.sendServerError(exchange, "No authorization.");
            db.closeConnection(false);
            return;
        }

        AuthToken authToken = HandlerUtils.authorization(exchange, db);
        if(authToken == null){
            System.out.print("No username found for authToken");
            HandlerUtils.sendServerError(exchange, "Not authorized.");
            db.closeConnection(false);
            return;
        }

        String[] url = exchange.getRequestURI().toString().split("/");
        String eventID = url[2]; // ["" / "event" / "EVENT_ID"]
        System.out.println("-- /event/" + eventID + " --");
        EventService service;
        try{
            service = new EventService(db.getConnection(), eventID, authToken.getUsername());
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            System.out.println("Service faulted");
            HandlerUtils.sendServerError(exchange, "Service failed.");
            db.closeConnection(false);
            return;
        }

        service.execute();
        EventResult result = service.getResult();
        if(!result.success){
            System.out.print("Service failed: ");
            System.out.println(result.message);
            HandlerUtils.sendServerError(exchange, "Service failed.");
            db.closeConnection(false);
            return;
        }

        String json = gson.toJson(result);
        HandlerUtils.sendSuccess(exchange, json);
        System.out.println("Event success");
    }
}
