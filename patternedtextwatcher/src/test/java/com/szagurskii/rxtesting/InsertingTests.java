package com.szagurskii.rxtesting;

import android.os.Build;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.BuildConfig;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * @author Savelii Zagurskii
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class InsertingTests extends BaseAdditionTests {
    @Override
    public void multipleAddition() {
        addTextAndAssert(editText, "(12", STRING_TO_BE_TYPED_LENGTH_TWO, PATTERN_1);
        addTextAndAssert(editText, "(123-", STRING_TO_BE_TYPED_LENGTH_THREE, PATTERN_1);
        addTextAndAssert(editText, "(123-456)", STRING_TO_BE_TYPED_LENGTH_EIGHT, PATTERN_1);
    }

    @Override
    void addTextAndAssert(EditText editText, String expected, String typed, String pattern) {
        editText.setText(typed);
        assertText(editText, expected, typed, pattern);
    }
}
