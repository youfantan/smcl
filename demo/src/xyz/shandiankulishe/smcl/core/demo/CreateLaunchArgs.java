package xyz.shandiankulishe.smcl.core.demo;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CreateLaunchArgs {
    String version;
    String type;
    String UUID;
    String accessToken;
    String logFilePath;
    String librariesPath;
    String name;
    String gamePath;
    String assetsPath;
    String assetsIndex;
    String mainClass;
    String classPaths;
    CreateLaunchArgs(String version,String type,String UUID,String accessToken,String logFilePath,String librariesPath,String name,String gamePath,String assetsPath,String assetsIndex,String mainClass,String classPaths){
        this.version=version;
        this.type=type;
        this.UUID=UUID;
        this.accessToken=accessToken;
        this.logFilePath=logFilePath;
        this.librariesPath=librariesPath;
        this.name=name;
        this.gamePath=gamePath;
        this.assetsPath=assetsPath;
        this.assetsIndex=assetsIndex;
        this.mainClass=mainClass;
        this.classPaths=classPaths;
    }
    public void init() throws IOException {
        File property_path=new File("Property");
        if (!property_path.exists()){
            property_path.mkdir();
        }
        File args_path=new File(property_path+"\\"+version);
        if (!args_path.exists()){
            args_path.mkdir();
        }
        File launch_args_storage=new File(args_path+"\\"+"launch_args_storage");
        String storage="GAME_VERSION:"+version+"\n"+"GAME_TYPE:"+type+"\n"+"PLAYER_UUID:"+UUID+"\n"+"ACCESS_TOKEN:";
        FileUtils.write(launch_args_storage,storage,"UTF-8");
    }
    public String createLaunchCommand(){
        StringBuilder cmd=new StringBuilder();
        List<String> game=new ArrayList<>();
        game.add(0,"-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump");
        game.add(1,"-Dos.name=Windows 10");
        game.add(2,"-Dos.version=10.0");
        game.add(3,"-Xss1M");
        game.add(4,"-Dminecraft.launcher.brand=minecraft-launcher");
        game.add(5,"-Dminecraft.launcher.version=2.1.15850");
        game.add(6,"-Xmx2G");
        game.add(7,"-XX:+UnlockExperimentalVMOptions");
        game.add(8,"-XX:+UseG1GC");
        game.add(9,"-XX:G1NewSizePercent=20");
        game.add(10,"-XX:G1ReservePercent=20");
        game.add(11,"-XX:MaxGCPauseMillis=50");
        game.add(12,"-XX:G1HeapRegionSize=32M");
        game.add(0,"-Djava.library.path="+librariesPath);
        game.add(1,"cp "+classPaths);
        game.add(2,"-Dlog4j.configurationFile="+logFilePath);
        game.add(0,mainClass);
        game.add(0,"--username "+name);
        game.add(1,"--version "+version);
        game.add(2,"--gameDir "+gamePath);
        game.add(3,"--assetsDir "+assetsPath);
        game.add(4,"--assetIndex "+assetsIndex);
        game.add(5,"-uuid "+UUID);
        game.add(6,"--accessToken "+accessToken);
        game.add(7,"--userType mojang");
        game.add(8,"--versionType "+type);
        Iterator<String> iterator=game.iterator();
        while (iterator.hasNext()){
            cmd.append(iterator.next()+" ");
        }
        System.out.println(cmd.toString());
        return cmd.toString();
    }
}
