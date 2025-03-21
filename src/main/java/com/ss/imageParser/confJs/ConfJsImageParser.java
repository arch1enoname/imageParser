package com.ss.imageParser.confJs;

import com.ss.config.js.*;

import java.io.FileNotFoundException;
import java.util.HashMap;

import static java.lang.System.load;

public class ConfJsImageParser extends ConfJs {

    public static final String APP_NAME = "image_loader_server";
    public static final ConfJsImageParser instance = new ConfJsImageParser();
    public static final String CONF_FILE_NAME = "conf_topt_serv.json";

    private ConfJsImageParser() {
        super(APP_NAME, ConfJsAppFactoryImageParser.getInstance());
        try {
            load(CONF_FILE_NAME, "../" + CONF_FILE_NAME);
        } catch (FileNotFoundException ex) {
            throw new ExceptConf("ErrConf1", "Can't load project configuration", "Can't find configuration file " + CONF_FILE_NAME, ex);
        } catch (ExceptCJsUnsupported ex) {
            throw new ExceptConf("ErrConf2", "Can't process project configuration", "Cant't parse configuration file " + CONF_FILE_NAME, ex);
        }
    }


    public void updateConf() {
        try {
            load(CONF_FILE_NAME, "../" + CONF_FILE_NAME);
        } catch (FileNotFoundException ex) {
            throw new ExceptConf("ErrConf531", "Can't load project configuration", "Can't find configuration file " + CONF_FILE_NAME, ex);
        } catch (ExceptCJsUnsupported ex) {
            throw new ExceptConf("ErrConf532", "Can't process project configuration", "Cant't parse configuration file " + CONF_FILE_NAME, ex);
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
                    String.format("Cant't get app %s in file %s", APP_NAME, CONF_FILE_NAME), ex);
        }
    }

}
