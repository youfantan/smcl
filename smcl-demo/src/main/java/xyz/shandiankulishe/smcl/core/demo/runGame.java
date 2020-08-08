package xyz.shandiankulishe.smcl.core.demo;

import java.io.IOException;
import java.util.Properties;

public class runGame {
    runGame(String LaunchArgs) throws IOException {
            Properties SystemProperties=System.getProperties();
            String JAVA_HOME=SystemProperties.getProperty("java.home"+"\\bin\\javaw.exe");
            String command=JAVA_HOME+" "+LaunchArgs;
            Process run=Runtime.getRuntime().exec("cmd /c "+command);
    }
}
