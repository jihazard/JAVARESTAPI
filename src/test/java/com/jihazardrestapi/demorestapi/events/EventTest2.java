package com.jihazardrestapi.demorestapi.events;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;

public class EventTest2 {
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

    @Test
    public void testFree() {
        //given
        Event event = Event.builder().basePrice(0).maxPrice(0).build();

        //when
        event.update();
        //then
        assertThat(event.isFree()).isTrue();

        event = Event.builder().basePrice(100).maxPrice(0).build();

        //when
        event.update();
        //then
        assertThat(event.isFree()).isFalse();

        event = Event.builder().basePrice(100).maxPrice(101).build();

        //when
        event.update();
        //then
        assertThat(event.isFree()).isFalse();
    }

    @Test
    public void testOffline() {
        //given
        Event event = Event.builder().location("가디역").build();

        //when
        event.update();
        //then
        assertThat(event.isOffline()).isTrue();

        event = Event.builder().build();

        //when
        event.update();
        //then
        assertThat(event.isOffline()).isFalse();
    }
}