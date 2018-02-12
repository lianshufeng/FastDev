package com.fast.dev.data.transfer.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fast.dev.data.transfer.DataOperate;
import com.fast.dev.data.transfer.model.DataItem;
import com.fast.dev.data.transfer.model.TableItem;

/**
 * excel的数据读取功能
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年2月12日
 *
 */
public class ExcelDataOperate extends DataOperate {

	@Override
	public boolean write(OutputStream outputStream, TableItem... tableItems) {
		try {
			Workbook workbook = new XSSFWorkbook();
			// 生成工作簿
			for (int i = 0; i < tableItems.length; i++) {
				makeSheet(workbook, tableItems[i]);
			}
			workbook.write(outputStream);
			workbook.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 生成工作簿
	 * 
	 * @param workbook
	 * @param tableItem
	 */
	private void makeSheet(Workbook workbook, TableItem tableItem) {
		// 创建工作簿
		Sheet sheet = workbook.createSheet(tableItem.getName());
		List<String> fields = tableItem.getFieldNames();
		// 生成标题
		Row headRow = sheet.createRow(0);
		for (int i = 0; i < fields.size(); i++) {
			String fieldName = fields.get(i);
			Cell cell = headRow.createCell(i);
			cell.setCellType(CellType.STRING);
			cell.setCellValue(fieldName);
			// 设置字体加粗
			CellStyle cellStyle = workbook.createCellStyle();
			// 设置背景颜色
			cellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			Font font = workbook.createFont();
			cellStyle.setFont(font);
			// 设置自动换行
			cellStyle.setWrapText(true);
			cell.setCellStyle(cellStyle);
			// 设置单元格宽度
			sheet.setColumnWidth(i, fieldName.length() * 1000);
		}
		// 生成内容
		List<DataItem> dataItems = tableItem.getDataItems();
		for (int i = 0; i < dataItems.size(); i++) {
			DataItem dataItem = dataItems.get(i);
			Row row = sheet.createRow(i + 1);
			for (int j = 0; j < dataItem.size(); j++) {
				Object o = dataItem.get(j);
				Cell cell = row.createCell(j);
				// 设置值
				setCellValue(cell, o);
			}
		}
	}

	/**
	 * 设置单元格值
	 * 
	 * @param cell
	 * @param o
	 */
	private void setCellValue(Cell cell, Object o) {
		if (o == null) {
			return;
		}
		if (o instanceof String) {
			cell.setCellType(CellType.STRING);
			cell.setCellValue(String.valueOf(o));
		} else if (o instanceof Number) {
			cell.setCellType(CellType.NUMERIC);
			cell.setCellValue(Double.parseDouble(String.valueOf(o)));
		} else if (o instanceof Date) {
			CellStyle cellStyle = cell.getSheet().getWorkbook().createCellStyle();
			cellStyle.setDataFormat((short) 14);
			cell.setCellStyle(cellStyle);
			cell.setCellValue((Date) o);
		} else if (o instanceof Boolean) {
			cell.setCellValue((boolean) o);
		} else {
			cell.setCellValue(String.valueOf(o));
		}
	}

	@Override
	public Map<String, DataItem[]> read(InputStream inputStream) {
		Map<String, DataItem[]> result = null;
		try {
			Workbook workbook = null;
			try {
				workbook = new XSSFWorkbook(inputStream);
			} catch (Exception e) {
				workbook = new HSSFWorkbook(inputStream);
			}
			try {
				// 循环读取所有的工作簿
				int sheetCount = workbook.getNumberOfSheets();
				result = new HashMap<String, DataItem[]>();
				for (int i = 0; i < sheetCount; i++) {
					Sheet sheet = workbook.getSheetAt(i);
					result.put(workbook.getSheetName(i), readSheet(sheet));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 关闭流
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * 读取工作簿
	 * 
	 * @param sheet
	 * 
	 * @return
	 */
	private DataItem[] readSheet(Sheet sheet) {
		int start = sheet.getFirstRowNum();
		List<DataItem> dataItems = new ArrayList<DataItem>();
		for (int i = start; i <= sheet.getLastRowNum(); i++) {
			DataItem dataItem = new DataItem();
			Row row = sheet.getRow(i);
			// 过滤空行
			if (row == null) {
				continue;
			}
			// 列数
			int startCellNum = row.getFirstCellNum();
			for (int j = startCellNum; j < row.getLastCellNum(); j++) {
				dataItem.add(cellToObject(row.getCell(j)));
			}
			dataItems.add(dataItem);
		}
		return dataItems.toArray(new DataItem[dataItems.size()]);

	}

	/**
	 * cell类型转换为java对象
	 * 
	 * @param cell
	 * @return
	 */
	private Object cellToObject(Cell cell) {
		if (cell == null) {
			return null;
		}
		Object o = null;
		switch (cell.getCellTypeEnum()) {
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				o = cell.getDateCellValue();
			} else {
				Double val = new Double(cell.getNumericCellValue());
				// 兼容科学计数法
				o = String.valueOf(val).indexOf("E") > -1 ? new BigDecimal(val) : val;
			}
			break;
		case STRING:
			o = cell.getStringCellValue();
			break;
		case BOOLEAN:
			o = cell.getBooleanCellValue();
			break;
		case BLANK:
			o = null;
			break;
		default:
			o = cell.getStringCellValue();
			break;
		}
		return o;
	}

}
