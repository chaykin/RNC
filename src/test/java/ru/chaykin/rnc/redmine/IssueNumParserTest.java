package ru.chaykin.rnc.redmine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class IssueNumParserTest {

    @Test
    public void parseTest() {
        Collection<String> issues = IssueNumParser.parse("#1 refs #2 #3, t1  #40  50  #60 #700000, #8000000 test");

        Assertions.assertEquals(6, issues.size());
        Assertions.assertTrue(issues.contains("1"));
        Assertions.assertTrue(issues.contains("2"));
        Assertions.assertTrue(issues.contains("3"));
        Assertions.assertTrue(issues.contains("40"));
        Assertions.assertTrue(issues.contains("60"));
        Assertions.assertTrue(issues.contains("700000"));
    }

    @Test
    public void parseNoIssueTest() {
        Collection<String> issues = IssueNumParser.parse("test without redmine number");

        Assertions.assertEquals(1, issues.size());
        Assertions.assertTrue(issues.contains(IssueNumParser.NO_ISSUE));
    }
}