package com.brian.transaction_importer_spring.controller;

import com.brian.transaction_importer_spring.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImportRequestController {
    @Autowired
    private ImportService importService;

    @GetMapping("/import-transactions")
    public void importTransactionsRequest() {
        importService.beginTransactionImport();
    }

    @GetMapping("/import-balance-summary")
    public void importBalanceSummaryRequest() {
        importService.beginBalanceSummaryImport();
    }
}
