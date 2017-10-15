package com.apps.awesome.smartpipican;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DogCompatibility {

    private Map<Dog.Personality, Set<Dog.Personality>> compatibilities;

    public DogCompatibility() {
        compatibilities = new HashMap<>();
        createCompatibilities();
    }

    private void createCompatibilities() {
        compatibilities.put(
                Dog.Personality.DOMINANT,
                createSet(
                        Dog.Personality.DOMINANT,
                        Dog.Personality.ENERGETIC,
                        Dog.Personality.CHILDFRIENDLY,
                        Dog.Personality.PPP,
                        Dog.Personality.UNTRUSTING,
                        Dog.Personality.RESOLUTE));

        compatibilities.put(
                Dog.Personality.ENERGETIC,
                createSet(
                        Dog.Personality.DOMINANT,
                        Dog.Personality.PPP,
                        Dog.Personality.FEARFUL,
                        Dog.Personality.UNTRUSTING));

        compatibilities.put(
                Dog.Personality.PLAYFUL,
                createSet(
                        Dog.Personality.PPP,
                        Dog.Personality.FEARFUL,
                        Dog.Personality.UNTRUSTING));

        compatibilities.put(
                Dog.Personality.CHILDFRIENDLY,
                createSet(
                        Dog.Personality.DOMINANT,
                        Dog.Personality.FEARFUL,
                        Dog.Personality.UNTRUSTING));

        compatibilities.put(
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

        compatibilities.put(
                Dog.Personality.SUBMISSIVE,
                createSet(
                        Dog.Personality.RESOLUTE));

        compatibilities.put(
                Dog.Personality.FEARFUL,
                createSet(
                        Dog.Personality.CONFIDENT,
                        Dog.Personality.RESOLUTE,
                        Dog.Personality.ENERGETIC,
                        Dog.Personality.PLAYFUL,
                        Dog.Personality.CHILDFRIENDLY,
                        Dog.Personality.PPP));

        compatibilities.put(
                Dog.Personality.UNTRUSTING,
                createSet(
                        Dog.Personality.DOMINANT,
                        Dog.Personality.CONFIDENT,
                        Dog.Personality.RESOLUTE,
                        Dog.Personality.ENERGETIC,
                        Dog.Personality.PLAYFUL,
                        Dog.Personality.CHILDFRIENDLY,
                        Dog.Personality.PPP));

        compatibilities.put(
                Dog.Personality.OBEDIENT,
                createSet());

        compatibilities.put(
                Dog.Personality.CONFIDENT,
                createSet(
                        Dog.Personality.PPP,
                        Dog.Personality.FEARFUL,
                        Dog.Personality.UNTRUSTING));

        compatibilities.put(
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
}
