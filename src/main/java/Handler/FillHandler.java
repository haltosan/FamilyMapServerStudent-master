package Handler;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Result.FillResult;
import Service.ClearService;
import Service.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.ArrayList;

public class FillHandler implements HttpHandler {

    private final Database db;

    public FillHandler(Database db) {
        this.db = db;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();

        try {
            FillResult result = attemptToHandle(exchange);
            HandlerUtils.sendSuccess(exchange, gson.toJson(result));
            db.closeConnection(true);
            return;
        }
        catch (UserErrorException exception) {
            System.out.println(exception.getMessage());
            HandlerUtils.sendUserError(exchange, exception.getMessage());
        }
        catch (ServerErrorException exception) {
            System.out.println(exception.getMessage());
            HandlerUtils.sendServerError(exchange, exception.getMessage());
        }
        db.closeConnection(false); //only reaches if try fails
    }

    private FillResult attemptToHandle(HttpExchange exchange) throws UserErrorException, ServerErrorException {
        if(!exchange.getRequestMethod().equalsIgnoreCase("post")){
            throw new UserErrorException("Bad method.");
        }

        String[] url = exchange.getRequestURI().toString().split("/");
        String username = url[2]; // ['' / 'fill' / 'username' / 'eventID']
        int generationCount = 4;
        if(url.length == 4){
            generationCount = Integer.parseInt(url[3]);
        }

        System.out.println("-- /fill --");

        if(HandlerUtils.filledUsernames.contains(username)){
            //clear current data
            try {
                ClearService clearService = new ClearService( username,null, db.getConnection()); //todo: clear after re-fill on same user
                clearService.execute();
                if(!clearService.getResult().success){
                    throw new ServerErrorException("Clear service failed.");
                }
                System.out.println("Cleared existing data for " + username);
            } catch (DataAccessException exception) {
                exception.printStackTrace();
                throw new ServerErrorException("Clear service faulted.");
            }
        }

        FillService service;
        try{
            service = new FillService(db.getConnection(), username, generationCount);
        }
        catch (DataAccessException exception){
            throw new ServerErrorException("Service faulted.");
        }

        service.execute();
        System.out.println("Fill success");
        HandlerUtils.filledUsernames.add(username);
        return service.getResult();
    }
}
