package com.jihazardrestapi.demorestapi.events;

import com.jihazardrestapi.demorestapi.account.Account;
import com.jihazardrestapi.demorestapi.account.AccountAdapter;
import com.jihazardrestapi.demorestapi.account.CurrentUser;
import com.jihazardrestapi.demorestapi.common.ErrorResource;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class EventController {
    @Autowired
     EventRepository eventRepository;

    @Autowired
    ModelMapper modelMapper;

    private EventValidator eventValidator;

    public EventController(EventValidator eventValidator) {
        this.eventValidator = eventValidator;
    }

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors
                                    ,@CurrentUser Account currentUser) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorResource(errors));
        }

        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Event event = modelMapper.map(eventDto, Event.class);
        event.update();
        event.setManger(currentUser);
        Event newEvent = this.eventRepository.save(event);
        ControllerLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvent.getId());
        URI createUri = selfLinkBuilder.toUri();
        ControllerLinkBuilder selfBuilder = linkTo(EventController.class).slash(event.getId());
        EventResource eventResource = new EventResource(event);
            eventResource.add(linkTo(EventController.class).withRel("query-events"));
            eventResource.add(selfBuilder.withRel("update-event"));
            eventResource.add(new Link("/docs/index.html").withRel("profile"));
        return ResponseEntity.created(createUri).body(eventResource);
    }


    @GetMapping
    public ResponseEntity queryEvents(Pageable pageable, PagedResourcesAssembler<Event> assembler,
                                      @CurrentUser Account account){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User principal = (User) authentication.getPrincipal();


        Page<Event> page = this.eventRepository.findAll(pageable);
        var pageResource = assembler.toResource(page , e -> new EventResource(e));
        pageResource.add(new Link("/docs/index.html#resources-events-list").withRel("profile"));

        if(account != null) {
            pageResource.add(linkTo(EventController.class).withRel("create-events"));
        }
        return ResponseEntity.ok(pageResource);

    }

    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Integer id, @CurrentUser Account currentUser){
        Optional<Event> optional =  this.eventRepository.findById(id);

        if(!optional.isPresent())
            return ResponseEntity.notFound().build();

        Event event = optional.get();
        EventResource eventResource = new EventResource(event);
        eventResource.add(new Link("/docs/index.html#resources-events-get").withRel("profile"));
        if(event.getManger().equals(currentUser)){
            eventResource.add(linkTo(EventController.class).slash(event.getId()).withRel("update-event"));
        }

        return ResponseEntity.ok(eventResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEvent(@PathVariable Integer id
                                    , @RequestBody @Valid EventDto eventDto
                                    , Errors errors
                                    ,@CurrentUser Account currentUser){
            Optional<Event> optional = this.eventRepository.findById(id);
            if(!optional.isPresent()){
                return ResponseEntity.notFound().build();
            }

            if(errors.hasErrors()){
                return ResponseEntity.badRequest().body(errors);
            }

            this.eventValidator.validate(eventDto, errors);
              if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
              }

              Event exEvent = optional.get();

              if(!exEvent.getManger().equals(currentUser)){
                  return new ResponseEntity(HttpStatus.UNAUTHORIZED);
              }
              this.modelMapper.map(eventDto,exEvent);
              Event save = this.eventRepository.save(exEvent);

              EventResource eventResource = new EventResource(save);
              eventResource.add(new Link("/docs/index.html#resources-events-update").withRel("profile"));

        return ResponseEntity.ok(eventResource);
    }

}
