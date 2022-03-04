package Handler;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.RegisterRequest;
import Result.RegisterResult;
import Service.FillService;
import Service.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;

public class RegisterHandler implements HttpHandler {

    private final Database db;

    public RegisterHandler(Database db) {
        this.db = db;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();

        if(!exchange.getRequestMethod().equalsIgnoreCase("post")){
            System.out.println("Bad method");
            HandlerUtils.sendServerError(exchange, "Bad method.");
            db.closeConnection(false);
            return;
        }

        InputStream body = exchange.getRequestBody();
        String requestData = HandlerUtils.readString(body);
        System.out.println("-- /register --");
        RegisterRequest request = gson.fromJson(requestData, RegisterRequest.class);

        RegisterService service;
        try{
            service = new RegisterService(request, db.getConnection());
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            db.closeConnection(false);
            System.out.println("Issue opening connection");
            return;
        }

        service.execute();
        RegisterResult result = service.getResult();
        if(!result.success){
            System.out.print("Service failed:");
            System.out.println(result.message);
            HandlerUtils.sendServerError(exchange, "Service failed.");
            db.closeConnection(false);
            return;
        }


        try {
            FillService fillService = new FillService(db.getConnection(), request.username, 4);
            fillService.execute();
            if(!fillService.getResult().success){
                db.closeConnection(false);
                System.out.println("Fill service faulted.");
                HandlerUtils.sendServerError(exchange, "Fill service faulted.");
                return;
            }
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            db.closeConnection(false);
            System.out.println("Fill service faulted.");
            HandlerUtils.sendServerError(exchange, "Fill service faulted.");
            return;
        }

        String json = gson.toJson(result);
        HandlerUtils.sendSuccess(exchange, json);
        System.out.println("Register success");
        db.closeConnection(true);
    }
}
