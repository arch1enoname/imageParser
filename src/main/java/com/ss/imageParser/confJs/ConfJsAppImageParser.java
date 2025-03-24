package com.ss.imageParser.confJs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ss.config.js.ConfJsApp;
import com.ss.config.js.ConfJsDb;
import com.ss.config.js.ExceptConf;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
public class ConfJsAppImageParser extends ConfJsApp {

    private String nameServer;
    private String urlBase;
    private String serverType;
    public String actionServiceUrlCreate;
    public String actionServiceUrlClose;
    public String keycloakOpenidUrl;
    public String keycloakPublicKey;
    @JsonIgnore
    public String keycloakClientSecret;
    public String keycloakClientUrl;
    public String domain;
    private int hikariPoolMaxSize;

    private int errorsSize;
    private int errorsInterval;
    private int errorsSleepInterval;
    private String directory;
    private String referrer;
    private String userAgent;


    public static final String SERVER_TYPE_DEV = "dev";
    public static final String SERVER_TYPE_TEST = "test";
    public static final String SERVER_TYPE_PREPROD = "preprod";
    public static final String SERVER_TYPE_PROD = "prod";

    public ConfJsAppImageParser() {
        super(ConfJsDb.knownDb);
    }

    public ConfJsAppImageParser(ConfJsApp p_kCopy) {
        super(p_kCopy);
    }

    @Override
    protected void initApp(JsonNode p_xParser) throws ExceptConf {
        try {

            // TECHNICAL
            nameServer = getStringRequired(p_xParser, "name");
            urlBase = getStringRequired(p_xParser, "url_base");
            serverType = getStringRequired(p_xParser, "server_type");
            domain = getStringRequired(p_xParser, "domain");
            hikariPoolMaxSize = getIntRequired(p_xParser, "hikari_pool_max_size");
            // API
            actionServiceUrlCreate = getStringRequired(p_xParser, "action_service_url_create");
            actionServiceUrlClose = getStringRequired(p_xParser, "action_service_url_close");
            //ERRORS
            errorsSize = getIntRequired(p_xParser, "attack_errors_size");
            errorsInterval = getIntRequired(p_xParser, "attack_errors_interval_sec");
            errorsSleepInterval = getIntRequired(p_xParser, "attack_errors_sleep_interval_sec");
            //keycloak
            keycloakOpenidUrl =  getStringRequired(p_xParser, "keycloak_openid_url");
            keycloakPublicKey =  getStringRequired(p_xParser, "keycloak_public_key");
            keycloakClientSecret =  getStringRequired(p_xParser, "keycloak_client_secret");
            keycloakClientUrl =  getStringRequired(p_xParser, "keycloak_client_url");
            directory = getStringRequired(p_xParser, "baseDirectory");
            referrer = getStringRequired(p_xParser, "referrerForDownload");
            userAgent = getStringRequired(p_xParser, "userAgent");

        } catch (RuntimeException ex) {
            throw new ExceptConf("ErrConfA1", "Can't process project configuration",
                    ex.getMessage(), ex);
        }
    }

    public String getNameServer() {
        return nameServer;
    }

    public String getUrlBase() {
        return urlBase;
    }

    public String getActionServiceUrlCreate() {
        return actionServiceUrlCreate;
    }

    public String getActionServiceUrlClose() {
        return actionServiceUrlClose;
    }

    public String getServerType() {
        return serverType;
    }
    public String getDomain() {
        return domain;
    }

    public int getErrorsSize() {
        return errorsSize;
    }

    public int getErrorsInterval() {
        return errorsInterval;
    }

    public int getErrorsSleepInterval() {
        return errorsSleepInterval;
    }

    public String getKeycloakOpenidUrl() {
        return keycloakOpenidUrl;
    }

    public String getKeycloakPublicKey() {
        return keycloakPublicKey;
    }

    public String getKeycloakClientSecret() {
        return keycloakClientSecret;
    }

    public String getKeycloakClientUrl() {
        return keycloakClientUrl;
    }

    public int getHikariPoolMaxSize() {
        return hikariPoolMaxSize;
    }

    public String getDirectory() {
        return directory;
    }

    public String getReferrer() {
        return referrer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    @Override
    public String toString() {
        return "urlBase=" + urlBase + "\n"
                + "serverType=" + serverType + "\n"
                + "domain=" + domain + "\n"
                + "actionServiceUrlCreate=" + actionServiceUrlCreate + "\n"
                + "actionServiceUrlClose=" + actionServiceUrlClose + "\n"
                + "keycloakOpenidUrl=" + keycloakOpenidUrl + "\n"
                + "keycloakPublicKey=" + keycloakPublicKey + "\n"
                + "keycloakClientUrl=" + keycloakClientUrl + "\n";

    }


}
