package com.jihazardrestapi.demorestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventTestController {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper; //자동으로 빈으로 등록함

//    @MockBean
//    EventRepository eventRepository;

    @Test
    public void createEvent() throws Exception {

        Event event = Event.builder()
                .name("Spring")
                .description("REST API Development")
                .beginEventDateTime(LocalDateTime.of(2019, 07, 02, 11, 14))
                .closeEnrollmentDateTime(LocalDateTime.of(2019, 07, 02, 11, 14))
                .beginEnrollmentDateTime(LocalDateTime.of(2019, 07, 02, 11, 14))
                .endEventDateTime(LocalDateTime.of(2019, 07, 02, 11, 14))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("가산디지털단지역").build();
        //event repository 리턴값 설정 " mockbean 으로는 리턴값이 null 이기 때문에 mockito로 리턴값을 설정해줘야 합니다.
        //Mockito.when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())//Expect Result : 201
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
        .andExpect(jsonPath("eventStatus").value(Matchers.not(EventStatus.DRAFT))); // return value id가 존재하는지
        //        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
        //.andExpect(jsonPath("free").value(Matchers.not(true))); // return value id가 존재하는지


    }@Test
    public void badRequest() throws Exception {

        Event event = Event.builder()
                .name("Spring")
                .description("REST API Development")
                .id(100)
                .beginEventDateTime(LocalDateTime.of(2019, 07, 02, 11, 14))
                .closeEnrollmentDateTime(LocalDateTime.of(2019, 07, 02, 11, 14))
                .beginEnrollmentDateTime(LocalDateTime.of(2019, 07, 02, 11, 14))
                .endEventDateTime(LocalDateTime.of(2019, 07, 02, 11, 14))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100).free(false).offline(false).eventStatus(EventStatus.PUBLISHED)
                .location("가산디지털단지역").build();
        //event repository 리턴값 설정 " mockbean 으로는 리턴값이 null 이기 때문에 mockito로 리턴값을 설정해줘야 합니다.
        //Mockito.when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;


    }

}
