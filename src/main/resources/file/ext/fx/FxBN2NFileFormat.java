package com.cosmos.file.ext.fx;

import java.io.UnsupportedEncodingException;

import com.cosmos.code.CibErrorCode;
import com.cosmos.file.bo.FileDetail;
import com.cosmos.file.bo.FileDoc;
import com.cosmos.type.FieldGroup;
import com.cosmos.type.TaskType;
import com.cosmos.type.TxBatchType;
import com.ibm.tw.commons.exception.ActionException;
import com.ibm.tw.commons.persistence.exception.DatabaseException;

/**
 * <p>
 * 外幣多扣多入檔案格式
 * </p>
 * 
 * @author monica
 * @version 1.0, 2013/11/19
 * @see
 * @since
 */
public abstract class FxBN2NFileFormat extends FxFileFormatNew {

	/** 整批類型 */
	public static TxBatchType TX_BATCH_TYPE = TxBatchType.BN2N;

	public FxBN2NFileFormat(int companyKey, String taskId) {
		super(companyKey, taskId, TX_BATCH_TYPE.getCode());
	}

	/**
	 * Constructor
	 * 
	 * @param fileFormatKey
	 */
	public FxBN2NFileFormat(String fileFormatKey) throws DatabaseException {
		super(fileFormatKey);
	}

	@Override
	public FieldGroup getFormatCycle(int rowNo) {
		return null;
	}

	@Override
	public FieldGroup getFormatHead(int rowNo) {
		// Excel 格式
		if (3 == master.getFieldDelimiterType()) {
			if (rowNo == 1) {
				return FieldGroup.COLUMNS; // 98 欄位說明
			}
			else {
				return FieldGroup.FX;
			}
		}
		// 非 Excel 格式
		else {
			return FieldGroup.FX;
		}
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.FXBN2N;
	}

	@Override
	protected void validateDetailSize(FileDoc fileDoc) throws ActionException {
		// 總筆數不可超過「整批付款開放筆數=1000筆」
		int limit = 1000;
		if (fileDoc.getDetails().size() > limit) {
			ActionException e = getActionException(CibErrorCode.VALIDATE_SIZE_ERROR);
			fileDoc.setException(e);
			throw e;
		}

	}

	/**
	 * 
	 * @return
	 * @throws ActionException
	 */
	public byte[] toFile() throws ActionException {

		byte[] dataBuff = new byte[0];

		try {

			// 明細
			for (FileDetail detail : fileDoc.getDetails()) {
				if (FieldGroup.FX.equals(detail.getSection(0).getFieldGroup())) {
					if (dataBuff.length > 0)
						dataBuff = concat(dataBuff, getLineSeparator().getBytes(ENCODING));
					dataBuff = concat(dataBuff, detailStringData(detail));

					String result = detail.getSection(0).getField("result").getValue();
					dataBuff = concat(dataBuff, result.getBytes(ENCODING));
				}

			}

			return dataBuff;
		}
		catch (UnsupportedEncodingException e) {

			ActionException ex = getActionException(CibErrorCode.VALIDATE_SIZE_ERROR);

			throw ex;
		}
	}

}
