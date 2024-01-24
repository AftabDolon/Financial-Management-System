package com.demo.task.model.enumFile.deserializer;

import com.demo.task.model.enumFile.Designation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class DesignationDeserializer extends JsonDeserializer<Designation> {

    @Override
    public Designation deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        String value = p.getText();
        for (Designation bg : Designation.values()) {
            if (bg.toString().equalsIgnoreCase(value)) {
                return bg;
            }
        }
        return null;
    }

}
