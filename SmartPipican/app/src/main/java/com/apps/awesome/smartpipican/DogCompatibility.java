package com.apps.awesome.smartpipican;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DogCompatibility {

    private Map<Dog.Personality, Set<Dog.Personality>> incompatibilities;

    public DogCompatibility() {
        incompatibilities = new HashMap<>();
        createCompatibilities();
    }

    private void createCompatibilities() {
        incompatibilities.put(
                Dog.Personality.DOMINANT,
                createSet(
                        Dog.Personality.DOMINANT,
                        Dog.Personality.ENERGETIC,
                        Dog.Personality.CHILDFRIENDLY,
                        Dog.Personality.PPP,
                        Dog.Personality.UNTRUSTING,
                        Dog.Personality.RESOLUTE));

        incompatibilities.put(
                Dog.Personality.ENERGETIC,
                createSet(
                        Dog.Personality.DOMINANT,
                        Dog.Personality.PPP,
                        Dog.Personality.FEARFUL,
                        Dog.Personality.UNTRUSTING));

        incompatibilities.put(
                Dog.Personality.PLAYFUL,
                createSet(
                        Dog.Personality.PPP,
                        Dog.Personality.FEARFUL,
                        Dog.Personality.UNTRUSTING));

        incompatibilities.put(
                Dog.Personality.CHILDFRIENDLY,
                createSet(
                        Dog.Personality.DOMINANT,
                        Dog.Personality.FEARFUL,
                        Dog.Personality.UNTRUSTING));

        incompatibilities.put(
                Dog.Personality.PPP,
                createSet(
                        Dog.Personality.DOMINANT,
                        Dog.Personality.ENERGETIC,
                        Dog.Personality.PLAYFUL,
                        Dog.Personality.PPP,
                        Dog.Personality.FEARFUL,
                        Dog.Personality.UNTRUSTING,
                        Dog.Personality.CONFIDENT,
                        Dog.Personality.RESOLUTE));

        incompatibilities.put(
                Dog.Personality.SUBMISSIVE,
                createSet(
                        Dog.Personality.RESOLUTE));

        incompatibilities.put(
                Dog.Personality.FEARFUL,
                createSet(
                        Dog.Personality.CONFIDENT,
                        Dog.Personality.RESOLUTE,
                        Dog.Personality.ENERGETIC,
                        Dog.Personality.PLAYFUL,
                        Dog.Personality.CHILDFRIENDLY,
                        Dog.Personality.PPP));

        incompatibilities.put(
                Dog.Personality.UNTRUSTING,
                createSet(
                        Dog.Personality.DOMINANT,
                        Dog.Personality.CONFIDENT,
                        Dog.Personality.RESOLUTE,
                        Dog.Personality.ENERGETIC,
                        Dog.Personality.PLAYFUL,
                        Dog.Personality.CHILDFRIENDLY,
                        Dog.Personality.PPP));

        incompatibilities.put(
                Dog.Personality.OBEDIENT,
                createSet());

        incompatibilities.put(
                Dog.Personality.CONFIDENT,
                createSet(
                        Dog.Personality.PPP,
                        Dog.Personality.FEARFUL,
                        Dog.Personality.UNTRUSTING));

        incompatibilities.put(
                Dog.Personality.RESOLUTE,
                createSet(
                        Dog.Personality.DOMINANT,
                        Dog.Personality.PPP,
                        Dog.Personality.SUBMISSIVE,
                        Dog.Personality.FEARFUL,
                        Dog.Personality.UNTRUSTING,
                        Dog.Personality.RESOLUTE));
    }

    private Set<Dog.Personality> createSet(Dog.Personality... args) {
        Set<Dog.Personality> personalities = new HashSet<>();
        Collections.addAll(personalities, args);
        return personalities;
    }

    public boolean isCompatible(Dog dog1, Dog dog2) {
        for (Dog.Personality p : dog1.getPersonality())
            if (dog2.getPersonality().contains(incompatibilities.get(p))) return false;
        return true;
    }
}
