package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void loadData() {

        // Prevent duplicate loading
        if (userRepository.count() > 0) {
            return;
        }

        userRepository.save(new UserRecord("Alice", 1000));
        userRepository.save(new UserRecord("Bob", 1000));
        userRepository.save(new UserRecord("Charlie", 1000));
        userRepository.save(new UserRecord("Waldorf", 1000));

        System.out.println("✅ Initial users loaded");
    }
}
