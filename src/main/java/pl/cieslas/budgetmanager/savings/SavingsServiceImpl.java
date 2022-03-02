package pl.cieslas.budgetmanager.savings;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cieslas.budgetmanager.user.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Primary
@Transactional
public class SavingsServiceImpl implements SavingsService {

    private final SavingsRepository savingsRepository;

    public SavingsServiceImpl(SavingsRepository savingsRepository) {
        this.savingsRepository = savingsRepository;
    }

    @Override
    public Optional<Savings> findById(Long id) {
        return savingsRepository.findById(id);
    }

    @Override
    public Optional<Savings> findByIdAndUser(Long id, User user) {
        return savingsRepository.findByIdAndUser(id, user);
    }

    @Override
    public List<Savings> findAllByUser(User user) {
        return savingsRepository.findAllByUser(user);
    }

    @Override
    public Savings save(Savings savings) {
        return savingsRepository.save(savings);
    }

    @Override
    public void deleteByIdAndUser(Long id, User user) {
        savingsRepository.deleteById(id);

    }

    @Override
    public void increaseValue(User user, long id, BigDecimal newValue) {
        Optional<Savings> saving = savingsRepository.findByIdAndUser(id, user);
        if (saving.isPresent()) {
            saving.get().setPreviousValue(saving.get().getValue());
            saving.get().setValue(newValue);
            savingsRepository.save(saving.get());
        } else throw new RuntimeException("Savings not found");
    }

    @Override
    public Map<Savings, BigDecimal> getSavingsDetails(List<Savings> savingsList) {
        Map<Savings, BigDecimal> savings = new HashMap<>();
        for (Savings saving : savingsList) {
            BigDecimal previousValue = saving.getPreviousValue();
            BigDecimal currentValue = saving.getValue();
            BigDecimal change = currentValue.subtract(previousValue);
            BigDecimal percentage = change.divide(previousValue, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
            savings.put(saving, percentage);
        }

        return savings.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

}
