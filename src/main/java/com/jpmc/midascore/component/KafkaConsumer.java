package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.entity.TransactionRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final IncentiveApiService incentiveApiService; // <--- NEW SERVICE

    public KafkaConsumer(UserRepository userRepository, TransactionRepository transactionRepository, IncentiveApiService incentiveApiService) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.incentiveApiService = incentiveApiService;
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-group")
    public void listen(Transaction transaction) {
        LOGGER.info("Received Transaction: {}", transaction);

        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        if (sender == null || recipient == null) {
            LOGGER.warn("Transaction failed: Invalid sender or recipient.");
            return;
        }

        if (sender.getBalance() < transaction.getAmount()) {
            LOGGER.warn("Transaction failed: Insufficient funds.");
            return;
        }

        // 1. Call the Incentive API
        Incentive incentive = incentiveApiService.getIncentive(transaction);
        float incentiveAmount = incentive.getAmount();

        // 2. Calculate new balances
        float transactionAmount = transaction.getAmount();

        // Sender loses ONLY the transaction amount
        sender.setBalance(sender.getBalance() - transactionAmount);

        // Recipient gets transaction amount + incentive
        recipient.setBalance(recipient.getBalance() + transactionAmount + incentiveAmount);

        userRepository.save(sender);
        userRepository.save(recipient);

        // 3. Save the record with the incentive
        TransactionRecord record = new TransactionRecord(sender, recipient, transactionAmount, incentiveAmount);
        transactionRepository.save(record);

        // Print Wilbur's Balance for the final answer
        if (sender.getName().equals("wilbur")) {
            LOGGER.info("WILBUR BALANCE: {}", sender.getBalance());
        }
        if (recipient.getName().equals("wilbur")) {
            LOGGER.info("WILBUR BALANCE: {}", recipient.getBalance());
        }
    }
}