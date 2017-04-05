package ir.ac.iust.dml.kg.knowledge.store.access.entities;

import java.net.MalformedURLException;

/**
 * Types of value
 */
public enum ValueType {
    Resource, Boolean, Byte, Short, Integer, Long, Double, Float;

    public Object parse(String value) throws MalformedURLException {
        switch (this) {
            case Resource:
                return new java.net.URL(value);
            case Boolean:
                return java.lang.Boolean.parseBoolean(value);
            case Byte:
                return java.lang.Byte.parseByte(value);
            case Short:
                return java.lang.Short.parseShort(value);
            case Integer:
                return java.lang.Integer.parseInt(value);
            case Long:
                return java.lang.Long.parseLong(value);
            case Double:
                return java.lang.Double.parseDouble(value);
            case Float:
                return java.lang.Float.parseFloat(value);
        }
        throw new RuntimeException("Unknown Type");
    }
}
