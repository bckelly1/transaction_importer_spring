package com.brian.transaction_importer_spring.service;

import com.brian.transaction_importer_spring.constants.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CategoryInfererServiceTest {

    @Autowired
    private CategoryInfererService categoryInfererService;

    @Test
    void inferCategory() {
        String input = "Corner Store";
        String output = categoryInfererService.getCategory(input);
        String expectedOutput = Category.GAS_AND_FUEL;

        assertEquals(expectedOutput, output);
    }

    @Test
    void inferCategoryCustomRuleFile() {
        String input = "money";
        String output = categoryInfererService.getCategory(input);
        String expectedOutput = Category.PAYCHECK;

        assertEquals(expectedOutput, output);
    }
}
