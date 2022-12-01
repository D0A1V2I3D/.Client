package top.davidon.ironware.microshit;

public class UrlParser {
    public static String codeUrl(String url) {
        String retUrl = url.split("=")[1];
        return retUrl;
    }
}
