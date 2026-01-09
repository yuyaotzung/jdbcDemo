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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cosmos.file.FileFormatDetail;
import com.cosmos.file.FileFormatNew;
import com.cosmos.file.bo.FileDetail;
import com.cosmos.file.bo.FileDoc;
import com.cosmos.file.bo.FileSection;
import com.cosmos.persistence.b2c.entity.FileFormatDetailEntity;
import com.cosmos.persistence.b2c.entity.FileFormatFieldEntity;
import com.cosmos.tx.AchDetailDoc;
import com.cosmos.type.AchTxType;
import com.cosmos.type.AchType;
import com.cosmos.type.FieldGroup;
import com.cosmos.type.TaskType;
import com.cosmos.type.TxBatchType;
import com.ibm.tw.commons.exception.ActionException;
import com.ibm.tw.commons.util.ConvertUtils;

/**
 * <p>
 * ACH代收作業檔案解析
 * </p>
 * 
 * @author BearChen
 * @version 1.0, 2013/9/14
 * @see
 * @since
 */
public class Ach01FileFormat extends FileFormatNew {

	public Ach01FileFormat(String taskId) {
		super(0, taskId, TxBatchType.UNKNOWN.getCode());
		detailGroup = loadDetailGroup();
	}

	private final TaskType taskType = TaskType.ACH01;

	/** 明細錄資料 */
	private List<AchDetailDoc> details = new ArrayList<AchDetailDoc>();

	/**
	 * 載入檔案定義
	 * 
	 * @return
	 */
	private Map<FieldGroup, List<FileFormatDetail>> loadDetailGroup() {
		Map<FieldGroup, List<FileFormatDetail>> detailGroup = new HashMap<FieldGroup, List<FileFormatDetail>>();

		// Header
		FieldGroup fieldGroup = FieldGroup.HEADER;
		detailGroup.put(fieldGroup, toFileFormatDetails(taskType, fieldGroup, Ach01MasterFileSection.values()));

		// Tx
		fieldGroup = FieldGroup.TX;
		detailGroup.put(fieldGroup, toFileFormatDetails(taskType, fieldGroup, Ach01TxFileSection.values()));

		// Footer
		fieldGroup = FieldGroup.FOOTER;
		detailGroup.put(fieldGroup, toFileFormatDetails(taskType, fieldGroup, Ach01FooterFileSection.values()));

		return detailGroup;
	}

	/**
	 * 轉換為FileFormatDetail
	 * 
	 * @param taskType
	 * @param fieldGroup
	 * @param sections
	 * @return
	 */
	private List<FileFormatDetail> toFileFormatDetails(TaskType taskType, FieldGroup fieldGroup, IAchFileDefinition[] sections) {
		List<FileFormatDetail> fileFormatDetails = new ArrayList<FileFormatDetail>();

		int orderNo = 1;
		int startPosition = 0;
		for (IAchFileDefinition section : sections) {
			FileFormatDetail fileFormatDetail = toFileFormatDetail(taskType, fieldGroup, section.getId(), section.getName(), orderNo++, section.getType(), section.getRequired(), section.getLength(), startPosition);
			fileFormatDetails.add(fileFormatDetail);
			startPosition += section.getLength();
		}

		return fileFormatDetails;
	}

	/**
	 * 轉換為FileFormatDetail
	 * 
	 * @param taskType
	 * @param fieldGroup
	 * @param fieldId
	 * @param fieldName
	 * @param orderNo
	 * @param type
	 * @param length
	 * @param startPosition
	 * @return
	 */
	private FileFormatDetail toFileFormatDetail(TaskType taskType, FieldGroup fieldGroup, String fieldId, String fieldName, int orderNo, int type, int required, int length, int startPosition) {
		// detail
		FileFormatDetailEntity detail = new FileFormatDetailEntity();
		detail.setFieldId(fieldId);
		detail.setFieldName(fieldName);
		detail.setOrderNo(orderNo);
		detail.setLength(length);
		detail.setStartPosition(startPosition);
		detail.setEndPosition(startPosition + detail.getLength() - 1);

		// field
		FileFormatFieldEntity field = new FileFormatFieldEntity();
		field.setFieldGroup(fieldGroup.getCode());
		field.setFieldId(fieldId);
		field.setTaskType(taskType.getCode());
		field.setType(type);
		field.setDefaultLength(length);
		field.setRequired(required);

		FileFormatDetail fileFormatDetail = new FileFormatDetail(taskType, detail, field);
		return fileFormatDetail;
	}

	/**
	 * 判斷是否為資料頭一筆
	 * 
	 * @param rowNo
	 * @return
	 */
	@Override
	public FieldGroup getFormatHead(int rowNo) {
		if (rowNo == 1) {
			return FieldGroup.HEADER;
		}
		else if (rowNo == getRows()) {
			return FieldGroup.FOOTER;
		}
		else {
			return FieldGroup.TX;
		}
	}

	@Override
	public FieldGroup getFormatCycle(int rowNo) {
		return null;
	}

	@Override
	public TaskType getTaskType() {
		return taskType;
	}

	@Override
	protected void validateDetailSize(FileDoc fileDoc) throws ActionException {

	}

	@Override
	protected void validateCustom(FileDoc fileDoc) throws ActionException {

	}

	@Override
	protected void validateDetailContent(FileDetail fileDetail) throws ActionException {

	}

	@Override
	protected void putData(FileDoc fileDoc) {

		// 將檔案資料轉為整批多扣多入明細資料 ,i為行號
		for (int i = 2; i < fileDoc.getDetails().size(); i++) {
			AchDetailDoc detail = new AchDetailDoc();
			putDetail(detail, fileDoc.getDetail(i), i);
			// putHeadData(detail, fileDoc.getFirstDetail());
			details.add(detail);
		}
	}

	/**
	 * 
	 * @param txDetailDoc
	 * @param fileDetail
	 */
	private void putHeadData(AchDetailDoc txDetailDoc, FileDetail fileDetail) {

		putHead(txDetailDoc, fileDetail.getFirstSection());
	}

	/**
	 * 
	 * @param detail
	 * @param header
	 */
	private void putHead(AchDetailDoc detail, FileSection header) {

		String tDateFieldValue = header.getField(Ach01MasterFileSection.P_TDATE.getId()).getStringValue();

		// 代收提回時檔案下載無交易日
		// int iYear = ConvertUtils.str2Int(tDateFieldValue.substring(0, 4)) +
		// 1911;
		// int iMonth = ConvertUtils.str2Int(tDateFieldValue.substring(4, 6));
		// int iDay = ConvertUtils.str2Int(tDateFieldValue.substring(6));
		//
		// Date txDate = DateUtils.getDate(iYear, iMonth, iDay);
		// detail.setTxDate(txDate);

		detail.settDate(tDateFieldValue);

		String tTimeFieldValue = header.getField(Ach01MasterFileSection.P_TTIME.getId()).getStringValue();
		detail.settTime(tTimeFieldValue);

		String sorgFieldValue = header.getField(Ach01MasterFileSection.P_SORG.getId()).getStringValue();
		detail.setP_Sorg(sorgFieldValue);

		String rorgFieldValue = header.getField(Ach01MasterFileSection.P_RORG.getId()).getStringValue();
		detail.setP_Rorg(rorgFieldValue);

		// String fillerFieldValue =
		// header.getField(NewAchP01MasterFileSection.FILLER.getId()).getStringValue();
		// detail.setFiller(fillerFieldValue);

	}

	private void putDetail(AchDetailDoc txDetailDoc, FileDetail fileDetail, int i) {

		putDocDetail(txDetailDoc, fileDetail.getFirstSection(), i);

	}

	private void putDocDetail(AchDetailDoc detail, FileSection section, int row) {

		detail.setSn(row);

		// 交易型態
		int type = AchType.UNKNOWN.getType();
		String typeFieldValue = section.getField(Ach01TxFileSection.P_TYPE.getId()).getStringValue();
		if (StringUtils.equals(typeFieldValue, "N")) {
			type = AchType.N.getType();
		}
		else if (StringUtils.equals(typeFieldValue, "R")) {
			type = AchType.R.getType();
		}

		detail.setType(type);

		// 交易類別
		int txType = AchTxType.UNKNOWN.getType();

		String txTypeFieldValue = section.getField(Ach01TxFileSection.P_TXTYPE.getId()).getStringValue();

		if (StringUtils.equals(txTypeFieldValue, "SC")) {
			txType = AchTxType.SC.getType();
		}
		else if (StringUtils.equals(txTypeFieldValue, "SD")) {
			txType = AchTxType.SD.getType();
		}
		else if (StringUtils.equals(txTypeFieldValue, "RG")) {
			txType = AchTxType.RG.getType();
		}

		detail.setTxType(txType);

		// 交易代號
		String txIdFieldValue = section.getField(Ach01TxFileSection.P_TXID.getId()).getStringValue();
		detail.setTxId(txIdFieldValue);

		// 交易序號
		String seqFieldValue = section.getField(Ach01TxFileSection.P_SEQ.getId()).getStringValue();
		detail.setSeq(seqFieldValue);

		// 提出行代號
		String pBankFieldValue = section.getField(Ach01TxFileSection.P_PBANK.getId()).getStringValue();
		detail.setpBank(pBankFieldValue);

		// 發動者帳號
		String pClnoFieldValue = section.getField(Ach01TxFileSection.P_PCLNO.getId()).getStringValue();
		detail.setpClno(pClnoFieldValue);

		// 提回行代號
		String rBankFieldValue = section.getField(Ach01TxFileSection.P_RBANK.getId()).getStringValue();
		detail.setrBank(rBankFieldValue);

		// 收受者帳號
		String rClnoFieldValue = section.getField(Ach01TxFileSection.P_RCLNO.getId()).getStringValue();
		detail.setrClno(rClnoFieldValue);

		// 金額
		String amtFieldValue = section.getField(Ach01TxFileSection.P_AMT.getId()).getStringValue();
		detail.setTxAmt(ConvertUtils.str2BigDecimal(amtFieldValue));

		// 退件理由代號
		String rCodeFieldValue = section.getField(Ach01TxFileSection.P_RCODE.getId()).getStringValue();
		if (StringUtils.isNotBlank(rCodeFieldValue)) {
			detail.setrCode(rCodeFieldValue);
		}

		// 提示交換次序
		String schdFieldValue = section.getField(Ach01TxFileSection.P_SCHD.getId()).getStringValue();
		detail.setSchd(schdFieldValue);

		// 發動者統一編號
		String cIdFieldValue = section.getField(Ach01TxFileSection.P_CID.getId()).getStringValue();
		detail.setcId(cIdFieldValue);

		// 收受者統一編號
		String pidFieldValue = section.getField(Ach01TxFileSection.P_PID.getId()).getStringValue();
		detail.setpId(pidFieldValue);

		// 上市上櫃公司代號
		String sidFieldValue = section.getField(Ach01TxFileSection.P_SID.getId()).getStringValue();
		detail.setsId(sidFieldValue);

		// 原提示交易日期
		String pDateFieldValue = section.getField(Ach01TxFileSection.P_PDATE.getId()).getStringValue();
		if (StringUtils.isNotBlank(pDateFieldValue)) {
			detail.setpDate(pDateFieldValue);
		}

		// 原提示交易序號
		String pSeqFieldValue = section.getField(Ach01TxFileSection.P_PSEQ.getId()).getStringValue();
		if (StringUtils.isNotBlank(pSeqFieldValue)) {
			detail.setpSeq(pSeqFieldValue);
		}

		// 原提示交換次序
		String pschdFieldValue = section.getField(Ach01TxFileSection.P_PSCHD.getId()).getStringValue();
		if (StringUtils.isNotBlank(pschdFieldValue)) {
			detail.setpSchd(pschdFieldValue);
		}

		// 用戶號碼
		String cNoFieldValue = section.getField(Ach01TxFileSection.P_CNO.getId()).getStringValue();
		if (StringUtils.isNotBlank(cNoFieldValue)) {
			detail.setcNo(cNoFieldValue);
		}

		// 發動者專用區
		String noteFieldValue = section.getField(Ach01TxFileSection.P_NOTE.getId()).getStringValue();
		if (StringUtils.isNotBlank(noteFieldValue)) {
			// detail.setNote(noteFieldValue);
			detail.setAchSeqKey(ConvertUtils.str2Long(noteFieldValue));
		}

		// 存摺摘要
		String memoFieldValue = section.getField(Ach01TxFileSection.P_MEMO.getId()).getStringValue();
		if (StringUtils.isNotBlank(memoFieldValue)) {
			detail.setMemo(memoFieldValue);
		}

		// 備用
		String fillerFieldValue = section.getField(Ach01TxFileSection.P_FILLER.getId()).getStringValue();
		if (StringUtils.isNotBlank(fillerFieldValue)) {
			detail.setFiller(fillerFieldValue);
		}

	}

	public List<AchDetailDoc> getDetails() {
		return details;
	}

	public void setDetails(List<AchDetailDoc> details) {
		this.details = details;
	}

}
