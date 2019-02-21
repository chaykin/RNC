package ru.chaykin.rnc.model.func;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.chaykin.rnc.model.TestRedmineIssue;
import ru.chaykin.rnc.redmine.IRedmineIssue;

public class JsonQueryFuncTest {

    @Test
    public void testFunc() {
        JSONObject jsonData =
                new JSONObject("{\"root\":{\"l1\":[{\"id\":10},{\"id\":20}], \"subject\":\"test subject\"}}");
        IRedmineIssue issue = new TestRedmineIssue(jsonData);

        Assertions.assertEquals("10", getValue(issue, "/root/l1/0/id"));
        Assertions.assertEquals("20", getValue(issue, "/root/l1/1/id"));
        Assertions.assertEquals("test subject", getValue(issue, "/root/subject"));
    }

    private String getValue(IRedmineIssue issue, String val) {
        return new JsonQueryFunc().getValue(issue, new String[]{"", val});
    }
}