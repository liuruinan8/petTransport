package com.pet.transport.common.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DataConvertUtil {

    /**
     * 将一个 JavaBean 对象转化为一个 Map
     *
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的 Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Map convertBeanToMap(Object bean)	throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     * @param type 要转化的类型
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InstantiationException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Object convertMapToBean(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);

                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }

    /**
     * 从JSON字符串转换成Map对象
     *
     * @param s
     * @return
     */
    public static Map convertJsonToMap(String s) {

        // 将JSON字符串转换为 HashMap
        Gson gson = new Gson();
        Map<String, String> map = (Map<String, String>) gson.fromJson(s, new TypeToken<Map<String, String>>(){}.getType());
        return map;
    }

    /**
     * 从JSON字符串转换成Map对象
     *
     * @param s
     * @return
     */
    public static Map convertJsonToMapObj(String s) {

        // 将JSON字符串转换为 HashMap
        Gson gson = new Gson();
        Map<String, Object> map = (Map<String, Object>) gson.fromJson(s, new TypeToken<Map<String, Object>>(){}.getType());
        return map;
    }

    /**
     * 将JSON字符串转换成JavaBean
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static Object convertJsonToBean(String jsonStr, Class clazz) {
        Gson gson = new Gson();
        Object object = gson.fromJson(jsonStr, clazz);
        return object;
    }

    /**
     * 将Map转换成Json字符串
     * @return
     */
    public static String convertMapToJson(Map object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * 将Map转换成Json字符串
     * @return
     */
    public static String convertMapToJsonWithNulls(Map object) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(object);
    }

    /**
     * 将Bean转换成Json字符串
     * @return
     */
    public static String convertBeanToJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * 将Bean转换成Json字符串
     * 不忽略null，将null序列化
     * @return
     */
    public static String convertBeanToJsonWithNulls(Object object) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(object);
    }

    /**
     * 将Array转换成Json字符串
     * @return
     */
    public static String convertArrayToJson(Object[] object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

}
