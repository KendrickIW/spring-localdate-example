package com.example.handle.localdate.birthday;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BirthdayService {

    private RestTemplate restTemplate;

    @Autowired
    public BirthdayService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Birthday> findAll() {
        return null;
    }

    public void sendBirthdayWihes(String recipientUrl, BirthdayWish birthdayWish) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BirthdayWish> requestEntity = new HttpEntity<>(birthdayWish, headers);
        restTemplate.exchange(recipientUrl, HttpMethod.POST, requestEntity, Object.class);
    }
}
