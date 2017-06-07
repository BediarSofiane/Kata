package fr.societe.kata.web.rest;

import fr.societe.kata.BankApp;
import fr.societe.kata.domain.BankAccount;
import fr.societe.kata.domain.Operation;
import fr.societe.kata.domain.User;
import fr.societe.kata.repository.BankAccountRepository;
import fr.societe.kata.repository.OperationRepository;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by SOFI on 06/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankApp.class)
public class OperationResourceTest {

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UserRepository userRepository;

    User client;

    BankAccount bankAccount;

    MockMvc restOpertationMockMvc;


    @Before
    public void setUp(){
        this.operationRepository.deleteAll();
        this.bankAccountRepository.deleteAll();
        this.client = this.userRepository.findOneByLogin("user").get();
        this.bankAccount=this.bankAccountRepository.save(new BankAccount(this.client,1000.0));
        MockitoAnnotations.initMocks(this);
        OperationResource operationResource=new OperationResource(this.operationRepository,this.bankAccountRepository);
        restOpertationMockMvc = MockMvcBuilders.standaloneSetup(operationResource).build();
    }

    @Test
    public void getAllOperations() throws Exception {
        this.restOpertationMockMvc.perform(get("/operation-api/operations"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("Application/json;charset=UTF-8"))
            .andReturn();

        this.operationRepository.save(new Operation(this.bankAccount, LocalDate.of(2017,11,23),13.0,"deposit"));

        this.restOpertationMockMvc.perform(get("/operation-api/operations"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("Application/json;charset=UTF-8"))
            .andReturn();
    }

    @Test
    public void createOperation() throws Exception {
        Operation op=new Operation(this.bankAccount,LocalDate.of(2017,6,6),223.0,"deposit");

        restOpertationMockMvc.perform(post("/operation-api/operations")
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(TestUtil.convertObjectToJsonBytes(op)))
            .andDo(print())
            .andExpect(status().isCreated());

        restOpertationMockMvc.perform(get("/operation-api/operations"))
            .andDo(print())
            .andExpect(status().isOk());

    }

    @Test
    public void getAllOperationsByBankAccountId() throws Exception {
        this.operationRepository.save(new Operation(this.bankAccount, LocalDate.of(2017,11,23),13.0,"deposit"));
        this.bankAccount=this.bankAccountRepository.save(new BankAccount(this.client,1000.0));
        this.operationRepository.save(new Operation(this.bankAccount, LocalDate.of(2017,11,23),13.0,"deposit"));
        this.restOpertationMockMvc.perform(get("/operation-api/operations/"+this.bankAccount.getId()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("Application/json;charset=UTF-8"))
            .andReturn();
    }

}
