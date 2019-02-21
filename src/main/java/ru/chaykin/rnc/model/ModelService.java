package ru.chaykin.rnc.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import ru.chaykin.rnc.config.Config;
import ru.chaykin.rnc.redmine.IRedmineIssue;
import ru.chaykin.rnc.redmine.IssueNumParser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModelService {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String NO_ISSUE_COL_NAME = Config.getNoIssueColumnName();
    private final Collection<IRedmineIssue> issues;

    public ModelService(Collection<IRedmineIssue> issues) {
        this.issues = issues;
    }

    public Map<String, Object> createDataModel() {
        Map<String, Object> model = new HashMap<>();
        List<Object> issueEntries = new ArrayList<>();

        Map<String, String> columnsConfig = getColumnsConfig();
        for (IRedmineIssue issue : issues) {
            LOGGER.debug("Process issue {}", issue.getNum());

            Map<String, Object> issueEntry = new HashMap<>();
            if (issue.getNum().equals(IssueNumParser.NO_ISSUE)) {
                for (SVNLogEntry logEntry : issue.getLogEntries()) {

                    columnsConfig.keySet().forEach(k -> issueEntry.put(k, NO_ISSUE_COL_NAME.equals(k) ? logEntry.getMessage() : ""));

                    var paths = logEntry.getChangedPaths().values().stream();
                    issueEntry.put("changes", processChangedPaths(paths));
                    issueEntries.add(issueEntry);
                }
            } else {
                columnsConfig.forEach((k, v) -> {
                    LOGGER.debug("For column '{}'", v);
                    issueEntry.put(k, new ModelValueResolver(v).getValue(issue));
                });
                var paths = issue.getLogEntries().stream().flatMap(e -> e.getChangedPaths().values().stream());
                issueEntry.put("changes", processChangedPaths(paths));
                issueEntries.add(issueEntry);
            }
        }

        model.put("isssueEntries", issueEntries);
        return model;
    }

    private List<List<String>> processChangedPaths(Stream<SVNLogEntryPath> paths) {
        return paths.map(this::getChangedPathVal).collect(Collectors.toList());
    }

    private List<String> getChangedPathVal(SVNLogEntryPath entryPath) {
        return Arrays.asList(String.valueOf(entryPath.getType()), entryPath.getPath());
    }

    private Map<String, String> getColumnsConfig() {
        String[] names = Config.getColumnsName().split(",");
        String[] values = Config.getColumnsValue().split(",");
        if (names.length != values.length) {
            throw new RuntimeException("Columns config is invalid");
        }

        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            result.put(names[i].trim(), values[i].trim());
        }

        return result;
    }
}