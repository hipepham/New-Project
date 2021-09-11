package com.hipepham.springboot.common.excel.utility.service.vo;

/**
 * 
 * @author CuongNV34
 *
 */
public class ExcelFieldVO {
	private String headerName;
    private String fieldName;
    private String dataFormat;
    private DataType dataType;

    public enum DataType {
        STRING,
        DATE,
        INTEGER,
        DOUBLE,
        LONG
    }

    public ExcelFieldVO() {
    }

    public ExcelFieldVO(String headerName, String fieldName, DataType dataType, String dataFormat) {
        this.headerName = headerName;
        this.fieldName = fieldName;
        this.dataType = dataType;
        this.dataFormat = dataFormat;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
}
