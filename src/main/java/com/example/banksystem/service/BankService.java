package com.example.banksystem.service;


import com.example.banksystem.entity.Account;
import com.example.banksystem.entity.Bank;
import com.example.banksystem.entity.Transaction;
import com.example.banksystem.repository.AccountRepository;
import com.example.banksystem.repository.BankRepository;
import com.example.banksystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankService {


    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;



    // Method to create a new Bank
    public Bank createBank(String bankName, BigDecimal flatFee, BigDecimal percentFee) {
        Bank bank = new Bank(bankName, flatFee, percentFee);
        return bankRepository.save(bank);
    }

    @Transactional
    public Account createAccount(Long bankId, String userName, BigDecimal initialBalance) {
        Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new RuntimeException("Bank not found"));

        Account account = new Account(userName, initialBalance);
        bank.getAccounts().add(account);
        accountRepository.save(account);
        return account;

    }



    @Transactional
    public String performTransaction(Long bankId, Long fromAccountId, Long toAccountId, BigDecimal amount, String reason) {
        Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new RuntimeException("Bank not found"));
        Account fromAccount = accountRepository.findById(fromAccountId).orElseThrow(() -> new RuntimeException("Originating account not found"));
        Account toAccount = accountRepository.findById(toAccountId).orElseThrow(() -> new RuntimeException("Resulting account not found"));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Not enough funds");
        }

        BigDecimal fee = calculateFee(bank, amount);
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount.add(fee)));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        bank.setTotalTransactionFeeAmount(bank.getTotalTransactionFeeAmount().add(fee));
        bank.setTotalTransferAmount(bank.getTotalTransferAmount().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        bankRepository.save(bank);

        return String.format("The transfer of $%.2f is made from %s to %s with this reason: %s",
                amount, fromAccount.getUserName(), toAccount.getUserName(), reason);
    }


    @Transactional
    public Account deposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        return account;

    }

    @Transactional
    public Account withdraw(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        return account;
    }

    public List<Transaction> findTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByOriginatingAccountIdOrResultingAccountId(accountId, accountId);
    }

    public List<Account> getAccounts(Long bankId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("Bank not found"));
        return bank.getAccounts();
    }

    public BigDecimal checkBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance();
    }

    public BigDecimal getTotalTransactionFees(Long bankId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("Bank not found"));
        return bank.getTotalTransactionFeeAmount();
    }

    public BigDecimal getTotalTransferAmount(Long bankId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("Bank not found"));
        return bank.getTotalTransferAmount();
    }




    private BigDecimal calculateFee(Bank bank, BigDecimal amount) {
        BigDecimal flatFee = bank.getTransactionFlatFeeAmount();
        BigDecimal percentFee = amount.multiply(bank.getTransactionPercentFeeValue().divide(new BigDecimal("100")));
        return flatFee.add(percentFee);
    }

}
