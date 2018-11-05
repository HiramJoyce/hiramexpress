package com.hiramexpress.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpClient {

    private final Logger logger = LoggerFactory.getLogger(HttpClient.class);

    public String client(String url, HttpMethod method, MultiValueMap<String, String> params) {
        RestTemplate template = new RestTemplate();
        logger.info("--->>> url: " + url);
        logger.info("--->>> method: " + method.toString());
        logger.info("--->>> params: " + params);
        ResponseEntity<String> response = template.postForEntity(url, method, String.class, params);
        return response.getBody();
    }
}
