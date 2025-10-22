package com.josephcsoftware.tsgstage2;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.josephcsoftware.tsgstage2.models.Address;

public final class Utils {

    // This class will be for static methods only
    private Utils() { }

    // Descriptions for what the hospital bills are for
    private static final String[] SILLY_REASONS = {
        "The vibe was off",
        "Groove needed restoration",
        "Steeze needed repair",
        "Four-dimensional spine realignment",
        "Upside-down kidneys",
        "Knees halfway down arms",
        "Extra eyebrows",
        "Double mustache",
        "Pelvic floor wax",
        "Face got stuck that way",
        "Stolen nose (hey give that back)",
        "Inside-out hairs",
        "Insufficient backface culling",
        "Failed backflip",
        "Sad",
        "A wonderfully and spectacularly terrible idea",
        "Underground skydiving recovery",
        "Vacuous elbows",
        "Staring contest with Medusa",
        "Abs were too chiseled",
        "Tis but a flesh wound",
        "Insufficient cowbell",
        "Hemineglect",
        "Excess of butterflies in stomach",
        "Heart in throat",
        "Owl mimicry",
        "Haunted",
        "Found out",
        "Wrought a brand-new illness upon humankind",
        "Looked at me weird",
        "Excessively-silly walk",
        "Swapped hips and shoulders",
        "Haven't the faintest clue",
        "Taxes",
        "Bird-related injuries",
        "Fake glasses became real glasses",
        "Yearning to bite",
        "Casual comatose",
        "Disco",
        "Overly-reticulated splines",
        "The Accursed Tickle",
        "Medical drama scene",
        "Going viral on TikTok",
        "Too many eyes",
        "Interrupted by a shark",
        "Challenged a bus and won",
        "Pre-Cambrian disease",
        "Foo-Bar Syndrome"
    };

    public static String[] randomReasons() {
        ArrayList<String> pool = new ArrayList<String>();
        ArrayList<String> shuffled = new ArrayList<String>();

        for (int i = 0; i < SILLY_REASONS.length; i++) {
            pool.add(SILLY_REASONS[i]);
        }

        // Randomly pick from the pool
        while (pool.size() > 0) {
            shuffled.add(pool.remove(ThreadLocalRandom.current().nextInt(pool.size())));
        }

        return shuffled.toArray(new String[shuffled.size()]);
    }

    public static LocalDate randomDateBetween(LocalDate startDate, LocalDate endDate) throws NullPointerException {
        if (startDate == null) throw new NullPointerException("Start date cannot be null.");
        if (endDate == null) throw new NullPointerException("End date cannot be null.");
        
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after start date.");
        }
        
        long range = ChronoUnit.DAYS.between(startDate, endDate);
        long randomDays = ThreadLocalRandom.current().nextLong(range + 1);
        
        return startDate.plusDays(randomDays);
    }

    public static LocalDate randomInYear(int year) {
        int randomDays = ThreadLocalRandom.current().nextInt(365);
        return LocalDate.ofYearDay(year, randomDays);
    }

    private static int randomIndex(String[] choices) {
        return ThreadLocalRandom.current().nextInt(choices.length);
    }

    private static int randomIndex(ArrayList<String> choices) {
        return ThreadLocalRandom.current().nextInt(choices.size());
    }

    private static String randomString(String[] choices) {
        int index = randomIndex(choices);
        return choices[index];
    }

    private static final String[] STREET_SIDES = { "N", "S", "E", "W" };
    private static final String[] APARTMENT_SIDES = { "A", "B", "C", "D" };

    // For getting address sides, like NESW for streets, and ABCD for apartments
    private static String randomSide(String[] sides, String prefix, String suffix) {
        // Sometimes there isn't one
        if (ThreadLocalRandom.current().nextBoolean()) return "";
        return prefix + randomString(sides) + suffix;
    }

    private static final String[][] CITIES = {
        {"New York City", "NY", "10024"},
        {"Los Angeles", "CA", "90041"},
        {"Chicago", "IL", "60610"},
        {"Houston", "TX", "77092"},
        {"Phoenix", "AZ", "85034"},
        {"Philadelphia", "PA", "19147"},
        {"Jacksonville", "FL", "32218"},
        {"Charlotte", "NC", "28262"},
        {"Columbus", "OH", "43215"},
        {"Indianapolis", "IN", "46204"}
    };

    private static void setGeoLocation(Address address) {
        String[] geoLocation = CITIES[ThreadLocalRandom.current().nextInt(CITIES.length)];

        address.setCity(geoLocation[0]);
        address.setState(geoLocation[1]);
        address.setPostalCode(geoLocation[2]);
    }

    // Named comedically, to explain why a client
    // would rack up so many hospital visits in the
    // insurance nightmare that is the USA.
    private static final String[] CLIENT_STREETS = {
        "Wonder St",
        "Danger Rd",
        "Reckless Ave",
        "Daring Dr",
        "Superhero St",
        "Yolo Blvd",
        "Wild Times Rd",
        "Holdmybeer Dr",
        "Skydive Blvd",
        "Cannon Ave"
    };

    private static final String[] PROVIDER_STREETS = {
        "Fixer St",
        "Bandage Blvd",
        "Healer Hwy",
        "Safety Dr",
        "Recovery Rd",
        "Walkitoff Way",
        "Rescue Rd",
        "Cooldown St",
        "Hospital Ln",
        "Doctor Dr"
    };

    private static String randomStreetNumber() {
        String ret = "" + (ThreadLocalRandom.current().nextInt(99900) + 100);
        return ret + randomSide(STREET_SIDES, " ", "");
    }

    private static String randomStreet(String streets[]) {
        return randomStreetNumber() + randomString(streets);
    }

    public static Address randomClientAddress() {
        Address clientAddress = new Address();

        setGeoLocation(clientAddress);

        clientAddress.setLine1(randomStreet(CLIENT_STREETS));

        String aptNumber = "APT " + (ThreadLocalRandom.current().nextInt(500) + 1);
        aptNumber += randomSide(APARTMENT_SIDES, "", "");

        clientAddress.setLine2(aptNumber);

        return clientAddress;
    }

    public static Address randomProviderAddress() {
        Address providerAddress = new Address();

        setGeoLocation(providerAddress);

        providerAddress.setLine1(randomStreet(PROVIDER_STREETS));

        return providerAddress;
    }
}
