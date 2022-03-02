package Handler;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Model.AuthToken;
import Model.Person;
import Request.PersonRequest;
import Result.PersonResult;
import Service.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import jdk.jshell.execution.Util;

import java.io.IOException;
import java.util.Arrays;

public class PersonHandler implements HttpHandler {

    private final Database db;

    public PersonHandler(Database db) {
        this.db = db;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();

        if(!exchange.getRequestMethod().equalsIgnoreCase("get")){
            System.out.print("Bad method");
            HandlerUtils.sendFail(exchange, "Bad method");
            db.closeConnection(false);
            return;
        }

        Headers requestHeaders = exchange.getRequestHeaders();
        if(!requestHeaders.containsKey("Authorization")){
            System.out.print("No authorization");
            HandlerUtils.sendFail(exchange, "No authorization.");
            db.closeConnection(false);
            return;
        }

        AuthTokenDAO authTokenDAO;
        AuthToken authToken;
        try {
            authTokenDAO = new AuthTokenDAO(db.getConnection());
            authToken = authTokenDAO.findFromToken(requestHeaders.getFirst("Authorization"));
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            HandlerUtils.sendFail(exchange, "Authorization mechanism failed.");
            db.closeConnection(false);
            return;
        }

        if(authToken == null){
            System.out.print("No username found for authToken");
            HandlerUtils.sendFail(exchange, "Not authorized.");
            db.closeConnection(false);
            return;
        }

        String[] url = exchange.getRequestURI().toString().split("/");
        String personID = url[url.length - 1];
        System.out.println("-- /person/[personID] --");
        PersonService service;
        try{
            service = new PersonService(db.getConnection(), personID, authToken.getUsername());
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            HandlerUtils.sendFail(exchange, "Service faulted.");
            db.closeConnection(false);
            return;
        }

        service.execute();
        PersonResult result = service.getResult();
        if(result == null){
            System.out.println("Person not found");
            HandlerUtils.sendFail(exchange, "Person not found.");
            db.closeConnection(false);
            return;
        }
        if(!result.success){
            System.out.print("Service failed: ");
            System.out.println(result.message);
            HandlerUtils.sendFail(exchange, "Service failed.");
            db.closeConnection(false);
            return;
        }

        String json = gson.toJson(result);
        HandlerUtils.sendSuccess(exchange, json);
        System.out.println("Person success");
        db.closeConnection(true);
    }
}
