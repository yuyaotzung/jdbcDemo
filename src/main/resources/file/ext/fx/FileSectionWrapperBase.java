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
package com.cosmos.file.ext.fx;

import org.apache.log4j.Logger;

import com.cosmos.file.bo.FileField;
import com.cosmos.file.bo.FileSection;
import com.cosmos.type.PayerKind;
import com.ibm.tw.commons.util.StringUtils;

/**
 * <p>
 * 外幣 File Section Wrapper Base
 * </p>
 * 
 * @author Rocky
 * @version 1.0, 2016/01/13
 * @see
 * @since
 */
public class FileSectionWrapperBase {

	private Logger logger = Logger.getLogger(FileSectionWrapperBase.class);

	/** 檔案區段 */
	protected FileSection fileSection;

	public FileSection getFileSection() {
		return fileSection;
	}

	public FileSectionWrapperBase(FileSection fileSection) {
		this.fileSection = fileSection;
	}

	public void setFieldValue(String fieldId, String value) {
		FileField fileField = fileSection.getField(fieldId);
		if (fileField == null) {
			logger.warn("fieldId not defined: [" + fieldId + "]");
			return;
		}
		fileField.setValue(StringUtils.trimToEmpty(value));
	}

	/**
	 * 取得 FileField 的內容值
	 * 
	 * @param fieldId
	 * @return
	 */
	public String getFieldValue(String fieldId) {

		FileField fileField = fileSection.getField(fieldId);
		
		if (fileField == null) {
			logger.warn("fieldId not defined: [" + fieldId + "]");
			return null;
		}
		
		FileField fileFieldPayeeBankName1 = fileSection.getField("payeeBankName1");
		FileField fileFieldPayeeCountry = fileSection.getField("payeeCountry");
		FileField fileFieldPayeeSwiftCode = fileSection.getField("payeeSwiftCode");
		// 自行轉帳=true;非自行轉帳=false
		boolean intraFlag = false;
		
		if (fileFieldPayeeBankName1 != null){
			if (StringUtils.containsIgnoreCase(fileFieldPayeeBankName1.getValue(), "KGI") && StringUtils.containsIgnoreCase(fileFieldPayeeCountry.getValue(), "TW") ){
				// 表自行轉帳
				intraFlag = true;
			}
		}

		//  非自行轉帳再接著判斷
		if (!intraFlag){
			if (fileFieldPayeeSwiftCode != null){
				if (StringUtils.isNotBlank(fileFieldPayeeSwiftCode.getValue()) && StringUtils.containsIgnoreCase(fileFieldPayeeSwiftCode.getValue().substring(0, 8), "CDIBTWTP") && StringUtils.containsIgnoreCase(fileFieldPayeeCountry.getValue(), "TW") ){
				//if (StringUtils.containsIgnoreCase(fileFieldPayeeSwiftCode.getValue().substring(0, 8), "CDIBTWTP") && StringUtils.containsIgnoreCase(fileFieldPayeeCountry.getValue(), "TW") ){
					// 表自行轉帳
					intraFlag = true;
				}
			}
		}
	
		String value = "";
		// 這3個欄位需要特別作判斷取值
		if (StringUtils.equals("sourceOfFund", fieldId) || StringUtils.equals("selfFlag", fieldId) || StringUtils.equals("txntTypeId", fieldId)){
			if (StringUtils.equals("sourceOfFund", fieldId)){
				// fileField.getTextType() == 0, 依檔案內容的值
				if (fileField.getTextType() == 1 && intraFlag ){
					value = StringUtils.trimToEmpty(fileField.getTextContent());
				}else{
					value = StringUtils.trimToEmpty(fileField.getValue());
				}
			}
			// fileField.getTextType() == 0, 依檔案內容的值
			if (StringUtils.equals("selfFlag", fieldId)){
				if (fileField.getTextType() != 0 && StringUtils.containsIgnoreCase(fileFieldPayeeCountry.getValue(), "TW") ){
					value = StringUtils.trimToEmpty(fileField.getTextContent());
				}else{
					value = StringUtils.trimToEmpty(fileField.getValue());
				}
			}
			// fileField.getTextType() == 0, 依檔案內容的值
			if (StringUtils.equals("txntTypeId", fieldId)){
				if (fileField.getTextType() != 0 && intraFlag){
					value = StringUtils.trimToEmpty(fileField.getTextContent());
				}else{
					value = StringUtils.trimToEmpty(fileField.getValue());
				}
			}
		}else{
			value = StringUtils.trimToEmpty(fileField.getValue());
		}
		
		// 欄位全部轉大寫，所以加在這邊，若將來欄位又沒有要全轉大寫，請再搬移到各個 getter 去轉大寫
		value = StringUtils.upperCase(value);
		
		return value;
	}

	/**
	 * 是否為自行
	 * 
	 * @param payeeBankName1
	 * @param payeeSwiftCode
	 * @return
	 */
	protected PayerKind isIntraBank(String payeeBankName1, String payeeSwiftCode) {

		payeeBankName1 = StringUtils.trimToEmpty(payeeBankName1);
		payeeSwiftCode = StringUtils.trimToEmpty(payeeSwiftCode);

		if (StringUtils.isAllBank(payeeBankName1, payeeSwiftCode))
			return null;

		String[] swiftCodeTokens = { "CDIBTWTP", "CDIBTWTPXXX" };
		if (StringUtils.equalsOne(payeeSwiftCode, swiftCodeTokens)) {
			return PayerKind.INTRABANK; // 自行
		}

		//String[] payeeBankNameTokens = { "CDIBTWTP", "CDIBTWTPXXX", "KGI BANK", "KGIBANK", "COSMOS BANK", "KGI", "CDIB" };
		// 檔案上傳與線上編輯自行匯款判斷不同,改成與線上編輯相同
		if(StringUtils.indexOf(payeeBankName1.toUpperCase().trim(), "KGI")!=-1 || StringUtils.indexOf(payeeBankName1.toUpperCase().trim(), "CDIB")!=-1){
			return PayerKind.INTRABANK; // 自行
		}
		/*if (StringUtils.equalsOne(payeeBankName1, payeeBankNameTokens)) {
			return PayerKind.INTRABANK; // 自行
		}*/

		return PayerKind.INTERBANK; // 跨行
	}

	/**
	 * 
	 * @param fieldId
	 * @param value
	 */
	public void setValue(String fieldId, String value) {

		if (fileSection == null) {
			fileSection = new FileSection();
		}

		FileField field = fileSection.getField(fieldId);
		if (field == null) {
			field = new FileField();
			field.setFieldId(fieldId);
			field.setValue(value);
			fileSection.addField(field);
		}
		else {
			field.setValue(value);
		}
	}
}
