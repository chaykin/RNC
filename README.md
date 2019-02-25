# README #


Project for generate Release Notes file, based on svn commits with link to Redmine issues. It collects data from svn and Redmine and put it to resulting file as html

### How do I get set up? ###

* This project builds under Apache Gradle tool
* Set base URL to Your SVN repo in *svn.base.url* of config.properties file
* Optionally, set *svn.user* and *svn.password* of Your repo. It can be skipped, if You have authorization data stored on Your machine
* Set base URL to Your Redmine in *redmine.base.url*
* Add Redmine API token in *redmine.key*
* Set base URL to Your eRoom in *eroom.base.url*
* Set name of Redmine custom fields, that contains info about related eRoom issue: number to *eroom.num.custom_field* and url to *eroom.url.custom_field*
* Build project by `gradle clean build` command

### How to use? ###

* Project requires Java 11 or above installed
* You can build it from source or download last binary version from [Downloads](https://https://bitbucket.org/Li_Ion/rnc/downloads/) page
* Set any number of relative SVN repo paths and revision ranges, that You want to be attached to generated Release Notes in *svn.targets* of user_conf.properties file. Example can be found in *svn.targets* of config.properties file
* Set Freemarker template to *template*. There are two predefined templates: RN_template.ftl and RN_template_compact.ftl, but You can create Your own.
* Set file name, where You want to save resulting Release Notes to *output*
* To run this program use `java -jar rnc-0.1.jar <path_to_user_config.properties>