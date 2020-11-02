/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author Jevison7x
 */
public class RandomNumberGenerator
{

    /**
     * A
     * <code>Random</code> Object instantiated by
     * <code>java.security.SecureRandom</code>'s default constructor
     *
     * @see java.security.SecureRandom
     */
    private static final Random RANDOM = new SecureRandom();
    /**
     * Length of alphanumeric characters.
     *
     * @see #generateRandomAlphanumericCharacters()
     */
    public static final int ALPHANUMERIC_LENGTH = 16;
    /**
     * Length of numeric characters.
     *
     * @see #generateRandomNumericCharacters()
     */
    public static final int NUMERIC_LENGTH = 14;

    /**
     * Static method that generates random alphanumeric String characters.
     *
     * @return concatenated alphanumeric String variable which has 16 characters' length.
     * @since 1.0
     */
    public static String generateRandomAlphanumericCharacters()
    {
        // Pick from some letters that won't be easily mistaken for each
        // other. So, for example, omit o O and 0, 1 l and L.
        String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";

        String alphaNum = "";
        for(int i = 0; i < ALPHANUMERIC_LENGTH; i++)
        {
            int index = (int)(RANDOM.nextDouble() * letters.length());
            alphaNum += letters.substring(index, index + 1);
        }
        return alphaNum;
    }

    /**
     * Static method that generates random alphanumeric
     * <code>String</code> characters.
     *
     * @return concatenated alphanumeric
     * <code>String</code> variable which has a variable length of characters.
     * @param length	The length of characters that is required.
     * @since 1.0
     */
    public static String generateRandomAlphanumericCharacters(int length)
    {
        // Pick from some letters that won't be easily mistaken for each
        // other. So, for example, omit o O and 0, 1 l and L.
        String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";

        String alphaNum = "";
        for(int i = 0; i < length; i++)
        {
            int index = (int)(RANDOM.nextDouble() * letters.length());
            alphaNum += letters.substring(index, index + 1);
        }
        return alphaNum;
    }

    /**
     * Static method that generates random alphanumeric
     * <code>String</code> characters.
     *
     * @return concatenated alphanumeric
     * <code>String</code> variable which has a variable length of characters.
     * @param length	The length of characters that is required.
     * @param lowerCase	Specifies upper and lower-case letters if <font color="green"><i>true</i></font> else upper-case letters if <font
     * color="red"><i>false</i></font>
     * @since 1.0
     */
    public static String generateRandomAlphanumericCharacters(int length, boolean lowerCase)
    {
        // Pick from some letters that won't be easily mistaken for each
        // other. So, for example, omit o O and 0, 1 l and L.

        String letters = new String();
        if(lowerCase)
        {
            letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";
        }
        else
        {
            letters = "ABCDEFGHJKMNPQRSTUVWXYZ23456789";
        }
        String alphaNum = "";
        for(int i = 0; i < length; i++)
        {
            int index = (int)(RANDOM.nextDouble() * letters.length());
            alphaNum += letters.substring(index, index + 1);
        }
        return alphaNum;
    }

    /**
     * Static method that generates random numeric String characters.
     *
     * @return concatenated numeric String variable which has 14 characters' length.
     * @since 1.0
     */
    public static String generateRandomNumericCharacters()
    {
        String letters = "1234567890";

        String num = "";
        for(int i = 0; i < NUMERIC_LENGTH; i++)
        {
            int index = (int)(RANDOM.nextDouble() * letters.length());
            num += letters.substring(index, index + 1);
        }
        return num;
    }

    /**
     * Static method that generates random numeric String characters.
     *
     * @return concatenated numeric String variable which has variable length of characters.
     * @param length	The length of characters that is required.
     * @since 1.0
     */
    public static String generateRandomNumericCharacters(int length)
    {

        String letters = "1234567890";

        String num = "";
        for(int i = 0; i < length; i++)
        {
            int index = (int)(RANDOM.nextDouble() * letters.length());
            num += letters.substring(index, index + 1);
        }
        return num;
    }
}
