package com.ss.imageParser.confJs;

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
@Getter
@Setter
public class ConfJsAppImageParser extends ConfJsApp {

    private String nameServer;
    private String urlBase;
    private String serverType;
    private String directory;
    public String domain;

    public static final String SERVER_TYPE_DEV = "dev";
    public static final String SERVER_TYPE_TEST = "test";

    public ConfJsAppImageParser() {
        super(ConfJsDb.knownDb);
    }

    @Override
    protected void initApp(JsonNode p_xParser) throws ExceptConf {
        try {
            // TECHNICAL
            nameServer = getStringRequired(p_xParser, "name");
            urlBase = getStringRequired(p_xParser, "url_base");
            serverType = getStringRequired(p_xParser, "server_type");
            domain = getStringRequired(p_xParser, "domain");
            directory = getStringRequired(p_xParser, "head_directory");
        } catch (RuntimeException ex) {
            throw new ExceptConf("ErrConfA1", "Can't process project configuration",
                    ex.getMessage(), ex);
        }
    }


}
