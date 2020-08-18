package xyz.shandiankulishe.smcl.core;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.URL;

public class authProfile {
    private String Account;
    private String Password;
    private OutputStreamWriter osw=null;
    private InputStreamReader isr=null;
    private BufferedReader br=null;
    private StringBuilder builder=new StringBuilder();
    private HttpURLConnection connection=null;
    private URL authServer=new URL("https://auth.mojang.com/authenticate");
    int ResponseCode;
    public authProfile(String Account,String Password) throws IOException {
        this.Account=Account;
        this.Password=Password;
    }
    private String createLoad(){
        String Object="{\"agent\":{\"name\":\"Minecraft\",\"version\":1},\"username\":\""+Account+"\",\"password\":\""+Password+"\"}";
        String load=Object;
        return load;
    }
    private String POST(String load) throws IOException, AccountNotFoundException {
        String content=null;
        connection= (HttpURLConnection) authServer.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);
        connection.setRequestProperty("Content-Type","application/json");
        osw=new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
        osw.write(load);
        osw.flush();
        osw.close();
        ResponseCode=connection.getResponseCode();
        if (connection.getResponseCode()==403){
            throw new AccountNotFoundException();
        } else {
            isr=new InputStreamReader(connection.getInputStream(),"UTF-8");
            br=new BufferedReader(isr);
            String line;
            while ((line=br.readLine())!=null){
                builder.append(line);
            }
            content=builder.toString();
            return builder.toString();
        }
    }
    public int getResponseCode(){
        return ResponseCode;
    }
    public String get() throws IOException, AccountNotFoundException {
        String load=createLoad();
        String returnValue=POST(load);
        return returnValue;
    }
    public String getAccessToken(String OriginReturnValue){
        JSONObject object=new JSONObject(OriginReturnValue);
        String accessToken=object.getString("accessToken");
        return accessToken;
    }
    public String getClientToken(String OriginReturnValue){
        JSONObject object=new JSONObject(OriginReturnValue);
        String clientToken=object.getString("clientToken");
        return clientToken;
    }
    public String getPlayerUUID(String OriginReturnValue){
        JSONObject object=new JSONObject(OriginReturnValue);
        JSONObject selectedProfile=object.getJSONObject("availableProfiles");
        String id=selectedProfile.getString("id");
        return id;
    }
    public String getPlayerName(String OriginReturnValue){
        JSONObject object=new JSONObject(OriginReturnValue);
        JSONObject selectedProfile=object.getJSONObject("availableProfiles");
        String name=selectedProfile.getString("name");
        return name;
    }
}