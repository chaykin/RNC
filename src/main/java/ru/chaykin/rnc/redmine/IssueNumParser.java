package ru.chaykin.rnc.redmine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IssueNumParser {
    public static final String NO_ISSUE = "NO_ISSUE";

    private static final Pattern ISSUE_NUm_REG_EXP = Pattern.compile("#(\\d{1,6})[^#\\d]");

    public static Collection<String> parse(String svnComment) {
        Collection<String> result = new ArrayList<>();

        Matcher matcher = ISSUE_NUm_REG_EXP.matcher(svnComment);
        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        if (result.isEmpty()) {
            result.add(NO_ISSUE);
        }

        return result;
    }
}
