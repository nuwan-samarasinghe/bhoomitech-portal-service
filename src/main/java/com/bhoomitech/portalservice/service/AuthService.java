package com.bhoomitech.portalservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AuthService {
    private final RestTemplate restTemplate;

    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${authUrl}")
    private String authUrl;

    public List getAllAuthDetails(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity entity = new HttpEntity<>(headers);
        return restTemplate.exchange(authUrl + "/auth//user/all", HttpMethod.GET, entity, List.class).getBody();
    }

    public Object getUserById(String token, String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity entity = new HttpEntity<>(headers);
        return restTemplate.exchange(authUrl + "/auth//user/"+ userId, HttpMethod.GET, entity, Object.class).getBody();
    }
}
