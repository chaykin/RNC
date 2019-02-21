package ru.chaykin.rnc.redmine;

import org.json.JSONObject;
import org.tmatesoft.svn.core.SVNLogEntry;

import java.util.Collection;

public interface IRedmineIssue {
    void addLogEntry(SVNLogEntry logEntry);

    Collection<SVNLogEntry> getLogEntries();

    JSONObject getJsonData();

    String getNum();
}