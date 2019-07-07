package com.jihazardrestapi.demorestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jihazardrestapi.demorestapi.common.RestDocConfiguration;
import com.jihazardrestapi.demorestapi.common.TestDescription;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocConfiguration.class)
@ActiveProfiles("test")
public class EventTestController {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper; //자동으로 빈으로 등록함

    @Autowired
    EventRepository eventRepository;

//    @MockBean
//    EventRepository eventRepository;

    @Test
    @TestDescription("이벤트 생성 ")
    public void createEvent() throws Exception {

        EventDto event = EventDto.builder()
                .name("Spring")
                .description("REST API Development")
                .beginEventDateTime(LocalDateTime.of(2019, 07, 02, 10, 14))
                .closeEnrollmentDateTime(LocalDateTime.of(2019, 07, 03, 11, 14))
                .beginEnrollmentDateTime(LocalDateTime.of(2019, 07, 02, 11, 14))
                .endEventDateTime(LocalDateTime.of(2019, 07, 04, 11, 14))
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
                .andExpect(jsonPath("eventStatus").value(Matchers.not(EventStatus.DRAFT))) // return value id가 존재하는지
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("free").value(false)) // return value id가 존재하는지
                .andExpect(jsonPath("offline").value(true)) // return value id가 존재하는지
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-events").exists())
                .andExpect(jsonPath("_links.update-event").exists())
                .andDo(document("create-event", links(
                        linkWithRel("self").description("link to self"),
                        linkWithRel("query-events").description("link to query-events"),
                        linkWithRel("update-event").description("link to update-event"),
                        linkWithRel("profile").description("link to profile")


                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        requestFields(
                                fieldWithPath("name").description("Name of new event"),
                                fieldWithPath("description").description("Name of new description"),
                                fieldWithPath("beginEnrollmentDateTime").description("Name of new beginEnrollmentDateTime"),
                                fieldWithPath("closeEnrollmentDateTime").description("Name of new closeEnrollmentDateTime"),
                                fieldWithPath("beginEventDateTime").description("Name of new beginEventDateTime"),
                                fieldWithPath("endEventDateTime").description("Name of new endEventDateTime"),
                                fieldWithPath("location").description("Name of new location"),
                                fieldWithPath("basePrice").description("Name of new basePrice"),
                                fieldWithPath("maxPrice").description("Name of new maxPrice"),
                                fieldWithPath("limitOfEnrollment").description("Name of new limitOfEnrollment")
                        )
                        , responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("location header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                        )
                        , responseFields(
                                fieldWithPath("id").description("Name of new id"),
                                fieldWithPath("name").description("Name of new event"),
                                fieldWithPath("description").description("Name of new description"),
                                fieldWithPath("beginEnrollmentDateTime").description("Name of new beginEnrollmentDateTime"),
                                fieldWithPath("closeEnrollmentDateTime").description("Name of new closeEnrollmentDateTime"),
                                fieldWithPath("beginEventDateTime").description("Name of new beginEventDateTime"),
                                fieldWithPath("endEventDateTime").description("Name of new endEventDateTime"),
                                fieldWithPath("location").description("Name of new location"),
                                fieldWithPath("basePrice").description("Name of new basePrice"),
                                fieldWithPath("maxPrice").description("Name of new maxPrice"),
                                fieldWithPath("limitOfEnrollment").description("Name of new limitOfEnrollment"),
                                fieldWithPath("free").description("Name of new free"),
                                fieldWithPath("offline").description("Name of new offline"),
                                fieldWithPath("eventStatus").description("Name of new eventStatus"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.query-events.href").description("link to query-events"),
                                fieldWithPath("_links.update-event.href").description("link to update-event"),
                                fieldWithPath("_links.profile.href").description("link to profile")
                        )

                ))
        ;

    }

    @Test
    @TestDescription("배드 리퀘스트 요청인 경우")
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

    @Test
    @TestDescription("배드 입력값이 비어있을 경우")
    public void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsBytes(eventDto)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @TestDescription("배드 리퀘스트 요청이 잘못되었을 경우")
    public void createEvent_Bad_Request_Wrong_Input() throws Exception {
        EventDto eventDto = EventDto.builder().name("Spring")
                .description("REST API Development")
                .beginEventDateTime(LocalDateTime.of(2019, 07, 02, 11, 14))
                .closeEnrollmentDateTime(LocalDateTime.of(2019, 07, 02, 11, 14))
                .beginEnrollmentDateTime(LocalDateTime.of(2019, 07, 02, 11, 14))
                .endEventDateTime(LocalDateTime.of(2019, 07, 02, 11, 14))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("가산디지털단지역").build();

        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsBytes(eventDto)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists())
        ;
    }


    @Test
    @TestDescription("30개의 이벤트를 10개씩 두번째 페이지 조회")
    public void getQueryEvents() throws Exception {
        //Given
        IntStream.range(0, 30).forEach(this::generateEvent);
        //when
        this.mockMvc.perform(get("/api/events")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "name,DESC")
        ).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.eventList[1]._links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-events"))
        ;
        //then
    }

    private void generateEvent(int i) {
        Event event = Event.builder().name("event " + i).description("event test").build();
        this.eventRepository.save(event);
    }
}

