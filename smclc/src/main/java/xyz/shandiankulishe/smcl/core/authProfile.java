package xyz.shandiankulishe.smcl.core;

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
    private String POST(String load) throws IOException {
        String content;
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
        isr=new InputStreamReader(connection.getInputStream(),"UTF-8");
        br=new BufferedReader(isr);
        String line;
        while ((line=br.readLine())!=null){
            builder.append(line);
        }
        content=builder.toString();
        ResponseCode=connection.getResponseCode();
        return content;
    }
    public int getResponseCode(){
        return ResponseCode;
    }
    public String get() throws IOException {
        String load=createLoad();
        String returnValue=POST(load);
        return returnValue;
    }
}