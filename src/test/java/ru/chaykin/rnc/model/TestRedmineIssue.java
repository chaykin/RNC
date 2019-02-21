package ru.chaykin.rnc.model;

import org.json.JSONObject;
import org.tmatesoft.svn.core.SVNLogEntry;
import ru.chaykin.rnc.redmine.IRedmineIssue;

import java.util.Collection;

public class TestRedmineIssue implements IRedmineIssue {
    public JSONObject jsonData;

    public TestRedmineIssue(JSONObject jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    public void addLogEntry(SVNLogEntry logEntry) {
    }

    @Override
    public Collection<SVNLogEntry> getLogEntries() {
        return null;
    }

    @Override
    public JSONObject getJsonData() {
        return jsonData;
    }

    @Override
    public String getNum() {
        return null;
    }
}