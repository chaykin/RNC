package ru.chaykin.rnc.model.func;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.chaykin.rnc.config.Config;
import ru.chaykin.rnc.redmine.IRedmineIssue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EroomUrlFunc extends AbstractEroomFunc implements IMultiValueColumnFunc {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Pattern URL_REG_EXP =
            Pattern.compile(String.format("(%s\\S+)", Pattern.quote(Config.getEroomBaseUrl())));

    @Override
    public List<String> getValue(IRedmineIssue issue, String[] funcData) {
        String value = getMainValue(issue);
        List<String> result = List.of(value);

        if (StringUtils.isBlank(value)) {
            result = getSecondaryValue(issue);
        }

        if (result.isEmpty()) {
            LOGGER.warn("No eRoom url found for issue {}", issue.getNum());
        } else {
            LOGGER.debug("Final result: {}", StringUtils.join(result, ", "));
        }

        return result;
    }

    @Override
    protected String getCustomField() {
        return Config.getEroomUrlCustomField();
    }

    private List<String> getSecondaryValue(IRedmineIssue issue) {
        String descr = issue.getJsonData().query("/issue/description").toString();

        Collection<String> results = new HashSet<>();
        Matcher matcher = URL_REG_EXP.matcher(descr);
        while (matcher.find()) {
            String url = matcher.group(1);
            LOGGER.debug("Secondary url: {}", url);

            results.add(url);
        }
        if (results.size() > 1) {
            LOGGER.warn("There are many eRoom urls for issue {}", issue.getNum());
        }

        return List.copyOf(results);
    }
}