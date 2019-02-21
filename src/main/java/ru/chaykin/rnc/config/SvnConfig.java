package ru.chaykin.rnc.config;

public class SvnConfig extends Config {

    public static String getBaseURL() {
        return getConfigValue("svn.base.url");
    }

    public static String getUserName() {
        return getConfigValue("svn.user");
    }

    public static String getPassword() {
        return getConfigValue("svn.password");
    }

    public static String getTargets() {
        return getConfigValue("svn.targets");
    }
}