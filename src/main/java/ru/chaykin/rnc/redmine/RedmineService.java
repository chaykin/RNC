package ru.chaykin.rnc.redmine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.io.SVNRepository;
import ru.chaykin.rnc.svn.target.Target;
import ru.chaykin.rnc.svn.target.TargetService;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class RedmineService {
    private static final Logger LOGGER = LogManager.getLogger();

    private static class LogEntriesComparator implements Comparator<String> {

        @Override
        public int compare(String issue1, String issue2) {
            if (issue1.equals(IssueNumParser.NO_ISSUE)) {
                return (issue2.equals(IssueNumParser.NO_ISSUE)) ? 0 : -1;
            } else if (issue2.equals(IssueNumParser.NO_ISSUE)) {
                return 1;
            } else {
                return issue1.compareTo(issue2);
            }
        }
    }

    private final SVNRepository repo;

    public RedmineService(SVNRepository repo) {
        this.repo = repo;
    }

    public Map<String, IRedmineIssue> getIssues() throws SVNException {
        Map<String, IRedmineIssue> result = new TreeMap<>(new LogEntriesComparator());
        new TargetService(repo).getTargets().forEach(t -> processTarget(t, result));
        return result;
    }

    private void processTarget(Target target, Map<String, IRedmineIssue> issues) {
        LOGGER.debug("Process target: {}", target);
        try {
            RedmineIssueFactory issueFactory = new RedmineIssueFactory();

            @SuppressWarnings("unchecked") Collection<SVNLogEntry> entries =
                    repo.log(new String[]{target.targetPath}, null, target.startRev, target.endRev, true, true);

            for (SVNLogEntry entry : entries) {
                LOGGER.debug("Process log entry: {} {}", entry.getRevision(), entry.getMessage());
                Collection<String> nums = IssueNumParser.parse(entry.getMessage());
                for (String num : nums) {
                    issues.computeIfAbsent(num, k -> issueFactory.createIssue(num)).addLogEntry(entry);
                }
            }
        } catch (SVNException e) {
            throw new RuntimeException(e);
        }
    }
}