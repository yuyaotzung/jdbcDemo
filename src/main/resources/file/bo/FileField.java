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

import java.util.Date;

import com.cosmos.type.FieldType;
import com.ibm.tw.commons.exception.ActionException;
import com.ibm.tw.commons.id.ICommonCode;
import com.ibm.tw.commons.util.StringUtils;
import com.ibm.tw.commons.util.time.DateUtils;

/**
 * <p>
 * 檔案欄位資料
 * </p>
 * 
 * @author Leo
 * @version 1.0, 2011/6/8
 * @see
 * @since
 */
public class FileField {

	/** 欄號 */
	private int columnNo;

	/** 欄位Id */
	private String fieldId;

	/** 欄位值 */
	private String value;

	/** 錯誤 */
	private ActionException exception;

	/** 日期格式 */
	private String dateType;

	/** 欄位資料格式　 */
	private FieldType fieldType;

	/** 金額格式　 */
	private int amtType;

	/** 小數位數　 */
	private int decLen;

	/** 欄位定義長度　 */
	private int fieldLen;
	
	/** 文字格式　 (0:依檔案上傳 其他值:依欄位屬性)*/
	private int textType;

	/** 文字欄位內容 */
	private String textContent;

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public FieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}

	public int getAmtType() {
		return amtType;
	}

	public void setAmtType(int amtType) {
		this.amtType = amtType;
	}

	public int getDecLength() {
		return decLen;
	}

	public void setDecLength(int decLen) {
		this.decLen = decLen;
	}

	public int getColumnNo() {
		return columnNo;
	}

	public void setColumnNo(int columnNo) {
		this.columnNo = columnNo;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	// public String getValue() {
	// return value;
	// }

	public int getFieldLen() {
		return fieldLen;
	}

	public void setFieldLen(int fieldLen) {
		this.fieldLen = fieldLen;
	}

	public String getRawValue() {
		return value;
	}

	// 2016/1/22: 根據新的日期格式與小數位數來回傳值
	public String getValue() {
		if (fieldType == null) { // 20160318, 測試證券上傳檔
			// nullpointException,加上判斷直接回應
			return value;
		}

		if (fieldType.compareTo(FieldType.TEXT) == 0) {
			return value;
		}
		else if (fieldType.compareTo(FieldType.INTEGER) == 0) {
			return value;
		}
		else if (fieldType.compareTo(FieldType.DECIMAL) == 0) {
			// 一律轉成小數點兩位
			return getDecimalValue();
		}
		else if (fieldType.compareTo(FieldType.DATE) == 0) {
			// 一律轉成yyyyMMdd simpleDate格式
			return getDateValue();
		}

		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ActionException getException() {
		return exception;
	}

	public void setException(ActionException exception) {
		this.exception = exception;
	}

	// 一律轉成simpleDate yyyyMMdd格式
	private String getDateValue() {
		String dateValue = "";
		// 民國年
		if (StringUtils.contains(dateType, "C")) {
			Date date = DateUtils.getROCDate(value, "");
			dateValue = DateUtils.getISODateStr(date, "");
		}
		// 西元年
		else {
			if (StringUtils.contains(dateType, "/")) {
				Date date = DateUtils.getISODate(value, "/");
				dateValue = DateUtils.getISODateStr(date, "");
			}
			else {
				dateValue = value;
			}
		}

		return dateValue;
	}

	private String getDecimalValue() {
		String decimalValue = value;
		// 含小數點
		if (amtType == 1) {
			String[] tokens = StringUtils.getTokens(value, ".");
			// 無小數點
			if (tokens.length == 1) {
				decimalValue = tokens[0];
			}
			// 有小數點
			else if (tokens.length == 2) {
				// 系統僅取兩位
				decimalValue = tokens[0] + "." + StringUtils.left(tokens[1], 2);
			}
			else {
				// throw new JsfActionException("no point for decimal = " +
				// value, CibErrorCode.FILE_DATA_FORMAT_ERROR);
			}

		}
		// 不含小數點
		else if (amtType == 2) {
			int len = value.length();
			// 資料大於小數長度
			if (len > decLen) {

				String lValue = StringUtils.right(value, decLen);
				String rValue = StringUtils.left(value, len - decLen);

				decimalValue = rValue + "." + lValue;
			}
			else {
				decimalValue = "0." + StringUtils.leftPad(value, decLen, "0");
			}
		}

		return decimalValue;
	}

	/**
	 * 是否有錯誤
	 * 
	 * @return
	 */
	public boolean isError() {

		return isSelfError();
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
			return null;
		}
	}

	public String getStringValue() {
		return StringUtils.trim(getValue());
	}
	
	public int getTextType() {
		return textType;
	}

	public void setTextType(int textType) {
		this.textType = textType;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	// /**
	// * 取得長度(byte)
	// *
	// * @return
	// */
	// public int getLength() {
	// if (StringUtils.isBlank(value)) {
	// return 0;
	// }
	// else {
	// return value.getBytes().length;
	// }
	// }
}
