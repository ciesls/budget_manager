package pl.cieslas.budgetmanager.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @DecimalMin(value = "0.01", message = "Amount must be bigger than zero")
    private BigDecimal amount;
    @Column(name = "created_on", updatable = false)
    @CreationTimestamp
    private LocalDate createdOn;

    @ManyToOne
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Expense(Long id, String name, String description, BigDecimal amount, LocalDate createdOn, User user, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.createdOn = createdOn;
        this.user = user;
        this.category = category;
    }

    public Expense() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    @PrePersist
    public void prePersist() {
        createdOn = LocalDate.now();
    }

    public YearMonth getYearMonth() {
        return YearMonth.from(createdOn);
    }


}
