package com.brian.transaction_importer_spring.controller;

import com.brian.transaction_importer_spring.entity.Transaction;
import com.brian.transaction_importer_spring.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ImportRequestController {
    @Autowired
    private ImportService importService;

    @GetMapping("/import-transactions")
    public String importTransactionsRequest(Model model) {
        List<Transaction> transactions = importService.beginTransactionImport();
        model.addAttribute("transactions", transactions);
        return "transactionImportStatus";
    }

    @GetMapping("/import-balance-summary")
    public void importBalanceSummaryRequest() {
        importService.beginBalanceSummaryImport();
    }
}
