package ru.chaykin.rnc.model.func;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.chaykin.rnc.model.TestRedmineIssue;
import ru.chaykin.rnc.redmine.IRedmineIssue;

import java.util.List;

public class EroomUrlFuncTest {

    @Test
    public void testFunc() {
        JSONObject jsonData = new JSONObject(
                "{\"issue\":{\"description\":\"test subject\",\"custom_fields\": [{\"name\":\"one\",\"value\":\"10\"},{\"name\":\"Ссылка на запрос\",\"value\": \"\"}]}}");
        TestRedmineIssue issue = new TestRedmineIssue(jsonData);
        Assertions.assertTrue(getValue(issue).isEmpty());

        issue.jsonData = new JSONObject(
                "{\"issue\":{\"description\":\"test subject\",\"custom_fields\":[{\"name\":\"one\",\"value\":\"40\"},{\"name\":\"Ссылка на запрос\",\"value\":\"https://eroom1.opentext.com/eRoom/ESGSales/NIPIGAS/0_ffff\"}]}}");
        List<String> value = getValue(issue);
        Assertions.assertEquals(1, value.size());
        Assertions.assertEquals("https://eroom1.opentext.com/eRoom/ESGSales/NIPIGAS/0_ffff", value.get(0));

        issue.jsonData = new JSONObject(
                "{\"issue\":{\"description\":\"test https://eroom1.opentext.com/eRoom/ESGSales/NIPIGAS/0_ffff\\r\\nsubject test\",\"custom_fields\":[{\"name\":\"one\",\"value\":\"40\"},{\"name\":\"Ссылка на запрос\",\"value\":\"\"}]}}");
        value = getValue(issue);
        Assertions.assertEquals(1, value.size());
        Assertions.assertEquals("https://eroom1.opentext.com/eRoom/ESGSales/NIPIGAS/0_ffff", value.get(0));

        issue.jsonData = new JSONObject(
                "{\"issue\":{\"description\":\"https://eroom1.opentext.com/eRoom/ESGSales/NIPIGAS/0_ffff\\r\\nsubject test\",\"custom_fields\":[{\"name\":\"one\",\"value\":\"40\"},{\"name\":\"Ссылка на запрос\",\"value\":\"\"}]}}");
        value = getValue(issue);
        Assertions.assertEquals(1, value.size());
        Assertions.assertEquals("https://eroom1.opentext.com/eRoom/ESGSales/NIPIGAS/0_ffff", value.get(0));

        issue.jsonData = new JSONObject(
                "{\"issue\":{\"description\":\"test https://eroom1.opentext.com/eRoom/ESGSales/NIPIGAS/0_ffff \\r\\nsubject test\",\"custom_fields\":[{\"name\":\"one\",\"value\":\"40\"},{\"name\":\"Ссылка на запрос\",\"value\":\"\"}]}}");
        value = getValue(issue);
        Assertions.assertEquals(1, value.size());
        Assertions.assertEquals("https://eroom1.opentext.com/eRoom/ESGSales/NIPIGAS/0_ffff", value.get(0));

        issue.jsonData = new JSONObject(
                "{\"issue\":{\"description\":\"test https://eroom1.opentext.com/eRoom/ESGSales/NIPIGAS/0_ffff\\r\\nhttps://eroom1.opentext.com/eRoom/ESGSales/NIPIGAS/0_bbbb test\",\"custom_fields\":[{\"name\":\"one\",\"value\":\"40\"},{\"name\":\"Ссылка на запрос\",\"value\":\"\"}]}}");
        value = getValue(issue);
        Assertions.assertEquals(2, value.size());
        Assertions.assertEquals("https://eroom1.opentext.com/eRoom/ESGSales/NIPIGAS/0_ffff", value.get(0));
        Assertions.assertEquals("https://eroom1.opentext.com/eRoom/ESGSales/NIPIGAS/0_bbbb", value.get(1));

        issue.jsonData = new JSONObject(
                "{\"issue\":{\"description\":\"test https://eroom1.opentext.com/eRoom/ESGSales/NIPIGAS/0_ffff\\r\\nsubject test\",\"custom_fields\":[{\"name\":\"one\",\"value\":\"40\"},{\"name\":\"Ссылка на запрос\",\"value\":\"https://eroom1.opentext.com/eRoom/ESGSales/NIPIGAS/0_aaaa\"}]}}");
        value = getValue(issue);
        Assertions.assertEquals(1, value.size());
        Assertions.assertEquals("https://eroom1.opentext.com/eRoom/ESGSales/NIPIGAS/0_aaaa", value.get(0));
    }

    private List<String> getValue(IRedmineIssue issue) {
        return new EroomUrlFunc().getValue(issue, new String[]{"eRoomNum"});
    }
}