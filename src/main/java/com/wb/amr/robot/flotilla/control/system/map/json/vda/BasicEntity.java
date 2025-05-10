package com.wb.amr.robot.flotilla.control.system.map.json.vda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;


public abstract class BasicEntity {
    private static final Logger LOGGER = LogManager.getLogger(BasicEntity.class.getName());

    /**
     * Sets the value for the object field, if it exists.
     * The value is passed as a string and converted to the field type, if possible.
     *
     * @param link  object to update the field for
     * @param key   name of the field to update
     * @param value string value to set
     */
    protected void checkSetValue(Object link, String key, Object value) {
        assert link != null : "Can't be null";
        assert key != null : "Can't be null";
        assert value != null : "Can't be null";

        Class<?> clazz = link.getClass();
        try {
            Field field = clazz.getDeclaredField(key);
            field.setAccessible(true);
            field.set(link, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
