package the.grid.smp.communis.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public abstract class Deserializer<T> extends StdSerializer<T> {

    protected Deserializer(Class<T> t) {
        super(t);
    }

    public abstract void attach(SimpleModule module);
}
