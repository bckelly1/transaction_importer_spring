package com.brian.transaction_importer_spring.service;
import com.brian.transaction_importer_spring.constants.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryInfererService {
    public String getCategory(String text) {
        text = text.toLowerCase();
        if (text.contains("airlines"))
            return Category.AIR_TRAVEL;
        if (text.contains("southwest"))
            return Category.AIR_TRAVEL;
        if (text.contains("american"))
            return Category.AIR_TRAVEL;

        if (text.contains("liquor"))
            return Category.ALCOHOL_AND_BARS;
        if (text.contains("brewing"))
            return Category.ALCOHOL_AND_BARS;
        if (text.contains("sports station"))
            return Category.ALCOHOL_AND_BARS;
        if (text.contains("firework"))
            return Category.AMUSEMENT;
        if (text.contains("zoo"))
            return Category.AMUSEMENT;
        if (text.contains("toll"))
            return Category.AUTO_AND_TRANSPORT;
        if (text.contains("driver"))
            return Category.AUTO_AND_TRANSPORT;
        if (text.contains("vehicle"))
            return Category.AUTO_AND_TRANSPORT;
        if (text.contains("ssfcu"))
            return Category.AUTO_PAYMENT;
        if (text.contains("icpayment"))
            return Category.AUTO_PAYMENT;
        if (text.contains("just between friends"))
            return Category.BABY_SUPPLIES;
        if (text.contains("momentpath"))
            return Category.BABYSITTER_AND_DAYCARE;
        if (text.contains("overdraft"))
            return Category.BANK_FEE;
        if (text.contains("barnes & noble"))
            return Category.BOOKS;
        if (text.contains("friends of the lvld li"))
            return Category.BOOKS_AND_SUPPLIES;
        if (text.contains("check"))
            return Category.CHECK;
        if (text.contains("footwear"))
            return Category.CLOTHING;
        if (text.contains("jcpenney"))
            return Category.CLOTHING;
        if (text.contains("529"))
            return Category.COLLEGE_FUND;
        if (text.contains("cardmember"))
            return Category.CREDIT_CARD_PAYMENT;
        if (text.contains("dentist"))
            return Category.DENTIST;
        if (text.contains("dmd"))
            return Category.DENTIST;
        if (text.contains("clinic"))
            return Category.DOCTOR;
        if (text.contains("medicine"))
            return Category.DOCTOR;
        if (text.contains("health"))
            return Category.DOCTOR;
        if (text.contains("labcorp"))
            return Category.DOCTOR;
        if (text.contains("pathology"))
            return Category.DOCTOR;
        if (text.contains("test"))
            return Category.EDUCATION;
        if (text.contains("exam"))
            return Category.EDUCATION;
        if (text.contains("univers"))
            return Category.EDUCATION;
        if (text.contains("steam"))
            return Category.ENTERTAINMENT;
        if (text.contains("games"))
            return Category.ENTERTAINMENT;
        if (text.contains("gog.com (text.contains("))
            return Category.ENTERTAINMENT;
        if (text.contains("gog  (text.contains("))
            return Category.ENTERTAINMENT;
        if (text.contains("steamgames"))
            return Category.ENTERTAINMENT;
        if (text.contains("playstation"))
            return Category.ENTERTAINMENT;
        if (text.contains("google"))
            return Category.ENTERTAINMENT;
        if (text.contains("newegg"))
            return Category.ELECTRONICS_AND_SOFTWARE;
        if (text.contains("apple"))
            return Category.ELECTRONICS_AND_SOFTWARE;
        if (text.contains("jetbrains"))
            return Category.ELECTRONICS_AND_SOFTWARE;
        if (text.contains("ui.com"))
            return Category.ELECTRONICS_AND_SOFTWARE;
        if (text.contains("robotic"))
            return Category.ELECTRONICS_AND_SOFTWARE;
        if (text.contains("home assistant cloud"))
            return Category.ELECTRONICS_AND_SOFTWARE;
        if (text.contains("optical"))
            return Category.EYECARE;
        if (text.contains("eye")) // Might be too generic;
            return Category.EYECARE;
        if (text.contains("associates in family e"))
            return Category.EYECARE;
        if (text.contains("five guys"))
            return Category.FAST_FOOD;
        if (text.contains("burgers"))
            return Category.FAST_FOOD;
        if (text.contains("pizza"))
            return Category.FAST_FOOD;
        if (text.contains("coffee"))
            return Category.FAST_FOOD;
        if (text.contains("donut"))
            return Category.FAST_FOOD;
        if (text.contains("starbucks"))
            return Category.FAST_FOOD;
        if (text.contains("bar"))
            return Category.FAST_FOOD;
        if (text.contains("cafe"))
            return Category.FAST_FOOD;
        if (text.contains("grill"))
            return Category.FAST_FOOD;
        if (text.contains("arby\"s"))
            return Category.FAST_FOOD;
        if (text.contains("arbys"))
            return Category.FAST_FOOD;
        if (text.contains("pizz"))
            return Category.FAST_FOOD;
        if (text.contains("wingshack"))
            return Category.FAST_FOOD;
        if (text.contains("freddys"))
            return Category.FAST_FOOD;
        if (text.contains("krazy karl"))
            return Category.FAST_FOOD;
        if (text.contains("good times"))
            return Category.FAST_FOOD;
        if (text.contains("dominos"))
            return Category.FAST_FOOD;
        if (text.contains("domino\"s"))
            return Category.FAST_FOOD;
        if (text.contains("cake"))
            return Category.FAST_FOOD;
        if (text.contains("dairy queen"))
            return Category.FAST_FOOD;
        if (text.contains("qdoba"))
            return Category.FAST_FOOD;
        if (text.contains("dairy delite"))
            return Category.FAST_FOOD;
        if (text.contains("baskin (text.contains("))
            return Category.FAST_FOOD;
        if (text.contains("ice cream"))
            return Category.FAST_FOOD;
        if (text.contains("freeze dried"))
            return Category.FAST_FOOD;
        if (text.contains("fee"))
            return Category.FEES_AND_CHARGES;
        if (text.contains("honey"))
            return Category.FOOD_AND_DINING;
        if (text.contains("uber eats"))
            return Category.FOOD_DELIVERY;
        if (text.contains("doordash"))
            return Category.FOOD_DELIVERY;
        if (text.contains("grubhub"))
            return Category.FOOD_DELIVERY;
        if (text.contains("ikea"))
            return Category.FURNISHINGS;
        if (text.contains("homegoods"))
            return Category.FURNISHINGS;
        if (text.contains("kum&go"))
            return Category.GAS_AND_FUEL;
        if (text.contains("corner store"))
            return Category.GAS_AND_FUEL;
        if (text.contains("conoco"))
            return Category.GAS_AND_FUEL;
        if (text.contains("sams"))
            return Category.GROCERIES;
        if (text.contains("hellofresh"))
            return Category.GROCERIES;
        if (text.contains("fruits"))
            return Category.GROCERIES;
        if (text.contains("king soopers"))
            return Category.GROCERIES;
        if (text.contains("safeway"))
            return Category.GROCERIES;
        if (text.contains("grocery"))
            return Category.GROCERIES;
        if (text.contains("market"))
            return Category.GROCERIES;
        if (text.contains("cherry"))
            return Category.GROCERIES;
        if (text.contains("sprouts"))
            return Category.GROCERIES;
        if (text.contains("head zep"))
            return Category.HAIR;
        if (text.contains("cookie cutters"))
            return Category.HAIR;
        if (text.contains("hair"))
            return Category.HAIR;
        if (text.contains("state farm"))
            return Category.HOME_INSURANCE;
        if (text.contains("ace h"))
            return Category.HOME_IMPROVEMENT;
        if (text.contains("home depot"))
            return Category.HOME_IMPROVEMENT;
        if (text.contains("sears"))
            return Category.HOME_IMPROVEMENT;
        if (text.contains("lowes"))
            return Category.HOME_IMPROVEMENT;
        if (text.contains("atgpay online"))
            return Category.HOME_OWNERS_ASSOC;
        if (text.contains("quality inn"))
            return Category.HOTEL;
        if (text.contains("dividend"))
        // TODO: Not sure if this should be interest or dividend for bank interest deposits;
            return Category.INTEREST_INCOME;
        if (text.contains("pulse"))
            return Category.INTERNET;
        if (text.contains("comcast"))
            return Category.INTERNET;
        if (text.contains("invest"))
            return Category.INVESTMENTS;
        if (text.contains("parks"))
            return Category.KIDS_ACTIVITIES;
        if (text.contains("lawn"))
            return Category.LAWN_AND_GARDEN;
        if (text.contains("seed"))
            return Category.LAWN_AND_GARDEN;
        if (text.contains("landscape"))
            return Category.LAWN_AND_GARDEN;
        if (text.contains("brecks"))
            return Category.LAWN_AND_GARDEN;
        if (text.contains("seeds"))
            return Category.LAWN_AND_GARDEN;
        if (text.contains("garlic"))
            return Category.LAWN_AND_GARDEN;
        if (text.contains("kvan bourgondien (text.contains("))
            return Category.LAWN_AND_GARDEN;
        if (text.contains("garden"))
            return Category.LAWN_AND_GARDEN;
        if (text.contains("nursery"))
            return Category.LAWN_AND_GARDEN;
        if (text.contains("thrivent"))
            return Category.LIFE_INSURANCE;
        if (text.contains("mint mobile (text.contains("))
            return Category.MOBILE_PHONE;
        if (text.contains("office depot"))
            return Category.OFFICE_SUPPLIES;
        if (text.contains("parking"))
            return Category.PARKING;
        if (text.contains("cat"))
            return Category.PETS;
        if (text.contains("dog"))
            return Category.PETS;
        if (text.contains("humane society"))
            return Category.PETS;
        if (text.contains("petco"))
            return Category.PET_FOOD_AND_SUPPLIES;
        if (text.contains("optum"))
            return Category.PHARMACY;
        if (text.contains("pharmacy"))
            return Category.PHARMACY;
        if (text.contains("print"))
            return Category.PRINTING;
        if (text.contains("avis.com"))
            return Category.RENTAL_CAR_AND_TAXI;
        if (text.contains("noodles"))
            return Category.RESTAURANTS;
        if (text.contains("olive garden"))
            return Category.RESTAURANTS;
        if (text.contains("food service"))
            return Category.RESTAURANTS;
        if (text.contains("texas roadhouse"))
            return Category.RESTAURANTS;
        if (text.contains("pho (text.contains("))
            return Category.RESTAURANTS;
        if (text.contains("wok"))
            return Category.RESTAURANTS;
        if (text.contains("sala thai"))
            return Category.RESTAURANTS;
        if (text.contains("santiagos mexican res"))
            return Category.RESTAURANTS;
        if (text.contains("cafe mexicali"))
            return Category.RESTAURANTS;
        if (text.contains("nordys"))
            return Category.RESTAURANTS;
        if (text.contains("bakery"))
            return Category.RESTAURANTS;
        if (text.contains("door 222"))
            return Category.RESTAURANTS;
        if (text.contains("casa real"))
            return Category.RESTAURANTS;
        if (text.contains("marys mountain"))
            return Category.RESTAURANTS;
        if (text.contains("restaurant"))
            return Category.RESTAURANTS;
        if (text.contains("mcdonald"))
            return Category.RESTAURANTS;
        if (text.contains("washme cw (text.contains("))
            return Category.SERVICE_AND_PARTS;
        if (text.contains("batteries+bulbs"))
            return Category.SERVICE_AND_PARTS;
        if (text.contains("usps"))
            return Category.SHIPPING;
        if (text.contains("us postal service"))
            return Category.SHIPPING;
        if (text.contains("jax"))
            return Category.SHOPPING;
        if (text.contains("rei"))
            return Category.SHOPPING;
        if (text.contains("walgreens"))
            return Category.SHOPPING;
        if (text.contains("amzn mktp"))
            return Category.SHOPPING;
        if (text.contains("walmart"))
            return Category.SHOPPING;
        if (text.contains("target"))
            return Category.SHOPPING;
        if (text.contains("wal-mart"))
            return Category.SHOPPING;
        if (text.contains("wm supercenter"))
            return Category.SHOPPING;
        if (text.contains("amazon"))
            return Category.SHOPPING;
        if (text.contains("etsy (text.contains("))
            return Category.SHOPPING;
        if (text.contains("scheels"))
            return Category.SHOPPING;
        if (text.contains("turbotax"))
            return Category.TAXES;
        if (text.contains("taxes"))
            return Category.TAXES;
        if (text.contains("bricks & minifigs"))
            return Category.TOYS;
        if (text.contains("transfer"))
            return Category.TRANSFER;
        if (text.contains("travel"))
            return Category.TRAVEL;
        if (text.contains("city"))
            return Category.UTILITIES;
        if (text.contains("xcel"))
            return Category.UTILITIES;

        return Category.UNKNOWN;
    }
}
