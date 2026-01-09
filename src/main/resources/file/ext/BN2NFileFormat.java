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

import com.cosmos.file.FileFormatNew;
import com.cosmos.type.FieldGroup;
import com.cosmos.type.TaskType;
import com.cosmos.type.TxBatchType;
import com.ibm.tw.commons.persistence.exception.DatabaseException;

/**
 * <p>
 * 多扣多入檔案格式
 * </p>
 * 
 * @author Leo
 * @version 1.0, 2011/6/8
 * @see
 * @since
 */
public abstract class BN2NFileFormat extends FileFormatNew {

	/** 整批類型 */
	public static TxBatchType TX_BATCH_TYPE = TxBatchType.BN2N;

	/**
	 * Constructor
	 * 
	 * @param loginUser
	 */
	public BN2NFileFormat(int companyKey, String taskId) {
		super(companyKey, taskId, TX_BATCH_TYPE.getCode());
	}

	/**
	 * Constructor
	 * 
	 * @param fileFormatKey
	 */
	public BN2NFileFormat(String fileFormatKey) throws DatabaseException {
		super(fileFormatKey);
	}

	@Override
	public FieldGroup getFormatCycle(int rowNo) {
		return FieldGroup.INVOICE;
	}

	@Override
	public FieldGroup getFormatHead(int rowNo) {
		// Excel 格式
		if (3 == master.getFieldDelimiterType()) {
			if (rowNo == 1) {
				return FieldGroup.COLUMNS; // 98 欄位說明
			}
			else {
				return FieldGroup.TX;
			}
		}
		// 非 Excel 格式
		else {
			return FieldGroup.TX;
		}
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.BN2N;
	}

	// @Override
	// protected void validateCustom(FileDoc fileDoc) throws ActionException {
	// // 檢核該檔案每筆付款日期是否相同
	//
	// // 取得第一筆付款日期
	// TxFileSection firstSection = new
	// TxFileSection(fileDoc.getFirstDetail().getSection(1));
	// if (firstSection == null) {
	// return;
	// }
	//		
	// String txDate = firstSection.getTxDate();
	//
	// // 檢核每筆付款日期是否相同
	// for (FileDetail detail : fileDoc.getDetails()) {
	// if (!detail.isError()) {
	// TxFileSection section = new TxFileSection(detail.getSection(1));
	// if (!StringUtils.equals(txDate, section.getTxDate())) {
	// ActionException e =
	// getActionException(CibErrorCode.VALIDATE_DATE_ERROR_6);
	// detail.setException(e);
	// }
	// }
	// }
	// }
}
