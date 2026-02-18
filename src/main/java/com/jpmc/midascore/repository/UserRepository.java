package com.jpmc.midascore.repository;

import com.jpmc.midascore.entity.UserRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;   // ✅ ADD THIS


public interface UserRepository extends CrudRepository<UserRecord, Long> {


    // ✅ ADD THIS METHOD
    Optional<UserRecord> findByNameIgnoreCase(String name);
}
