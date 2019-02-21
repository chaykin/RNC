package ru.chaykin.rnc.model.func;

import ru.chaykin.rnc.redmine.IRedmineIssue;

import java.util.List;

public interface IMultiValueColumnFunc extends IColumnFunc {

    List<String> getValue(IRedmineIssue issue, String[] funcData);
}