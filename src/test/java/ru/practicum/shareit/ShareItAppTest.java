package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShareItAppTest {

    @Test
    void testMain() {
        Assertions.assertDoesNotThrow(ShareItAppTest::new);
        Assertions.assertDoesNotThrow(() -> ShareItApp.main(new String[]{}));
    }
}
