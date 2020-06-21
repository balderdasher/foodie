package com.mrdios.foodie.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json工具类,封装spring都推荐的jackson库常用序列化和反序列化操作,
 * 研究表明jackson的性能要比fastjson好,而且fastjson经常曝出漏洞需要修复,
 * 所以json相关操作建议统一使用此工具
 *
 * @author huxiong
 * @date 2019-06-08
 */
public class JacksonUtil {
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 序列化为json字符串
     */
    public static String toJackson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }

    /**
     * 将json字符串反序列化为目标类型的对象
     *
     * @param jsonStr json字符串
     * @param clazz   目标对象类型
     * @return 反序列化后的对象
     */
    public static <T> T toObject(String jsonStr, Class<T> clazz) {
        if (null == jsonStr || "".equals(jsonStr)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据类型引用反序列化目标类型的对象,适用于泛型对象
     *
     * @param jsonStr       json字符串
     * @param typeReference 类型引用
     */
    public static <T> T toObject(String jsonStr,
                                 TypeReference<T> typeReference) throws Exception {
        return OBJECT_MAPPER.readValue(jsonStr, typeReference);
    }

    /**
     * 获得格式化好的json字符串
     */
    public static String toPrettyJson(Object object) {
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}