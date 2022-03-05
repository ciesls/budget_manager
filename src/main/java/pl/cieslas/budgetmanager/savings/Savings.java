package pl.cieslas.budgetmanager.savings;

import pl.cieslas.budgetmanager.user.User;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Savings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal value;
    private BigDecimal previousValue;
    private String description;

    @ManyToOne
    private User user;

    public Savings(Long id, String name, BigDecimal value, BigDecimal previousValue, String description) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.previousValue = previousValue;
        this.description = description;
    }

    public Savings() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(BigDecimal previousValue) {
        this.previousValue = previousValue;
    }
}
