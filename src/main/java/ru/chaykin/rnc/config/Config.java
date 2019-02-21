package ru.chaykin.rnc.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

public class Config {
    private static final ResourceBundle MAIN_CONFIG = ResourceBundle.getBundle("config");
    private static final Properties USER_CONFIG = new Properties();

    public static void init(String userConfigPath) {
        try (InputStream in = new FileInputStream(new File(userConfigPath))) {
            USER_CONFIG.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Invaid user config file", e);
        }
    }

    public static String getFunctionClassName(String funcName) {
        return getConfigValue(String.format("function.%s", funcName.toLowerCase()));
    }

    public static String getEroomNumCustomField() {
        return getConfigValue("eroom.num.custom_field");
    }

    public static String getEroomUrlCustomField() {
        return getConfigValue("eroom.url.custom_field");
    }

    public static String getEroomBaseUrl() {
        return getConfigValue("eroom.base.url");
    }

    public static String getColumnsName() {
        return getConfigValue("columns.name");
    }

    public static String getColumnsValue() {
        return getConfigValue("columns.value");
    }

    public static String getNoIssueColumnName() {
        return getConfigValue("column.noissue.name");
    }

    public static String getTemplateFile() {
        return getConfigValue("template");
    }

    public static String getOutputFile() {
        return getConfigValue("output");
    }

    public static String getConfigValue(String name) {
        return USER_CONFIG.getProperty(name, MAIN_CONFIG.containsKey(name) ? MAIN_CONFIG.getString(name) : name);
    }
}