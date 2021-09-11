package com.hipepham.springboot.common.custom;

import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

/**
 * The type Custom my sql dialect.
 */
public class CustomMySQLDialect extends MySQL5InnoDBDialect {
    /**
     * Instantiates a new Custom my sql dialect.
     */
    public CustomMySQLDialect() {
        super();
        // register custom/inner function here
        this.registerFunction("group_concat", new SQLFunctionTemplate(
                StandardBasicTypes.STRING, "group_concat(?1)"));
        this.registerFunction("datediff", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "datediff(?1, ?2)"));

    }
}
