package com.example.handle.localdate.birthday;


import com.example.handle.localdate.HandleLocalDateApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HandleLocalDateApplication.class)
@WebAppConfiguration
public class BirthdayServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private BirthdayService birthdayService;

    @Before
    public void setup() {
        birthdayService = new BirthdayService(restTemplate);
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void sendBirthdayWishes_shouldPostBirthdayMessage() throws Exception {
        mockServer.expect(requestTo("http://happybirthday.com"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().string("{\"recipient\":\"Kendrick\",\"birthDay\":\"01/10/1988\"}"))
                .andRespond(withSuccess());

        birthdayService.sendBirthdayWihes("http://happybirthday.com", new BirthdayWish("Kendrick", LocalDate.parse("1988-01-10")));

        mockServer.verify();
    }
}