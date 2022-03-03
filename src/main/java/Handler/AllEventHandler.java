package Handler;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.AuthToken;
import Model.Event;
import Result.AllEventResult;
import Service.AllEventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class AllEventHandler implements HttpHandler {

    private final Database db;

    public AllEventHandler(Database db) {
        this.db = db;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            EventDAO eventDAO = new EventDAO(db.getConnection());
            eventDAO.insert(new Event("e1","a","p1",100,-100,"oui","ouioui","idk",999));
            eventDAO.insert(new Event("e2","a","p1",100,-100,"oui","ouioui","idk",999));
            eventDAO.insert(new Event("e3","a","p1",100,-100,"oui","ouioui","idk",999));
            eventDAO.insert(new Event("e4","b","p1",100,-100,"oui","ouioui","idk",999));
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        try {
            AllEventResult result = attemptToHandle(exchange);
            HandlerUtils.sendSuccess(exchange, gson.toJson(result));
            db.closeConnection(true);
            return;
        }
        catch (UserErrorException exception) {
            System.out.println(exception.getMessage());
            HandlerUtils.sendUserError(exchange, exception.getMessage());
        }
        catch (AuthorizationException exception) {
            System.out.println(exception.getMessage());
            HandlerUtils.sendNotAuthorized(exchange, exception.getMessage());
        }
        catch (ServerErrorException exception) {
            System.out.println(exception.getMessage());
            HandlerUtils.sendServerError(exchange, exception.getMessage());
        }
        db.closeConnection(false); //only reaches this if try fails
    }

    private AllEventResult attemptToHandle(HttpExchange exchange) throws UserErrorException, IOException, AuthorizationException, ServerErrorException {
        if(!exchange.getRequestMethod().equalsIgnoreCase("get")){
            throw new UserErrorException("Bad method.");
        }

        AuthToken authToken = HandlerUtils.authorization(exchange, db);
        if(authToken == null){
            throw new AuthorizationException("Not authorized.");
        }

        System.out.println("-- /event --");
        AllEventService service;
        try {
            service = new AllEventService(db.getConnection(), authToken.getUsername());
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            throw new ServerErrorException("Service faulted.");
        }

        service.execute();
        System.out.println("All event success");
        return service.getResult();
    }
}
