package com.demo2do.core.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Json utility
 *
 * @author David
 */
public abstract class JsonUtils {

    /**
     * Convert an objct to Json string
     *
     * @param object
     * @return
     */
    public static String toJsonString(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * Parse Json string to Map
     *
     * @param jsonText
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parse(String jsonText) {
        return (Map<String, Object>) JSON.parse(jsonText);
    }

    /**
     * Parse Json string to object
     *
     * @param jsonText
     * @param clazz
     * @return
     */
    public static <T> T parse(String jsonText, Class<T> clazz) {
        return JSON.parseObject(jsonText, clazz);
    }

    /**
     * Parse Json file to Map
     *
     * @param file
     * @return
     */
    @SuppressWarnings({"unchecked"})
    public static Map<String, Object> parse(File file) {
        try {
            String jsonText = FileUtils.readFileToString(file);
            return (Map<String, Object>) JSON.parse(jsonText);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parse resource file to Map
     *
     * @param resource
     * @return
     */
    @SuppressWarnings({"unchecked"})
    public static Map<String, Object> parse(Resource resource) {
        try {
            String jsonText = FileUtils.readFileToString(resource.getFile());
            return (Map<String, Object>) JSON.parse(jsonText);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parse Json string to a group of Map
     *
     * @param jsonText
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<Map<String, Object>> parseArray(String jsonText) {
        return (List) JSON.parseArray(jsonText);
    }

    /**
     * Parse Json file to a group of Map
     *
     * @param file
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<Map<String, Object>> parseArray(File file) {
        try {
            String jsonText = FileUtils.readFileToString(file);
            return (List) JSON.parseArray(jsonText);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parse resource file to a group of Map
     *
     * @param resource
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<Map<String, Object>> parseArray(Resource resource) {
        try {
            String jsonText = FileUtils.readFileToString(resource.getFile());
            return (List) JSON.parseArray(jsonText);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parse Json string to a group of object
     *
     * @param jsonText
     * @param clazz
     * @return
     */
    public static <T> List<T> parseArray(String jsonText, Class<T> clazz) {
        return JSON.parseArray(jsonText, clazz);
    }

    /**
     * Parse Json file to a group of object
     *
     * @param file
     * @param clazz
     * @return
     */
    public static <T> List<T> parseArray(File file, Class<T> clazz) {
        try {
            String jsonText = FileUtils.readFileToString(file);
            return JSON.parseArray(jsonText, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parse resource file to a group of object
     *
     * @param resource
     * @param clazz
     * @return
     */
    public static <T> List<T> parseArray(Resource resource, Class<T> clazz) {
        try {
            String jsonText = FileUtils.readFileToString(resource.getFile());
            return JSON.parseArray(jsonText, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
