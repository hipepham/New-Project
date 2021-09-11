package com.hipepham.springboot.common.excel.utility;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);

	private static ExcelUtils instance = null;

	private ExcelUtils(){

	}

	public static synchronized ExcelUtils getInstance(){
		if(instance == null){
			instance = new ExcelUtils();
		}
		return instance;
	}
	
	/**
	 * Header style of excel export.
	 *
	 * @return
	 */
	public HSSFCellStyle headerStyle(HSSFWorkbook hwb) {
		if (hwb == null) {
			hwb = new HSSFWorkbook();
		}
		// Header Cell Style
		HSSFCellStyle cellStyle = hwb.createCellStyle();
		try {
			HSSFPalette palette = hwb.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 250, (byte) 192, (byte) 144);
			// Set Header Style
			HSSFFont font = hwb.createFont();
			font.setBold(true);
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			if(palette.getColor(HSSFColor.ORANGE.index) != null)
				cellStyle.setFillForegroundColor(palette.getColor(HSSFColor.ORANGE.index).getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setFont(font);
			lineBorder(hwb, cellStyle);
			hwb.close();
		} catch (Exception ex) {
			LOGGER.error("ExcelUtils headerStyle() error: " + ex.getMessage());
		}
		return cellStyle;
	}

	/**
	 * String style.
	 *
	 * @param params
	 * @return
	 */
	public HSSFCellStyle stringStyle(HSSFWorkbook hwb, String... params) {
		if (hwb == null) {
			hwb = new HSSFWorkbook();
		}
		HSSFCellStyle cellStyle = hwb.createCellStyle();
		try {
			CreationHelper createHelper = hwb.getCreationHelper();
			if (params != null && params.length > 0) {
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(params[0]));
			} 
			// default value
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			lineBorder(hwb, cellStyle);
			hwb.close();
		} catch (Exception ex) {
			LOGGER.error("ExcelUtils stringStyle() error: " + ex.getMessage());
		}
		return cellStyle;
	}

	/**
	 * Number style.
	 *
	 * @param params
	 * @return
	 */
	public HSSFCellStyle numberStyle(HSSFWorkbook hwb, String... params) {
		if (hwb == null) {
			hwb = new HSSFWorkbook();
		}
		HSSFCellStyle cellStyle = hwb.createCellStyle();
		try {
			CreationHelper createHelper = hwb.getCreationHelper();
			if (params != null && params.length > 0) {
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(params[0]));
			}
			cellStyle.setAlignment(HorizontalAlignment.RIGHT);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			lineBorder(hwb, cellStyle);
		} catch (Exception ex) {
			LOGGER.error("ExcelUtils numberStyle() error: " + ex.getMessage());
		}
		return cellStyle;
	}

	/**
	 * Date style.
	 *
	 * @param params
	 * @return
	 */
	public HSSFCellStyle dateStyle(HSSFWorkbook hwb, String... params) {
		if (hwb == null) {
			hwb = new HSSFWorkbook();
		}
		HSSFCellStyle cellStyle = hwb.createCellStyle();
		try {
			CreationHelper createHelper = hwb.getCreationHelper();
			if (params != null && params.length > 0) {
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(params[0]));
			}
			lineBorder(hwb, cellStyle);
		} catch (Exception ex) {
			LOGGER.error("ExcelUtils dateStyle() error: " + ex.getMessage());
		}
		return cellStyle;
	}

	/**
	 * Line border of cell.
	 *
	 * @param cellStyle
	 * @return
	 */
	private HSSFCellStyle lineBorder(HSSFWorkbook hwb, HSSFCellStyle cellStyle) {
		if (cellStyle == null) {
			cellStyle = hwb.createCellStyle();
		}
		cellStyle.setBorderBottom(BorderStyle.THIN); // single line border
		cellStyle.setBorderLeft(BorderStyle.THIN); // single line border
		cellStyle.setBorderRight(BorderStyle.THIN); // single line border
		cellStyle.setBorderTop(BorderStyle.THIN); // single line border
		return cellStyle;
	}
	
	public Object printCellValue(Cell cell) {
	    Object result;
		switch (cell.getCellTypeEnum()) {
	        case BOOLEAN:
	        	result = cell.getBooleanCellValue();
	            break;
	        case STRING:
	        	result = cell.getRichStringCellValue().getString();
	            break;
	        case NUMERIC:
	            if (DateUtil.isCellDateFormatted(cell)) {
	            	result = cell.getDateCellValue();
	            } else {
	            	result = cell.getNumericCellValue();
	            }
	            break;
	        case FORMULA:
	        	result = cell.getCellFormula();
	            break;
	        case BLANK:
	        	result = "";
	            break;
	        default:
	        	result = "";
	    }
		return result;
	}
}
