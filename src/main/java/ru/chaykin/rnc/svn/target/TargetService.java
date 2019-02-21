package ru.chaykin.rnc.svn.target;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;
import ru.chaykin.rnc.config.SvnConfig;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class TargetService {
    private static final Logger LOGGER = LogManager.getLogger();

    private final long lastRev;

    public TargetService(SVNRepository repo) throws SVNException {
        lastRev = repo.getLatestRevision();
    }

    public Collection<Target> getTargets() {
        String targetsRawData = SvnConfig.getTargets();

        return Arrays.stream(targetsRawData.split(","))
                .map(String::trim)
                .map(v -> v.split("@"))
                .map(d -> {
                    String path = d[0].trim();
                    String[] rawRevData = d[1].split(":");
                    long startRev = Long.parseLong(rawRevData[0].trim());
                    long endRev = lastRev;
                    if (rawRevData.length == 2 && !"HEAD".equalsIgnoreCase(rawRevData[1].trim())) {
                        endRev = Long.parseLong(rawRevData[1].trim());
                    }
                    Target target = new Target(path, startRev, endRev);
                    LOGGER.debug("Found target: {}", target);

                    return target;
                })
                .collect(Collectors.toList());
    }
}