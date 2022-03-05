package pl.cieslas.budgetmanager.dto;

import pl.cieslas.budgetmanager.user.User;

import java.time.LocalDate;

public interface BudgetDetailsDTOService {

    BudgetDetailsDTO getBudgetDetailsDTO(User user, LocalDate startTime, LocalDate endTime, long id);

}
