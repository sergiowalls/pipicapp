package com.apps.awesome.smartpipican;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PipicanSeeder {

    static Pipican getA5() {
        Pipican pipican = new Pipican();
        pipican.setTitle("PipicanA5");
        pipican.setAddress("C/ Jordi Girona 1-3");
        pipican.addAgilityFacility();
        pipican.addWaterFacility();
        pipican.addBagsFacility();
        return pipican;
    }

    static Pipican getEusebiGuell() {
        Pipican pipican = new Pipican();
        pipican.setTitle("pipicanEusebiGuell");
        pipican.setAddress("C/ Carrer de Conca, 25X");
        pipican.addBigSizeFacility();
        pipican.addSpecialZonesFacility();
        pipican.addBagsFacility();
        return pipican;
    }

    static Pipican getRoyalPipi() {
        Pipican pipican = new Pipican();
        pipican.setTitle("royalPipi");
        pipican.setAddress("C/ Falsa, 123");
        pipican.addAgilityFacility();
        pipican.addWaterFacility();
        pipican.addBigSizeFacility();
        pipican.addSpecialZonesFacility();
        pipican.addBagsFacility();
        return pipican;
    }
}
