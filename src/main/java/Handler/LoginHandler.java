package Handler;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.LoginRequest;
import Result.LoginResult;
import Service.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;

public class LoginHandler implements HttpHandler {

    private final Database db;

    public LoginHandler(Database db){
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
        System.out.println("-- /login --");
        LoginRequest request = gson.fromJson(requestData, LoginRequest.class);
        LoginService service;
        try{
            service = new LoginService(request, db.getConnection());
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            HandlerUtils.sendFail(exchange, "Service faulted.");
            db.closeConnection(false);
            return;
        }

        service.execute();
        LoginResult result = service.getResult();
        if(!result.success){
            System.out.print("Service failed: ");
            System.out.println(result.message);
            HandlerUtils.sendFail(exchange, "Service failed.");
            db.closeConnection(false);
            return;
        }

        String json = gson.toJson(result);
        HandlerUtils.sendSuccess(exchange, json);
        System.out.println("Login success");
        db.closeConnection(true);
    }
}
