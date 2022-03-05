package pl.cieslas.budgetmanager.category;


import pl.cieslas.budgetmanager.budget.Budget;
import pl.cieslas.budgetmanager.user.User;

import javax.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category_name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @ManyToOne
    private User user;

    public Category(Long id, String name, Budget budget, User user) {
        this.id = id;
        this.name = name;
        this.budget = budget;
        this.user = user;
    }

    public Category() {
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
