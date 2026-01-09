package com.cosmos.file.ext.fx;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.cosmos.code.CibErrorCode;
import com.cosmos.file.FileFormatDetail;
import com.cosmos.file.bo.FileDetail;
import com.cosmos.file.bo.FileDoc;
import com.cosmos.type.FieldGroup;
import com.cosmos.type.TaskType;
import com.cosmos.type.TxBatchType;
import com.ibm.tw.commons.exception.ActionException;
import com.ibm.tw.commons.persistence.exception.DatabaseException;

/**
 * <p>
 * 外幣一扣多入檔案格式
 * </p>
 * 
 * @author monica
 * @version 1.0, 2013/11/19
 * @see
 * @since
 */
public abstract class FxB12NFileFormat extends FxFileFormatNew {

	/** 整批類型 */
	public static TxBatchType TX_BATCH_TYPE = TxBatchType.B12N;

	public FxB12NFileFormat(int companyKey, String taskId) {
		super(companyKey, taskId, TX_BATCH_TYPE.getCode());
	}

	/**
	 * Constructor
	 * 
	 * @param fileFormatKey
	 */
	public FxB12NFileFormat(String fileFormatKey) throws DatabaseException {
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
			if (rowNo == 1 || rowNo == 3) {
				return FieldGroup.COLUMNS; // 98 欄位說明
			}
			else if (rowNo == 2) {
				return FieldGroup.FXPAYER; // 7 付款資料
			}
			else {
				return FieldGroup.FXPAYEE; // 8 收款資料
			}
		}
		// 非 Excel 格式
		else {
			if (rowNo == 1) {
				return FieldGroup.FXPAYER; // 7 付款資料
			}
			else {
				return FieldGroup.FXPAYEE; // 8 收款資料
			}
		}
	}

	/**
	 * 檢查長度
	 * 
	 * @param fileDetail
	 * @return
	 */
	@Override
	protected boolean validateDetailLength(FileDetail fileDetail) {

		// EXCEL 不檢核長度
		if (master.getFieldDelimiterType() == 3) {
			return true;
		}

		// 首筆 付款資料 長度超過不檢核
		if (fileDetail.getRowNo() == 1) {
			int lengthDetail = fileDetail.getLength();
			List<FileFormatDetail> formatHead = getFormatDetails(getFormatHead(fileDetail.getRowNo()));
			return checkPayerLength(lengthDetail, formatHead);
		}

		return super.validateDetailLength(fileDetail);
	}

	/**
	 * 取得FormatDetails
	 * 
	 * @param fieldGroup
	 * @return
	 */
	private List<FileFormatDetail> getFormatDetails(FieldGroup fieldGroup) {
		if (fieldGroup == null) {
			return null;
		}

		return detailGroup.get(fieldGroup);
	}

	/**
	 * 檢查長度
	 * 
	 * @param byteDetail
	 * @param formatHead
	 * @param formatCycle
	 * @return
	 */
	protected boolean checkPayerLength(int lengthDetail, List<FileFormatDetail> formatHead) {

		int lengthHead = getLength(formatHead);

		// 資料長度小於起始格式
		if (lengthDetail < lengthHead) {
			return false;
		}
		// 資料長度等於起始格式
		else if (lengthDetail == lengthHead) {
			return true;
		}
		// 資料長度大於起始格式
		else {

			return true;
		}
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.FXB12N;
	}

	@Override
	protected void validateDetailSize(FileDoc fileDoc) throws ActionException {
		// 總筆數不可超過「整批付款開放筆數=1000筆」
		int limit = 1000;
		if (fileDoc.getDetails().size() > (limit + 1)) {
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

		FileDetail header = fetchHeader();

		try {

			// 首筆
			if (header != null) {
				dataBuff = detailStringData(header);
			}

			// 明細
			for (FileDetail detail : fileDoc.getDetails()) {
				if (FieldGroup.FXPAYEE.equals(detail.getSection(0).getFieldGroup())) {
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

	private FileDetail fetchHeader() {

		for (FileDetail detail : fileDoc.getDetails()) {
			if (FieldGroup.FXPAYER.equals(detail.getSection(0).getFieldGroup())) {
				return detail;
			}
		}
		return null;
	}

}
