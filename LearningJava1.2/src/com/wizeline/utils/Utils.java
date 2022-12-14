package com.wizeline.utils;

import com.wizeline.enums.AccountType;
import com.wizeline.enums.Country;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$]).{6,8}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
    // Definición del patrón para validar formato de fecha (dd-mm-yyyy)
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");



    public static boolean isPasswordValid(String password) {
    // Valida la contraseña contra el patrón definido
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static String getString(String value) {
        if (value != null) {
            return value;
        }
        return "";
    }

    public static double randomBalance() {
        return new Random()
                .doubles(1000, 9000)
                .limit(1)
                .findFirst()
                .getAsDouble();
    }

    public static AccountType pickRandomAccountType() {
        AccountType[] accountTypes = AccountType.values();
        return accountTypes[new Random().nextInt(accountTypes.length)];
    }

    public static String randomInt() {
        return String.valueOf(new Random().ints(1, 10)
                .findFirst()
                .getAsInt());
    }

    public static String getCountry(Country country) {
        Map<Country, String> countries = new HashMap<>();
        countries.put(Country.US, "United States");
        countries.put(Country.MX, "Mexico");
        countries.put(Country.FR, "France");
        return countries.get(country);
    }

    public static boolean validateNullValue(String value) {
        return value != null;
    }

    public static boolean isDateFormatValid(String date) {
        // Valida la fecha contra el patron que definimos
        return DATE_PATTERN.matcher(date).matches();
    }


}


