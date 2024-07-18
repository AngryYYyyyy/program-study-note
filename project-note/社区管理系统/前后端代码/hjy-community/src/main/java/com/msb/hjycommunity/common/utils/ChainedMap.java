package com.msb.hjycommunity.common.utils;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 链式Map
 *  继承 LinkedCaseInsensitiveMap, 对key大小写不敏感的LinkedHashMap实现
 * @author spikeCong
 * @date 2023/5/3
 **/
public class ChainedMap extends LinkedCaseInsensitiveMap<Object> {
    private ChainedMap() {
        super();
    }

    /**
     * 创建ChainedMap
     *
     * @return ChainedMap
     */
    public static ChainedMap create() {
        return new ChainedMap();
    }

    public static <K, V> HashMap<K, V> newMap() {
        return new HashMap<>(16);
    }

    /**
     * 设置列
     *
     * @param attr  属性
     * @param value 值
     * @return 本身
     */
    public ChainedMap set(String attr, Object value) {
        this.put(attr, value);
        return this;
    }

    /**
     * 设置全部
     *
     * @param map 属性
     * @return 本身
     */
    public ChainedMap setAll(Map<? extends String, ?> map) {
        if (map != null) {
            this.putAll(map);
        }
        return this;
    }

    /**
     * 设置列，当键或值为null时忽略
     *
     * @param attr  属性
     * @param value 值
     * @return 本身
     */
    public ChainedMap setIgnoreNull(String attr, Object value) {
        if (attr != null && value != null) {
            set(attr, value);
        }
        return this;
    }

    public Object getObj(String key) {
        return super.get(key);
    }

    /**
     * 获得特定类型值
     *
     * @param <T>          值类型
     * @param attr         字段名
     * @param defaultValue 默认值
     * @return 字段值
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String attr, T defaultValue) {
        final Object result = get(attr);
        return (T) (result != null ? result : defaultValue);
    }

    /**
     * 获得特定类型值
     *
     * @param attr 字段名
     * @return 字段值
     */
    public String getStr(String attr) {
        if (null == attr || attr.equals(StringPool.NULL)) {
            return StringPool.NULL;
        }
        return attr;
    }

    /**
     * 获得特定类型值
     *
     * @param attr 字段名
     * @return 字段值
     */
    public Integer getInt(String attr) {
        if (attr == null) {
            return -1;
        }
        try {
            return Integer.valueOf(attr);
        } catch (final NumberFormatException nfe) {
            return -1;
        }
    }

    /**
     * 获得特定类型值
     *
     * @param attr 字段名
     * @return 字段值
     */
    public Long getLong(String attr) {
        if (attr == null) {
            return -1L;
        }
        try {
            return Long.valueOf(attr);
        } catch (final NumberFormatException nfe) {
            return -1L;
        }
    }

    /**
     * 获得特定类型值
     *
     * @param attr 字段名
     * @return 字段值
     */
    public Float getFloat(String attr) {
        if (attr != null) {
            return Float.valueOf(attr.trim());
        }
        return null;
    }

    public Double getDouble(String attr) {
        if (attr != null) {
            return Double.valueOf(attr.trim());
        }
        return null;
    }


    /**
     * 获得特定类型值
     *
     * @param attr 字段名
     * @return 字段值
     */
    public Boolean getBool(String attr) {
        if (attr != null) {
            String val = String.valueOf(attr);
            val = val.toLowerCase().trim();
            return Boolean.parseBoolean(val);
        }
        return null;
    }

    /**
     * 获得特定类型值
     *
     * @param attr 字段名
     * @return 字段值
     */
    public byte[] getBytes(String attr) {
        return get(attr, null);
    }

    /**
     * 获得特定类型值
     *
     * @param attr 字段名
     * @return 字段值
     */
    public Date getDate(String attr) {
        return get(attr, null);
    }

    /**
     * 获得特定类型值
     *
     * @param attr 字段名
     * @return 字段值
     */
    public Time getTime(String attr) {
        return get(attr, null);
    }

    /**
     * 获得特定类型值
     *
     * @param attr 字段名
     * @return 字段值
     */
    public Timestamp getTimestamp(String attr) {
        return get(attr, null);
    }

    /**
     * 获得特定类型值
     *
     * @param attr 字段名
     * @return 字段值
     */
    public Number getNumber(String attr) {
        return get(attr, null);
    }

    @Override
    public ChainedMap clone() {
        ChainedMap clone = new ChainedMap();
        clone.putAll(this);
        return clone;
    }

}
