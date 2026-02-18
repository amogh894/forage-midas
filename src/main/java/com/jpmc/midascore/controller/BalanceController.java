package com.jpmc.midascore.controller;

import com.jpmc.midascore.dto.Balance;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class BalanceController {

    private final UserRepository userRepository;

    public BalanceController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/balance")
    public Balance getBalance(@RequestParam Long userId) {

        Optional<UserRecord> userOpt = userRepository.findById(userId);

        float balance = 0;

        if (userOpt.isPresent()) {
            balance = userOpt.get().getBalance();
        }

        return new Balance(balance);
    }
}
