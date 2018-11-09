package com.hiramexpress.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author hiram 2018年11月09日 23:39
 */

@Service
public class AnalysisExpress {

    String analysis(String express) {
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
            return json.getJSONObject("data").getString(express);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
