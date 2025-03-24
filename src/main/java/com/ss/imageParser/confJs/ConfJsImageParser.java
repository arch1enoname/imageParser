package com.ss.imageParser.confJs;

import com.ss.config.js.*;

import java.io.FileNotFoundException;
import java.util.HashMap;

import static java.lang.System.load;

public class ConfJsImageParser extends ConfJs {

    public static final String APP_NAME = "topt_server";
    private static final ConfJsImageParser instance = new ConfJsImageParser();
    private static final String CONF_FILE_NMAE = "conf_topt_serv.json";

    private ConfJsImageParser() {
        super(APP_NAME, ConfJsAppFactoryImageParser.getInstance());
        try {
            load(CONF_FILE_NMAE, "../" + CONF_FILE_NMAE);
        } catch (FileNotFoundException ex) {
            throw new ExceptConf("ErrConf1", "Can't load project configuration", "Can't find configuration file " + CONF_FILE_NMAE, ex);
        } catch (ExceptCJsUnsupported ex) {
            throw new ExceptConf("ErrConf2", "Can't process project configuration", "Cant't parse configuration file " + CONF_FILE_NMAE, ex);
        }
    }

    public void updateConf() {
        try {
            load(CONF_FILE_NMAE, "../" + CONF_FILE_NMAE);
        } catch (FileNotFoundException ex) {
            throw new ExceptConf("ErrConf1", "Can't load project configuration", "Can't find configuration file " + CONF_FILE_NMAE, ex);
        } catch (ExceptCJsUnsupported ex) {
            throw new ExceptConf("ErrConf2", "Can't process project configuration", "Cant't parse configuration file " + CONF_FILE_NMAE, ex);
        }
    }

    public static ConfJsImageParser getInstance() {
        return instance;
    }

    public ConfJsAppImageParser getApp() {
        try {
            return (ConfJsAppImageParser) super.getApp(APP_NAME);
        } catch (ExceptCJsNoObject ex) {
            throw new ExceptConf("ErrConf3", "Can't process project configuration",
                    String.format("Cant't get app %s in file %s", APP_NAME, CONF_FILE_NMAE), ex);
        }
    }

}
