package com.hiramexpress.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ConvertExpress {

    public String convert(String express, String platform) {
        ClassPathResource resource = new ClassPathResource("expressinfo.json");
        try {
            File filePath = resource.getFile();
            InputStreamReader read = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
            BufferedReader br = new BufferedReader(read);
            String s;
            StringBuilder tipster = new StringBuilder();
            while ((s = br.readLine()) != null) {
                tipster.append(s);
            }
            JSONObject json = JSONObject.parseObject(tipster.toString());
            return json.getJSONObject("data").getJSONObject(platform).getString(express);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
