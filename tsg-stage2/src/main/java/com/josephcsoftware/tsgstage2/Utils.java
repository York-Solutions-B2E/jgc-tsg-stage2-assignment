package com.josephcsoftware.tsgstage2;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import com.josephcsoftware.tsgstage2.models.Address;

public final class Utils {

    // This class will be for static methods only
    private Utils() { }

    public static LocalDate randomDateBetween(LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(startDate, "Start date cannot be null.");
        Objects.requireNonNull(endDate, "End date cannot be null.");
        
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after start date.");
        }
        
        long range = ChronoUnit.DAYS.between(startDate, endDate);
        long randomDays = ThreadLocalRandom.current().nextLong(range + 1);
        
        return startDate.plusDays(randomDays);
    }

    public static LocalDate randomInYear(int year) {
        int randomDays = ThreadLocalRandom.current().nextLong(365);
        return LocalDate.ofYearDay(year, randomDays);
    }

    private static String randomString(String[] choices) {
        int index = ThreadLocalRandom.current().nextInt(choices.length());
        return choices[index]
    }

    private const String[] STREET_SIDES = { "N", "S", "E", "W" };
    private const String[] APARTMENT_SIDES = { "A", "B", "C", "D" };

    // For getting address sides, like NESW for streets, and ABCD for apartments
    private static String randomSide(String[] sides, String prefix, String suffix) {
        // Sometimes there isn't one
        if (ThreadLocalRandom.current().nextBoolean()) return "";
        return prefix + randomString(sides) + suffix;
    }

    private const String[][] CITIES = {
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
        String[] geoLocation = CITIES[ThreadLocalRandom.current().nextInt(CITIES.length())];

        clientAddress.setCity(geoLocation[0]);
        clientAddress.setState(geoLocation[1]);
        clientAddress.setPostalCode(geoLocation[2]);
    }

    // Named comedically, to explain why a client
    // would rack up so many hospital visits in the
    // insurance nightmare that is the USA.
    private const String[] CLIENT_STREETS = {
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

    private const String[] PROVIDER_STREETS = {
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
        String ret = (ThreadLocalRandom.current().nextInt(99900) + 100).toString();
        return ret + randomSide(STREET_SIDES, " ", "");
    }

    private static String randomStreet(String streets[]) {
        return randomStreetNumber() + randomString(streets);
    }

    public static Address randomClientAddress() {
        Address clientAddress = new Address();

        setGeoLocation(clientAddress);

        clientAddress.setLine1(randomStreet(CLIENT_STREETS));

        String aptNumber = "APT " + (ThreadLocalRandom.current().nextInt(500) + 1).toString();
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
