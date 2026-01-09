/*
 * ===========================================================================
 * IBM Confidential
 * AIS Source Materials
 * 
 * 
 * (C) Copyright IBM Corp. 2016.
 *
 * ===========================================================================
 */
package com.cosmos.file.parser.handler;

import java.util.Collections;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Row;

import com.cosmos.code.CibErrorCode;
import com.cosmos.file.FileException;
import com.cosmos.file.FileFormatDetail;
import com.cosmos.file.FileFormatDetailComparator;
import com.cosmos.file.FileFormatNew;
import com.cosmos.file.bo.FileDetail;
import com.cosmos.file.bo.FileDoc;
import com.cosmos.file.bo.FileField;
import com.cosmos.file.bo.FileSection;
import com.cosmos.type.FieldGroup;
import com.cosmos.type.FieldType;
import com.ibm.tw.commons.util.NumericUtils;
import com.ibm.tw.commons.util.StringUtils;

/**
 * <p>
 * EXCEL 檔案PARSER
 * </p>
 * 
 * @author Hank
 * @version 1.0, 2016/12/30
 * @see
 * @since
 */
public class FileHandlerExcel extends FileHandlerBase {

	public FileHandlerExcel(FileFormatNew format) {
		this.format = format;
		this.detailGroup = format.getDetailGroup();
	}

	public FileDoc parse(List<Row> fileRecords) {

		fileDoc = new FileDoc();

		// 筆數
		int rows = fileRecords.size();

		// 資料起始列
		int startRow = format.getMaster().getStartRow() - 1;

		// 格式有尾筆，不算在明細資料內
		if (format.getMaster().getFileFooter() == 1) {
			rows -= 1;
		}

		for (int i = startRow; i < rows; i++) {

			Row row = fileRecords.get(i);

			// 行號
			int rowNo = i + 1;
			// 2016/1/22:因為新增可設定「資料起始列」，用rowNo來抓取format的方式要調整一下
			int formatRowNo = rowNo - startRow;

			// 欄位說明 Row, 不處理
			if (FieldGroup.COLUMNS == format.getFormatHead(formatRowNo)) {
				continue;
			}

			fileDoc.addDetail(parseDetail(rowNo, row, getFormatDetails(format.getFormatHead(formatRowNo)), getFormatDetails(format.getFormatCycle(formatRowNo))));

		}

		// 處理尾筆
		if (format.getMaster().getFileFooter() == 1) {
			Row row = fileRecords.get(rows);
			List<FileFormatDetail> footerFormats = getFormatDetails(format.getFormatFooter());
			if (footerFormats != null && footerFormats.size() > 0) {
				fileDoc.addDetail(parseFooter(rows + 1, row, footerFormats));
			}
		}

		return fileDoc;

	}

	/**
	 * 取得FormatDetails
	 * 
	 * @param fieldGroup
	 * @return
	 */
	private List<FileFormatDetail> getFormatDetails(FieldGroup fieldGroup) {
		if (fieldGroup == null) {
			return null;
		}

		return detailGroup.get(fieldGroup);
	}

	/**
	 * format排序
	 * 
	 * @param formatHead
	 * @param formatCycle
	 */
	private void sortFormat(List<FileFormatDetail> formatHead, List<FileFormatDetail> formatCycle) {
		if (formatHead != null && formatHead.size() > 0) {
			// 對formatHead排序
			Collections.sort(formatHead, new FileFormatDetailComparator());
		}

		if (formatCycle != null && formatCycle.size() > 0) {
			// 對formatCycle排序
			Collections.sort(formatCycle, new FileFormatDetailComparator());
		}
	}

	/**
	 * 計算格式長度
	 * 
	 * @param formatDetails
	 * @return
	 */
	private int getLength(List<FileFormatDetail> formatDetails) {
		int length = 0;

		if (formatDetails == null) {
			return length;
		}

		for (FileFormatDetail formatDetail : formatDetails) {
			length += formatDetail.getLength();
		}

		return length;
	}

	/**
	 * parse單行資料
	 * 
	 * 先parse起始格式 再parse循環格式
	 * 
	 * @param byteDetail
	 * @param formatHead
	 * @param formatCycle
	 * @return
	 */
	private FileDetail parseDetail(int rowNo, Row row, List<FileFormatDetail> formatHead, List<FileFormatDetail> formatCycle) {

		FileDetail fileDetail = new FileDetail();

		// format排序
		sortFormat(formatHead, formatCycle);

		// 行號
		fileDetail.setRowNo(rowNo);

		int sectionNo = 1;

		int formatRowNo = rowNo - (format.getMaster().getStartRow() - 1);
		FileSection sectionHead = parseSection(sectionNo++, row, formatHead);
		sectionHead.setFieldGroup(format.getFormatHead(formatRowNo));
		fileDetail.addSection(sectionHead);

		int cellNo = formatHead.size();

		// 以是否有循環格式資料，來判斷是否進入parsing循環格式
		if (formatCycle != null) {
			// TODO : parse循環格式(當循環資料第一個 cell 為空白時,表示已無資料)
			while (StringUtils.isNotBlank(getCellvalue(row, cellNo))) {

				FileSection sectionCycle = parseSection(sectionNo++, cellNo, row, formatCycle);
				sectionCycle.setFieldGroup(format.getFormatCycle(formatRowNo)); // 2016/1/25:
				// 改成formatRowNo
				fileDetail.addSection(sectionCycle);

				cellNo += formatCycle.size();
			}
		}
		return fileDetail;
	}

	/**
	 * parse 尾筆
	 * 
	 * @param byteFooter
	 * @return
	 */
	private FileDetail parseFooter(int rowNo, Row row, List<FileFormatDetail> formatFooter) {

		Collections.sort(formatFooter, new FileFormatDetailComparator());

		FileSection section = parseSection(1, row, formatFooter);
		section.setFieldGroup(format.getFormatFooter());

		FileDetail fileFooter = new FileDetail();
		// 行號
		fileFooter.setRowNo(rowNo);
		// 長度
		fileFooter.setLength(getLength(formatFooter));

		fileFooter.addSection(section);

		return fileFooter;
	}

	/**
	 * parse資料區段
	 * 
	 * @param sectionNo
	 * @param cellNo
	 * @param row
	 * @param formatSection
	 * @return
	 */
	private FileSection parseSection(int sectionNo, int cellNo, Row row, List<FileFormatDetail> formatSection) {

		FileSection fileSection = new FileSection();

		// 段號
		fileSection.setSectionNo(sectionNo);

		for (FileFormatDetail formatDetail : formatSection) {

			// 取得 Cell Data
			String value = getCellvalue(row, cellNo);

			FileField fileField = new FileField();

			// 欄號
			fileField.setColumnNo(formatDetail.getOrderNo());

			// 欄位Id
			fileField.setFieldId(formatDetail.getFieldId());

			// 欄位值
			fileField.setValue(value);

			// 2016/1/22: 新增日期格式，小數點格式
			fileField.setDateType(format.getMaster().getDateType());
			fileField.setAmtType(formatDetail.getDetail().getAmtType());
			fileField.setDecLength(formatDetail.getDetail().getDecLength());
			fileField.setTextType(formatDetail.getDetail().getTextType());
			fileField.setTextContent(formatDetail.getDetail().getTextContent());
			// 2017/5/25: 新增欄位定義長度
			fileField.setFieldLen(formatDetail.getLength());

			// 長度驗證
			if (value.getBytes().length > formatDetail.getLength()) {
				FileException ex = new FileException("長度超過", CibErrorCode.FIELD_LENGTH_NOT_MATCH);
				ex.setParam("FieldName", formatDetail.getDetail().getFieldName());
				fileField.setException(ex);
			}

			// TODO : 20160323
			FieldType fieldType = FieldType.valueOf(formatDetail.getField().getType());
			fileField.setFieldType(fieldType);
			if (fieldType == FieldType.INTEGER || fieldType == FieldType.DECIMAL) {
				fileField.setFieldType(FieldType.TEXT);
			}

			if (fileField.getException() == null) {
				if (fieldType == FieldType.INTEGER) {
					if (!NumericUtils.isDigits(value)) {
						fileField.setException(new FileException("整數格式錯誤", CibErrorCode.INTEGER_FORMAT_ERROR));
					}
				}
				if (fieldType == FieldType.DECIMAL) {
					if (!NumericUtils.isNumber(value)) {
						fileField.setException(new FileException("資料欄位不符", CibErrorCode.FILE_DATA_FIELD_NOT_MATCH));
					}
					else {
						int pos = StringUtils.indexOf(value, ".");
						if (pos >= 0) {
							int dotLen = value.length() - pos - 1;
							int decLen = formatDetail.getDetail().getDecLength();
							if (dotLen > decLen) {
								fileField.setException(new FileException("小數格式不符", CibErrorCode.DOT_FORMAT_ERROR));
							}
						}
					}
				}
			}

			fileSection.addField(fileField);

			cellNo++;

		}

		return fileSection;
	}

	/**
	 * parse資料區段
	 * 
	 * @param byteSection
	 * @param formatSection
	 * @return
	 */
	private FileSection parseSection(int sectionNo, Row row, List<FileFormatDetail> formatSection) {

		return parseSection(sectionNo, 0, row, formatSection);
	}

	/**
	 * 取得 Cell String Value *
	 * 
	 * @author hank
	 * @param row
	 * @param iPos
	 * @return
	 */
	private String getCellvalue(Row row, int iPos) {

		String sRtn = "";

		if (row.getCell(iPos) == null) {
			return "";
		}

		try {

			if (HSSFCell.CELL_TYPE_STRING == row.getCell(iPos).getCellType()) {
				sRtn = row.getCell(iPos).getStringCellValue();
			}
			else if (HSSFCell.CELL_TYPE_FORMULA == row.getCell(iPos).getCellType()) {
				sRtn = row.getCell(iPos).getRichStringCellValue().toString();
			}
			else if (HSSFCell.CELL_TYPE_BLANK == row.getCell(iPos).getCellType()) {
				sRtn = "";
			}
			else {
				String sDateFormat = row.getCell(iPos).getCellStyle().getDataFormatString();
				if ("yyyymmdd".equals(sDateFormat)) {
					sRtn = new java.text.SimpleDateFormat("yyyyMMdd").format(row.getCell(iPos).getDateCellValue());

				}
				else if ("m/d/yy".equals(sDateFormat)) {
					sRtn = new java.text.SimpleDateFormat("yyyyMMdd").format(row.getCell(iPos).getDateCellValue());
				}
				else {
					sRtn = String.valueOf(row.getCell(iPos).getNumericCellValue());
				}

				sRtn = reFormat(sRtn);

			}

		}
		catch (Exception e) {
			System.out.println("column at " + iPos + "," + e);
		}
		return sRtn;
	}

	/**
	 * 
	 * 去除小數後面的 .0 及 E
	 * 
	 * ex: reFormat("1000.0") => 1000 reFormat("1.211241E6") => 1211241
	 * 
	 * @author hank
	 * @param sSrc
	 * @return
	 */
	private String reFormat(String sSrc) {

		int iPos = sSrc.indexOf(".");
		int iPos2 = sSrc.indexOf("E");

		if (iPos > 0) {

			if (iPos2 > 0) {

				int iLen = Integer.parseInt(sSrc.substring(iPos2 + 1));
				String sStr = sSrc.substring(iPos + 1, iPos2);
				sStr = StringUtils.rightPad(sStr, iLen, "0");
				sSrc = sSrc.substring(0, iPos) + sStr;

			}
			else {
				int iValue = Integer.parseInt(sSrc.substring(iPos + 1));

				if (iValue == 0) {
					sSrc = sSrc.substring(0, iPos);
				}
			}

		}
		return sSrc;
	}

}
