package com.jihazardrestapi.demorestapi.events;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {
    @Test
    public void builder() {
        Event event = Event.builder().build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        Event event = new Event();
        String name = "EVENT";
        String description = "SPRING";


        //WHEN
        event.setName(name);
        event.setDescription(description);

        //then
        assertThat(event.getName()).isEqualTo("EVENT");
        assertThat(event.getDescription()).isEqualTo(description);

    }
}