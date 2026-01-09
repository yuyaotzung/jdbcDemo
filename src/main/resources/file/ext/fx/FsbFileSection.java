package com.cosmos.file.ext.fx;

import com.cosmos.file.bo.FileField;
import com.cosmos.file.bo.FileSection;
import com.ibm.tw.commons.util.StringUtils;

/**
 * <p>
 * 外幣複委託含付款資料、及收款資料 File Section
 * </p>
 * 
 * @author monica
 * @version 1.0, 2013/11/20
 * @see
 * @since
 */
public class FsbFileSection {

	/** 檔案區段 */
	private FileSection fileSection;

	public FsbFileSection(FileSection fileSection) {
		this.fileSection = fileSection;
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
	 * 付款日期
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
	 * 交易類別
	 * 
	 * @return
	 */
	public String getTxntTypeId() {
		FileField fileField = fileSection.getField("txntType");
		if (fileField == null) {
			return null;
		}
		else {
			return StringUtils.trim(fileField.getValue());
		}
	}

	/**
	 * 付款幣別
	 * 
	 * @return
	 */
	public String getPayerCcy() {
		FileField fileField = fileSection.getField("payerCcy");
		if (fileField == null) {
			return null;
		}
		else {
			return StringUtils.trim(fileField.getValue());
		}
	}

	/**
	 * 交易金額
	 * 
	 * @return
	 */
	public String getPayerAmt() {
		FileField fileField = fileSection.getField("payerAmt");
		if (fileField == null) {
			return null;
		}
		else {
			return fileField.getValue();
		}
	}

	public String getPayerTxfRefNo() {
		FileField fileField = fileSection.getField("payerTxfRefNo");
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

			String payeeAccount = StringUtils.trim(fileField.getValue());

			if (StringUtils.length(payeeAccount) == 12) {

				payeeAccount = StringUtils.leftPad(payeeAccount, 14, "0");

			}

			return payeeAccount;
		}
	}
	
	/**
	 * 客戶上傳收款帳號
	 * 
	 * @return
	 */
	public String getPayeeAccountNoOri() {

		FileField fileField = fileSection.getField("payeeAccountNo");
		if (fileField == null) {
			return null;
		}
		else {
			return StringUtils.trim(fileField.getValue());
		}

	}

	/**
	 * 收款幣別
	 * 
	 * @return
	 */
	public String getPayeeCcy() {
		FileField fileField = fileSection.getField("payeeCcy");
		if (fileField == null) {
			return null;
		}
		else {
			return StringUtils.trim(fileField.getValue());
		}
	}

	/**
	 * 收款金額
	 * 
	 * @return
	 */
	public String getPayeeAmt() {
		FileField fileField = fileSection.getField("payeeAmt");
		if (fileField == null) {
			return null;
		}
		else {
			return fileField.getValue();
		}
	}

	public String getPayeeTxfRefNo() {
		FileField fileField = fileSection.getField("payeeTxfRefNo");
		if (fileField == null) {
			return null;
		}
		else {
			return StringUtils.trim(fileField.getValue());
		}
	}

	public String getExCntrNo() {
		FileField fileField = fileSection.getField("exCntrNo");
		if (fileField == null) {
			return null;
		}
		else {
			return StringUtils.trim(fileField.getValue());
		}
	}

	public String getSpreadBSpot() {
		FileField fileField = fileSection.getField("spreadBSpot");
		if (fileField == null) {
			return null;
		}
		else {
			return fileField.getValue();
		}
	}

	public String getSpreadSSpot() {
		FileField fileField = fileSection.getField("spreadSSpot");
		if (fileField == null) {
			return null;
		}
		else {
			return fileField.getValue();
		}
	}

	public String getPayeeAccountTypeId() {
		FileField fileField = fileSection.getField("payeeAccountTypeId");
		if (fileField == null) {
			return null;
		}
		else {
			return fileField.getValue();
		}
	}

}
