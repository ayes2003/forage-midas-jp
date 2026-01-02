package com.jpmc.midascore.entity;

import jakarta.persistence.*;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserRecord sender;

    @ManyToOne
    private UserRecord recipient;

    private float amount;
    private float incentive; // <--- NEW FIELD

    public TransactionRecord() {
    }

    // Updated Constructor
    public TransactionRecord(UserRecord sender, UserRecord recipient, float amount, float incentive) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.incentive = incentive;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public UserRecord getSender() { return sender; }
    public UserRecord getRecipient() { return recipient; }
    public float getAmount() { return amount; }
    public float getIncentive() { return incentive; } // <--- NEW GETTER

    public void setSender(UserRecord sender) { this.sender = sender; }
    public void setRecipient(UserRecord recipient) { this.recipient = recipient; }
    public void setAmount(float amount) { this.amount = amount; }
    public void setIncentive(float incentive) { this.incentive = incentive; } // <--- NEW SETTER
}