package fr.societe.kata.repository;

import fr.societe.kata.domain.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SOFI on 31/05/2017.
 */
public interface OperationRepository  extends JpaRepository<Operation,String>{
    List<Operation> findByBankAccountId(String bankAccountId);
}
