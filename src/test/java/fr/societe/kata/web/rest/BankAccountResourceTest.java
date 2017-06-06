package fr.societe.kata.web.rest;

import fr.societe.kata.BankApp;
import fr.societe.kata.domain.BankAccount;
import fr.societe.kata.domain.User;
import fr.societe.kata.repository.BankAccountRepository;
import fr.societe.kata.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.*;

/**
 * Created by SOFI on 02/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankApp.class)
public class BankAccountResourceTest {

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UserRepository userRepository;

    BankAccount ba;
    User client;
    MockMvc restBankAccountMockMvc;

    @Before
    public void setup() {
        this.bankAccountRepository.deleteAll();
        this.client = this.userRepository.findOneByLogin("user").get();
        MockitoAnnotations.initMocks(this);
        BankAccountResource bankAccountResource = new BankAccountResource(this.bankAccountRepository);
        restBankAccountMockMvc = MockMvcBuilders.standaloneSetup(bankAccountResource).build();
    }


    @Test
    public void getAllBankAccounts() throws Exception {
        restBankAccountMockMvc.perform(get("/bank-account-api/accounts"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void getAllBankAccountsByClientLogin() throws Exception {

        this.bankAccountRepository.save(new BankAccount(this.client,6847.0));
        this.bankAccountRepository.save(new BankAccount(this.client,5645.0));
        restBankAccountMockMvc.perform(get("/bank-account-api/accounts/user"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void createBankAccount() throws Exception {
        restBankAccountMockMvc.perform(post("/bank-account-api/accounts")
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(TestUtil.convertObjectToJsonBytes(this.ba))
        );
    }

}
