package ru.chaykin.rnc.model.func;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.chaykin.rnc.model.TestRedmineIssue;
import ru.chaykin.rnc.redmine.IRedmineIssue;

public class EroomNumFuncTest {

    @Test
    public void testFunc() {
        JSONObject jsonData = new JSONObject("{\"issue\":{\"subject\":\"test subject\",\"custom_fields\": [{\"name\":\"one\",\"value\":\"10\"},{\"name\":\"Номер запроса\",\"value\": \"\"}]}}");
        TestRedmineIssue issue = new TestRedmineIssue(jsonData);
        Assertions.assertEquals("", getValue(issue));

        issue.jsonData=new JSONObject("{\"issue\":{\"subject\":\"test subject\",\"custom_fields\": [{\"name\":\"one\",\"value\":\"10\"},{\"name\":\"Номер запроса\",\"value\": \"40\"}]}}");
        Assertions.assertEquals("40", getValue(issue));

        issue.jsonData=new JSONObject("{\"issue\":{\"subject\":\"50. test subject\",\"custom_fields\": [{\"name\":\"one\",\"value\":\"10\"},{\"name\":\"Номер запроса\",\"value\": \"\"}]}}");
        Assertions.assertEquals("50", getValue(issue));

        issue.jsonData=new JSONObject("{\"issue\":{\"subject\":\"50. test subject\",\"custom_fields\": [{\"name\":\"one\",\"value\":\"10\"},{\"name\":\"Номер запроса\",\"value\": \"40\"}]}}");
        Assertions.assertEquals("40", getValue(issue));
    }

    private String getValue(IRedmineIssue issue) {
        return new EroomNumFunc().getValue(issue, new String[]{"eRoomNum"});
    }
}
