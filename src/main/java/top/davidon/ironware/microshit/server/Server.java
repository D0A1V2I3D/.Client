package top.davidon.ironware.microshit.server;

import com.sun.net.httpserver.HttpServer;
import org.lwjgl.Sys;
import top.davidon.ironware.utils.Utils;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    public static HttpServer server;
    public static String MICROSHIT_LOGIN_URL = "https://login.live.com/oauth20_authorize.srf?client_id=cf770d92-9f71-4e97-a1f8-2c2c69c3cf9a&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A6947&scope=XboxLive.signin%20offline_access";

    public static void start() {
        try {
            server = HttpServer.create(new InetSocketAddress("localhost", 6947), 0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-47);
        }
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        server.createContext("/",  new MyHttpHandler());
        server.setExecutor(threadPoolExecutor);
        server.start();
        Utils.getLogger().info("HttpServer started on demand of microsoft login.");
        try {
            Desktop.getDesktop().browse(new URI(MICROSHIT_LOGIN_URL));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            System.exit(-47);
        }
        Utils.getLogger().info(MICROSHIT_LOGIN_URL);
    }

    public static void close() {
        server.stop(0);
        Utils.getLogger().info("Server was stopped.");
    }
}
