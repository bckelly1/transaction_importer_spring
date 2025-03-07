package com.brian.transaction_importer_spring.service;

import com.brian.transaction_importer_spring.entity.MailMessage;
import com.brian.transaction_importer_spring.enums.KnownInstitution;
import com.brian.transaction_importer_spring.institution.fidelity.FidelityAccountImporter;
import com.brian.transaction_importer_spring.institution.first_tech.FirstTechAccountImporter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BalanceImporterService {

    private final FirstTechAccountImporter firstTechAccountImporter;

    private final FidelityAccountImporter fidelityAccountImporter;


    public void parseBalanceSummary(MailMessage[] mailMessages) {
        for (MailMessage mailMessage : mailMessages) {
            KnownInstitution knownInstitution = parseInstitution(mailMessage);
            if (knownInstitution == KnownInstitution.FIDELITY) {
                fidelityAccountImporter.handleBalanceSummary(mailMessage.getHtml());
            } else if (knownInstitution == KnownInstitution.FIRST_TECH) {
                firstTechAccountImporter.handleBalanceSummary(mailMessage.getHtml());
            } else {
                throw new IllegalStateException("No summary handler for institution " + knownInstitution);
            }
        }
    }

    private KnownInstitution parseInstitution(MailMessage mailMessage) {
        String fromField = mailMessage.getHeaders().get("From").toLowerCase();
        if (fromField.contains("fidelity")) {
            return KnownInstitution.FIDELITY;
        } else if (fromField.contains("first tech")) {
            return KnownInstitution.FIRST_TECH;
        } else if (fromField.contains("us bank")) {
            return KnownInstitution.US_BANK;
        } else if (fromField.contains("home depot")) {
            return KnownInstitution.HOME_DEPOT;
        }
        throw new IllegalStateException("Unknown institution: " + fromField);
    }
}
