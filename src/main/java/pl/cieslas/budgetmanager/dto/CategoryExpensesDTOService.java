package pl.cieslas.budgetmanager.dto;

import pl.cieslas.budgetmanager.user.User;

import java.time.LocalDate;

public interface CategoryExpensesDTOService {


    CategoryExpensesDTO getCategoryExpensesDTO(User user, LocalDate monthStart, LocalDate now, long id);

}
