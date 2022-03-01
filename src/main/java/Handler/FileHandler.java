package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException{
        if(!exchange.getRequestMethod().equalsIgnoreCase("get")){
            System.out.println("404 on method");
            error404(exchange);
        }

        String path = "web" + exchange.getRequestURI().toString();
        if(path.equals("web/")){
            path = "web/index.html";
        }
        File file = new File(path);
        if(!file.exists()){
            System.out.println("404 on file not existing");
            error404(exchange);
        }

        try {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        }
        catch (IOException exception){
            exception.printStackTrace();
            error404(exchange);
        }
        OutputStream outputStream = exchange.getResponseBody();
        try {
            Files.copy(file.toPath(), outputStream);
            outputStream.close();
        }
        catch (IOException exception){
            exception.printStackTrace();
            error404(exchange);
        }
        System.out.println(path + " served");
    }

    private void error404(HttpExchange exchange) throws IOException{
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
        File errorPage = new File("/web/HTML/404.html");
        OutputStream outputStream = exchange.getResponseBody();
        Files.copy(errorPage.toPath(), outputStream);
        outputStream.close();
    }
}
