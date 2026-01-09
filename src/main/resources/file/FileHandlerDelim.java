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

import java.io.IOException;
import java.util.Collections;
import java.util.List;

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
import com.ibm.tw.commons.exception.ActionException;
import com.ibm.tw.commons.util.ConvertUtils;
import com.ibm.tw.commons.util.StringUtils;

/**
 * <p>
 * 分隔符號的檔案PARSER
 * </p>
 * 
 * @author Ally
 * @version 1.0, 2016/1/13
 * @see
 * @since
 */
public class FileHandlerDelim extends FileHandlerBase {

	public static final String UTF8_BOM = "\uFEFF";

	public FileHandlerDelim(FileFormatNew format) {
		this.format = format;
		this.detailGroup = format.getDetailGroup();
	}

	public FileDoc parse(List<byte[]> fileRecords) {
		// TODO 自動產生方法 Stub
		fileDoc = new FileDoc();

		/** 資料筆數 **/
		int rows = fileRecords.size();

		// 2016/1/22: 資料起始列
		int startRow = format.getMaster().getStartRow() - 1;

		// 2016/1/22:格式有尾筆，不算在明細資料內
		if (format.getMaster().getFileFooter() == 1) {
			rows -= 1;
		}

		for (int i = startRow; i < rows; i++) {
			byte[] byteDetail = fileRecords.get(i);
			String txtDetail = ConvertUtils.bytes2Str(byteDetail, format.getENCODING());
			// 若有BOM，將其移除
			if (i == 0) {
				txtDetail = removeUTF8BOM(txtDetail);
			}

			// 行號
			int rowNo = i + 1;
			// 2016/1/22:因為新增可設定「資料起始列」，用rowNo來抓取format的方式要調整一下
			int formatRowNo = rowNo - startRow;

			FileDetail fileDetail = getFileDetail(rowNo, txtDetail, getFormatDetails(format.getFormatHead(formatRowNo)), getFormatDetails(format.getFormatCycle(formatRowNo)));
			fileDoc.addDetail(fileDetail);
		}

		// 處理尾筆
		if (format.getMaster().getFileFooter() == 1) {

			byte[] byteFooter = fileRecords.get(fileRecords.size() - 1);
			String txtFooter = ConvertUtils.bytes2Str(byteFooter, format.getENCODING());

			List<FileFormatDetail> formatFooter = getFormatDetails(format.getFormatFooter());

			if (formatFooter != null && formatFooter.size() > 0) {
				parseFooter(txtFooter, fileRecords.size(), formatFooter);
			}
		}

		return fileDoc;
	}

	/**
	 * parse 尾筆
	 * 
	 * @param byteFooter
	 * @return
	 */
	private FileDetail parseFooter(String txtFooter, int rowNo, List<FileFormatDetail> formatFooter) {

		FileDetail fileFooter = new FileDetail();

		Collections.sort(formatFooter, new FileFormatDetailComparator());

		try {

			// delim
			char delim = getDelim();
			String[] tokens = parseLine(txtFooter, delim);

			// 改檢查欄位數，不是長度~
			if (formatFooter.size() != tokens.length) {
				FileException exception = new FileException("長度不符", CibErrorCode.FILE_DATA_LENGTH_NOT_MATCH);
				// 行號
				fileFooter.setRowNo(rowNo);
				fileFooter.setException(exception);
			}
			else {
				FileSection section = parseSection(1, tokens, formatFooter);
				section.setFieldGroup(format.getFormatFooter());

				// 行號
				fileFooter.setRowNo(rowNo);
				// 長度
				fileFooter.setLength(tokens.length);

				fileFooter.addSection(section);
			}

		}
		catch (ActionException e) {
			// 行號
			fileFooter.setRowNo(rowNo);
			fileFooter.setException(e);
		}

		return fileFooter;
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
	 * Parse單筆
	 * 
	 * @param fileRecord
	 * @param formatDetails
	 * @return
	 */
	protected FileDetail getFileDetail(int rowNo, String txtDetail, List<FileFormatDetail> headFormat, List<FileFormatDetail> cycleFormat) {

		FileDetail fileDetail = null;

		// format排序
		sortFormat(headFormat, cycleFormat);
		try {
			// delim
			char delim = getDelim();
			String[] tokens = parseLine(txtDetail, delim);

			// 改檢查欄位數，不是長度~
			if (!checkLength(tokens, headFormat, cycleFormat)) {
				FileException exception = new FileException("長度不符", CibErrorCode.FILE_DATA_LENGTH_NOT_MATCH);
				fileDetail = new FileDetail();
				// 行號
				fileDetail.setRowNo(rowNo);
				fileDetail.setException(exception);
			}
			else {
				// parse資料
				fileDetail = parseDetail(rowNo, tokens, headFormat, cycleFormat);
			}
		}
		catch (ActionException e) {
			logger.debug("delim parse", e);
		}

		return fileDetail;
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
	 * 檢查長度
	 * 
	 * @param byteDetail
	 * @param formatHead
	 * @param formatCycle
	 * @return
	 */
	protected boolean checkLength(String[] tokens, List<FileFormatDetail> formatHead, List<FileFormatDetail> formatCycle) {
		int lengthDetail = tokens.length;
		// todo: 首尾的檢核好像不能這樣檢查....
		return checkLength(lengthDetail, formatHead, formatCycle);
		// return true;
	}

	/**
	 * 檢查長度(欄位數)
	 * 
	 * @param byteDetail
	 * @param formatHead
	 * @param formatCycle
	 * @return
	 */
	protected boolean checkLength(int lengthDetail, List<FileFormatDetail> formatHead, List<FileFormatDetail> formatCycle) {
		// 不含循環格式時，則檢核不小於格式設定所選欄位數即可
		// FieldGroup fieldGroup = formatHead.get(0).getFieldGroup();
		// if( fieldGroup.compareTo(FieldGroup.PAYER) == 0 ||
		// fieldGroup.compareTo(FieldGroup.FXPAYER) == 0){
		if (formatCycle == null) {
			if (lengthDetail >= formatHead.size()) {
				return true;
			}
			else {
				return false;
			}
		}

		// 若含循環格式，則可由主檔所填資訊，去檢查檔案內實際的欄位數是否合理
		int lengthHead = format.getMaster().getReceiptStart() - 1; // 陣列從0開始，所以減1
		int lengthCycle = format.getMaster().getReceiptLen();

		System.err.println("lengthDetail=" + lengthDetail);
		System.err.println("lengthHead=" + lengthHead);

		// 資料長度小於起始格式
		if (lengthDetail < lengthHead) {
			return false;
		}
		// 資料長度等於起始格式
		else if (lengthDetail == lengthHead) {
			return true;
		}
		// 資料長度大於起始格式
		else {
			lengthDetail = lengthDetail - lengthHead;

			// 檢查資料長度是否為循環格式的倍數

			// 無循環格式
			if (lengthCycle == 0) {
				// 有多餘資料長度
				return false;
			}
			// 有循環格式
			else {
				// 為循環格式倍數
				if (lengthDetail % lengthCycle == 0) {
					return true;
				}
				// 不為循環格式倍數
				else {
					return false;
				}
			}
		}
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
	private FileDetail parseDetail(int rowNo, String[] tokens, List<FileFormatDetail> formatHead, List<FileFormatDetail> formatCycle) {
		// todo:取得單據的起始位置與長度
		int lengthHead = format.getMaster().getReceiptStart() - 1; // 陣列從0開始，所以減1
		int lengthCycle = format.getMaster().getReceiptLen();
		int lengthDetail = tokens.length;

		FileDetail fileDetail = new FileDetail();

		// 行號
		fileDetail.setRowNo(rowNo);

		// 長度
		fileDetail.setLength(lengthDetail);

		int sectionNo = 1;
		int pivot = 0;

		// 2016/1/22:因為新增可設定「資料起始列」，用rowNo來抓取format的方式要調整一下
		int formatRowNo = rowNo - (format.getMaster().getStartRow() - 1);
		// parse起始格式
		FileSection sectionHead = parseSection(sectionNo++, tokens, formatHead);
		sectionHead.setFieldGroup(format.getFormatHead(formatRowNo)); // 2016/1/25:
		// 改成formatRowNo
		fileDetail.addSection(sectionHead);

		pivot += lengthHead;

		// 2016/1/25: 以是否有循環格式資料，來判斷是否進入parsing循環格式
		if (formatCycle != null) {
			// parse循環格式
			while (pivot < lengthDetail) {

				String[] tokenCycle = subTokens(tokens, pivot, pivot + lengthCycle);

				FileSection sectionCycle = parseSection(sectionNo++, tokenCycle, formatCycle);
				sectionCycle.setFieldGroup(format.getFormatCycle(formatRowNo)); // 2016/1/25:
				// 改成formatRowNo
				fileDetail.addSection(sectionCycle);

				pivot += lengthCycle;
			}
		}

		return fileDetail;
	}

	/**
	 * parse資料區段
	 * 
	 * @param byteSection
	 * @param formatSection
	 * @return
	 */
	private FileSection parseSection(int sectionNo, String[] tokens, List<FileFormatDetail> formatSection) {

		FileSection fileSection = new FileSection();

		// 段號
		fileSection.setSectionNo(sectionNo);

		// int pivot = 0;

		for (FileFormatDetail formatDetail : formatSection) {

			// byte[] byteField = subArray(tokens, pivot, pivot +
			// formatDetail.getLength());

			// 將byte陣列轉換為字串
			// String value = ConvertUtils.bytes2Str(byteField,
			// format.getENCODING());

			// todo: 取得欄位
			String value = "";
			if (formatDetail.getOrderNo() <= tokens.length) {
				value = tokens[formatDetail.getOrderNo() - 1];
			}
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
			fileField.setFieldType(FieldType.valueOf(formatDetail.getField().getType()));
			// 2017/5/25: 新增欄位定義長度
			fileField.setFieldLen(formatDetail.getLength());

			fileSection.addField(fileField);

			// pivot += formatDetail.getLength();
		}

		return fileSection;
	}

	/**
	 * parse CSV line
	 * 
	 * @throws IOException
	 */
	private String[] parseLine(String line, char delim) throws ActionException {

		String[] tokens = null;
		tokens = StringUtils.getTokens(line, String.valueOf(delim));
		return (tokens != null) ? tokens : new String[0];
	}

	/**
	 * 取得DELIM CHAR
	 * 
	 * @return
	 */
	private char getDelim() {

		char delim;

		String delimStr = format.getMaster().getFieldDelimiter();
		// String delimStr = "";
		if (StringUtils.equals(delimStr, "tab")) {
			delim = '\t';
		}
		else {
			delim = delimStr.charAt(0);
		}

		return delim;
	}

	/**
	 * 取得String陣列的子陣列
	 * 
	 * @param array
	 * @param start
	 * @param end
	 * @return
	 */
	protected String[] subTokens(String[] array, int start, int end) {
		int length = end - start;

		if (length <= 0) {
			return new String[0];
		}

		String[] subArray = new String[length];
		for (int i = 0; i < length; i++) {
			subArray[i] = array[i + start];
		}
		return subArray;
	}

	private static String removeUTF8BOM(String s) {
		if (s.startsWith(UTF8_BOM)) {
			s = s.substring(1);
		}
		return s;
	}
}
