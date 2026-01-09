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

import java.io.UnsupportedEncodingException;

import com.cosmos.code.CibErrorCode;
import com.cosmos.file.FileFormatNew;
import com.cosmos.file.bo.FileDetail;
import com.cosmos.type.FieldGroup;
import com.cosmos.type.TaskType;
import com.cosmos.type.TxBatchType;
import com.ibm.tw.commons.exception.ActionException;
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
public abstract class FsbFileFormat extends FileFormatNew {

	/** 整批類型 */
	public static TxBatchType TX_BATCH_TYPE = TxBatchType.UNKNOWN;

	/**
	 * Constructor
	 * 
	 * @param loginUser
	 */
	public FsbFileFormat(int companyKey, String taskId) {
		super(companyKey, taskId, TX_BATCH_TYPE.getCode());
	}

	/**
	 * Constructor
	 * 
	 * @param fileFormatKey
	 */
	public FsbFileFormat(String fileFormatKey) throws DatabaseException {
		super(fileFormatKey);
	}

	@Override
	public FieldGroup getFormatCycle(int rowNo) {
		return null;
	}

	@Override
	public FieldGroup getFormatHead(int rowNo) {

		if (rowNo == 1) {
			return FieldGroup.FSBHEADER;
		}
		else {
			return FieldGroup.FSB;
		}
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.FSB;
	}

	/**
	 * 
	 * @return
	 * @throws ActionException
	 */
	public byte[] toFile() throws ActionException {

		byte[] dataBuff = new byte[0];

		FileDetail header = fetchFSBHEADER();

		try {

			// 首筆
			if (header != null) {
				dataBuff = detailStringData(header);
			}

			// 明細
			for (FileDetail detail : fileDoc.getDetails()) {
				if (!detail.isTSBHEADER()) {
					if (dataBuff.length > 0)
						dataBuff = concat(dataBuff, getLineSeparator().getBytes(ENCODING));
					dataBuff = concat(dataBuff, detailStringData(detail));
				}
			}

			return dataBuff;
		}
		catch (UnsupportedEncodingException e) {

			ActionException ex = getActionException(CibErrorCode.VALIDATE_SIZE_ERROR);

			throw ex;
		}
	}

	private FileDetail fetchFSBHEADER() {

		for (FileDetail detail : fileDoc.getDetails()) {
			if (detail.isFSBHEADER()) {
				return detail;
			}
		}
		return null;
	}

}
