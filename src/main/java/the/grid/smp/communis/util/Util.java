package the.grid.smp.communis.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Util {

    public static ObjectMapper createMapper(Serializer<?>[] serializers, Deserializer<?>[] deserializers) {
        SimpleModule module = new SimpleModule();

        if (serializers != null) {
            for (Serializer<?> serializer : serializers) {
                serializer.attach(module);
            }
        }

        if (deserializers != null) {
            for (Deserializer<?> deserializer : deserializers) {
                deserializer.attach(module);
            }
        }

        return new ObjectMapper(new YAMLFactory()).registerModule(module);
    }
}
