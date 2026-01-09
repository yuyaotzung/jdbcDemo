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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.cosmos.file.FileFormatDetail;
import com.cosmos.file.FileFormatNew;
import com.cosmos.file.bo.FileDetail;
import com.cosmos.file.bo.FileField;
import com.cosmos.file.bo.FileSection;
import com.cosmos.persistence.b2c.entity.FileFormatEntity;
import com.cosmos.type.ChargeAmtType;
import com.cosmos.type.FullPaymentType;
import com.ibm.tw.commons.exception.ActionException;
import com.ibm.tw.commons.persistence.exception.DatabaseException;
import com.ibm.tw.commons.util.StringUtils;

/**
 * <p>
 * 外幣共用格式
 * </p>
 * 
 * @author Bear
 * @version 1.0, Mar 21, 2016
 * @see
 * @since
 */
public abstract class FxFileFormatNew extends FileFormatNew {

	/** */
	private List<FileSectionWrapperBase> details = new ArrayList<FileSectionWrapperBase>();

	public FxFileFormatNew(int companyKey, String taskId, int txBatchType) {
		super(companyKey, taskId, txBatchType);
	}

	/**
	 * Constructor
	 * 
	 * @param fileFormatKey
	 */
	public FxFileFormatNew(String fileFormatKey) throws DatabaseException {
		super(fileFormatKey);
	}

	protected ChargeAmtType parseChargeFeeChar(FileFormatEntity format, String fieldChar) {

		// 檔案格式設定的費用負擔別
		ChargeAmtType formatFeeType = ChargeAmtType.valueOf(format.getFeeType());
		ChargeAmtType chargeFeeType = ChargeAmtType.UNKNOWN;

		// 上傳欄位的字元與格式設定的識別字元相同: 取檔案設定的負擔別
		if (StringUtils.equals(format.getFeeChar(), fieldChar)) {
			chargeFeeType = formatFeeType;
		}
		// 上傳欄位的字元與格式設定的識別字元不相同: 取檔案設定的相反負擔別
		else {
			// 付款人負擔
			if (formatFeeType.isPayer()) {
				chargeFeeType = ChargeAmtType.PAYEE;
			}
			else {
				chargeFeeType = ChargeAmtType.PAYER;
			}
		}

		return chargeFeeType;

	}

	protected static FullPaymentType parseFullPayment(FileFormatEntity format, String fieldChar) {

		// 格式設定值
		FullPaymentType formatType = (1 == format.getFullPaymentType() ? FullPaymentType.Y : FullPaymentType.N);
		// 實際檔案值
		FullPaymentType fieldType = FullPaymentType.UNKNOWN;

		// 上傳欄位的字元與格式設定的識別字元相同: 取檔案設定的匯款方式
		if (StringUtils.equals(format.getFullPaymentChar(), fieldChar)) {
			fieldType = formatType;
		}
		// 上傳欄位的字元與格式設定的識別字元不相同: 取檔案設定的相反匯款方式
		else {
			// 付款人負擔
			if (formatType.isY()) {
				fieldType = FullPaymentType.N;
			}
			else {
				fieldType = FullPaymentType.Y;
			}
		}

		return fieldType;

	}

	public abstract byte[] toFile() throws ActionException;

	/**
	 * 加入明細
	 * 
	 * @param detail
	 */
	public void addDetails(FileSectionWrapperBase detail) {
		details.add(detail);
		FileDetail fileDetail = new FileDetail();
		fileDetail.addSection(detail.getFileSection());
		getFileDoc().addDetail(fileDetail);
	}

	/**
	 * 組成 字串資料
	 * 
	 * @param detail
	 * @return
	 * @throws ActionException
	 * @throws UnsupportedEncodingException
	 */
	protected byte[] detailStringData(FileDetail detail) throws ActionException, UnsupportedEncodingException {

		byte[] result = new byte[0];

		for (FileSection section : detail.getSections()) {

			List<FileFormatDetail> fileFormatDetails = detailGroup.get(section.getFieldGroup());

			int length = getFixTypeLimitLength(fileFormatDetails);
			result = StringUtils.repeat(" ", length).getBytes(ENCODING);
			for (FileFormatDetail fileFormatDetail : fileFormatDetails) {
				String fieldvalue = valueToString(fileFormatDetail, section.getField(fileFormatDetail.getFieldId()));

				// 數值型態
				if (fileFormatDetail.isInteger() || fileFormatDetail.isDouble()) {
					fieldvalue = StringUtils.leftPad(fieldvalue, fileFormatDetail.getLength(), "0");
				}
				else {
					fieldvalue = StringUtils.rightPad(fieldvalue, fileFormatDetail.getLength(), " ");
				}

				byte[] fieldBuf = fieldvalue.getBytes(ENCODING);

				// 替換
				int pos = fileFormatDetail.getStartPos() - 1;
				System.arraycopy(fieldBuf, 0, result, pos, fileFormatDetail.getLength());
			}

		}
		return result;
	}

	/**
	 * 資料轉換
	 * 
	 * @param formatDetail
	 * @param fileField
	 * @throws ActionException
	 */
	private String valueToString(FileFormatDetail formatDetail, FileField fileField) throws ActionException {

		if (fileField == null) {
			return "";
		}

		Object value = fileField.getValue();

		String result = "";

		if (value == null) {
			result = "";
		}

		else {
			result = String.valueOf(value);
		}

		return result;
	}

}
