package fr.societe.kata.repository;

import fr.societe.kata.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by SOFI on 31/05/2017.
 */
public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
