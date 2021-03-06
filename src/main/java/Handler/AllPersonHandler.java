package Handler;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Model.AuthToken;
import Model.Person;
import Result.AllPersonResult;
import Service.AllPersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

public class AllPersonHandler implements HttpHandler {

    private final Database db;

    public AllPersonHandler(Database db) {
        this.db = db;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();

        try {
            AllPersonResult result = attemptToHandle(exchange);
            HandlerUtils.sendSuccess(exchange, gson.toJson(result));
            db.closeConnection(true);
            System.out.println("All person succeeded");
        }
        catch (UserErrorException exception) {
            HandlerUtils.sendUserError(exchange, exception.getMessage());
            db.closeConnection(false);
        }
        catch (AuthorizationException exception) {
            HandlerUtils.sendNotAuthorized(exchange, exception.getMessage());
            db.closeConnection(false);
        }
        catch (ServerErrorException exception) {
            HandlerUtils.sendServerError(exchange, exception.getMessage());
            db.closeConnection(false);
        }


    }

    public AllPersonResult attemptToHandle(HttpExchange exchange) throws IOException, UserErrorException, AuthorizationException, ServerErrorException {

        if(!exchange.getRequestMethod().equalsIgnoreCase("get")){
            System.out.println("Bad method");
            throw new UserErrorException("Bad method");
        }

        AuthToken authToken = HandlerUtils.authorization(exchange, db);
        if(authToken == null){
            System.out.println("Not authorized");
            throw new AuthorizationException("Not authorized.");
        }

        System.out.println("-- /person --");
        AllPersonService service;
        try {
            service = new AllPersonService(db.getConnection(), authToken.getUsername());
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            System.out.println("Service failed");
            throw new ServerErrorException("Service faulted.");
        }

        service.execute();

        return service.getResult();
    }
}
