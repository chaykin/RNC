package ru.chaykin.rnc.svn.target;

public class Target {
    public final String targetPath;
    public final long startRev;
    public final long endRev;

    public Target(String targetPath, long startRev, long endRev) {
        this.targetPath = targetPath;
        this.startRev = startRev;
        this.endRev = endRev;
    }

    @Override
    public String toString() {
        return String.format("%s@%s:%s", targetPath, startRev, endRev);
    }
}