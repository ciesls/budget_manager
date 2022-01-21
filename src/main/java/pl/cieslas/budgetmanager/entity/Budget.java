package pl.cieslas.budgetmanager.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "budget_name")
    private String name;
    @Column(name = "budget_amount")
    private BigDecimal amount;
    @ManyToOne
    private Category category;

    public Budget(Long id, String name, BigDecimal amount, Category category) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.category = category;
    }

    public Budget(Long id) {
        this.id = id;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
