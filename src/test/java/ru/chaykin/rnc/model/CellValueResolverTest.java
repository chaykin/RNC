package ru.chaykin.rnc.model;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CellValueResolverTest {

    @Test
    public void testResolver() {
        JSONObject jsonData = new JSONObject("{\"issue\":{\"id\":\"10\",\"subject\":\"test subject\"}}");
        TestRedmineIssue issue = new TestRedmineIssue(jsonData);

        String cfg =
                "${query=/issue/id}, ${conf_val=redmine.base.url}/issues/${query=/issue/id}, ${query=/issue/subject}";
        ModelValueResolver resolver = new ModelValueResolver(cfg);
        resolver.getValue(issue);

        Assertions.assertEquals("10, https://redmine.ot-ps.ru/issues/10, test subject", resolver.getValue(issue));
    }
}