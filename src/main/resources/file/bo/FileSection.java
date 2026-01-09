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

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cosmos.type.FieldGroup;
import com.ibm.tw.commons.exception.ActionException;
import com.ibm.tw.commons.id.ICommonCode;

/**
 * <p>
 * 檔案區段資料
 * </p>
 * 
 * @author Leo
 * @version 1.0, 2011/6/8
 * @see
 * @since
 */
public class FileSection {

	/** 段號 */
	private int sectionNo;

	/** 所屬群組 */
	private FieldGroup fieldGroup;

	/** 欄位 */
	private Map<String, FileField> fieldMap = new LinkedHashMap<String, FileField>();

	/** 錯誤 */
	private ActionException exception;

	public int getSectionNo() {
		return sectionNo;
	}

	public void setSectionNo(int sectionNo) {
		this.sectionNo = sectionNo;
	}

	public FieldGroup getFieldGroup() {
		return fieldGroup;
	}

	public void setFieldGroup(FieldGroup fieldGroup) {
		this.fieldGroup = fieldGroup;
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

		return isSelfError() || isFieldError();
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

	private boolean isFieldError() {
		// 是否有欄位錯誤
		for (FileField fileField : fieldMap.values()) {
			if (fileField.isError()) {
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
			for (FileField fileField : fieldMap.values()) {
				if (fileField.isError()) {
					return fileField.getError();
				}
			}
			return null;
		}
	}
	
	/**
	 * 加入欄位資料
	 * 
	 * @param field
	 */
	public void addField(FileField field) {
		fieldMap.put(field.getFieldId(), field);
	}

	/**
	 * 取得欄位資料
	 * 
	 * @param fieldId
	 */
	public FileField getField(String fieldId) {
		return fieldMap.get(fieldId);
	}

//	/**
//	 * 取得長度(byte)
//	 * 
//	 * @return
//	 */
//	public int getLength() {
//		int length = 0;
//		for (FileField field : fieldMap.values()) {
//			length += field.getLength();
//		}
//		return length;
//	}
}
