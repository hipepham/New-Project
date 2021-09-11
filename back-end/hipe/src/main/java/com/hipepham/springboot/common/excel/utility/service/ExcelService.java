/*
 *(C) Copyright HoanNT12
 *
 * @author nguyentrung
 * @date Jan 6, 2019
 * @version 1.0
 */
package com.hipepham.springboot.common.excel.utility.service;

import com.google.gson.JsonArray;
import com.hipepham.springboot.common.excel.utility.service.vo.ExcelFieldVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ExcelService {
	/**
	 * 
	 * @param workbook
	 * @param columns
	 * @return
	 */
	JSONArray getJSONArrayFromExcel(Workbook workbook, List<ExcelFieldVO> columns);

	/**
	 * 
	 * @param workbook
	 * @param columns
	 * @return
	 */
	List<?> getDataFromExcel(Workbook workbook, List<ExcelFieldVO> columns);

	/**
	 * 
	 * @param list
	 * @param columns
	 * @param res
	 * @param excelName
	 * @param autoResizeColumn
	 */
	void renderExcel(List<?> list, List<ExcelFieldVO> columns, HttpServletResponse res, String excelName,
                     boolean autoResizeColumn);


	/**
	 * Render Violation List
	 *
	 * @param list
	 * @param columns
	 * @param res
	 * @param excelName
	 * @param autoResizeColumn
	 * @param ipName
	 * @param scanDate
	 */
	void renderExcelViolation(List<?> list, List<ExcelFieldVO> columns, HttpServletResponse res, String excelName,
                              boolean autoResizeColumn, String ipName, String scanDate);

	/**
	 * 
	 * @param jsonArray
	 * @param columns
	 * @param res
	 * @param excelName
	 */
	void renderExcel(JsonArray jsonArray, List<ExcelFieldVO> columns, HttpServletResponse res, String excelName);

	/**
	 * 
	 * @param stringJsonArray
	 * @param columns
	 * @param res
	 * @param excelName
	 */
	void renderExcel(String stringJsonArray, List<ExcelFieldVO> columns, HttpServletResponse res, String excelName);

	String renderExcel(List<?> list, List<ExcelFieldVO> columns,String excelName, boolean autoResizeColumn);
	/**
	 * 
	 * @param list
	 * @param columns
	 * @param res
	 * @param excelName
	 * @param autoResizeColumn
	 * @param hwb
	 */
	void renderSheet(List<?> list, List<ExcelFieldVO> columns, HttpServletResponse res, String excelName,
                     boolean autoResizeColumn, HSSFWorkbook hwb);
	/**
	 * 
	 * @param list
	 * @param columns
	 * @param excelName
	 * @param autoResizeColumn
	 * @param hwb
	 */
	void renderSheet(List<?> list, List<ExcelFieldVO> columns, String excelName,
			boolean autoResizeColumn, HSSFWorkbook hwb);
	
}
