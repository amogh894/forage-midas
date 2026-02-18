package com.jpmc.midascore.entity;

import jakarta.persistence.*;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserRecord sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private UserRecord recipient;

    private double amount;

    public TransactionRecord() {}

    public TransactionRecord(UserRecord sender,
                             UserRecord recipient,
                             double amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

}


