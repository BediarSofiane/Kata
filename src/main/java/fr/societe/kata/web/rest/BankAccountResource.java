package fr.societe.kata.web.rest;

import fr.societe.kata.domain.BankAccount;
import fr.societe.kata.repository.BankAccountRepository;
import fr.societe.kata.web.rest.util.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @Autowired
    private BankAccountRepository bankAccountRepository;

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

        if(bankAccountRepository.findOne(bankAccount.getId())!=null){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("bank-account","idexists","a new Bank Account should not have already an ID")).body(null);
        }
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
