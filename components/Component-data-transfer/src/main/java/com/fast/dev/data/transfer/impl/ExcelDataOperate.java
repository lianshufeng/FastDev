package com.fast.dev.data.transfer.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fast.dev.data.transfer.DataOperate;
import com.fast.dev.data.transfer.model.DataItem;
import com.fast.dev.data.transfer.model.FieldInformation;
import com.fast.dev.data.transfer.model.FileOption;

import jxl.Workbook;
import jxl.biff.EmptyCell;
import jxl.format.Colour;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * excel的数据读取功能
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年2月12日
 *
 */
public class ExcelDataOperate extends DataOperate {

	// 对应类型
	private static final Map<Class<?>, Class<? extends WritableCell>> CellType = new HashMap<Class<?>, Class<? extends WritableCell>>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			// 数字类型
			put(int.class, Number.class);
			put(Integer.class, Number.class);
			put(Long.class, Number.class);
			put(long.class, Number.class);
			put(float.class, Number.class);
			put(Float.class, Number.class);
			put(double.class, Number.class);
			put(Double.class, Number.class);

			// 字符串
			put(String.class, Label.class);
			put(StringBuffer.class, Label.class);
			put(StringBuilder.class, Label.class);

			// 日期格式
			put(Date.class, DateTime.class);

			// 空类型
			put(Void.class, EmptyCell.class);
			put(null, EmptyCell.class);

		}

	};

	/**
	 * 通过覆写该功能获取指定的文件描述
	 * 
	 * @return
	 */
	public FileOption getFileOption(String version) {
		return null;
	}

	@Override
	public boolean write(OutputStream outputStream, List<DataItem> datas, FileOption option) {
		try {
			// 工作表格
			WritableWorkbook workbook = makeWorkbook(outputStream);
			// 生成工作簿
			WritableSheet sheet = makeSheets(workbook, option);
			// 写入数据
			writeDataItems(sheet, datas);
			// 推送流
			pushStream(workbook);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<DataItem> read(InputStream inputStream) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 创建单元格
	 * 
	 * @param item
	 * @return
	 */
	private WritableCell makeCell(Object item, int c, int r) {
		Class<?> cls = CellType.get(item.getClass());
		if (cls == Number.class) {
			return new Number(c, r, Double.parseDouble(String.valueOf(item)));
		}
		if (cls == EmptyCell.class) {
			return new EmptyCell(c, r);
		}
		if (cls == Label.class) {
			return new Label(c, r, String.valueOf(item));
		}
		if (cls == DateTime.class) {
			return new DateTime(c, r, (Date) item);
		}
		return new Label(c, r, String.valueOf(item));
	}

	/**
	 * 写工作簿数据
	 * 
	 * @param writableSheet
	 * @param datas
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	private void writeDataItems(WritableSheet writableSheet, List<DataItem> datas)
			throws RowsExceededException, WriteException {
		if (datas == null || datas.size() == 0) {
			return;
		}
		// 判断是否含有标题
		int indexOffSet = (writableSheet.getColumns() == 0) ? 0 : 1;
		for (int i = 0; i < datas.size(); i++) {
			DataItem dataItem = datas.get(i);
			for (int j = 0; j < dataItem.size(); j++) {
				Object item = dataItem.get(j);
				writableSheet.addCell(makeCell(item, j, i + indexOffSet));
			}
		}
	}

	/**
	 * 生成工作簿
	 * 
	 * @param writableWorkbook
	 * @param option
	 * @throws WriteException
	 */
	private WritableSheet makeSheets(WritableWorkbook workbook, FileOption option) throws WriteException {
		// 删除之前所有的空的工作簿
		for (int i = 0; i < workbook.getSheetNames().length; i++) {
			workbook.removeSheet(i);
		}
		if (option == null) {
			return workbook.createSheet("Sheet1", 0);
		}

		// 生成指定的版本号
		WritableSheet sheet = workbook.createSheet(option.getVersion(), 0);
		// 字段信息
		List<FieldInformation> fieldInformations = option.getFields();
		// 生成指定的数据格式
		for (int i = 0; i < fieldInformations.size(); i++) {
			FieldInformation info = fieldInformations.get(i);
			// 设置字体加粗
			// WritableFont font = new WritableFont(WritableFont.ARIAL, 10,
			// WritableFont.BOLD);
			// WritableCellFormat format = new WritableCellFormat(font);
			WritableCellFormat format = new WritableCellFormat();
			// 背景颜色
			format.setBackground(Colour.GRAY_25);
			// 设置自动换行
			format.setWrap(true);
			Label label = new Label(i, 0, info.getName(), format);
			sheet.addCell(label);
			// 设置单元格宽度
			sheet.setColumnView(i, info.getName().length() * 4 + 10);
		}
		return sheet;
	}

	/**
	 * 创建写出的表格
	 * 
	 * @param outputStream
	 * @return
	 * @throws IOException
	 */
	private WritableWorkbook makeWorkbook(OutputStream outputStream) throws IOException {
		return Workbook.createWorkbook(outputStream);
	}

	/**
	 * 推送流
	 * 
	 * @throws IOException
	 * @throws WriteException
	 */
	private void pushStream(WritableWorkbook workbook) throws IOException, WriteException {
		workbook.write();
		workbook.close();
	}

}
