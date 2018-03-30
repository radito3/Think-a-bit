package org.elsys.netprog.view;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonWrapper {

    public static String getJsonFromObject(Object o) throws IOException {
        return new ObjectMapper().writeValueAsString(o);
    }
}
