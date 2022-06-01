package com.EElshereif.TopologyAPI.Exceptions;

public class JsonNotFoundException extends RuntimeException {
    public JsonNotFoundException(String fileName) {
        super("file %s couldn't be found".formatted(fileName));
    }
}
