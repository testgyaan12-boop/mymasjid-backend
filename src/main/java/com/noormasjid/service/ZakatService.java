package com.noormasjid.service;

import com.noormasjid.entity.zakat.ZakatCalculation;
import com.noormasjid.repository.UserRepository;
import com.noormasjid.repository.ZakatCalculationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZakatService {

    private final ZakatCalculationRepository zakatCalculationRepository;
    private final UserRepository userRepository;

    public ZakatService(ZakatCalculationRepository zakatCalculationRepository,
                        UserRepository userRepository) {
        this.zakatCalculationRepository = zakatCalculationRepository;
        this.userRepository = userRepository;
    }

    public ZakatCalculation calculate(ZakatCalculation calc, Long userId) {
        double netWorth = (calc.getGoldValue() != null ? calc.getGoldValue() : 0)
                + (calc.getSilverValue() != null ? calc.getSilverValue() : 0)
                + (calc.getCashValue() != null ? calc.getCashValue() : 0)
                + (calc.getBusinessValue() != null ? calc.getBusinessValue() : 0)
                + (calc.getPropertyValue() != null ? calc.getPropertyValue() : 0)
                - (calc.getLiabilities() != null ? calc.getLiabilities() : 0);

        calc.setNetWorth(netWorth);
        calc.setZakatDue(netWorth * 0.025);
        calc.setUser(userRepository.getReferenceById(userId));
        calc.setCalculationDate(java.time.LocalDate.now());
        return zakatCalculationRepository.save(calc);
    }

    public List<ZakatCalculation> getHistory(Long userId) {
        return zakatCalculationRepository.findByUserIdOrderByCalculationDateDesc(userId);
    }
}
