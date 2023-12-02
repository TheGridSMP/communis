package the.grid.smp.communis.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public abstract class Serializer<T> extends StdSerializer<T> {

    protected Serializer(Class<T> t) {
        super(t);
    }

    public abstract void attach(SimpleModule module);
}
