package fr.societe.kata.domain;

import fr.societe.kata.BankApp;
import fr.societe.kata.repository.BankAccountRepository;
import fr.societe.kata.repository.OperationRepository;
import fr.societe.kata.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * Created by SOFI on 01/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankApp.class)
public class OperationTest {
   @Autowired
    UserRepository userRepository;

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    BankAccount ba;
    Operation op1;
    User client;

    @Before
    public void setup(){
        this.operationRepository.deleteAll();
        this.client= this.userRepository.findOneByLogin("user").get();
        this.ba=this.bankAccountRepository.save(new BankAccount(this.client,4445.45));
    }

    @Test
    public void setsIdOnSave(){

        this.op1=this.operationRepository.save(
            new Operation(this.ba, LocalDate.of(2017,5,23),45.0,"deposit")
        );

        Operation recup=this.operationRepository.findAll().get(0);
        assertThat(recup.getId()).isNotNull();

    }


    @Test
    public void findByBankAccountId(){
        this.ba=this.bankAccountRepository.findAllByClientLogin("user").get(0);
        this.operationRepository.save(new Operation(this.ba,LocalDate.of(2017,2,18),-123.0,"withdrawal"));
        this.operationRepository.save(new Operation(this.ba,LocalDate.of(2017,4,3),45.0,"deposit"));
        List<Operation> operations=this.operationRepository.findByBankAccountId(this.ba.getId());
        assertThat(operations).hasSize(2).extracting("date.month").containsExactly(Month.FEBRUARY,Month.APRIL);
        assertThat(operations).extracting("amount").containsExactly(-123.0,45.0);
    }

    @Test
    public void equals()  {
        Operation op1=this.operationRepository.save(new Operation(this.ba,LocalDate.of(2017,12,3),445.5,"deposit"));
        Operation op2=this.operationRepository.save(new Operation(this.ba,LocalDate.of(2017,12,3),445.5,"deposit"));
        assertThat(op1.equals(op2)).isFalse();
        op2=op1;
        assertThat(op1.equals(op2)).isTrue();
    }

}
