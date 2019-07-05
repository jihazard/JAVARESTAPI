package com.jihazardrestapi.demorestapi.events;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;


@RunWith(JUnitParamsRunner.class)
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
//    @Parameters({
//            "0, 0, true",
//            "100, 0, false",
//            "0, 100, false"
//    })

    //@Parameters(method = "paramsForTestFree")
    @Parameters
    public void testFree(int basePirce, int maxPrice, boolean isFree) {
        //given
        Event event = Event.builder().basePrice(basePirce).maxPrice(maxPrice).build();

        //when
        event.update();
        //then
        assertThat(event.isFree()).isEqualTo(isFree);
    }

    private Object[] parametersForTestFree() {
        return new Object[]{
                new Object[]{0, 0, true},
                new Object[]{100, 0, false},
                new Object[]{0, 100, false},
                new Object[]{100, 200, false}
        };
    }

    @Test
    @Parameters
    public void testOffline(String location, boolean isOffLine) {
        //given
        Event event = Event.builder().location(location).build();

        //when
        event.update();
        //then
        assertThat(event.isOffline()).isEqualTo(isOffLine);
}

    private Object[] parametersForTestOffline() {
        return new Object[]{
                new Object[]{"가산", true},
                new Object[]{null, false},
                new Object[]{" ", false},
        };
    }

}