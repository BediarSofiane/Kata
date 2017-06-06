package fr.societe.kata.repository;

import fr.societe.kata.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SOFI on 31/05/2017.
 */
public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
    List<BankAccount> findAllByClientLogin(String clientLogin);
}
