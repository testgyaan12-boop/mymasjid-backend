package com.noormasjid.service;

import com.noormasjid.entity.tasbih.CustomAdhkar;
import com.noormasjid.entity.tasbih.TasbihLog;
import com.noormasjid.repository.CustomAdhkarRepository;
import com.noormasjid.repository.TasbihLogRepository;
import com.noormasjid.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TasbihService {

    private final TasbihLogRepository tasbihLogRepository;
    private final CustomAdhkarRepository customAdhkarRepository;
    private final UserRepository userRepository;

    public TasbihService(TasbihLogRepository tasbihLogRepository,
                         CustomAdhkarRepository customAdhkarRepository,
                         UserRepository userRepository) {
        this.tasbihLogRepository = tasbihLogRepository;
        this.customAdhkarRepository = customAdhkarRepository;
        this.userRepository = userRepository;
    }

    public List<TasbihLog> getLogs(Long userId) {
        return tasbihLogRepository.findByUserIdOrderBySessionDateDesc(userId);
    }

    public TasbihLog saveLog(Long userId, TasbihLog log) {
        log.setUser(userRepository.getReferenceById(userId));
        if (log.getSessionDate() == null) {
            log.setSessionDate(LocalDate.now());
        }
        return tasbihLogRepository.save(log);
    }

    public List<CustomAdhkar> getAdhkars(Long userId) {
        return customAdhkarRepository.findByUserId(userId);
    }

    public CustomAdhkar saveAdhkar(Long userId, CustomAdhkar adhkar) {
        adhkar.setUser(userRepository.getReferenceById(userId));
        return customAdhkarRepository.save(adhkar);
    }

    public void deleteAdhkar(Long id) {
        customAdhkarRepository.deleteById(id);
    }

    public void deleteLog(Long id, Long userId) {
        TasbihLog log = tasbihLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tasbih log not found"));
        if (!log.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        tasbihLogRepository.deleteById(id);
    }
}
