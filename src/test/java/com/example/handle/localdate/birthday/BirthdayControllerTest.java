package com.example.handle.localdate.birthday;

import com.example.handle.localdate.HandleLocalDateApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HandleLocalDateApplication.class)
@WebAppConfiguration
public class BirthdayControllerTest {

    private MockMvc mockMvc;

    private BirthdayController birthdayController;
    private BirthdayService birthdayService;

    @Before
    public void setup() {
        birthdayService = mock(BirthdayService.class);
        birthdayController = new BirthdayController(birthdayService);
        mockMvc = MockMvcBuilders.standaloneSetup(birthdayController).build();
    }

    @Test
    public void createNewBirthday_requestMapping_shouldReturnCreated_whenContentIsPresent() throws Exception {
        final String content = "{\"name\":\"Kendrick Williams\",\"date\":\"01/10/1988\"}";
        ResultActions resultActions = mockMvc.perform(
                post("/birthday/create")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void createNewBirthday_requestMapping_shouldReturnClientError_whenNoBirthdayParams() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                post("/birthday/create").contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    public void createNewBirthday_requestMapping_shouldReturnClientError_whenDateInWrongFormat() throws Exception {
        final String content = "{\"name\":\"Kendrick Williams\",\"date\":\"1988-01-10\"}";
        ResultActions resultActions = mockMvc.perform(
                post("/birthday/create")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    public void getBirthdays_requestMapping_shouldReturnListOfBirthday_whenPresent() throws Exception {
        when(birthdayService.findAll()).thenReturn(asList(new Birthday("name", LocalDate.now())));

        ResultActions resultActions = mockMvc.perform(
                get("/birthdays")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("name")))
                .andExpect(jsonPath("$[0].date", is(LocalDate.now().format(ofPattern("MM/dd/yyyy")))));
    }
}