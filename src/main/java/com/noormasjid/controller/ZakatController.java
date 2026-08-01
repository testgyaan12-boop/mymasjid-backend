package com.noormasjid.controller;

import com.noormasjid.entity.zakat.ZakatCalculation;
import com.noormasjid.security.UserDetailsImpl;
import com.noormasjid.service.ZakatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/zakat")
public class ZakatController {

    private final ZakatService zakatService;

    public ZakatController(ZakatService zakatService) {
        this.zakatService = zakatService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<ZakatCalculation> calculate(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody ZakatCalculation calc) {
        return ResponseEntity.ok(zakatService.calculate(calc, user.getId()));
    }

    @GetMapping("/history")
    public ResponseEntity<List<ZakatCalculation>> getHistory(
            @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(zakatService.getHistory(user.getId()));
    }
}
