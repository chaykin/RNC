package ru.chaykin.rnc.model.func;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConfigValueFuncTest {

    @Test
    public void testFunc() {
        Assertions.assertEquals("", getValue(""));
        Assertions.assertEquals("no-config-test", getValue("no-config-test"));
        Assertions.assertEquals("https://redmine.ot-ps.ru", getValue("redmine.base.url"));
    }

    private String getValue(String val) {
        return new ConfigValueFunc().getValue(null, new String[]{"", val});
    }
}
