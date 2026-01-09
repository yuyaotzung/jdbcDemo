/*
 * ===========================================================================
 * IBM Confidential
 * AIS Source Materials
 * 
 * 
 * (C) Copyright IBM Corp. 2011.
 *
 * ===========================================================================
 */
package com.cosmos.file.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cosmos.type.FieldGroup;
import com.ibm.tw.commons.exception.ActionException;
import com.ibm.tw.commons.id.ICommonCode;

/**
 * <p>
 * 檔案明細資料
 * </p>
 * 
 * @author Leo
 * @version 1.0, 2011/6/8
 * @see
 * @since
 */
public class FileDetail {

	/** 行號 */
	private int rowNo;

	/** 長度 */
	private int length;

	/** 區段 */
	private List<FileSection> sections = new ArrayList<FileSection>();

	/** 錯誤 */
	private ActionException exception;

	public int getRowNo() {
		return rowNo;
	}

	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}

	public List<FileSection> getSections() {
		return sections;
	}

	public ActionException getException() {
		return exception;
	}

	public void setException(ActionException exception) {
		this.exception = exception;
	}

	/**
	 * 是否有錯誤
	 * 
	 * @return
	 */
	public boolean isError() {

		return isSelfError() || isSectionError();
	}

	private boolean isSelfError() {
		// 無例外
		if (exception == null) {
			return false;
		}

		// 例外無錯誤代碼
		if (exception.getStatus() == null) {
			return false;
		}

		String errorCode = exception.getStatus().getStatusCode();

		// 錯誤代碼為空或0
		if (StringUtils.isBlank(errorCode) || StringUtils.equals(errorCode, ICommonCode.SUCCESS_STATUS_CODE)) {
			return false;
		}
		// 有錯誤代碼
		else {
			return true;
		}
	}

	private boolean isSectionError() {
		// 是否有區段錯誤
		for (FileSection fileSection : sections) {
			if (fileSection.isError()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 取得錯誤
	 * 
	 * 包含自己或子陣列
	 * 
	 * @return
	 */
	public ActionException getError() {

		if (isSelfError()) {
			return getException();
		}
		else {
			for (FileSection fileSection : sections) {
				if (fileSection.isError()) {
					return fileSection.getError();
				}
			}
			return null;
		}
	}

	/**
	 * 加入區段資料
	 * 
	 * @param field
	 */
	public void addSection(FileSection section) {
		sections.add(section);
	}

	/**
	 * 取得長度(byte)
	 * 
	 * @return
	 */
	public int getLength() {
		return length;
	}

	/**
	 * 設定長度(byte);
	 * 
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * 依據段號取得明細資料
	 * 
	 * 段號編號由1開始
	 * 
	 * @param sectionNo
	 * @return
	 */
	public FileSection getSection(int sectionNo) {
		for (FileSection fileSection : sections) {
			if (fileSection.getSectionNo() == sectionNo) {
				return fileSection;
			}
		}

		return null;
	}

	/**
	 * 取得第一筆明細資料
	 * 
	 * @return
	 */
	public FileSection getFirstSection() {
		return getSection(1);
	}

	public boolean isPAYER() {

		return FieldGroup.PAYER.equals(sections.get(0).getFieldGroup());
	}

	public boolean isPAYEE() {

		return FieldGroup.PAYEE.equals(sections.get(0).getFieldGroup());
	}

	public boolean isTX() {

		return FieldGroup.TX.equals(sections.get(0).getFieldGroup());
	}

	public boolean isPBS() {

		return FieldGroup.PBS.equals(sections.get(0).getFieldGroup());
	}

	public boolean isPBSHEADER() {

		return FieldGroup.PBSHEADER.equals(sections.get(0).getFieldGroup());
	}

	public boolean isTSB() {

		return FieldGroup.TSB.equals(sections.get(0).getFieldGroup());
	}

	public boolean isTSBHEADER() {

		return FieldGroup.TSBHEADER.equals(sections.get(0).getFieldGroup());
	}

	public boolean isFSB() {

		return FieldGroup.FSB.equals(sections.get(0).getFieldGroup());
	}

	public boolean isFSBHEADER() {

		return FieldGroup.FSBHEADER.equals(sections.get(0).getFieldGroup());
	}

	public boolean isST() {

		return FieldGroup.ST.equals(sections.get(0).getFieldGroup());
	}

	public boolean isFooter() {

		return FieldGroup.FOOTER.equals(sections.get(0).getFieldGroup());
	}

	public boolean isHeader() {

		return FieldGroup.HEADER.equals(sections.get(0).getFieldGroup());
	}

}
