package com.iscte.mei.ads.schedules.api.deserializers;

import java.io.IOException;
import java.util.Base64;

abstract public class Deserializer<T> {

    private final static Base64.Decoder decoder = Base64.getDecoder();

    abstract T deserialize(String content) throws IOException;

    String decodeFromBase64(String content) {
        try {
            byte[] data = decoder.decode(content);

            return new String(data);
        } catch (Exception ex) {
            throw new IllegalArgumentException();
        }
    }

}
