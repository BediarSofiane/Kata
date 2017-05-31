package fr.societe.kata.domain;

import com.carrotsearch.hppc.HashOrderMixing;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by SOFI on 31/05/2017.
 */
@Entity
public class Operation implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @ManyToOne
    private BankAccount bankAccount;
    private Date date;
    private  Double amount;
    private String description;

    public String getId() {
        return id;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public Date getDate() {
        return date;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        return getId().equals(operation.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "Operation{" +
            "id='" + id + '\'' +
            ", bankAccount=" + bankAccount +
            ", date=" + date +
            ", amount=" + amount +
            ", description='" + description + '\'' +
            '}';
    }

    private static final long serialVersionUID = 1L;
}
