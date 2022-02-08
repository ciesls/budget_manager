package pl.cieslas.budgetmanager.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "income_amount")
    private BigDecimal amount;

    @Column(name = "created_on", updatable = false)
    @CreationTimestamp
    private LocalDate createdOn;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Income(Long id, String name, BigDecimal amount, LocalDate createdOn, User user, Account account) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.createdOn = createdOn;
        this.user = user;
        this.account = account;
    }

    public Income() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

}
