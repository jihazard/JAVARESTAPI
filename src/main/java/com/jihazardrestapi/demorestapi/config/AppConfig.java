package com.jihazardrestapi.demorestapi.config;

import com.jihazardrestapi.demorestapi.account.Account;
import com.jihazardrestapi.demorestapi.account.AccountRole;
import com.jihazardrestapi.demorestapi.account.AccountService;
import com.jihazardrestapi.demorestapi.events.Event;
import com.jihazardrestapi.demorestapi.events.EventDto;
import com.jihazardrestapi.demorestapi.events.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner(){

        return new ApplicationRunner() {
            @Autowired
            AccountService accountService;

            @Autowired
            EventRepository eventRepository;

            @Autowired
            ModelMapper modelMapper;

            @Autowired
            AppProperties appProperties;


            @Override
            public void run(ApplicationArguments args) throws Exception {
                Set<AccountRole> roles = new HashSet<>();
                roles.add(AccountRole.ADMIN);
                roles.add(AccountRole.USER);


                Account account= Account.builder()
                        .email(appProperties.getAdminUsername())
                        .password(appProperties.getAdminPassword())
                        .roles(roles)
                        .build();

                accountService.saveAccount(account);

                roles.remove(AccountRole.ADMIN);
                Account user= Account.builder()
                        .email(appProperties.getUserName())
                        .password(appProperties.getUserPassword())
                        .roles(roles)
                        .build();

                accountService.saveAccount(account);

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
                Event event = modelMapper.map(eventDto, Event.class);




                eventRepository.save(event);

            }
        };
    }

}
