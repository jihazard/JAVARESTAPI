package com.jihazardrestapi.demorestapi.config;

import com.jihazardrestapi.demorestapi.account.Account;
import com.jihazardrestapi.demorestapi.account.AccountRole;
import com.jihazardrestapi.demorestapi.account.AccountService;
import com.jihazardrestapi.demorestapi.common.BaseControllerTest;
import com.jihazardrestapi.demorestapi.common.TestDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    AccountService accountService;

    @Test
    @TestDescription("인증토큰 발급 테스트")
    public void getAuthToken() throws Exception{

        //given
        Set<AccountRole> roles = new HashSet<>();
        roles.add(AccountRole.ADMIN);
        roles.add(AccountRole.USER);

        String username = "yoonjh2382@gmail.com";
        String pass = "12345";
        Account user = Account.builder().email(username).password(pass).roles(roles).build();
        this.accountService.saveAccount(user);

        String clientId ="myApp";
        String clientSecret = "pass";

        this.mockMvc.perform(post("/oauth/token")

                    .param("username",username)
                    .param("password",pass)
                    .param("grant_type","password")
                     .with(httpBasic(clientId,clientSecret))
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print())
                  .andExpect(jsonPath("access_token").exists());

    }
}