package ru.netology.allure.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private DataGenerator() {
    }

    public static String generateDate(int daysInFuture) {
        return LocalDate.now().plusDays(daysInFuture).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        String[] cities = new String[]{"Москва", "Казань", "Новосибирск", "Пенза"};
        return cities[new Random().nextInt(cities.length)];
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

    public static String generateInvalidPhone() {
        Faker faker = new Faker();
        return faker.numerify("####-##");
    }


    public static class Registration {
        private Registration() {
        }


        public static UserInfo generateUser(String locale) {
            return new UserInfo(generateName(locale), generatePhone(locale), generateCity());
        }
    }

    @Value
    public static class UserInfo {
        String name;
        String phone;
        String city;
    }
}