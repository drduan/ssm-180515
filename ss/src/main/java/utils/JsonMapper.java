package utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 简单封装Jackson实现JSON<->Java Object的Mapper. 封装不同的输出风格, 使用不同的builder函数创建实例.
 */
public class JsonMapper {

    private ObjectMapper mapper;

    public JsonMapper(Include inclusion) {

        mapper = new ObjectMapper();
        // 设置输出时包含属性的风格
        mapper.setSerializationInclusion(inclusion);

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 禁止使用int代表Enum的order()來反序列化Enum,非常危險
        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        // 忽略特殊字符
        mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // 设置日期格式
        // mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 创建只输出非空属性到Json字符串的Mapper.
     */
    public static JsonMapper buildNonNullMapper() {
        return new JsonMapper(Include.NON_EMPTY);
    }

    public ObjectMapper getMapper() {
        return this.mapper;
    }

    /**
     * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合. 如需读取集合如List/Map,
     * 且不是List<String>这种简单类型时使用如下语句,使用後面的函數.
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 構造泛型的Type如List<MyBean>, Map<String,MyBean>
     */
    public JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
        return mapper.getTypeFactory().constructParametrizedType(parametrized, parametrized, parameterClasses);
    }

    /**
     * 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".
     */
    public String toJson(Object object) {

        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合. 如需读取集合如List/Map,
     * 且不是List<String>時,
     * 先用constructParametricType(List.class,MyBean.class)構造出JavaTeype,再調用本函數.
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            return null;
        }
    }

    public <T> T fromJson(String jsonString, TypeReference<T> javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            return null;
        }
    }

    public <T> List<T> fromJsonToList(String jsonString, Class<T> clazz) {
        
        List<T> objectList = new ArrayList<T>();
        if (jsonString.startsWith("[")) { //List
            JavaType javaType = constructParametricType(List.class, clazz);
            objectList = fromJson(jsonString, javaType);
        } else { //Single
            T t = fromJson(jsonString, clazz);
            if (null != t) {
                objectList.add(t);
            } else {
                objectList = null;
            }
        }
        return objectList;
    }
}
