package top.davidon.ironware.ui.alts;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import top.davidon.ironware.Client;
import top.davidon.ironware.microshit.server.Server;
import top.davidon.ironware.utils.Utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountHelper {
    public static JSONParser parser = new JSONParser();
    public static String MICROSOFT_TOEN_URL = "https://login.live.com/oauth20_token.srf";
    public static void login() throws Exception {
        Server.start();
        while (Client.accountManager.getAltScreen().getCode() == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(MICROSOFT_TOEN_URL);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("client_id", "cf770d92-9f71-4e97-a1f8-2c2c69c3cf9a"));
        params.add(new BasicNameValuePair("client_secret", "oKx8Q~cDNKOkq1ex0znmYQJO-sZBw-akye6-AcDk"));
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
        params.add(new BasicNameValuePair("redirect_uri", "http://localhost:6947"));
        params.add(new BasicNameValuePair("code", Client.accountManager.getAltScreen().getCode()));

        httpPost.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(new InputStreamReader(entity.getContent(), "UTF-8"));
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        String accessToken = (String) json.get("access_token");
        String refreshToken = (String) json.get("refresh_token");

        HttpPost httpPost1 = new HttpPost("https://user.auth.xboxlive.com/user/authenticate");
        //httpPost1.addHeader("Accept", "application/json");
        httpPost1.setEntity(new StringEntity(String.format("{\"Properties\":{\"AuthMethod\":\"RPS\",\"SiteName\":\"user.auth.xboxlive.com\",\"RpsTicket\":\"d=%s\"},\"RelyingParty\":\"http://auth.xboxlive.com\",\"TokenType\":\"JWT\"}", accessToken), ContentType.APPLICATION_JSON));
        HttpResponse response1 = httpClient.execute(httpPost1);
        JSONObject json1 = null;
        try {
            json1 = (JSONObject) parser.parse(new InputStreamReader(response1.getEntity().getContent(), "UTF-8"));
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        String tmpToken = (String) json1.get("Token");
        JSONObject tmpjson = (JSONObject) json1.get("DisplayClaims");
        JSONArray tmparray = (JSONArray) tmpjson.get("xui");
        JSONObject tmpjson1 = (JSONObject) tmparray.get(0);
        String userHash = (String) tmpjson1.get("uhs");

        HttpPost httpPost2 = new HttpPost("https://xsts.auth.xboxlive.com/xsts/authorize");
        httpPost2.addHeader("Accept", "application/json");
        httpPost2.setEntity(new StringEntity(String.format("{\"Properties\":{\"SandboxId\":\"RETAIL\",\"UserTokens\":[\"%s\"]},\"RelyingParty\":\"rp://api.minecraftservices.com/\",\"TokenType\":\"JWT\"}", tmpToken), ContentType.APPLICATION_JSON));
        HttpResponse response2 = httpClient.execute(httpPost2);
        JSONObject json2 = null;
        try {
            json2 = (JSONObject) parser.parse(new InputStreamReader(response2.getEntity().getContent(), "UTF-8"));
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        String xstsToken = (String) json2.get("Token");

        HttpPost httpPost3 = new HttpPost("https://api.minecraftservices.com/authentication/login_with_xbox");
        httpPost3.setEntity(new StringEntity(String.format("{\"identityToken\":\"XBL3.0 x=%s;%s\",\"ensureLegacyEnabled\":true}", userHash, xstsToken), ContentType.APPLICATION_JSON));
        HttpResponse response3 = httpClient.execute(httpPost3);
        JSONObject json3 = null;
        try {
            json3 = (JSONObject) parser.parse(new InputStreamReader(response3.getEntity().getContent(), "UTF-8"));
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        String token = (String) json3.get("access_token");

        HttpGet httpGet = new HttpGet("https://api.minecraftservices.com/minecraft/profile");
        httpGet.addHeader("Authorization", "Bearer " + token);
        HttpResponse response4 = httpClient.execute(httpGet);
        JSONObject json4 = null;
        try {
            json4 = (JSONObject) parser.parse(new InputStreamReader(response4.getEntity().getContent(), "UTF-8"));
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        String uuid = (String) json4.get("id");
        String uname = (String) json4.get("name");

        Minecraft.getMinecraft().session = new Session(uname, uuid, token, "legacy");
        Client.accountManager.getAltScreen().setLoggedInAccount(new Account(uname, token, refreshToken, uuid, AccountType.MICROSOFT, UUID.randomUUID().toString()));
        Client.accountManager.getAccounts().add(Client.accountManager.getAltScreen().getLoggedInAccount());
    }
}
