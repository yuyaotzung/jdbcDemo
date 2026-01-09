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
package com.cosmos.file.ext.fx;

import com.cosmos.file.bo.FileField;
import com.cosmos.file.bo.FileSection;
import com.ibm.tw.commons.util.StringUtils;

/**
 * <p>
 * 證券款複委託檔案上傳
 * </p>
 * 
 * @author BearChen
 * @version 1.0, 2011/6/27
 * @see
 * @since
 */
public class FSBHeaderFileSection {

	/** 檔案區段 */
	private FileSection fileSection;

	public FSBHeaderFileSection(FileSection fileSection) {
		this.fileSection = fileSection;
	}

	/**
	 * 總筆數
	 * 
	 * @return
	 */
	public String getTotalCount() {
		FileField fileField = fileSection.getField("totalCount");
		if (fileField == null) {
			return null;
		}
		else {
			return StringUtils.trim(fileField.getValue());
		}
	}

	/**
	 * 交易日期
	 * 
	 * @return
	 */
	public String getTxDate() {
		FileField fileField = fileSection.getField("txDateHeader");
		if (fileField == null) {
			return null;
		}
		else {
			return fileField.getValue();
		}
	}

	/**
	 * 帳號轉帳類別代號 1為入帳 2為扣帳
	 * 
	 * @return
	 */
	public String getPayeeAccountTypeId() {
		FileField fileField = fileSection.getField("payeeAccountTypeIdH");
		if (fileField == null) {
			return null;
		}
		else {
			return fileField.getValue();
		}
	}

	/**
	 * 交易幣別
	 * 
	 * @return
	 */
	public String getCcy() {
		FileField fileField = fileSection.getField("ccyH");
		if (fileField == null) {
			return null;
		}
		else {
			return fileField.getValue();
		}
	}

	/**
	 * 交易金額
	 * 
	 * @return
	 */
	public String getTotalAmt() {
		FileField fileField = fileSection.getField("totalAmt");
		if (fileField == null) {
			return null;
		}
		else {
			return fileField.getValue();
		}
	}

	/**
	 * 券商統編
	 * 
	 * @return
	 */
	public String getPayeeUid() {
		FileField fileField = fileSection.getField("payeeUidHeader");
		if (fileField == null) {
			return null;
		}
		else {
			return StringUtils.trim(fileField.getValue());
		}
	}

}
