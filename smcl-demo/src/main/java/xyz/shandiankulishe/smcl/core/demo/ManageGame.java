package xyz.shandiankulishe.smcl.core.demo;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ManageGame {
    public static Logger logger= LogManager.getLogger(ManageGame.class);
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {
        System.out.println("您的游戏档案");
        String gameProperty=FileUtils.readFileToString(new File("game_property.json"),"UTF-8");
        JSONObject property=new JSONObject(gameProperty);
        Iterator<String> iterator=property.keys();
        String NextValue;
        JSONObject object;
        HashMap<String,String> ID=new HashMap<String,String>();
        while (iterator.hasNext()){
            NextValue=iterator.next();
            object=property.getJSONObject(NextValue);
            ID.put(NextValue,object.getString("version"));
            System.out.println("档案名称："+property.getString("ProfileID")+"游戏版本："+property.getString("version")+" 版本类型："+property.getString("type")+" 创建时间："+property.getString("createTime"));
        }
        System.out.println("请输入您想启动的游戏档案名称，输入CREATE_NEW_PROFILE即为创建一个新游戏档案");
        Scanner input=new Scanner(System.in);
        String vl=new String(input.next());
        if (vl.equals("CREATE_NEW_PROFILE")){
            Main main=new Main();
            main.createNewGameProfile();
        } else {
            for (String Keys:ID.keySet()){
                if (vl.equals(Keys)){
                    String gameArgs=FileUtils.readFileToString(new File(ID.get(Keys)));
                    new runGame(gameArgs);
                    logger.info("游戏已启动");
                }
            }
        }
    }
}
