package com.szagurskii.rxtesting;

import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;
import com.szagurskii.rxtesting.models.PatternCheck;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.szagurskii.rxtesting.utils.EditTextUtils.addTextChangedListener;
import static com.szagurskii.rxtesting.utils.EditTextUtils.clearTextChangeListener;

/**
 * @author Savelii Zagurskii
 */
public abstract class BaseAdditionTests extends BaseTests {
    // 1 char
    static final String STRING_TO_BE_TYPED_LENGTH_ONE = "1";
    // 2 chars
    static final String STRING_TO_BE_TYPED_LENGTH_TWO = "12";
    // 3 chars
    static final String STRING_TO_BE_TYPED_LENGTH_THREE = "123";
    // 4 chars
    static final String STRING_TO_BE_TYPED_LENGTH_FOUR = "1234";
    // 5 chars
    static final String STRING_TO_BE_TYPED_LENGTH_FIVE = "12345";
    // 6 chars
    static final String STRING_TO_BE_TYPED_LENGTH_SIX = "123456";
    // 7 chars
    static final String STRING_TO_BE_TYPED_LENGTH_SEVEN = "1234567";
    // 8 chars
    static final String STRING_TO_BE_TYPED_LENGTH_EIGHT = "12345678";
    // 9 chars
    static final String STRING_TO_BE_TYPED_LENGTH_NINE = "123456789";
    // 10 chars
    static final String STRING_TO_BE_TYPED_LENGTH_TEN = "1234567890";
    // 11 chars
    static final String STRING_TO_BE_TYPED_LENGTH_ELEVEN = "12345678901";
    // 12 chars
    static final String STRING_TO_BE_TYPED_LENGTH_TWELVE = "123456789012";

    // 9 chars
    static final String PATTERN_1 = "(###-###)";

    static final List<PatternCheck> PATTERN_CHECKS = new ArrayList<>();

    static {
        // Pattern, Input, Expected.
        PATTERN_CHECKS.add(new PatternCheck("######", STRING_TO_BE_TYPED_LENGTH_SIX, "123456"));
        PATTERN_CHECKS.add(new PatternCheck("(######", STRING_TO_BE_TYPED_LENGTH_SIX, "(123456"));
        PATTERN_CHECKS.add(new PatternCheck("######)", STRING_TO_BE_TYPED_LENGTH_SIX, "123456)"));
        PATTERN_CHECKS.add(new PatternCheck("###-###", STRING_TO_BE_TYPED_LENGTH_SIX, "123-456"));
        PATTERN_CHECKS.add(new PatternCheck("(######)", STRING_TO_BE_TYPED_LENGTH_SIX, "(123456)"));
        PATTERN_CHECKS.add(new PatternCheck("(###-###)", STRING_TO_BE_TYPED_LENGTH_SIX, "(123-456)"));
        PATTERN_CHECKS.add(new PatternCheck("(-######)", STRING_TO_BE_TYPED_LENGTH_SIX, "(-123456)"));
        PATTERN_CHECKS.add(new PatternCheck("(######-)", STRING_TO_BE_TYPED_LENGTH_SIX, "(123456-)"));
        PATTERN_CHECKS.add(new PatternCheck("(-######-)", STRING_TO_BE_TYPED_LENGTH_SIX, "(-123456-)"));
        PATTERN_CHECKS.add(new PatternCheck("(-#-#-#-#-#-#-)", STRING_TO_BE_TYPED_LENGTH_SIX, "(-1-2-3-4-5-6-)"));
        PATTERN_CHECKS.add(new PatternCheck("(-#-#-#-#-#-#-)))))))))))))))))))))", STRING_TO_BE_TYPED_LENGTH_SIX, "(-1-2-3-4-5-6-)))))))))))))))))))))"));

        PATTERN_CHECKS.add(new PatternCheck("+# (###) ###-##-##", STRING_TO_BE_TYPED_LENGTH_ELEVEN, "+1 (234) 567-89-01"));
        PATTERN_CHECKS.add(new PatternCheck("+# (###) ###-##-##", STRING_TO_BE_TYPED_LENGTH_TWELVE, "+1 (234) 567-89-01"));

        PATTERN_CHECKS.add(new PatternCheck(")))###(((###", "((()))", ")))(((((()))"));
        PATTERN_CHECKS.add(new PatternCheck(")))######(((", "((()))", ")))((()))((("));
        PATTERN_CHECKS.add(new PatternCheck("###)))(((###", "((()))", "((()))((()))"));
        PATTERN_CHECKS.add(new PatternCheck("###)))###(((", "((()))", "((())))))((("));

        PATTERN_CHECKS.add(new PatternCheck("(((###)))###", "((()))", "(((((())))))"));
        PATTERN_CHECKS.add(new PatternCheck("(((######)))", "((()))", "(((((())))))"));
        PATTERN_CHECKS.add(new PatternCheck("###((()))###", "((()))", "(((((())))))"));
        PATTERN_CHECKS.add(new PatternCheck("###(((###)))", "((()))", "(((((())))))"));
    }

    @Test
    @Override
    public void validateAddingAndRemovingTextWatcher() {
        appendAndCheck("", "");
    }

    @Test
    public void basicSingleAddition() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_ONE, "(1");
    }

    @Test
    public void basicMultipleAddition() {
        PatternedTextWatcher patternedTextWatcher = addTextChangedListener(editText, PATTERN_1);
        multipleAddition();
        clearTextChangeListener(editText, patternedTextWatcher, true);
    }

    public abstract void multipleAddition();

    @Test
    public void basicAdditionExactPattern() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_NINE, "(123-456)");
    }

    @Test
    public void basicAdditionMoreThanPattern() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_ELEVEN, "(123-456)");
    }

    @Test
    public void basicAdditionLessThanPattern() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_SEVEN, "(123-456)");
    }

    @Test
    public void basicAdditionExactPatternSpecialCharacters() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_SIX, "(123-456)");
    }

    @Test
    public void basicAdditionMoreThanPatternSpecialCharacters() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_TEN, "(123-456)");
    }

    @Test
    public void basicAdditionLessThanPatternSpecialCharacters() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_FOUR, "(123-4");
    }

    @Test
    public void multipleAdditionPatternCheck() {
        for (PatternCheck patternCheck : PATTERN_CHECKS)
            appendAndCheck(patternCheck.getInput(), patternCheck.getExpected(), patternCheck.getPattern(), true);
    }

    private void appendAndCheck(String appended, String expected) {
        appendAndCheck(appended, expected, PATTERN_1, true);
    }

    private void appendAndCheck(String appended, String expected, boolean clearText) {
        appendAndCheck(appended, expected, PATTERN_1, clearText);
    }

    private void appendAndCheck(String appended, String expected, String pattern, boolean clearText) {
        PatternedTextWatcher patternedTextWatcher = addTextChangedListener(editText, pattern);
        addTextAndAssert(editText, expected, appended, pattern);
        clearTextChangeListener(editText, patternedTextWatcher, clearText);
    }

    /**
     * Add or set text to EditText and assert the expected result.
     *
     * @param editText current EdiText to watch.
     * @param expected expected string.
     * @param typed    what was typed.
     * @param pattern  the pattern which was used.
     */
    abstract void addTextAndAssert(EditText editText, String expected, String typed, String pattern);
}
