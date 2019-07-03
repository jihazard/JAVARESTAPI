package com.jihazardrestapi.demorestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventTestController {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper; //자동으로 빈으로 등록함

    @Test
    public void createEvent() throws Exception{

        Event event = Event.builder().name("Spring").description("REST API Development")
                .beginEventDateTime(LocalDateTime.of(2019,07,02,11,14))
                .closeEnrollmentDateTime(LocalDateTime.of(2019,07,02,11,14))
                .beginEnrollmentDateTime(LocalDateTime.of(2019,07,02,11,14))
                .endEventDateTime(LocalDateTime.of(2019,07,02,11,14))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("가산디지털단지역").build();


        mockMvc.perform(post("/api/events")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())//Expect Result : 201
                .andExpect(jsonPath("id").exists()); // return value id가 존재하는지


    }

}
