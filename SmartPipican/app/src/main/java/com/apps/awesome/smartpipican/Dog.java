package com.apps.awesome.smartpipican;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Dog {

    private String name;
    private int age;
    private Sex sex;
    private String race;
    private Set<Personality> personality;
    private int drawable;

    private enum Sex {
        MALE, FEMALE
    }

    private enum Personality {
        SUBMISSIVE, FEARFUL, UNTRUSTING, PPP, DOMINANT, OBEDIENT, CONFIDENT, RESOLUTE, ENERGETIC,
        PLAYFUL, CHILDFRIENDLY
    }

    public Dog() {
        this.personality = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMale() {
        return sex.equals(Sex.MALE);
    }

    public boolean isFemale() {
        return sex.equals(Sex.FEMALE);
    }

    public void setMale() {
        this.sex = Sex.MALE;
    }

    public void setFemale() {
        this.sex = Sex.FEMALE;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public List<String> getPersonality() {
        List<String> s = new ArrayList<>();
        for (Personality p : personality) {
            s.add(p.name().toLowerCase());
        }
        return s;
    }

    public void setSubmissive() {
        personality.add(Personality.SUBMISSIVE);
    }

    public void removeSubmissive() {
        personality.remove(Personality.SUBMISSIVE);
    }

    public void setFearful() {
        personality.add(Personality.FEARFUL);
    }

    public void removeFearful() {
        personality.remove(Personality.FEARFUL);
    }

    public void setUntrusting() {
        personality.add(Personality.UNTRUSTING);
    }

    public void removeUntrusting() {
        personality.remove(Personality.UNTRUSTING);
    }


    public void setPPP() {
        personality.add(Personality.PPP);
    }

    public void removePPP() {
        personality.remove(Personality.PPP);
    }

    public void setDominant() {
        personality.add(Personality.DOMINANT);
    }

    public void removeDominant() {
        personality.remove(Personality.DOMINANT);
    }

    public void setObedient() {
        personality.add(Personality.OBEDIENT);
    }

    public void removeObedient() {
        personality.remove(Personality.OBEDIENT);
    }

    public void setConfident() {
        personality.add(Personality.CONFIDENT);
    }

    public void removeConfident() {
        personality.remove(Personality.CONFIDENT);
    }

    public void setResolute() {
        personality.add(Personality.RESOLUTE);
    }

    public void removeResolute() {
        personality.remove(Personality.RESOLUTE);
    }

    public void setEnergetic() {
        personality.add(Personality.ENERGETIC);
    }

    public void removeEnergetic() {
        personality.remove(Personality.ENERGETIC);
    }

    public void setPlayful() {
        personality.add(Personality.PLAYFUL);
    }

    public void removePlayful() {
        personality.remove(Personality.PLAYFUL);
    }

    public void setChildFriendly() {
        personality.add(Personality.CHILDFRIENDLY);
    }

    public void removeChildFriendly() {
        personality.remove(Personality.CHILDFRIENDLY);
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
