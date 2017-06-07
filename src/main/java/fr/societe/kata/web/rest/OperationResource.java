package fr.societe.kata.web.rest;

import fr.societe.kata.domain.BankAccount;
import fr.societe.kata.domain.Operation;
import fr.societe.kata.repository.BankAccountRepository;
import fr.societe.kata.repository.OperationRepository;
import fr.societe.kata.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by SOFI on 31/05/2017.
 */
@RestController
@RequestMapping("/operation-api")
public class OperationResource {

    private final Logger log = LoggerFactory.getLogger(OperationResource.class);

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public OperationResource() {
    }

    public OperationResource(OperationRepository operationRepository, BankAccountRepository bankAccountRepository) {
        this.operationRepository = operationRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @GetMapping("/operations")
    public List<Operation> getAllOperations(){
        return operationRepository.findAll();
    }

    @PostMapping("/operations")
    @Transactional
    public ResponseEntity<Operation> createOperation(@RequestBody Operation operation) throws URISyntaxException {

        log.info("################## creating a new operation");
        log.info(operation.toString());

        if(operation.getId()!=null){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("operation","idexists","a new Operation should not have already an ID")).body(null);
        }


        BankAccount account=this.bankAccountRepository.findOne(operation.getBankAccount().getId());
        operation.setBankAccount(account);
        bankAccountRepository.setCredit(account.getId(),account.getCredit()+operation.getAmount());
        Operation result= operationRepository.save(operation);
        return ResponseEntity.created(new URI("/api/operations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("operation",result.getId().toString()))
            .body(result);
    }

    @GetMapping("operations/{accountId}")
    public List<Operation> getAllOperationsByBankAccountId(@PathVariable("accountId") String accountId){
        log.info("id=============== "+accountId);
        return this.operationRepository.findByBankAccountId(accountId);
    }
}
