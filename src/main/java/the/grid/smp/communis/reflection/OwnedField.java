package the.grid.smp.communis.reflection;

import java.lang.reflect.Field;

public record OwnedField(Object owner, Field field) {

    public void set(Object value) throws IllegalAccessException {
        this.field.set(this.owner, value);
    }

    public Object get() throws IllegalAccessException {
        return this.field.get(this.owner);
    }

    public Class<?> getType() {
        return this.field.getType();
    }

    public void setAccessible(boolean accessible) {
        this.field.setAccessible(accessible);
    }
}
