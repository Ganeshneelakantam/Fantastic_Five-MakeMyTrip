package com.makemytrip.utils;

import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

public class ExcelUtils {

	String fileName;
	FileInputStream fis;
	FileOutputStream fos;
	Workbook wb;
	Sheet sheet;
	CellStyle style;

	public ExcelUtils(String excelPathOrName) {
		try {
			// If the provided path is absolute or points under
			// src/test/resources/testdata/... just use it.
			File file = new File(excelPathOrName);
			if (!file.exists()) {
				// Try relative to project root (user.dir + provided relative path)
				file = new File(System.getProperty("user.dir"));
			}

			if (!file.exists()) {
				// Try classpath as last resort (use resource path without leading slash)
				String cp = excelPathOrName.startsWith("/") ? excelPathOrName.substring(1) : excelPathOrName;
				InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(cp);
				if (is == null) {
					// Also try only the filename portion (e.g., MakeMyTripData.xlsx) if a nested
					// path was given
					String justName = new File(excelPathOrName).getName();
					is = Thread.currentThread().getContextClassLoader().getResourceAsStream(justName);
				}
				if (is != null) {
					wb = new XSSFWorkbook(is);
					is.close();
					this.fileName = excelPathOrName;
					return;
				}
				throw new FileNotFoundException("Excel not found using path or classpath: " + excelPathOrName);
			}

			this.fileName = file.getAbsolutePath();
			fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
		} catch (IOException e) {
			throw new RuntimeException("Failed to open Excel: " + excelPathOrName, e);
		}
	}

	public int getRowCount(String sheetName) {
		// Return total number of rows in sheet
		sheet = wb.getSheet(sheetName);
		return sheet.getPhysicalNumberOfRows();
	}

	public int getColumnCount(String sheetName) {
		// Return total number of columns in first row
		sheet = wb.getSheet(sheetName);
		return sheet.getRow(0).getLastCellNum();
	}

	public String getCellData(String sheetName, int rownum, int columnum) {
		// Get cell data as string using DataFormatter
		sheet = wb.getSheet(sheetName);
		Cell cell = sheet.getRow(rownum).getCell(columnum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		try {
			return new DataFormatter().formatCellValue(cell);
		} catch (Exception e) {
			e.printStackTrace();
			return "Missing Data";
		}
	}

	public void setCellData(String sheetName, int rowIndex, int columnIndex, String data) throws IOException {
		// Write data to cell and apply styling
		sheet = wb.getSheet(sheetName);
		Row row = sheet.getRow(rowIndex);
		if (row == null)
			row = sheet.createRow(rowIndex);
		Cell cell = row.getCell(columnIndex);
		if (cell == null)
			cell = row.createCell(columnIndex);
		cell.setCellValue(data);

		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cell.setCellStyle(cellStyle);

		sheet.autoSizeColumn(columnIndex);
		fis.close();
		fos = new FileOutputStream(fileName);
		wb.write(fos);
		fos.close();
	}

	public void fillGreenColor(String sheetName, int rownum, int columnIndex) throws IOException {
		// Fill cell with green color
		fillColor(sheetName, rownum, columnIndex, IndexedColors.GREEN);
	}

	public void fillRedColor(String sheetName, int rownum, int columnIndex) throws IOException {
		// Fill cell with red color
		fillColor(sheetName, rownum, columnIndex, IndexedColors.RED);
	}

	private void fillColor(String sheetName, int rownum, int columnIndex, IndexedColors color) throws IOException {
		// Apply background color and styling to cell
		sheet = wb.getSheet(sheetName);
		Row row = sheet.getRow(rownum);
		if (row == null)
			row = sheet.createRow(rownum);
		Cell cell = row.getCell(columnIndex);
		if (cell == null)
			cell = row.createCell(columnIndex);

		style = wb.createCellStyle();
		style.setFillForegroundColor(color.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		cell.setCellStyle(style);

		fos = new FileOutputStream(fileName);
		wb.write(fos);
		fos.close();
	}

	public void validation(String sheetName, int rownum, int columnIndex, String actual, String expected) {
		// Validate actual vs expected and mark cell with PASS/FAIL
		try {
			Assert.assertEquals(actual, expected);
			setCellData(sheetName, rownum, columnIndex, "PASS");
			fillGreenColor(sheetName, rownum, columnIndex);
		} catch (Throwable e) {
			try {
				setCellData(sheetName, rownum, columnIndex, "FAIL");
				fillRedColor(sheetName, rownum, columnIndex);
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
			throw new AssertionError("Validation failed: " + e.getMessage());
		}
	}

	public void closeFile() {
		// Close workbook and output stream
		try {
			if (fos != null)
				fos.close();
			if (wb != null)
				wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
