package com.jihazardrestapi.demorestapi.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {
    public void validate(EventDto eventDto, Errors errors){
        if(eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() != 0 ){
            errors.rejectValue("basePrice","wrongValue", "basePrice is Wrong"); //필드에러
            errors.rejectValue("maxPrice","wrongValue", "maxPrice is Wrong"); //필드에러
            errors.reject("wrongPrices", "Value for Price are Wrong");   //글로벌 에러
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime())||
        endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
        endEventDateTime.isBefore(eventDto.getBeginEventDateTime())){
            errors.rejectValue("endEventDateTime" , "wrongValue" , "endEventDateTime is WrongValue");
        }
    }
}
