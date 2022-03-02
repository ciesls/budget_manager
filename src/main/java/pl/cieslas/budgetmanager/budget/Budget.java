package pl.cieslas.budgetmanager.budget;

import pl.cieslas.budgetmanager.category.Category;
import pl.cieslas.budgetmanager.user.User;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "budget_name")
    private String name;
    @Column(name = "budget_amount")
    @DecimalMin(value = "0.00", message = "Amount must be bigger than or equal to zero")
    private BigDecimal amount;

    @ManyToOne
    private User user;

    public Budget(Long id, String name, BigDecimal amount, List<Category> categories, User user) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.user = user;
    }

    public Budget() {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
