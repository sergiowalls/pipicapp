package com.apps.awesome.smartpipican;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DogSeeder {

    static Dog getRex() {
        Dog dog = new Dog();
        dog.setName("Rex");
        dog.setAge(3);
        dog.setMale();
        dog.setRace("Mixed Breed");
        dog.setDominant();
        dog.setEnergetic();
        dog.setPlayful();
        dog.setChildFriendly();
        dog.setDrawable(R.drawable.rex_icon);
        return dog;
    }

    static Dog getAngus() {
        Dog dog = new Dog();
        dog.setName("Angus");
        dog.setAge(7);
        dog.setMale();
        dog.setRace("Rottweiler");
        dog.setPPP();
        dog.setDominant();
        dog.setObedient();
        dog.setConfident();
        dog.setResolute();
        dog.setDrawable(R.drawable.angus_icon);
        return dog;
    }

    static Dog getKira() {
        Dog dog = new Dog();
        dog.setName("Kira");
        dog.setAge(1);
        dog.setFemale();
        dog.setRace("Beagle");
        dog.setSubmissive();
        dog.setObedient();
        dog.setDrawable(R.drawable.kira_icon);
        return dog;
    }

    private static List<String> createList(String... args) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, args);
        return list;
    }
}
