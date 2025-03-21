package com.ss.imageParser.confJs;

import com.ss.config.js.ConfJsApp;
import com.ss.config.js.ConfJsAppFactory;
import com.ss.config.js.ConfJsDbFactory_I;

import java.util.HashMap;

public class ConfJsAppFactoryImageParser extends ConfJsAppFactory {

    private static final ConfJsAppFactoryImageParser instance = new ConfJsAppFactoryImageParser();

    public static ConfJsAppFactoryImageParser getInstance() {
        return instance;
    }

    @Override
    public ConfJsApp newObj(HashMap<String, ConfJsDbFactory_I> factoriesDb) {
        return new ConfJsAppImageParser();
    }
}
