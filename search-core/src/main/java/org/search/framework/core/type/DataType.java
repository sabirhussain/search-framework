package org.search.framework.core.type;

/**
 * Data type of an entity field.
 * 
 * @author sabir
 *
 */
public enum DataType {

    BOOLEAN("java.lang.Boolean"), INTEGER("java.math.BigInteger"), DECIMAL("java.math.BigDecimal"),
    VARCHAR("java.lang.String"), DATETIME("java.util.Date");

    private String javaType;

    private DataType(String javaType) {
        this.javaType = javaType;
    }

    public String getJavaType() {
        return javaType;
    }
}
