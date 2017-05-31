package fr.societe.kata.web.rest;

import fr.societe.kata.domain.Operation;
import fr.societe.kata.repository.OperationRepository;
import fr.societe.kata.web.rest.util.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private OperationRepository operationRepository;


    @GetMapping("/operations")
    public List<Operation> getAllOperations(){
        return operationRepository.findAll();
    }

    @PostMapping("/operations")
    public ResponseEntity<Operation> createOperation(@RequestBody Operation operation) throws URISyntaxException {

        if(operationRepository.findOne(operation.getId())!=null){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("operation","idexists","a new Operation should not have already an ID")).body(null);
        }
        Operation result= operationRepository.save(operation);
        return ResponseEntity.created(new URI("/api/operations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("operation",result.getId().toString()))
            .body(result);
    }
}
