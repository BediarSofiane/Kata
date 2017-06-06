package fr.societe.kata.repository;

import fr.societe.kata.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by SOFI on 31/05/2017.
 */
public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
    List<BankAccount> findAllByClientLogin(String clientLogin);

    @Transactional
    @Modifying
    @Query("UPDATE BankAccount account SET account.credit=:credit WHERE account.id=:accountId")
    Integer setCredit(@Param("accountId") String accountId,@Param("credit") Double credit);
}
