package com.hiramexpress.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class AnalysisUtil {
    public String analysis(String shipperCodeUpper, String analysisPlatform) {
        try {
            InputStream io = this.getClass().getResourceAsStream("/analysis.json");
            InputStreamReader read = new InputStreamReader(io, StandardCharsets.UTF_8.name());
            BufferedReader br = new BufferedReader(read);
            String s;
            StringBuilder tipster = new StringBuilder();
            while ((s = br.readLine()) != null) {
                tipster.append(s);
            }
            JSONObject json = JSONObject.parseObject(tipster.toString());
            return json.getJSONObject("data").getJSONObject(analysisPlatform).getString(shipperCodeUpper);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
