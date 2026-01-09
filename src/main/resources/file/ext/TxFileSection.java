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
package com.cosmos.file.ext;

import com.cosmos.file.bo.FileField;
import com.cosmos.file.bo.FileSection;
import com.ibm.tw.commons.util.StringUtils;
import com.ibm.tw.commons.util.converter.HalfConvertUtils;

/**
 * <p>
 * Tx File Section
 * </p>
 * 
 * @author Leo
 * @version 1.0, 2011/6/27
 * @see
 * @since
 */
public class TxFileSection {

	/** 檔案區段 */
	private FileSection fileSection;

	public TxFileSection(FileSection fileSection) {

		this.fileSection = fileSection;

		// 2017/5/25 : 收款人戶名轉全形
		FileField fileField = fileSection.getField("payeeName");
		if (fileField != null) {
			fileField.setValue(HalfConvertUtils.half2Full(StringUtils.trimAllBigSpace(fileField.getValue())));
		}

		// 2017/6/2 : 附言轉全形
		fileField = fileSection.getField("payeeMemo");
		if (fileField != null) {
			fileField.setValue(HalfConvertUtils.half2Full(StringUtils.trimAllBigSpace(fileField.getValue())));
		}

		// 2017/6/2 : 付款性質轉全形
		fileField = fileSection.getField("payerRemarkType");
		if (fileField != null) {
			fileField.setValue(HalfConvertUtils.half2Full(StringUtils.trimAllBigSpace(fileField.getValue())));
		}
	}

	/**
	 * 付款帳號
	 * 
	 * @return
	 */
	public String getPayerAccountNo() {

		FileField fileField = fileSection.getField("payerAccountNo");
		if (fileField == null) {
			return null;
		}
		else {

			String payerAccount = StringUtils.trim(fileField.getValue());

			if (StringUtils.length(payerAccount) == 12) {

				payerAccount = StringUtils.leftPad(payerAccount, 14, "0");

			}

			return payerAccount;
		}
	}

	/**
	 * 客戶上傳付款帳號
	 * 
	 * @return
	 */
	public String getPayerAccountNoOri() {

		FileField fileField = fileSection.getField("payerAccountNo");
		if (fileField == null) {
			return null;
		}
		else {
			return StringUtils.trim(fileField.getValue());
		}
	}

	/**
	 * 付款性質原因
	 * 
	 * @return
	 */
	public String getPayerRemarkType() {
		FileField fileField = fileSection.getField("payerRemarkType");
		if (fileField == null) {
			return null;
		}
		else {
			return StringUtils.trim(fileField.getValue());
		}
	}

	/**
	 * 付款人統編
	 * 
	 * @return
	 */
	public String getPayerUid() {
		FileField fileField = fileSection.getField("payerUid");
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
		FileField fileField = fileSection.getField("txDate");
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
	public String getTxAmt() {
		FileField fileField = fileSection.getField("txAmt");
		if (fileField == null) {
			return null;
		}
		else {
			return fileField.getValue();
		}
	}

	/**
	 * 付款通路
	 * 
	 * @return
	 */
	public String getPayerKind() {
		FileField fileField = fileSection.getField("payerKind");
		if (fileField == null) {
			return null;
		}
		else {
			return StringUtils.trim(fileField.getValue());
		}
	}

	/**
	 * 收款人戶名
	 * 
	 * @return
	 */
	public String getPayeeName() {
		FileField fileField = fileSection.getField("payeeName");
		if (fileField == null) {
			return null;
		}
		else {
			return StringUtils.trim(fileField.getValue());
		}
	}

	/**
	 * 收款人Email
	 * 
	 * @return
	 */
	public String getPayeeEmail() {
		FileField fileField = fileSection.getField("payeeEmail");
		if (fileField == null) {
			return null;
		}
		else {
			return StringUtils.trim(fileField.getValue());
		}
	}

	/**
	 * 收款銀行代號
	 * 
	 * @return
	 */
	public String getPayeeBankId() {
		FileField fileField = fileSection.getField("payeeBankId");
		if (fileField == null) {
			return null;
		}
		else {
			return StringUtils.trim(fileField.getValue());
		}
	}

	/**
	 * 收款帳號
	 * 
	 * @return
	 */
	public String getPayeeAccountNo() {

		FileField fileField = fileSection.getField("payeeAccountNo");
		if (fileField == null) {
			return null;
		}
		else {
			return StringUtils.trim(fileField.getValue());
		}

	}

	/**
	 * 收款人統編
	 * 
	 * @return
	 */
	public String getPayeeUid() {
		FileField fileField = fileSection.getField("payeeUid");
		if (fileField == null) {
			return null;
		}
		else {
			return StringUtils.trim(fileField.getValue());
		}
	}

	/**
	 * 手續費負擔
	 * 
	 * @return
	 */
	public String getChargeAmtType() {
		FileField fileField = fileSection.getField("chargeAmtType");
		if (fileField == null) {
			return null;
		}
		else {
			return fileField.getValue();
		}
	}

	/**
	 * 收款人備註
	 * 
	 * @return
	 */
	public String getPayeeMemo() {
		FileField fileField = fileSection.getField("payeeMemo");
		if (fileField == null) {
			return null;
		}
		else {
			return StringUtils.trim(fileField.getValue());
		}
	}

	/**
	 * 取得收款人戶名欄位長度定義
	 * 
	 * @return
	 */
	public int getPayeeNameFieldLen() {
		FileField fileField = fileSection.getField("payeeName");
		if (fileField == null) {
			return -1;
		}
		else {
			return fileField.getFieldLen();
		}
	}

	/**
	 * 取得付款性質欄位長度定義
	 * 
	 * @return
	 */
	public int getPayerRemarkTypeFieldLen() {
		FileField fileField = fileSection.getField("payerRemarkType");
		if (fileField == null) {
			return -1;
		}
		else {
			return fileField.getFieldLen();
		}
	}

	/**
	 * 取得收款人備註欄位長度定義
	 * 
	 * @return
	 */
	public int getPayeeMemoFieldLen() {
		FileField fileField = fileSection.getField("payeeMemo");
		if (fileField == null) {
			return -1;
		}
		else {
			return fileField.getFieldLen();
		}
	}

}
