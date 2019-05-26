package de.esports.aeq.admins.common.conversion;

import org.modelmapper.spi.MappingContext;

public class Converters {

    public static Object convertStringToLong(MappingContext<Object, Object> context) {
        String source = (String) context.getSource();
        return Long.valueOf(source);
    }

    public static Object convertLongToString(MappingContext<Object, Object> context) {
        Long source = (Long) context.getSource();
        return String.valueOf(source);
    }

}
