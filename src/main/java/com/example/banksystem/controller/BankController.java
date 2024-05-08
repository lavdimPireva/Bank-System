package com.example.banksystem.controller;


import com.example.banksystem.dto.AccountDTO;
import com.example.banksystem.dto.BankDTO;
import com.example.banksystem.dto.TransferDTO;
import com.example.banksystem.entity.Account;
import com.example.banksystem.entity.Bank;
import com.example.banksystem.entity.Transaction;
import com.example.banksystem.repository.BankRepository;
import com.example.banksystem.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @Autowired
    private BankRepository bankRepository;



    @PostMapping("/createBank")
    public ResponseEntity<?> createBank(@RequestBody BankDTO bankDTO) {
        try {
            Bank bank = bankService.createBank(bankDTO.getBankName(), bankDTO.getFlatFee(), bankDTO.getPercentFee());
            String message = String.format("New bank created: %s with flat fee $%.2f and percent fee %.2f%%", bank.getBankName(), bank.getTransactionFlatFeeAmount(), bank.getTransactionPercentFeeValue().multiply(BigDecimal.valueOf(100)));
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/createAccount")
    public ResponseEntity<?> createAccount(@RequestBody AccountDTO accountDTO) {
        try {
            Account account = bankService.createAccount(accountDTO.getBankId(), accountDTO.getUserName(), accountDTO.getInitialBalance());
            Bank bank = bankRepository.findById(accountDTO.getBankId()).orElseThrow(() -> new RuntimeException("Bank not found"));
            String message = String.format("Account created for user '%s' with initial balance $%.2f in bank: %s", account.getUserName(), account.getBalance(), bank.getBankName());
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/accounts/{id}/deposit")
    public ResponseEntity<?> deposit(@PathVariable Long id, @RequestParam BigDecimal amount) {
        try {
            Account account = bankService.deposit(id, amount);
            String message = String.format("This user with the name %s has deposited $%.2f", account.getUserName(), amount);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/accounts/{id}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable Long id, @RequestParam BigDecimal amount) {
        try {
            Account account = bankService.withdraw(id, amount);
            String message = String.format("This user with the name %s has withdrawn $%.2f", account.getUserName(), amount);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferDTO transferDTO) {
        try {
            String transactionDetails = bankService.performTransaction(transferDTO.getBankId(), transferDTO.getFromAccountId(), transferDTO.getToAccountId(), transferDTO.getAmount(), transferDTO.getDescription());
            return ResponseEntity.ok(transactionDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/accounts/{id}/transactions")
    public ResponseEntity<?> getTransactionsForAccount(@PathVariable Long id) {
        try {
            List<Transaction> transactions = bankService.findTransactionsByAccountId(id);
            if (transactions.isEmpty()) {
                return ResponseEntity.ok("No transactions available for this account.");
            }

            List<String> transactionDetails = new ArrayList<>();
            for (Transaction transaction : transactions) {
                String detail = String.format("Transaction ID %d: $%.2f from account ID %d to account ID %d for %s",
                        transaction.getTransactionId(), transaction.getAmount(), transaction.getOriginatingAccountId(),
                        transaction.getResultingAccountId(), transaction.getTransactionReason());
                transactionDetails.add(detail);
            }
            return ResponseEntity.ok(transactionDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found or no transactions available.");
        }
    }



    @GetMapping("/accounts/{id}/balance")
    public ResponseEntity<?> getAccountBalance(@PathVariable Long id) {
        try {
            BigDecimal balance = bankService.checkBalance(id);
            return ResponseEntity.ok("Balance for account ID " + id + " is $" + balance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found.");
        }
    }

    @GetMapping("/banks/{bankId}/accounts")
    public ResponseEntity<?> getAccountsForBank(@PathVariable Long bankId) {
        try {
            List<Account> accounts = bankService.getAccounts(bankId);
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank not found or no accounts available.");
        }
    }

    @GetMapping("/banks/{bankId}/total-transaction-fees")
    public ResponseEntity<?> getBankTotalTransactionFees(@PathVariable Long bankId) {
        try {
            BigDecimal totalFees = bankService.getTotalTransactionFees(bankId);
            return ResponseEntity.ok("Total transaction fees for bank ID " + bankId + " are $" + totalFees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank not found.");
        }
    }

    @GetMapping("/banks/{bankId}/total-transfers")
    public ResponseEntity<?> getBankTotalTransferAmount(@PathVariable Long bankId) {
        try {
            BigDecimal totalTransfers = bankService.getTotalTransferAmount(bankId);
            return ResponseEntity.ok("Total transfer amount for bank ID " + bankId + " is $" + totalTransfers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank not found.");
        }
    }






}
