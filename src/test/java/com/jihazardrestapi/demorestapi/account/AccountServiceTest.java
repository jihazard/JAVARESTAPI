package com.jihazardrestapi.demorestapi.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {
    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Test
    public void  findByUsername() {
        //given
        Set<AccountRole> set = new HashSet<>();
        set.add(AccountRole.USER);
        set.add(AccountRole.ADMIN);
        String password = "jihwan";
        String username = "yoonjh238@gmail.com";
        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(set)
                .build();

        this.accountService.saveAccount(account);

        //WHEN
        UserDetailsService userDetailsService = accountService;
        UserDetails user = userDetailsService.loadUserByUsername(username);

        //THEN
        assertThat(this.passwordEncoder.matches(password, user.getPassword())).isTrue();
    }
}