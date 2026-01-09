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
 * 一扣多入檔案格式
 * </p>
 * 
 * @author Leo
 * @version 1.0, 2011/6/8
 * @see
 * @since
 */
public abstract class B12NFileFormat extends FileFormatNew {

	/** 整批類型 */
	public static TxBatchType TX_BATCH_TYPE = TxBatchType.B12N;

	/**
	 * Constructor
	 * 
	 * @param loginUser
	 */
	public B12NFileFormat(int companyKey, String taskId) {
		super(companyKey, taskId, TX_BATCH_TYPE.getCode());
	}

	/**
	 * Constructor
	 * 
	 * @param fileFormatKey
	 */
	public B12NFileFormat(String fileFormatKey) throws DatabaseException {
		super(fileFormatKey);
	}

	@Override
	public FieldGroup getFormatCycle(int rowNo) {
		if (rowNo == 1) {
			return null;
		}
		else {
			return FieldGroup.INVOICE;
		}
	}

	@Override
	public FieldGroup getFormatHead(int rowNo) {

		// Excel 格式
		if (3 == master.getFieldDelimiterType()) {
			if (rowNo == 1 || rowNo == 3) {
				return FieldGroup.COLUMNS; // 98 欄位說明
			}
			else if (rowNo == 2) {
				return FieldGroup.PAYER; // 1 付款資料
			}
			else {
				return FieldGroup.PAYEE; // 2 收款資料
			}
		}
		// 非Excel 格式
		else {
			if (rowNo == 1) {
				return FieldGroup.PAYER;
			}
			else {
				return FieldGroup.PAYEE;
			}
		}
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.B12N;
	}

}
