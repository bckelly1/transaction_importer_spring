package com.brian.transaction_importer_spring.service;

import com.brian.transaction_importer_spring.constants.Category;
import com.brian.transaction_importer_spring.entity.CustomRule;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
@Log4j2
public class CategoryInfererService {
    public String getCategory(final String text) {
        String test = text.toLowerCase();
        if (test.contains("airlines"))
            return Category.AIR_TRAVEL;
        if (test.contains("southwest"))
            return Category.AIR_TRAVEL;
        if (test.contains("american"))
            return Category.AIR_TRAVEL;

        if (test.contains("liquor"))
            return Category.ALCOHOL_AND_BARS;
        if (test.contains("brewing"))
            return Category.ALCOHOL_AND_BARS;
        if (test.contains("sports station"))
            return Category.ALCOHOL_AND_BARS;
        if (test.contains("firework"))
            return Category.AMUSEMENT;
        if (test.contains("zoo"))
            return Category.AMUSEMENT;
        if (test.contains("toll"))
            return Category.AUTO_AND_TRANSPORT;
        if (test.contains("driver"))
            return Category.AUTO_AND_TRANSPORT;
        if (test.contains("vehicle"))
            return Category.AUTO_AND_TRANSPORT;
        if (test.contains("ssfcu"))
            return Category.AUTO_PAYMENT;
        if (test.contains("icpayment"))
            return Category.AUTO_PAYMENT;
        if (test.contains("just between friends"))
            return Category.BABY_SUPPLIES;
        if (test.contains("momentpath"))
            return Category.BABYSITTER_AND_DAYCARE;
        if (test.contains("overdraft"))
            return Category.BANK_FEE;
        if (test.contains("barnes & noble"))
            return Category.BOOKS;
        if (test.contains("friends of the lvld li"))
            return Category.BOOKS_AND_SUPPLIES;
        if (test.contains("check"))
            return Category.CHECK;
        if (test.contains("footwear"))
            return Category.CLOTHING;
        if (test.contains("jcpenney"))
            return Category.CLOTHING;
        if (test.contains("529"))
            return Category.COLLEGE_FUND;
        if (test.contains("cardmember"))
            return Category.CREDIT_CARD_PAYMENT;
        if (test.contains("dentist"))
            return Category.DENTIST;
        if (test.contains("dmd"))
            return Category.DENTIST;
        if (test.contains("clinic"))
            return Category.DOCTOR;
        if (test.contains("medicine"))
            return Category.DOCTOR;
        if (test.contains("health"))
            return Category.DOCTOR;
        if (test.contains("labcorp"))
            return Category.DOCTOR;
        if (test.contains("pathology"))
            return Category.DOCTOR;
        if (test.contains("test"))
            return Category.EDUCATION;
        if (test.contains("exam"))
            return Category.EDUCATION;
        if (test.contains("univers"))
            return Category.EDUCATION;
        if (test.contains("steam"))
            return Category.ENTERTAINMENT;
        if (test.contains("games"))
            return Category.ENTERTAINMENT;
        if (test.contains("gog.com (test.contains("))
            return Category.ENTERTAINMENT;
        if (test.contains("gog  (test.contains("))
            return Category.ENTERTAINMENT;
        if (test.contains("steamgames"))
            return Category.ENTERTAINMENT;
        if (test.contains("playstation"))
            return Category.ENTERTAINMENT;
        if (test.contains("google"))
            return Category.ENTERTAINMENT;
        if (test.contains("newegg"))
            return Category.ELECTRONICS_AND_SOFTWARE;
        if (test.contains("apple"))
            return Category.ELECTRONICS_AND_SOFTWARE;
        if (test.contains("jetbrains"))
            return Category.ELECTRONICS_AND_SOFTWARE;
        if (test.contains("ui.com"))
            return Category.ELECTRONICS_AND_SOFTWARE;
        if (test.contains("robotic"))
            return Category.ELECTRONICS_AND_SOFTWARE;
        if (test.contains("home assistant cloud"))
            return Category.ELECTRONICS_AND_SOFTWARE;
        if (test.contains("optical"))
            return Category.EYECARE;
        if (test.contains("eye")) // Might be too generic;
            return Category.EYECARE;
        if (test.contains("associates in family e"))
            return Category.EYECARE;
        if (test.contains("five guys"))
            return Category.FAST_FOOD;
        if (test.contains("burgers"))
            return Category.FAST_FOOD;
        if (test.contains("pizza"))
            return Category.FAST_FOOD;
        if (test.contains("coffee"))
            return Category.FAST_FOOD;
        if (test.contains("donut"))
            return Category.FAST_FOOD;
        if (test.contains("starbucks"))
            return Category.FAST_FOOD;
        if (test.contains("bar"))
            return Category.FAST_FOOD;
        if (test.contains("cafe"))
            return Category.FAST_FOOD;
        if (test.contains("grill"))
            return Category.FAST_FOOD;
        if (test.contains("arby\"s"))
            return Category.FAST_FOOD;
        if (test.contains("arbys"))
            return Category.FAST_FOOD;
        if (test.contains("pizz"))
            return Category.FAST_FOOD;
        if (test.contains("wingshack"))
            return Category.FAST_FOOD;
        if (test.contains("freddys"))
            return Category.FAST_FOOD;
        if (test.contains("krazy karl"))
            return Category.FAST_FOOD;
        if (test.contains("good times"))
            return Category.FAST_FOOD;
        if (test.contains("dominos"))
            return Category.FAST_FOOD;
        if (test.contains("domino\"s"))
            return Category.FAST_FOOD;
        if (test.contains("cake"))
            return Category.FAST_FOOD;
        if (test.contains("dairy queen"))
            return Category.FAST_FOOD;
        if (test.contains("qdoba"))
            return Category.FAST_FOOD;
        if (test.contains("dairy delite"))
            return Category.FAST_FOOD;
        if (test.contains("baskin (test.contains("))
            return Category.FAST_FOOD;
        if (test.contains("ice cream"))
            return Category.FAST_FOOD;
        if (test.contains("freeze dried"))
            return Category.FAST_FOOD;
        if (test.contains("fee"))
            return Category.FEES_AND_CHARGES;
        if (test.contains("honey"))
            return Category.FOOD_AND_DINING;
        if (test.contains("uber eats"))
            return Category.FOOD_DELIVERY;
        if (test.contains("doordash"))
            return Category.FOOD_DELIVERY;
        if (test.contains("grubhub"))
            return Category.FOOD_DELIVERY;
        if (test.contains("ikea"))
            return Category.FURNISHINGS;
        if (test.contains("homegoods"))
            return Category.FURNISHINGS;
        if (test.contains("kum&go"))
            return Category.GAS_AND_FUEL;
        if (test.contains("corner store"))
            return Category.GAS_AND_FUEL;
        if (test.contains("conoco"))
            return Category.GAS_AND_FUEL;
        if (test.contains("sams"))
            return Category.GROCERIES;
        if (test.contains("hellofresh"))
            return Category.GROCERIES;
        if (test.contains("fruits"))
            return Category.GROCERIES;
        if (test.contains("king soopers"))
            return Category.GROCERIES;
        if (test.contains("safeway"))
            return Category.GROCERIES;
        if (test.contains("grocery"))
            return Category.GROCERIES;
        if (test.contains("market"))
            return Category.GROCERIES;
        if (test.contains("cherry"))
            return Category.GROCERIES;
        if (test.contains("sprouts"))
            return Category.GROCERIES;
        if (test.contains("head zep"))
            return Category.HAIR;
        if (test.contains("cookie cutters"))
            return Category.HAIR;
        if (test.contains("hair"))
            return Category.HAIR;
        if (test.contains("state farm"))
            return Category.HOME_INSURANCE;
        if (test.contains("ace h"))
            return Category.HOME_IMPROVEMENT;
        if (test.contains("home depot"))
            return Category.HOME_IMPROVEMENT;
        if (test.contains("sears"))
            return Category.HOME_IMPROVEMENT;
        if (test.contains("lowes"))
            return Category.HOME_IMPROVEMENT;
        if (test.contains("atgpay online"))
            return Category.HOME_OWNERS_ASSOC;
        if (test.contains("quality inn"))
            return Category.HOTEL;
        if (test.contains("dividend"))
            // TODO: Not sure if this should be interest or dividend for bank interest deposits;
            return Category.INTEREST_INCOME;
        if (test.contains("pulse"))
            return Category.INTERNET;
        if (test.contains("comcast"))
            return Category.INTERNET;
        if (test.contains("invest"))
            return Category.INVESTMENTS;
        if (test.contains("parks"))
            return Category.KIDS_ACTIVITIES;
        if (test.contains("lawn"))
            return Category.LAWN_AND_GARDEN;
        if (test.contains("seed"))
            return Category.LAWN_AND_GARDEN;
        if (test.contains("landscape"))
            return Category.LAWN_AND_GARDEN;
        if (test.contains("brecks"))
            return Category.LAWN_AND_GARDEN;
        if (test.contains("seeds"))
            return Category.LAWN_AND_GARDEN;
        if (test.contains("garlic"))
            return Category.LAWN_AND_GARDEN;
        if (test.contains("kvan bourgondien (test.contains("))
            return Category.LAWN_AND_GARDEN;
        if (test.contains("garden"))
            return Category.LAWN_AND_GARDEN;
        if (test.contains("nursery"))
            return Category.LAWN_AND_GARDEN;
        if (test.contains("thrivent"))
            return Category.LIFE_INSURANCE;
        if (test.contains("mint mobile (test.contains("))
            return Category.MOBILE_PHONE;
        if (test.contains("office depot"))
            return Category.OFFICE_SUPPLIES;
        if (test.contains("parking"))
            return Category.PARKING;
        if (test.contains("cat"))
            return Category.PETS;
        if (test.contains("dog"))
            return Category.PETS;
        if (test.contains("humane society"))
            return Category.PETS;
        if (test.contains("petco"))
            return Category.PET_FOOD_AND_SUPPLIES;
        if (test.contains("optum"))
            return Category.PHARMACY;
        if (test.contains("pharmacy"))
            return Category.PHARMACY;
        if (test.contains("print"))
            return Category.PRINTING;
        if (test.contains("avis.com"))
            return Category.RENTAL_CAR_AND_TAXI;
        if (test.contains("noodles"))
            return Category.RESTAURANTS;
        if (test.contains("olive garden"))
            return Category.RESTAURANTS;
        if (test.contains("food service"))
            return Category.RESTAURANTS;
        if (test.contains("texas roadhouse"))
            return Category.RESTAURANTS;
        if (test.contains("pho (test.contains("))
            return Category.RESTAURANTS;
        if (test.contains("wok"))
            return Category.RESTAURANTS;
        if (test.contains("sala thai"))
            return Category.RESTAURANTS;
        if (test.contains("santiagos mexican res"))
            return Category.RESTAURANTS;
        if (test.contains("cafe mexicali"))
            return Category.RESTAURANTS;
        if (test.contains("nordys"))
            return Category.RESTAURANTS;
        if (test.contains("bakery"))
            return Category.RESTAURANTS;
        if (test.contains("door 222"))
            return Category.RESTAURANTS;
        if (test.contains("casa real"))
            return Category.RESTAURANTS;
        if (test.contains("marys mountain"))
            return Category.RESTAURANTS;
        if (test.contains("restaurant"))
            return Category.RESTAURANTS;
        if (test.contains("mcdonald"))
            return Category.RESTAURANTS;
        if (test.contains("washme cw (test.contains("))
            return Category.SERVICE_AND_PARTS;
        if (test.contains("batteries+bulbs"))
            return Category.SERVICE_AND_PARTS;
        if (test.contains("usps"))
            return Category.SHIPPING;
        if (test.contains("us postal service"))
            return Category.SHIPPING;
        if (test.contains("jax"))
            return Category.SHOPPING;
        if (test.contains("rei"))
            return Category.SHOPPING;
        if (test.contains("walgreens"))
            return Category.SHOPPING;
        if (test.contains("amzn mktp"))
            return Category.SHOPPING;
        if (test.contains("walmart"))
            return Category.SHOPPING;
        if (test.contains("target"))
            return Category.SHOPPING;
        if (test.contains("wal-mart"))
            return Category.SHOPPING;
        if (test.contains("wm supercenter"))
            return Category.SHOPPING;
        if (test.contains("amazon"))
            return Category.SHOPPING;
        if (test.contains("etsy (test.contains("))
            return Category.SHOPPING;
        if (test.contains("scheels"))
            return Category.SHOPPING;
        if (test.contains("turbotax"))
            return Category.TAXES;
        if (test.contains("taxes"))
            return Category.TAXES;
        if (test.contains("bricks & minifigs"))
            return Category.TOYS;
        if (test.contains("transfer"))
            return Category.TRANSFER;
        if (test.contains("travel"))
            return Category.TRAVEL;
        if (test.contains("city"))
            return Category.UTILITIES;
        if (test.contains("xcel"))
            return Category.UTILITIES;

        return applyCustomRules(test);
    }

    public String applyCustomRules(final String text) {
        CustomRule[] customRules = retrieveCustomRules();
        if (customRules == null) {
            return Category.UNKNOWN;
        }

        for (CustomRule customRule : customRules) {
            if (text.contains(customRule.getRuleString())) {
                return customRule.getCategoryName();
            }
        }

        return Category.UNKNOWN;
    }

    private CustomRule[] retrieveCustomRules() {
        String rulesFileContents;
        String customRulesFile = "custom_rules_file.json";

        URL resource = Thread.currentThread().getContextClassLoader().getResource(customRulesFile);
        if (resource == null) {
            log.info("No custom rules file detected");
            return null;
        } else {
            try (InputStream resourceAsStream = resource.openStream()) {
                rulesFileContents = new String(resourceAsStream.readAllBytes());
                log.info("Loading custom rules for rules file");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (rulesFileContents.isEmpty()) {
            return null;
        }

        CustomRule[] myObjects = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            myObjects = objectMapper.readValue(rulesFileContents, CustomRule[].class);
        } catch (Exception e) {
            log.error("Failed to parse custom rules file {} with exception: {}", rulesFileContents, e);
        }

        return myObjects;
    }
}
