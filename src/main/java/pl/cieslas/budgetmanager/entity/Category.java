package pl.cieslas.budgetmanager.entity;


import javax.persistence.*;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category_name")
    private String name;

    @OneToMany
    private List<Expense> expenses;

    @ManyToOne
    private User user;

    public Category(Long id, String name, List<Expense> expenses) {
        this.id = id;
        this.name = name;
        this.expenses = expenses;
    }

    public Category() {
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

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
