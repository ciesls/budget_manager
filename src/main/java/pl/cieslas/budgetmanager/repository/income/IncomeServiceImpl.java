package pl.cieslas.budgetmanager.repository.income;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cieslas.budgetmanager.entity.Income;
import pl.cieslas.budgetmanager.entity.User;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@Transactional
public class IncomeServiceImpl implements IncomeService{

    private IncomeRepository incomeRepository;

    @Autowired
    public IncomeServiceImpl(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public Optional<Income> findById(long id) {
        return incomeRepository.findById(id);
    }

    @Override
    public Optional<Income> findByIdAndUser(Long id, User user) {
        return incomeRepository.findByIdAndUser(id, user);
    }

    @Override
    public List<Income> findAllByUser(User user) {
        return incomeRepository.findAllByUser(user);
    }

    @Override
    public void deleteByIdAndUser(Long id, User user) {
        incomeRepository.deleteByIdAndUser(id, user);
    }

    @Override
    public Income save(Income income) {
        return incomeRepository.save(income);
    }
}
