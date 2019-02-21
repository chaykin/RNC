package ru.chaykin.rnc.model.func;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.chaykin.rnc.config.Config;
import ru.chaykin.rnc.redmine.IRedmineIssue;

public class EroomNumFunc extends AbstractEroomFunc implements ISingleValueColumnFunc {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String getValue(IRedmineIssue issue, String[] funcData) {
        String value = getMainValue(issue);

        if (StringUtils.isBlank(value)) {
            value = getSecondaryValue(issue);
        }

        if (StringUtils.isBlank(value)) {
            LOGGER.warn("No eRoom number found for issue {}", issue.getNum());
        } else {
            LOGGER.debug("Final result: {}", value);
        }

        return value;
    }

    @Override
    protected String getCustomField() {
        return Config.getEroomNumCustomField();
    }

    private String getSecondaryValue(IRedmineIssue issue) {
        String subject = issue.getJsonData().query("/issue/subject").toString();
        LOGGER.debug("Redmine issue subject: {}", subject);

        String result = subject.split("\\.")[0].trim();
        result = result.matches("\\d{1,7}") ? result : "";

        LOGGER.debug("Secondary result: {}", result);
        return result;
    }
}