Instruction text

| #  | eRoom | Ссылка на eRoom | Описание |
| :- | :---- | :-------------- | :------- |
<#list isssueEntries as issueEntry>
| [#${issueEntry.redmineNum}](${issueEntry.redmineUrl})| ${issueEntry.eRoomNum} | <#list issueEntry.eRoomUrl as eRoomUrlEntry>[${eRoomUrlEntry}](${eRoomUrlEntry}) </#list>| ${issueEntry.description} |
</#list>