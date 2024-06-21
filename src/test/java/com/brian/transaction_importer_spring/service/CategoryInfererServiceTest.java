package com.brian.transaction_importer_spring.service;

import com.brian.transaction_importer_spring.constants.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryInfererServiceTest {

    private final CategoryInfererService categoryInfererService = new CategoryInfererService();

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
