package ru.chaykin.rnc;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import ru.chaykin.rnc.config.Config;
import ru.chaykin.rnc.config.SvnConfig;
import ru.chaykin.rnc.model.ModelService;
import ru.chaykin.rnc.redmine.IRedmineIssue;
import ru.chaykin.rnc.redmine.RedmineService;

import java.io.*;
import java.nio.file.Path;
import java.util.Collection;

public class RNC {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.print("Usage: <user-config-file>");
            return;
        }

        try {
            Config.init(args[0]);
            Path template = Path.of(Config.getTemplateFile());

            SVNRepository repo = getRepository();
            Collection<IRedmineIssue> issues = new RedmineService(repo).getIssues().values();

            Configuration cfg = createFreemarkerConf(template.getParent());
            Template temp = cfg.getTemplate(template.getFileName().toString());

            File outFile = new File(Config.getOutputFile());
            try (Writer w = new OutputStreamWriter(new FileOutputStream(outFile))) {
                temp.process(new ModelService(issues).createDataModel(), w);
            }
        } catch (Throwable e) {
            LOGGER.error(e);
        }
    }

    private static Configuration createFreemarkerConf(Path template) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setDirectoryForTemplateLoading(template.toFile());
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);

        return cfg;
    }

    private static SVNRepository getRepository() throws SVNException {
        SVNURL url = SVNURL.parseURIEncoded(SvnConfig.getBaseURL());
        SVNRepository repo = SVNRepositoryFactory.create(url);
        repo.setAuthenticationManager(getAuthMan());

        return repo;
    }

    private static ISVNAuthenticationManager getAuthMan() {
        String user = SvnConfig.getUserName();
        String pass = SvnConfig.getPassword();

        if (StringUtils.isAnyBlank(user, pass)) {
            return SVNWCUtil.createDefaultAuthenticationManager();
        }
        return SVNWCUtil.createDefaultAuthenticationManager(user, pass.toCharArray());
    }
}