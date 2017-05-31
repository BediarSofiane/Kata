package fr.societe.kata.domain;

import javax.persistence.*;
import javax.persistence.GenerationType;
import java.io.Serializable;

/**
 * Created by SOFI on 31/05/2017.
 */
@Entity
public class BankAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private User client;
    private Double credit;





    public void setId(String id) {
        this.id = id;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getId() {

        return this.id;
    }

    public User getClient() {
        return client;
    }

    public Double getCredit() {
        return credit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankAccount bankAccount = (BankAccount) o;

        return getId().equals(bankAccount.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "BankAccount{" +
            "Id='" + id + '\'' +
            ", client=" + client +
            ", credit=" + credit +
            '}';
    }

    private static final long serialVersionUID = 1L;
}
