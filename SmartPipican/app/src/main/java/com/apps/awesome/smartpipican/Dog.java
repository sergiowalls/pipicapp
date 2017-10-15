package com.apps.awesome.smartpipican;


import java.util.List;

class Dog {

    private String name;
    private int age;
    private Sex sex;
    private String race;
    private List<String> personality;
    private int drawable;

    private enum Sex {
        MALE, FEMALE
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
        return personality;
    }

    public void setPersonality(List<String> personality) {
        this.personality = personality;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
