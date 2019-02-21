package ru.chaykin.rnc.redmine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import ru.chaykin.rnc.config.RedmineConfig;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RedmineIssueFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String URI_TEMPLATE =
            String.format("%s/issues/%s.json?key=%s", RedmineConfig.getBaseURL(), "%s", RedmineConfig.getKey());

    private final HttpClient httpClient;

    public RedmineIssueFactory() {
        this.httpClient = HttpClient.newBuilder().build();
    }

    public IRedmineIssue createIssue(String issueNum) {
        LOGGER.debug("Creating Redmine issue for number {}...", issueNum);

        if (IssueNumParser.NO_ISSUE.equals(issueNum)) {
            return new RedmineIssue();
        }

        HttpRequest request = HttpRequest.newBuilder().uri(getUri(issueNum)).build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        String.format("Error while get responce. Code: %s, Body: %s", response.statusCode(),
                                      response.body()));
            }

            return new RedmineIssue(issueNum, new JSONObject(response.body()));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private URI getUri(String issueNum) {
        return URI.create(String.format(URI_TEMPLATE, issueNum));
    }
}