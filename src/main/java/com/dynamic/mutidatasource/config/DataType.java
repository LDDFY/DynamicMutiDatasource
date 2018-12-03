package com.dynamic.mutidatasource.config;

/**
 * 当前数据库枚举值
 */
public enum DataType {
    main("main", "主要数据源"),//主要数据源
    slave1("slave1", "分支数据源1");//分支数据源1

    private String name;
    private String value;

    DataType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
