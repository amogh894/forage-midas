package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;
import java.util.Optional;

import org.springframework.web.client.RestTemplate;
import com.jpmc.midascore.dto.Incentive;




@Component
public class DatabaseConduit {

    @Autowired
    private UserRepository userRepository;

    private final RestTemplate restTemplate;

    private static final String INCENTIVE_URL =
            "http://localhost:8080/incentive";


    @Autowired
    public DatabaseConduit(UserRepository userRepository,
                           RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }


    // =====================================================
    // ✅ REQUIRED BY FORAGE TESTS (DO NOT REMOVE)
    // =====================================================
    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
    }

    // =====================================================
    // ✅ TASK 3 — PROCESS TRANSACTIONS
    // =====================================================
    public void handleTransaction(Transaction transaction) {

        // Find sender and recipient using IDs
        Optional<UserRecord> senderOpt =
                userRepository.findById(transaction.getSenderId());

        Optional<UserRecord> recipientOpt =
                userRepository.findById(transaction.getRecipientId());

        // Continue only if both exist
        if (senderOpt.isPresent() && recipientOpt.isPresent()) {

            UserRecord sender = senderOpt.get();
            UserRecord recipient = recipientOpt.get();

            float amount = transaction.getAmount();

            Incentive incentiveResponse =
                    restTemplate.postForObject(
                            INCENTIVE_URL,
                            transaction,
                            Incentive.class
                    );

            float incentiveAmount = 0;

            if (incentiveResponse != null) {
                incentiveAmount = incentiveResponse.getAmount();
            }

            // Update balances
            sender.setBalance(
                    sender.getBalance() - transaction.getAmount()
            );

            recipient.setBalance(
                    recipient.getBalance()
                            + transaction.getAmount()
                            + incentiveAmount   // ⭐ incentive added ONLY to recipient
            );

            // Save updated balances
            userRepository.save(sender);
            userRepository.save(recipient);

            if (recipient.getName().equalsIgnoreCase("wilbur")) {
                System.out.println("✅ WILBUR BALANCE NOW: " + recipient.getBalance());
            }

            if (sender.getName().equalsIgnoreCase("wilbur")) {
                System.out.println("✅ WILBUR BALANCE NOW: " + sender.getBalance());
            }


            System.out.println("Transaction processed:");
            System.out.println("Sender: " + sender.getName()
                    + " -> " + sender.getBalance());

            System.out.println("Recipient: " + recipient.getName()
                    + " -> " + recipient.getBalance());
        }
    }




    // =====================================================
    // ✅ TASK 3 FINAL OUTPUT (AUTO PRINT)
    // =====================================================
    @PreDestroy
    public void printFinalBalances() {

        System.out.println("\n========== FINAL BALANCES ==========");

        Iterable<UserRecord> users = userRepository.findAll();

        for (UserRecord user : users) {

            System.out.println(
                    user.getName() + " -> " + user.getBalance()
            );

            // REQUIRED SUBMISSION OUTPUT
            if (user.getName().equalsIgnoreCase("waldorf")) {

                int roundedBalance =
                        (int) Math.floor(user.getBalance());

                System.out.println(
                        "\n✅ WALDORF FINAL BALANCE : "
                                + roundedBalance
                );
            }
        }

        System.out.println("====================================\n");
    }
}

