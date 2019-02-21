package ru.chaykin.rnc.model.func;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import ru.chaykin.rnc.redmine.IRedmineIssue;

public abstract class AbstractEroomFunc implements IColumnFunc {
    private static final Logger LOGGER = LogManager.getLogger();

    protected String getMainValue(IRedmineIssue issue) {
        String customField = getCustomField();

        String result = null;
        JSONArray customFields = (JSONArray) issue.getJsonData().query("/issue/custom_fields");
        for (int i = 0; i < customFields.length(); i++) {
            String name = customFields.getJSONObject(i).getString("name");
            if (StringUtils.equals(name, customField)) {
                result = customFields.getJSONObject(i).getString("value");
                break;
            }
        }

        LOGGER.debug("Custom field value: {}", result);

        return result;
    }

    protected abstract String getCustomField();
}