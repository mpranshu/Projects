package com.webapp.javaurlshortner.service;

import org.springframework.stereotype.Service;

// A service to perform base conversion from from base 10 to base 62.
// It also performs conversion from base 62 to base 10
// A Service is a key business logic
// @Services hold business logic and call method in repository layer.
// Apart from the fact that it is used to indicate that it's holding the business logic,
// there’s no noticeable specialty that this annotation provides.

// This is the main service used by UrlService. UrlService interacts with repository and dto acting on actual data.
// The data to UrlService is passed by UrlController

@Service
public class BaseConversionService {
    // on base 62 we can have [a-z, A-Z, 0-9] Make an array of same to use for index based addressing
    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // Array of characters
    private char[] allowedCharacters = allowedString.toCharArray();

    // base 64
    private int base = allowedCharacters.length;

    // public function to convert number to a short link
    // numbers are saved in database as id
    public String encodeToShortURL(long input){
        var encodedString = new StringBuilder();

        if(input == 0) {
            return String.valueOf(allowedCharacters[0]);
        }

        while (input > 0) {
            encodedString.append(allowedCharacters[(int) (input % base)]);
            input = input / base;
        }

        return encodedString.reverse().toString();
    }

    // public function to convert short link to a number. Short link is not saved in database.
    // We convert short link to id and then use id to query data from database which returns long Url
    public long decodeToNumber(String shortURL) {

        var length = shortURL.length();

        var decoded = 0;

        for (int i = 0; i < length; i++) {
            // Below is order of n+m
            // decoded += allowedString.indexOf(characters[i]) * Math.pow(base, length - counter);
            // formula for base conversion source geeks for geeks
            // https://www.geeksforgeeks.org/how-to-design-a-tiny-url-or-url-shortener/
            // more info on same here: https://www.rapidtables.com/convert/number/hex-to-decimal.html

            if ('a' <= shortURL.charAt(i) &&
                    shortURL.charAt(i) <= 'z')
                decoded = decoded * 62 + shortURL.charAt(i) - 'a';
            if ('A' <= shortURL.charAt(i) &&
                    shortURL.charAt(i) <= 'Z')
                decoded = decoded * 62 + shortURL.charAt(i) - 'A' + 26;
            if ('0' <= shortURL.charAt(i) &&
                    shortURL.charAt(i) <= '9')
                decoded = decoded * 62 + shortURL.charAt(i) - '0' + 52;
        }
        return decoded;
    }
// The var keyword was introduced in Java 10.
// Type inference is used in var keyword in which it detects automatically the data type of a variable based on the surrounding context
}
