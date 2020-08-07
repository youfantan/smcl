package xyz.shandiankulishe.smcl.core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

import java.net.URL;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class analyzeManifest {
    private static File manifest;
    private static String manifestString;
    private boolean ifSetURL=false;
    public void setManifest(File Manifest) throws IOException {
        manifest=Manifest;
        BufferedReader reader=new BufferedReader(new FileReader(Manifest));
        String line;
        StringBuilder builder=new StringBuilder();
        while ((line=reader.readLine())!=null){
            builder.append(line);
        }
        manifestString=builder.toString();
        ifSetURL=true;
    }
    public void useLatest() throws IOException {
        URL MojangVersionManifest=new URL("https://launchermeta.mojang.com/mc/game/version_manifest.json");
        BufferedReader reader=new BufferedReader(new InputStreamReader(MojangVersionManifest.openStream()));
        String line;
        StringBuilder builder=new StringBuilder();
        while ((line=reader.readLine())!=null){
            builder.append(line);
        }
        manifestString=builder.toString();
        ifSetURL=true;
    }
    public HashMap<String,String> analyzeLatest() throws IOException {
        if (ifSetURL!=true){
            useLatest();
        }
        JSONObject manifestObject=new JSONObject(manifestString);
        HashMap<String,String> Map=new HashMap<String, String>();
        JSONObject latest=manifestObject.getJSONObject("latest");
        String release=latest.getString("release");
        String snapshot=latest.getString("snapshot");
        Map.put("release",release);
        Map.put("snapshot",snapshot);
        return Map;
    }
    public HashMap<String,List<String>> analyzeVersions() throws IOException {
        if (ifSetURL!=true){
            useLatest();
        }
        JSONObject manifestObject=new JSONObject(manifestString);
        JSONArray versions=manifestObject.getJSONArray("versions");
        HashMap<String,List<String>> Map=new HashMap<String,List<String>>();
        List<String> id=new ArrayList<String>();
        List<String> type=new ArrayList<String>();
        List<String> url=new ArrayList<String>();
        List<String> time=new ArrayList<String>();
        List<String> releaseTime=new ArrayList<String>();
        JSONObject object;
        for (int i=0;i<versions.length();i++){
            object=versions.getJSONObject(i);
            id.add(i,object.getString("id"));
            type.add(i,object.getString("type"));
            url.add(i,object.getString("url"));
            time.add(i,object.getString("time"));
            releaseTime.add(i,object.getString("releaseTime"));
        }

        Map.put("id",id);
        Map.put("type",type);
        Map.put("url",url);
        Map.put("time",time);
        Map.put("releaseTime",releaseTime);
        return Map;
    }
}
