package com.jhippolyte.bankaccount.model;

import com.jhippolyte.bankaccount.exception.WithdrawNotAllowedException;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "bankAccounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private Person owner;

    @Column(name = "accountNumber")
    private String accountNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "balance")
    private Double balance = 50.00; //An account is created initially with 50 EUROS

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name = "overdraft")
    private Double overdraft = 0.00; //No overdraft allowed

    private static final String WITHDRAWAL_ERROR_MESSAGE = "The amount %s cannot be withdrawn because it exceeds the balance and the overdraft of the account";

    public void withdraw(double amount) throws WithdrawNotAllowedException{
        if (balance + overdraft - amount < 0) {
            throw new WithdrawNotAllowedException(String.format(WITHDRAWAL_ERROR_MESSAGE, amount));
        }
        this.balance = balance - amount;
    }

    public void deposit(double amount){
        this.balance = balance + amount;
    }

}
