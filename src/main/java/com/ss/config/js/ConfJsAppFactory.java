package com.ss.config.js;

import java.util.HashMap;

public class ConfJsAppFactory implements ConfJsAppFactory_I
{
    private static ConfJsAppFactory instance = new ConfJsAppFactory();

    public static ConfJsAppFactory getInstance() {
        return instance;
    }

    @Override
    public ConfJsApp newObj(HashMap<String, ConfJsDbFactory_I> factoriesDb) {
        return new ConfJsAppEmpty(factoriesDb);
    }

}
