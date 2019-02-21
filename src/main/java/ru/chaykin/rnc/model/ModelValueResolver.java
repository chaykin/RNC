package ru.chaykin.rnc.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.chaykin.rnc.config.Config;
import ru.chaykin.rnc.model.func.IColumnFunc;
import ru.chaykin.rnc.model.func.IMultiValueColumnFunc;
import ru.chaykin.rnc.model.func.ISingleValueColumnFunc;
import ru.chaykin.rnc.redmine.IRedmineIssue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelValueResolver {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Pattern PLACEHOLDER_REG_EXP = Pattern.compile("(\\$\\{(.*?)})");
    private final String columnCfg;

    public ModelValueResolver(String columnCfg) {
        this.columnCfg = columnCfg;
    }

    public Object getValue(IRedmineIssue issue) {
        StringBuffer result = new StringBuffer();
        Matcher matcher = PLACEHOLDER_REG_EXP.matcher(columnCfg);
        while (matcher.find()) {
            String[] funcData = matcher.group(2).split("=");
            String funcName = funcData[0];
            IColumnFunc func = getFunc(funcName);
            if (func instanceof ISingleValueColumnFunc) {
                String value = ((ISingleValueColumnFunc) func).getValue(issue, funcData);
                LOGGER.debug("Resolved function value: {}", value);

                matcher.appendReplacement(result, Matcher.quoteReplacement(value));
            } else if (func instanceof IMultiValueColumnFunc) {
                //Multivalue column function does not support any concatenation or subsequent function calls
                return ((IMultiValueColumnFunc) func).getValue(issue, funcData);
            } else {
                throw new RuntimeException(String.format("Unknown function instance for func %s", funcName));
            }
        }
        matcher.appendTail(result);
        String value = result.toString();
        LOGGER.debug("Resolved cell value: {}", value);

        return value;
    }

    private IColumnFunc getFunc(String name) {
        String className = Config.getFunctionClassName(name);
        LOGGER.debug("Function '{}' class {}", name, className);
        try {
            return Class.forName(className).asSubclass(IColumnFunc.class).getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(String.format("No class found for function %s", name), e);
        }
    }
}