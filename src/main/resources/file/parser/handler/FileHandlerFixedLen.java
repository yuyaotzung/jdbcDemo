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
import com.ibm.tw.commons.util.ConvertUtils;

/**
 * <p>
 * 固定長度的檔案PARSER
 * </p>
 * 
 * @author Ally
 * @version 1.0, 2016/1/13
 * @see
 * @since
 */
public class FileHandlerFixedLen extends FileHandlerBase {

	public FileHandlerFixedLen(FileFormatNew format) {
		this.format = format;
		this.detailGroup = format.getDetailGroup();
	}

	public FileDoc parse(List<byte[]> fileRecords) {
		// TODO 自動產生方法 Stub
		fileDoc = new FileDoc();
		int rows = fileRecords.size();

		// 資料起始列
		int startRow = format.getMaster().getStartRow() - 1;

		// 格式有尾筆，不算在明細資料內
		if (format.getMaster().getFileFooter() == 1) {
			rows -= 1;
		}

		for (int i = startRow; i < rows; i++) {
			byte[] byteDetail = fileRecords.get(i);

			// 行號
			int rowNo = i + 1;
			// 2016/1/22:因為新增可設定「資料起始列」，用rowNo來抓取format的方式要調整一下
			int formatRowNo = rowNo - startRow;

			FileDetail fileDetail = getFileDetail(rowNo, byteDetail, getFormatDetails(format.getFormatHead(formatRowNo)), getFormatDetails(format.getFormatCycle(formatRowNo)));
			fileDoc.addDetail(fileDetail);
		}

		// 處理尾筆
		if (format.getMaster().getFileFooter() == 1) {

			byte[] byteFooter = fileRecords.get(fileRecords.size() - 1);
			List<FileFormatDetail> formatFooter = getFormatDetails(format.getFormatFooter());

			if (formatFooter != null && formatFooter.size() > 0) {
				fileDoc.addDetail(parseFooter(byteFooter, fileRecords.size(), formatFooter));
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
	private FileDetail parseFooter(byte[] byteFooter, int rowNo, List<FileFormatDetail> formatFooter) {

		Collections.sort(formatFooter, new FileFormatDetailComparator());

		FileSection section = parseSection(1, byteFooter, formatFooter);
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
	protected FileDetail getFileDetail(int rowNo, byte[] byteDetail, List<FileFormatDetail> headFormat, List<FileFormatDetail> cycleFormat) {

		FileDetail fileDetail = null;

		// format排序
		sortFormat(headFormat, cycleFormat);

		// 檢查長度
		if (!checkLength(byteDetail, headFormat, cycleFormat)) {

			FileException exception = new FileException("長度不符", CibErrorCode.FILE_DATA_LENGTH_NOT_MATCH);
			fileDetail = new FileDetail();
			// 行號
			fileDetail.setRowNo(rowNo);
			fileDetail.setException(exception);
		}
		else {

			// parse資料
			fileDetail = parseDetail(rowNo, byteDetail, headFormat, cycleFormat);
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
	protected boolean checkLength(byte[] byteDetail, List<FileFormatDetail> formatHead, List<FileFormatDetail> formatCycle) {
		int lengthDetail = byteDetail.length;
		return checkLength(lengthDetail, formatHead, formatCycle);
		// return true;
	}

	/**
	 * 檢查長度
	 * 
	 * @param byteDetail
	 * @param formatHead
	 * @param formatCycle
	 * @return
	 */
	protected boolean checkLength(int lengthDetail, List<FileFormatDetail> formatHead, List<FileFormatDetail> formatCycle) {
		// 不含循環格式時，檢查不小於格式設定所設定的長度總合即可
		int lengthHead = getLength(formatHead);
		if (formatCycle == null) {
			if (lengthDetail >= lengthHead) {
				return true;
			}
			else {
				return false;
			}
		}

		// 若含循環格式，則可由主檔所填資訊，去檢查檔案內實際的長度是否合理
		lengthHead = format.getMaster().getReceiptStart() - 1; // 陣列從0開始，所以減1
		// int lengthCycle = format.getMaster().getReceiptLen();
		int lengthCycle = getLength(formatCycle);

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
	 * 計算格式長度
	 * 
	 * @param formatDetails
	 * @return
	 */
	protected int getLength(List<FileFormatDetail> formatDetails) {
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
	private FileDetail parseDetail(int rowNo, byte[] byteDetail, List<FileFormatDetail> formatHead, List<FileFormatDetail> formatCycle) {

		// int lengthHead = getLength(formatHead);
		// int lengthCycle = getLength(formatCycle);

		// 2016/1/25:
		// 這裡也要改成fileformat設定長度，不是fileDetail加總。只有含循環格式的明細才需要這個資訊，其他可直接從byteDetail
		// parsing。
		int lengthHead = format.getMaster().getReceiptStart() - 1; // 陣列從0開始，所以減1
		int lengthCycle = format.getMaster().getReceiptLen();

		int lengthDetail = byteDetail.length;

		FileDetail fileDetail = new FileDetail();

		// 行號
		fileDetail.setRowNo(rowNo);

		// 長度
		fileDetail.setLength(lengthDetail);

		int sectionNo = 1;
		int pivot = 0;

		// parse起始格式。2016/1/15: 其實不需切出byteHead，就可以直接從byteDetail parsing首筆。
		// byte[] byteHead = subArray(byteDetail, pivot, pivot + lengthHead);

		// 2016/1/22:因為新增可設定「資料起始列」，用rowNo來抓取format的方式要調整一下
		int formatRowNo = rowNo - (format.getMaster().getStartRow() - 1);
		FileSection sectionHead = parseSection(sectionNo++, byteDetail, formatHead);
		sectionHead.setFieldGroup(format.getFormatHead(formatRowNo)); // 2016/1/25:
		// 改成formatRowNo
		fileDetail.addSection(sectionHead);

		pivot += lengthHead;

		// 2016/1/25: 以是否有循環格式資料，來判斷是否進入parsing循環格式
		if (formatCycle != null) {
			// parse循環格式
			while (pivot < lengthDetail) {

				byte[] byteCycle = subArray(byteDetail, pivot, pivot + lengthCycle);

				FileSection sectionCycle = parseSection(sectionNo++, byteCycle, formatCycle);
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
	private FileSection parseSection(int sectionNo, byte[] byteSection, List<FileFormatDetail> formatSection) {

		FileSection fileSection = new FileSection();

		// 段號
		fileSection.setSectionNo(sectionNo);

		int pivot = 0;

		for (FileFormatDetail formatDetail : formatSection) {

			// byte[] byteField = subArray(byteSection, pivot, pivot +
			// formatDetail.getLength());
			// 2016/1/28:要改成用起始位置做為pivot
			pivot = formatDetail.getStartPos() - 1;
			byte[] byteField = subArray(byteSection, pivot, pivot + formatDetail.getLength());

			// 將byte陣列轉換為字串
			String value = ConvertUtils.bytes2Str(byteField, format.getENCODING());

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
	
	public int getHeadLength(List<byte[]> fileRecords) {
		int rows = fileRecords.size();
		int headLength = 0;

		// 資料起始列
		int startRow = format.getMaster().getStartRow() - 1;


		for (int i = startRow; i < rows; i++) {
			byte[] byteDetail = fileRecords.get(i);

			// 行號
			int rowNo = i + 1;
			// 2016/1/22:因為新增可設定「資料起始列」，用rowNo來抓取format的方式要調整一下
			int formatRowNo = rowNo - startRow;

			headLength =  getLength(getFormatDetails(format.getFormatHead(formatRowNo)));
			break;
		}
		return headLength;
	}

}
