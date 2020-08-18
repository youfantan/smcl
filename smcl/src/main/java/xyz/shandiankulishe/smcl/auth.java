package xyz.shandiankulishe.smcl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import xyz.shandiankulishe.smcl.core.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class auth {
    Logger logger= LogManager.getLogger(xyz.shandiankulishe.smcl.auth.class);
    String Email=null;
    String Password=null;
    authProfile auth;
    public auth(String Email,String Password) throws IOException {
        String log4jPath= xyz.shandiankulishe.smcl.auth.class.getClassLoader().getResource("log4j2.xml").toString();
        log4jPath=log4jPath.substring(6);
        ConfigurationSource source=new ConfigurationSource(new FileInputStream(new File(log4jPath)), new File(log4jPath));
        Configurator.initialize(null,source);
        this.Email=Email;
        this.Password=Password;
        auth=new authProfile(Email,Password);
    }
    public String getReturnValue() throws IOException {
        String returnValue;
        try {
            returnValue=auth.get();
        } catch (AccountNotFoundException e){
            logger.error(e);
            return null;
        }
        return returnValue;
    }
    public String getToken(String returnValue){
        return auth.getAccessToken(returnValue);
    }
    public String getName(String returnValue){
        return auth.getPlayerName(returnValue);
    }
    public String getUUID(String returnValue){
        return auth.getPlayerUUID(returnValue);
    }
    public int getRC(){
        return auth.getResponseCode();
    }
}
