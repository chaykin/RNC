package ru.chaykin.rnc.model.func;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.chaykin.rnc.redmine.IRedmineIssue;

public class JsonQueryFunc implements ISingleValueColumnFunc {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String getValue(IRedmineIssue issue, String[] funcData) {
        String result = issue.getJsonData().query(funcData[1]).toString();
        LOGGER.debug("Final result: {}", result);
        return result;
    }
}