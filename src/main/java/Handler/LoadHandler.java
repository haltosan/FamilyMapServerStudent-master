package Handler;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.LoadRequest;
import Result.LoadResult;
import Service.ClearService;
import Service.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;

public class LoadHandler implements HttpHandler {

    private final Database db;

    public LoadHandler(Database db) {
        this.db = db;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();

        try {
            LoadResult result = attemptToHandle(exchange);
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
        db.closeConnection(false);
    }

    private LoadResult attemptToHandle(HttpExchange exchange) throws UserErrorException, IOException, ServerErrorException {
        Gson gson = new Gson();

        if(!exchange.getRequestMethod().equalsIgnoreCase("post")){
            throw new UserErrorException("Bad method.");
        }

        try {
            ClearService clearService = new ClearService(null, db.getConnection());
            clearService.execute();
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            throw new ServerErrorException("Clear service faulted.");
        }

        InputStream body = exchange.getRequestBody();
        String requestData = HandlerUtils.readString(body);
        System.out.println("-- /load --");
        LoadRequest request = gson.fromJson(requestData, LoadRequest.class);
        LoadService service;
        try {
            service = new LoadService(db.getConnection(), request.users, request.persons, request.events);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            throw new ServerErrorException("Service faulted");
        }

        service.execute();
        System.out.println("Load success");
        return service.getResult();
    }
}
