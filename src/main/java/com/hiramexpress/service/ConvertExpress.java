package com.hiramexpress.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@Service
public class ConvertExpress {

    String convert(String express, String platform) {
        try {
            InputStream io = this.getClass().getResourceAsStream("/expressions.json");
            InputStreamReader read = new InputStreamReader(io, StandardCharsets.UTF_8.name());
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

    JSONObject listExpress() {
        try {
            InputStream io = this.getClass().getResourceAsStream("/expressions.json");
            InputStreamReader read = new InputStreamReader(io, StandardCharsets.UTF_8.name());
            BufferedReader br = new BufferedReader(read);
            String s;
            StringBuilder tipster = new StringBuilder();
            while ((s = br.readLine()) != null) {
                tipster.append(s);
            }
            JSONObject json = JSONObject.parseObject(tipster.toString());
            return json.getJSONObject("data").getJSONObject("KDNIAO");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
