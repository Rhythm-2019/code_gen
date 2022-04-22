package com.mdnote.rym.util;


import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * @author Rhythm-2019
 * @date 2022/4/22
 * @description
 */
public class StringUtilTest {
    @Test
    public void testGetCamelSign01() {
        String[] inputs = {"a", "ab", " ab", "abc_", "abc_d", "_d", "_", "__abcd", "__abc_d__"};
        String[] expected = {"A", "Ab", "Ab", "Abc", "AbcD", "D", "", "Abcd", "AbcD"};
        for (int i = 0; i < inputs.length; i++) {
            String actual = StringUtil.getCamelSign(inputs[i], true);
            System.out.printf("expected: %s, input: %s, actual: %s\n",expected[i], inputs[i], actual);
            assertEquals(expected[i], actual);
        }
    }

    @Test
    public void testGetCamelSign02() {
        String[] inputs = {"a", "ab", " ab", "abc_", "abc_d", "_d", "_", "__abcd", "__abc_d__"};
        String[] expected = {"a", "ab", "ab", "abc", "abcD", "d", "", "abcd", "abcD"};
        for (int i = 0; i < inputs.length; i++) {
            String actual = StringUtil.getCamelSign(inputs[i], false);
            System.out.printf("expected: %s, input: %s, actual: %s\n",expected[i], inputs[i], actual);
            assertEquals(expected[i], actual);

        }
    }

}

