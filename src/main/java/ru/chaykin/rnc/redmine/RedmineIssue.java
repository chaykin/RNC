package ru.chaykin.rnc.redmine;

import org.json.JSONObject;
import org.tmatesoft.svn.core.SVNLogEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RedmineIssue implements IRedmineIssue {
    private final String num;
    private final JSONObject jsonData;
    private final Collection<SVNLogEntry> logEntries = new ArrayList<>();

    RedmineIssue() {
        this(IssueNumParser.NO_ISSUE, null);
    }

    RedmineIssue(String num, JSONObject jsonData) {
        this.num = num;
        this.jsonData = jsonData;
    }

    @Override
    public void addLogEntry(SVNLogEntry logEntry) {
        logEntries.add(logEntry);
    }

    @Override
    public Collection<SVNLogEntry> getLogEntries() {
        return List.copyOf(logEntries);
    }

    @Override
    public JSONObject getJsonData() {
        return jsonData;
    }

    @Override
    public String getNum() {
        return num;
    }
}