/*
 *(C) Copyright HoanNT12
 *
 * @author nguyentrung
 * @date Jan 7, 2019
 * @version 1.0
 */
package com.hipepham.springboot.common.excel.utility.service.impl;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.hipepham.springboot.common.excel.utility.Constants;
import com.hipepham.springboot.common.excel.utility.DeserializerUtils;
import com.hipepham.springboot.common.excel.utility.ExcelUtils;
import com.hipepham.springboot.common.excel.utility.NumberUtils;
import com.hipepham.springboot.common.excel.utility.service.ExcelService;
import com.hipepham.springboot.common.excel.utility.service.vo.ExcelFieldVO;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

@org.springframework.stereotype.Service
public class ExcelServiceImpl extends HttpServlet implements ExcelService {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelServiceImpl.class);

	private final Object lock = new Object();
	
	@Value("${gitlab.zipPath}")
    private String zipPath;

	public JSONArray getJSONArrayFromExcel(Workbook excelWorkBook, List<ExcelFieldVO> columns) {
		JSONArray jsonArray = new JSONArray();
		try {
			Map<String, String> headerFieldMap = new HashMap<String, String>();
			for (ExcelFieldVO excelFieldVO : columns) {
				headerFieldMap.put(excelFieldVO.getHeaderName(), excelFieldVO.getFieldName());
			}

			Sheet sheet = excelWorkBook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.rowIterator();

			int rowNum = 0;
			Map<Integer, String> columnIndexMap = new HashMap<Integer, String>();
			while (rowIterator.hasNext()) {
				JSONObject jsonObject = new JSONObject();
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				int columnIndex = 0;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					Object cellValue = ExcelUtils.getInstance().printCellValue(cell);

					if (rowNum == 0) {
						if (headerFieldMap.containsKey(cell.getRichStringCellValue().getString())) {
							columnIndexMap.put(columnIndex,
									headerFieldMap.get(cell.getRichStringCellValue().getString()));
						} else {
							columnIndexMap.put(columnIndex, cell.getRichStringCellValue().getString());
						}
					}
					jsonObject.put(columnIndexMap.get(columnIndex), cellValue);
					columnIndex++;
				}

				// put to json array if not header row
				if (rowNum > 0) {
					jsonArray.put(jsonObject);
				}

				rowNum++;
			}
			excelWorkBook.close();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
		return jsonArray;
	}

	public void renderExcel(List<?> list, List<ExcelFieldVO> columns, HttpServletResponse res, String excelName,
                            boolean autoResizeColumn) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet(StringUtils.isEmpty(excelName) ? "Sheet1" : excelName);
		sheet.setAutobreaks(true);
		sheet.setDefaultColumnWidth(30);
		HSSFRow rowHead = sheet.createRow((short) 0);
		Cell term;
		HSSFCellStyle numberStyleDefault = ExcelUtils.getInstance().numberStyle(hwb);
		HSSFCellStyle stringStyleDefault = ExcelUtils.getInstance().stringStyle(hwb);
		HSSFCellStyle dateStyleDefault = ExcelUtils.getInstance().dateStyle(hwb);
		HSSFCellStyle numberStyleFormat = null;
		HSSFCellStyle dateStyleFormat = null;
		HSSFCellStyle stringStyleFormat = null;
		HSSFCellStyle headerStyleDefault = ExcelUtils.getInstance().headerStyle(hwb);

		for (int i = 0; i < columns.size(); i++) {
			term = rowHead.createCell(i);
			term.setCellValue(columns.get(i).getHeaderName());
			term.setCellStyle(headerStyleDefault);
		}

		HSSFRow row;
		HSSFCell cell;
		try {
			Object object;
			Field field;
			Object objCellValue;
			// String dataType;
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(i + 1);
				object = list.get(i);
				for (int j = 0; j < columns.size(); j++) {
					cell = row.createCell(j);
					field = object.getClass().getDeclaredField(columns.get(j).getFieldName());
					field.setAccessible(true);
					objCellValue = field.get(object);
					// dataType = field.getType().getName();
					if (columns.get(j).getDataType() == ExcelFieldVO.DataType.INTEGER
							|| columns.get(j).getDataType() == ExcelFieldVO.DataType.DOUBLE
							|| columns.get(j).getDataType() == ExcelFieldVO.DataType.LONG) {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && numberStyleFormat == null) {
								numberStyleFormat = ExcelUtils.getInstance().numberStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(numberStyleFormat);
						} else {
							cell.setCellStyle(numberStyleDefault);
						}
						if (objCellValue == null || NumberUtils.parseDouble(objCellValue.toString()) == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(NumberUtils.parseDouble(objCellValue.toString()));
						}
					} else if (columns.get(j).getDataType() == ExcelFieldVO.DataType.DATE) {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && dateStyleFormat == null) {
								dateStyleFormat = ExcelUtils.getInstance().dateStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(dateStyleFormat);
						} else {
							cell.setCellStyle(dateStyleDefault);
						}
						if (objCellValue == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue((Date) objCellValue);
						}
					} else {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && stringStyleFormat == null) {
								stringStyleFormat = ExcelUtils.getInstance().stringStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(stringStyleFormat);
						} else {
							cell.setCellStyle(stringStyleDefault);
						}
						cell.setCellValue(objCellValue == null ? "" : objCellValue.toString());
					}

					// Break down with wrap text cell
					if (objCellValue != null && objCellValue.toString().contains(Constants.BREAK_DOWN)) {
						String[] st = objCellValue.toString().split(Constants.BREAK_DOWN);
						row.setHeightInPoints(((st.length) * sheet.getDefaultRowHeightInPoints()));
					}
				}
			}
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			LOGGER.error("renderExcel createCell error: " + e.getMessage());
			e.printStackTrace();
		}

		// set auto size
		if (autoResizeColumn) {
			int w;
			int maxColumnWidth = 255 * 50;
			for (int i = 0; i < columns.size(); i++) {
				sheet.autoSizeColumn(i);
				// The maximum column width
				// for an individual cell is 255 characters
				w = (int) (sheet.getColumnWidth(i) * 1.3);
				if (w > maxColumnWidth) {
					w = maxColumnWidth;
				}
				sheet.setColumnWidth(i, (int) (w));
			}
		}

		// save
		res.setContentType("application/x-ms-excel");
//		res.setContentType("application/x-felix; charset=us-ascii");
//		res.setHeader("Content-Transfer-Encoding", "7bit");
		res.setHeader("Content-Disposition",
				"attachment; filename=" + (StringUtils.isEmpty(excelName) ? "Export" : excelName) + ".xls");
		try {
			OutputStream os = res.getOutputStream();
			hwb.write(os);
			os.close();

			hwb.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("renderExcel() export file exception: " + ex.getMessage());
		}
	}

	public void renderExcelViolation(List<?> list, List<ExcelFieldVO> columns, HttpServletResponse res, String excelName,
                                     boolean autoResizeColumn, String ipName, String scanDate) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet(StringUtils.isEmpty(excelName) ? "Sheet1" : excelName);
		sheet.setAutobreaks(true);
		sheet.setDefaultColumnWidth(30);

		HSSFRow rowhead1 = sheet.createRow((short)0);
		rowhead1.createCell(0).setCellValue("IP Name: ");
		rowhead1.createCell(1).setCellValue(ipName);
		HSSFRow rowhead2 = sheet.createRow((short)1);
		rowhead2.createCell(0).setCellValue("Scan Time: ");
		rowhead2.createCell(1).setCellValue(scanDate);

		HSSFRow rowHead = sheet.createRow((short)4);

		Cell term;
		HSSFCellStyle numberStyleDefault = ExcelUtils.getInstance().numberStyle(hwb);
		HSSFCellStyle stringStyleDefault = ExcelUtils.getInstance().stringStyle(hwb);
		HSSFCellStyle dateStyleDefault = ExcelUtils.getInstance().dateStyle(hwb);
		HSSFCellStyle numberStyleFormat = null;
		HSSFCellStyle dateStyleFormat = null;
		HSSFCellStyle stringStyleFormat = null;
		HSSFCellStyle headerStyleDefault = ExcelUtils.getInstance().headerStyle(hwb);

		for (int i = 0; i < columns.size(); i++) {
			term = rowHead.createCell(i);
			term.setCellValue(columns.get(i).getHeaderName());
			term.setCellStyle(headerStyleDefault);
		}

		HSSFRow row;
		HSSFCell cell;
		try {
			Object object;
			Field field;
			Object objCellValue;
			// String dataType;
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(i + 5);
				object = list.get(i);
				for (int j = 0; j < columns.size(); j++) {
					cell = row.createCell(j);
					field = object.getClass().getDeclaredField(columns.get(j).getFieldName());
					field.setAccessible(true);
					objCellValue = field.get(object);
					// dataType = field.getType().getName();
					if (columns.get(j).getDataType() == ExcelFieldVO.DataType.INTEGER
							|| columns.get(j).getDataType() == ExcelFieldVO.DataType.DOUBLE
							|| columns.get(j).getDataType() == ExcelFieldVO.DataType.LONG) {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && numberStyleFormat == null) {
								numberStyleFormat = ExcelUtils.getInstance().numberStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(numberStyleFormat);
						} else {
							cell.setCellStyle(numberStyleDefault);
						}
						if (objCellValue == null || NumberUtils.parseDouble(objCellValue.toString()) == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(NumberUtils.parseDouble(objCellValue.toString()));
						}
					} else if (columns.get(j).getDataType() == ExcelFieldVO.DataType.DATE) {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && dateStyleFormat == null) {
								dateStyleFormat = ExcelUtils.getInstance().dateStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(dateStyleFormat);
						} else {
							cell.setCellStyle(dateStyleDefault);
						}
						if (objCellValue == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue((Date) objCellValue);
						}
					} else {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && stringStyleFormat == null) {
								stringStyleFormat = ExcelUtils.getInstance().stringStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(stringStyleFormat);
						} else {
							cell.setCellStyle(stringStyleDefault);
						}
						cell.setCellValue(objCellValue == null ? "" : objCellValue.toString());
					}

					// Break down with wrap text cell
					if (objCellValue != null && objCellValue.toString().contains(Constants.BREAK_DOWN)) {
						String[] st = objCellValue.toString().split(Constants.BREAK_DOWN);
						row.setHeightInPoints(((st.length) * sheet.getDefaultRowHeightInPoints()));
					}
				}
			}
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			LOGGER.error("renderExcel createCell error: " + e.getMessage());
			e.printStackTrace();
		}

		// set auto size
		if (autoResizeColumn) {
			int w;
			int maxColumnWidth = 255 * 50;
			for (int i = 0; i < columns.size(); i++) {
				sheet.autoSizeColumn(i);
				// The maximum column width
				// for an individual cell is 255 characters
				w = (int) (sheet.getColumnWidth(i) * 1.3);
				if (w > maxColumnWidth) {
					w = maxColumnWidth;
				}
				sheet.setColumnWidth(i, (int) (w));
			}
		}

		// save
		res.setContentType("application/x-ms-excel");
		res.setHeader("Content-Disposition",
				"attachment; filename=" + (StringUtils.isEmpty(excelName) ? "Export" : excelName) + ".xls");
		try {
			OutputStream os = res.getOutputStream();
			hwb.write(os);
			os.close();

			hwb.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("renderExcel() export file exception: " + ex.getMessage());
		}
	}

	@Override
	public void renderSheet(List<?> list, List<ExcelFieldVO> columns, HttpServletResponse res, String excelName,
                            boolean autoResizeColumn, HSSFWorkbook hwb) {
		HSSFSheet sheet = hwb.createSheet(StringUtils.isEmpty(excelName) ? "Sheet1" : excelName);
		sheet.setAutobreaks(true);
		sheet.setDefaultColumnWidth(30);
		HSSFRow rowHead = sheet.createRow((short) 0);
		Cell term;
		HSSFCellStyle numberStyleDefault = ExcelUtils.getInstance().numberStyle(hwb);
		HSSFCellStyle stringStyleDefault = ExcelUtils.getInstance().stringStyle(hwb);
		HSSFCellStyle dateStyleDefault = ExcelUtils.getInstance().dateStyle(hwb);
		HSSFCellStyle numberStyleFormat = null;
		HSSFCellStyle dateStyleFormat = null;
		HSSFCellStyle stringStyleFormat = null;
		HSSFCellStyle headerStyleDefault = ExcelUtils.getInstance().headerStyle(hwb);

		for (int i = 0; i < columns.size(); i++) {
			term = rowHead.createCell(i);
			term.setCellValue(columns.get(i).getHeaderName());
			term.setCellStyle(headerStyleDefault);
		}

		HSSFRow row;
		HSSFCell cell;
		try {
			Object object;
			Field field;
			Object objCellValue;
			// String dataType;
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(i + 1);
				object = list.get(i);
				for (int j = 0; j < columns.size(); j++) {
					cell = row.createCell(j);
					field = object.getClass().getDeclaredField(columns.get(j).getFieldName());
					field.setAccessible(true);
					objCellValue = field.get(object);
					// dataType = field.getType().getName();
					if (columns.get(j).getDataType() == ExcelFieldVO.DataType.INTEGER
							|| columns.get(j).getDataType() == ExcelFieldVO.DataType.DOUBLE
							|| columns.get(j).getDataType() == ExcelFieldVO.DataType.LONG) {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && numberStyleFormat == null) {
								numberStyleFormat = ExcelUtils.getInstance().numberStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(numberStyleFormat);
						} else {
							cell.setCellStyle(numberStyleDefault);
						}
						if (objCellValue == null || NumberUtils.parseDouble(objCellValue.toString()) == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(NumberUtils.parseDouble(objCellValue.toString()));
						}
					} else if (columns.get(j).getDataType() == ExcelFieldVO.DataType.DATE) {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && dateStyleFormat == null) {
								dateStyleFormat = ExcelUtils.getInstance().dateStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(dateStyleFormat);
						} else {
							cell.setCellStyle(dateStyleDefault);
						}
						if (objCellValue == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue((Date) objCellValue);
						}
					} else {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && stringStyleFormat == null) {
								stringStyleFormat = ExcelUtils.getInstance().stringStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(stringStyleFormat);
						} else {
							cell.setCellStyle(stringStyleDefault);
						}
						cell.setCellValue(objCellValue == null ? "" : objCellValue.toString());
					}

					// Break down with wrap text cell
					if (objCellValue != null && objCellValue.toString().contains(Constants.BREAK_DOWN)) {
						String[] st = objCellValue.toString().split(Constants.BREAK_DOWN);
						row.setHeightInPoints(((st.length) * sheet.getDefaultRowHeightInPoints()));
					}
				}
			}
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			LOGGER.error("renderExcel createCell error: " + e.getMessage());
			e.printStackTrace();
		}

		// set auto size
		if (autoResizeColumn) {
			int w;
			int maxColumnWidth = 255 * 50;
			for (int i = 0; i < columns.size(); i++) {
				sheet.autoSizeColumn(i);
				// The maximum column width
				// for an individual cell is 255 characters
				w = (int) (sheet.getColumnWidth(i) * 1.3);
				if (w > maxColumnWidth) {
					w = maxColumnWidth;
				}
				sheet.setColumnWidth(i, (int) (w));
			}
		}

		// save

		try {
//			OutputStream os = res.getOutputStream();
//			hwb.write(os);
//			os.close();
//
//			hwb.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("renderExcel() export file exception: " + ex.getMessage());
		}
	}

	public void renderExcel(String stringJsonArray, List<ExcelFieldVO> columns, HttpServletResponse res,
			String excelName) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet(StringUtils.isEmpty(excelName) ? "Sheet1" : excelName);
		sheet.setAutobreaks(true);
		sheet.setDefaultColumnWidth(30);
		HSSFRow rowHead = sheet.createRow((short) 0);
		Cell term;
		HSSFCellStyle numberStyleDefault = ExcelUtils.getInstance().numberStyle(hwb);
		HSSFCellStyle stringStyleDefault = ExcelUtils.getInstance().stringStyle(hwb);
		HSSFCellStyle dateStyleDefault = ExcelUtils.getInstance().dateStyle(hwb);
		HSSFCellStyle numberStyleFormat = null;
		HSSFCellStyle dateStyleFormat = null;
		HSSFCellStyle stringStyleFormat = null;
		HSSFCellStyle headerStyleDefault = ExcelUtils.getInstance().headerStyle(hwb);

		for (int i = 0; i < columns.size(); i++) {
			term = rowHead.createCell(i);
			term.setCellValue(columns.get(i).getHeaderName());
			term.setCellStyle(headerStyleDefault);
		}

		HSSFRow row;
		HSSFCell cell;
		try {
			Object object;
			Object objCellValue;
			JsonParser parser = new JsonParser();
			JsonElement tradeElement = parser.parse(stringJsonArray);
			JsonArray jsonArray = tradeElement.getAsJsonArray();
			for (int i = 0; i < jsonArray.size(); i++) {
				row = sheet.createRow(i + 1);
				object = jsonArray.get(i);
				// Convert Object to JsonObject
				JsonElement jsonElement = new Gson().toJsonTree(object, object.getClass());
				JsonObject jsonObject = (JsonObject) jsonElement;
				for (int j = 0; j < columns.size(); j++) {
					cell = row.createCell(j);
					// Get value from key member of JsonObject
					objCellValue = jsonObject.get(columns.get(j).getFieldName()).getAsString();
					if (columns.get(j).getDataType() == ExcelFieldVO.DataType.INTEGER
							|| columns.get(j).getDataType() == ExcelFieldVO.DataType.DOUBLE
							|| columns.get(j).getDataType() == ExcelFieldVO.DataType.LONG) {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && numberStyleFormat == null) {
								numberStyleFormat = ExcelUtils.getInstance().numberStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(numberStyleFormat);
						} else {
							cell.setCellStyle(numberStyleDefault);
						}
						if (objCellValue == null || NumberUtils.parseDouble(objCellValue.toString()) == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(NumberUtils.parseDouble(objCellValue.toString()));
						}
					} else if (columns.get(j).getDataType() == ExcelFieldVO.DataType.DATE) {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && dateStyleFormat == null) {
								dateStyleFormat = ExcelUtils.getInstance().dateStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(dateStyleFormat);
						} else {
							cell.setCellStyle(dateStyleDefault);
						}
						if (objCellValue == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue((Date) objCellValue);
						}
					} else {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && stringStyleFormat == null) {
								stringStyleFormat = ExcelUtils.getInstance().stringStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(stringStyleFormat);
						} else {
							cell.setCellStyle(stringStyleDefault);
						}
						cell.setCellValue(objCellValue == null ? "" : objCellValue.toString());
					}

					// Break down with wrap text cell
					if (objCellValue != null && objCellValue.toString().contains(Constants.BREAK_DOWN)) {
						String[] st = objCellValue.toString().split(Constants.BREAK_DOWN);
						row.setHeightInPoints(((st.length) * sheet.getDefaultRowHeightInPoints()));
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("renderExcelFromJsonObject createCell error: " + e.getMessage());
			e.printStackTrace();
		}

		// set auto size
		int w;
		int maxColumnWidth = 255 * 256;
		for (int i = 0; i < columns.size(); i++) {
			sheet.autoSizeColumn(i);
			// The maximum column width
			// for an individual cell is 255 characters
			w = (int) (sheet.getColumnWidth(i) * 1.3);
			if (w > maxColumnWidth) {
				w = maxColumnWidth;
			}
			sheet.setColumnWidth(i, (int) (w));
		}
		// save
		res.setContentType("application/x-ms-excel");
//		res.setContentType("application/x-felix; charset=us-ascii");
		res.setHeader("Content-Transfer-Encoding", "7bit");
		res.setHeader("Content-Disposition",
				"attachment; filename=\"" + (StringUtils.isEmpty(excelName) ? "Export" : excelName) + ".xls\"");
		try {
			OutputStream os = res.getOutputStream();
			hwb.write(os);
			os.close();
			hwb.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("renderExcelFromJsonObject() export file exception: " + ex.getMessage());
		}
	}

	@Override
	public void renderExcel(JsonArray jsonArray, List<ExcelFieldVO> columns, HttpServletResponse res,
			String excelName) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet(StringUtils.isEmpty(excelName) ? "Sheet1" : excelName);
		sheet.setAutobreaks(true);
		sheet.setDefaultColumnWidth(30);
		HSSFRow rowHead = sheet.createRow((short) 0);
		Cell term;
		HSSFCellStyle numberStyleDefault = ExcelUtils.getInstance().numberStyle(hwb);
		HSSFCellStyle stringStyleDefault = ExcelUtils.getInstance().stringStyle(hwb);
		HSSFCellStyle dateStyleDefault = ExcelUtils.getInstance().dateStyle(hwb);
		HSSFCellStyle numberStyleFormat = null;
		HSSFCellStyle dateStyleFormat = null;
		HSSFCellStyle stringStyleFormat = null;
		HSSFCellStyle headerStyleDefault = ExcelUtils.getInstance().headerStyle(hwb);

		for (int i = 0; i < columns.size(); i++) {
			term = rowHead.createCell(i);
			term.setCellValue(columns.get(i).getHeaderName());
			term.setCellStyle(headerStyleDefault);
		}

		HSSFRow row;
		HSSFCell cell;
		try {
			Object objCellValue;
			for (int i = 0; i < jsonArray.size(); i++) {
				row = sheet.createRow(i + 1);
				for (int j = 0; j < columns.size(); j++) {
					cell = row.createCell(j);
					// Get value at index i of JsonArray
					objCellValue = jsonArray.get(i).getAsJsonObject().get(columns.get(j).getFieldName()).getAsString();
					if (columns.get(j).getDataType() == ExcelFieldVO.DataType.INTEGER
							|| columns.get(j).getDataType() == ExcelFieldVO.DataType.DOUBLE
							|| columns.get(j).getDataType() == ExcelFieldVO.DataType.LONG) {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && numberStyleFormat == null) {
								numberStyleFormat = ExcelUtils.getInstance().numberStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(numberStyleFormat);
						} else {
							cell.setCellStyle(numberStyleDefault);
						}
						if (objCellValue == null || NumberUtils.parseDouble(objCellValue.toString()) == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(NumberUtils.parseDouble(objCellValue.toString()));
						}
					} else if (columns.get(j).getDataType() == ExcelFieldVO.DataType.DATE) {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && dateStyleFormat == null) {
								dateStyleFormat = ExcelUtils.getInstance().dateStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(dateStyleFormat);
						} else {
							cell.setCellStyle(dateStyleDefault);
						}
						if (objCellValue == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue((Date) objCellValue);
						}
					} else {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && stringStyleFormat == null) {
								stringStyleFormat = ExcelUtils.getInstance().stringStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(stringStyleFormat);
						} else {
							cell.setCellStyle(stringStyleDefault);
						}
						cell.setCellValue(objCellValue == null ? "" : objCellValue.toString());
					}

					// Break down with wrap text cell
					if (objCellValue != null && objCellValue.toString().contains(Constants.BREAK_DOWN)) {
						String[] st = objCellValue.toString().split(Constants.BREAK_DOWN);
						row.setHeightInPoints(((st.length) * sheet.getDefaultRowHeightInPoints()));
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("renderExcelFromJsonArray createCell error: " + e.getMessage());
			e.printStackTrace();
		}

		// set auto size
		int w;
		int maxColumnWidth = 255 * 256;
		for (int i = 0; i < columns.size(); i++) {
			sheet.autoSizeColumn(i);
			// The maximum column width
			// for an individual cell is 255 characters
			w = (int) (sheet.getColumnWidth(i) * 1.3);
			if (w > maxColumnWidth) {
				w = maxColumnWidth;
			}
			sheet.setColumnWidth(i, (int) (w));
		}
		// save
		res.setContentType("application/x-ms-excel");
//		res.setContentType("application/x-felix; charset=us-ascii");
		res.setHeader("Content-Transfer-Encoding", "7bit");
		res.setHeader("Content-Disposition",
				"attachment; filename=\"" + (StringUtils.isEmpty(excelName) ? "Export" : excelName) + ".xls\"");
		try {
			OutputStream os = res.getOutputStream();
			hwb.write(os);
			os.close();

			hwb.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("renderExcelFromJsonArray() export file exception: " + ex.getMessage());
		}
	}

	public List<?> getDataFromExcel(Workbook workbook, List<ExcelFieldVO> columns) {
		JSONArray jsonArray = getJSONArrayFromExcel(workbook, columns);
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, DeserializerUtils.getDateDeserializer()).create();
		Type listType = new TypeToken<List<?>>() {
		}.getType();
		if (jsonArray != null) {
			List<?> result = gson.fromJson(jsonArray.toString(), listType);
			return result;
		} else {
			return null;
		}

	}

	@Override
	public String renderExcel(List<?> list, List<ExcelFieldVO> columns, String excelName, boolean autoResizeColumn) {
		HSSFWorkbook hwb =new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet(StringUtils.isEmpty(excelName) ? "Sheet1" : excelName);
		sheet.setAutobreaks(true);
		sheet.setDefaultColumnWidth(30);
		HSSFRow rowHead = sheet.createRow((short) 0);
		Cell term;
		HSSFCellStyle numberStyleDefault = ExcelUtils.getInstance().numberStyle(hwb);
		HSSFCellStyle stringStyleDefault = ExcelUtils.getInstance().stringStyle(hwb);
		HSSFCellStyle dateStyleDefault = ExcelUtils.getInstance().dateStyle(hwb);
		HSSFCellStyle numberStyleFormat = null;
		HSSFCellStyle dateStyleFormat = null;
		HSSFCellStyle stringStyleFormat = null;
		HSSFCellStyle headerStyleDefault = ExcelUtils.getInstance().headerStyle(hwb);

		for (int i = 0; i < columns.size(); i++) {
			term = rowHead.createCell(i);
			term.setCellValue(columns.get(i).getHeaderName());
			term.setCellStyle(headerStyleDefault);
		}

		HSSFRow row;
		HSSFCell cell;
		try {
			Object object;
			Field field;
			Object objCellValue;
			// String dataType;
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(i + 1);
				object = list.get(i);
				for (int j = 0; j < columns.size(); j++) {
					cell = row.createCell(j);
					field = object.getClass().getDeclaredField(columns.get(j).getFieldName());
					field.setAccessible(true);
					objCellValue = field.get(object);
					// dataType = field.getType().getName();
					if (columns.get(j).getDataType() == ExcelFieldVO.DataType.INTEGER
							|| columns.get(j).getDataType() == ExcelFieldVO.DataType.DOUBLE
							|| columns.get(j).getDataType() == ExcelFieldVO.DataType.LONG) {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && numberStyleFormat == null) {
								numberStyleFormat = ExcelUtils.getInstance().numberStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(numberStyleFormat);
						} else {
							cell.setCellStyle(numberStyleDefault);
						}
						if (objCellValue == null || NumberUtils.parseDouble(objCellValue.toString()) == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(NumberUtils.parseDouble(objCellValue.toString()));
						}
					} else if (columns.get(j).getDataType() == ExcelFieldVO.DataType.DATE) {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && dateStyleFormat == null) {
								dateStyleFormat = ExcelUtils.getInstance().dateStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(dateStyleFormat);
						} else {
							cell.setCellStyle(dateStyleDefault);
						}
						if (objCellValue == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue((Date) objCellValue);
						}
					} else {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && stringStyleFormat == null) {
								stringStyleFormat = ExcelUtils.getInstance().stringStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(stringStyleFormat);
						} else {
							cell.setCellStyle(stringStyleDefault);
						}
						cell.setCellValue(objCellValue == null ? "" : objCellValue.toString());
					}

					// Break down with wrap text cell
					if (objCellValue != null && objCellValue.toString().contains(Constants.BREAK_DOWN)) {
						String[] st = objCellValue.toString().split(Constants.BREAK_DOWN);
						row.setHeightInPoints(((st.length) * sheet.getDefaultRowHeightInPoints()));
					}
				}
			}
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			LOGGER.error("renderExcel createCell error: " + e.getMessage());
			e.printStackTrace();
		}

		// set auto size
		if(autoResizeColumn) {
			int w;
			int maxColumnWidth = 255 * 50;
			for (int i = 0; i < columns.size(); i++) {
				sheet.autoSizeColumn(i);
				// The maximum column width
				// for an individual cell is 255 characters
				w = (int) (sheet.getColumnWidth(i) * 1.3);
				if (w > maxColumnWidth) {
					w = maxColumnWidth;
				}
				sheet.setColumnWidth(i, (int) (w));
			}
		}
		
		// save
		  File pathDir = new File(zipPath);
	        if (!pathDir.exists()) {
	        	pathDir.mkdir();
	        }
	        File bestip = new File(pathDir, "Best IP");
	        if (!bestip.exists()) {
	        	bestip.mkdir();
	        }
	        File output = new File(bestip, excelName);
	        if (!output.exists()) {
	            output.mkdir();
	        }
	        File path = output.toPath().resolve(excelName).toFile();
	        
	        String filePath = path+"_BestIP_Excel.xls";
	        File file = new File(filePath);
	            try {
					FileDeleteStrategy.FORCE.delete(file);
				} catch (IOException e) {
					LOGGER.error("File exists" + e.getMessage());
	        } 
		try (FileOutputStream outputStream = new FileOutputStream(filePath)){
			hwb.write(outputStream);
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("renderExcel() export file exception: " + ex.getMessage());
		}
		return filePath;
		
	}

	@Override
	public void renderSheet(List<?> list, List<ExcelFieldVO> columns, String excelName, boolean autoResizeColumn,
			HSSFWorkbook hwb) {
		HSSFSheet sheet = hwb.createSheet(StringUtils.isEmpty(excelName) ? "Sheet1" : excelName);
		sheet.setAutobreaks(true);
		sheet.setDefaultColumnWidth(30);
		HSSFRow rowHead = sheet.createRow((short) 0);
		Cell term;
		HSSFCellStyle numberStyleDefault = ExcelUtils.getInstance().numberStyle(hwb);
		HSSFCellStyle stringStyleDefault = ExcelUtils.getInstance().stringStyle(hwb);
		HSSFCellStyle dateStyleDefault = ExcelUtils.getInstance().dateStyle(hwb);
		HSSFCellStyle numberStyleFormat = null;
		HSSFCellStyle dateStyleFormat = null;
		HSSFCellStyle stringStyleFormat = null;
		HSSFCellStyle headerStyleDefault = ExcelUtils.getInstance().headerStyle(hwb);

		for (int i = 0; i < columns.size(); i++) {
			term = rowHead.createCell(i);
			term.setCellValue(columns.get(i).getHeaderName());
			term.setCellStyle(headerStyleDefault);
		}

		HSSFRow row;
		HSSFCell cell;
		try {
			Object object;
			Field field;
			Object objCellValue;
			// String dataType;
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(i + 1);
				object = list.get(i);
				for (int j = 0; j < columns.size(); j++) {
					cell = row.createCell(j);
					field = object.getClass().getDeclaredField(columns.get(j).getFieldName());
					field.setAccessible(true);
					objCellValue = field.get(object);
					// dataType = field.getType().getName();
					if (columns.get(j).getDataType() == ExcelFieldVO.DataType.INTEGER
							|| columns.get(j).getDataType() == ExcelFieldVO.DataType.DOUBLE
							|| columns.get(j).getDataType() == ExcelFieldVO.DataType.LONG) {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && numberStyleFormat == null) {
								numberStyleFormat = ExcelUtils.getInstance().numberStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(numberStyleFormat);
						} else {
							cell.setCellStyle(numberStyleDefault);
						}
						if (objCellValue == null || NumberUtils.parseDouble(objCellValue.toString()) == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(NumberUtils.parseDouble(objCellValue.toString()));
						}
					} else if (columns.get(j).getDataType() == ExcelFieldVO.DataType.DATE) {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && dateStyleFormat == null) {
								dateStyleFormat = ExcelUtils.getInstance().dateStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(dateStyleFormat);
						} else {
							cell.setCellStyle(dateStyleDefault);
						}
						if (objCellValue == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue((Date) objCellValue);
						}
					} else {
						if (StringUtils.isNotEmpty(columns.get(j).getDataFormat())) {
							if (i == 0 && stringStyleFormat == null) {
								stringStyleFormat = ExcelUtils.getInstance().stringStyle(hwb,
										columns.get(j).getDataFormat());
							}
							cell.setCellStyle(stringStyleFormat);
						} else {
							cell.setCellStyle(stringStyleDefault);
						}
						cell.setCellValue(objCellValue == null ? "" : objCellValue.toString());
					}

					// Break down with wrap text cell
					if (objCellValue != null && objCellValue.toString().contains(Constants.BREAK_DOWN)) {
						String[] st = objCellValue.toString().split(Constants.BREAK_DOWN);
						row.setHeightInPoints(((st.length) * sheet.getDefaultRowHeightInPoints()));
					}
				}
			}
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			LOGGER.error("renderExcel createCell error: " + e.getMessage());
			e.printStackTrace();
		}

		// set auto size
		if (autoResizeColumn) {
			int w;
			int maxColumnWidth = 255 * 50;
			for (int i = 0; i < columns.size(); i++) {
				sheet.autoSizeColumn(i);
				// The maximum column width
				// for an individual cell is 255 characters
				w = (int) (sheet.getColumnWidth(i) * 1.3);
				if (w > maxColumnWidth) {
					w = maxColumnWidth;
				}
				sheet.setColumnWidth(i, (int) (w));
			}
		}
	}

}
