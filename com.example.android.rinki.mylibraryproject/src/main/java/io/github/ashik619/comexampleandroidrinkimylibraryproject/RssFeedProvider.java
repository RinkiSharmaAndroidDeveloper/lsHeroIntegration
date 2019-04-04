package io.github.ashik619.comexampleandroidrinkimylibraryproject;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dilip on 3/4/19.
 */

public class RssFeedProvider {
    private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String generateRandom(int length) {
        Random random = new SecureRandom();
        if (length <= 0) {
            throw new IllegalArgumentException("String length must be a positive integer");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }

}
