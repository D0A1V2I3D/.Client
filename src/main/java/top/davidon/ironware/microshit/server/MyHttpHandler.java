package top.davidon.ironware.microshit.server;

import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.lwjgl.Sys;
import top.davidon.ironware.Client;
import top.davidon.ironware.microshit.UrlParser;
import top.davidon.ironware.utils.Utils;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestParamvalue = null;
        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            String code = UrlParser.codeUrl(exchange.getRequestURI().toString());
            handleResponse(exchange);
            Client.accountManager.getAltScreen().setCode(code);
            Server.close();
        }
    }

    private void handleResponse(HttpExchange httpExchange) {
        OutputStream outputStream = httpExchange.getResponseBody();
        String html = "<html><body><div>Microsoft transaction succesfull</div></body></html>";
        try {
            httpExchange.sendResponseHeaders(200, html.length());
            outputStream.write(html.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-74);
        }
    }
}
