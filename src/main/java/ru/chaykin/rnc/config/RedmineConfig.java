package ru.chaykin.rnc.config;

public class RedmineConfig extends Config {

    public static String getBaseURL() {
        return getConfigValue("redmine.base.url");
    }

    public static String getKey() {
        return getConfigValue("redmine.key");
    }
}