package com.jihazardrestapi.demorestapi.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class EventResource extends Resource<Event> {
//    @JsonUnwrapped
//    private Event event;
//
//    public EventResource(Event event) {
//        this.event = event;
//    }
//
//    public Event getEvent() {
//        return event;
//    }


    public EventResource(Event event, Link... links) {
        super(event, links);

        ControllerLinkBuilder selfBuilder = linkTo(EventController.class).slash(event.getId());

            add(selfBuilder.withSelfRel());
//        add(linkTo(EventController.class).withRel("query-events"));
//        add(selfBuilder.withRel("update-event"));
        //add(new Link("/docs/index.html").withRel("profile"));

    }
}
