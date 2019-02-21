package ru.chaykin.rnc.model.func;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.chaykin.rnc.config.Config;
import ru.chaykin.rnc.redmine.IRedmineIssue;

public class ConfigValueFunc implements ISingleValueColumnFunc {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String getValue(IRedmineIssue issue, String[] funcData) {
        String result = Config.getConfigValue(funcData[1]);
        LOGGER.debug("Final result: {}", result);
        return result;
    }
}