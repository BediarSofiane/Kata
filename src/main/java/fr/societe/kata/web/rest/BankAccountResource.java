package fr.societe.kata.web.rest;

import fr.societe.kata.domain.BankAccount;
import fr.societe.kata.domain.User;
import fr.societe.kata.repository.BankAccountRepository;
import fr.societe.kata.repository.UserRepository;
import fr.societe.kata.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by SOFI on 31/05/2017.
 */
@RestController
@RequestMapping("/bank-account-api")
public class BankAccountResource {

    private final Logger log = LoggerFactory.getLogger(BankAccountResource.class);

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    public BankAccountResource(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }


    public BankAccountResource() {

    }


    @GetMapping("/accounts")
    public List<BankAccount> getAllBankAccounts(){
        return bankAccountRepository.findAll();
    }

    @PostMapping("/accounts")
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount) throws URISyntaxException {

        log.info("################## creating a new BankAccount");
        log.info(bankAccount.toString());

        if(bankAccount.getId()!=null){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("bank-account","idexists","a new Bank Account should not have already an ID")).body(null);
        }
        User user=this.userRepository.findOneByLogin(bankAccount.getClient().getLogin()).get();
        bankAccount.setClient(user);
        bankAccount.setAlias("Account");
        log.info("################## after changing user");
        log.info(bankAccount.toString());
        BankAccount result= bankAccountRepository.save(bankAccount);
        return ResponseEntity.created(new URI("/bank-account-api/accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bank-account",result.getId().toString()))
            .body(result);
    }

    @GetMapping("/accounts/{login}")
    public List<BankAccount> getAllBankAccountsByClientLogin(@PathVariable("login") String login ){
        return this.bankAccountRepository.findAllByClientLogin(login);
    }

}
