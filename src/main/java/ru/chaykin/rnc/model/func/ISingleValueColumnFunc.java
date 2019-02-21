package ru.chaykin.rnc.model.func;

import ru.chaykin.rnc.redmine.IRedmineIssue;

public interface ISingleValueColumnFunc extends IColumnFunc {

    String getValue(IRedmineIssue issue, String[] funcData);
}