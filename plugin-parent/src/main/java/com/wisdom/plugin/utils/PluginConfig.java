package com.wisdom.plugin.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;


public class PluginConfig extends HashMap<String,Object> {
    private static Logger logger = Logger.getLogger(PluginConfig.class.getName());

    private PluginConfig(){}

    public void loadFile(FileCallBack fileCallBack,String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            logger.info("加载配置文件." + path);
            if (path.lastIndexOf(".properties") > 0) {
                Properties p = new Properties();
                p.load(new FileInputStream(file));
                Enumeration enumeration = p.propertyNames();
                while (enumeration.hasMoreElements()) {
                    Object key = enumeration.nextElement();
                    Object value = p.get(key);
                    this.put(key.toString(), value);
                }
            }
            if (path.lastIndexOf(".yml") > 0) {
                Yaml yaml = new Yaml();
                Map<String,String> yamlMap = new HashMap<>();
                this.setYmlMap(yamlMap,null,yaml.loadAs(new FileInputStream(file),Map.class));
                this.putAll(yamlMap);
            }
            if(fileCallBack != null){
                fileCallBack.invok(path,this);
            }
        }
    }

    private void setYmlMap(Map<String,String> resultMap,String pkey,Map<String, Object> yamlMap){
        for(String key:yamlMap.keySet()){
            Object value = yamlMap.get(key);
            if(value != null) {
                String allKey = pkey == null ? key : pkey + "." + key;
                if (value instanceof Map) {
                    Map<String, Object> valueMap = (Map<String, Object>) value;
                    this.setYmlMap(resultMap, allKey, valueMap);
                } else {
                    resultMap.put(allKey, value.toString());
                }
            }
        }

    }

    public static PluginConfig load(String ... paths) throws IOException {
       return load(null,paths);
    }

    public static PluginConfig load(FileCallBack fileCallBack,String ... paths) throws IOException {
        PluginConfig pc = new PluginConfig();
        for(String path : paths) {
            pc.loadFile(fileCallBack,path);
        }
        if(pc.isEmpty()){
            throw new FileNotFoundException("未找到配置文件.");
        }
        return pc;
    }
    public interface FileCallBack{
        public void invok(String path,PluginConfig pluginConfig);
    }
}
