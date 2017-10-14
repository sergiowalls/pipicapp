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
        dog.setPersonality(createList("dominant", "energetic", "playful", "child-friendly"));
        dog.setImage();
        return dog;
    }

    static Dog getAngus() {
        Dog dog = new Dog();
        dog.setName("Angus");
        dog.setAge(7);
        dog.setMale();
        dog.setRace("Rottweiler");
        dog.setPersonality(createList("PPP", "dominant", "obedient", "confident", "resolute"));
        dog.setImage();
        return dog;
    }

    static Dog getKira() {
        Dog dog = new Dog();
        dog.setName("Kira");
        dog.setAge(1);
        dog.setFemale();
        dog.setRace("Beagle");
        dog.setPersonality(createList("submissive", "fearful", "untrusting"));
        dog.setImage();
        return dog;
    }

    private static List<String> createList(String... args) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, args);
        return list;
    }
}
