package top.uaian.api.controller.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import com.alibaba.fastjson.JSON;


public class FastJsonArrayRedisSerializer<T> implements RedisSerializer<Object> {
    private FastJsonConfig fastJsonConfig = new FastJsonConfig();

    private Class<T> type;

    public FastJsonArrayRedisSerializer(Class<T> type) {
        this.type = type;
    }

    public FastJsonConfig getFastJsonConfig() {
        return this.fastJsonConfig;
    }

    public void setFastJsonConfig(FastJsonConfig fastJsonConfig) {
        this.fastJsonConfig = fastJsonConfig;
    }
    @Override
    public byte[] serialize(Object t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        } else {
            try {
                return JSON.toJSONBytes(t, this.fastJsonConfig.getSerializeConfig(), this.fastJsonConfig.getSerializerFeatures());
            } catch (Exception var3) {
                throw new SerializationException("Could not serialize: " + var3.getMessage(), var3);
            }
        }
    }
    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes != null && bytes.length != 0) {
            try {
                String res = new String(bytes);
                return JSON.parseArray(res, this.type);
            } catch (Exception var3) {
                throw new SerializationException("Could not deserialize: " + var3.getMessage(), var3);
            }
        } else {
            return null;
        }
    }
}
