/*
 * ===========================================================================
 * IBM Confidential
 * AIS Source Materials
 * 
 * 
 * (C) Copyright IBM Corp. 2018.
 *
 * ===========================================================================
 */
package com.cosmos.file.def;

/**
 * <p>
 * 證券款檔案上傳至代收付 Tx檔案格式
 * </p>
 * 
 * @author monica
 * @version 1.0, Feb 23, 2018
 * @see
 * @since
 */
public enum PBSResultTxFileDefinition implements IFileDefinition {

	/** 欄位代號(類型, 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	BATCH_NO(1, 0, 24, "", "批號BATCH-NO"),
	SERNO(1, 0, 5, "", "流水號PBMT-SERNO"),
	PAYER_ACCOUNT_NO(1, 0, 14, "", "客戶銀行轉帳帳號"),
	PAYER_ID_NO(1, 0, 10, "", "客戶之統一編號"),
	PAYEE_ACCOUNT_TYPE_ID(1, 0, 4, "", "入扣帳種類"),
	TX_AMT(1, 0, 11, "", "交易金額"),
	TRANS_TYPE(1, 0, 3, "", "轉帳業務種類"),
	SHOP_ID(1, 0, 10, "", "營業員代號"),
	PAYEE_ACCOUNT_NO(1, 0, 7, "", "證券戶號(客戶)"),
	STOCK_ID(1, 0, 7, "", "股票代號"),
	PROMO_CODE(1, 0, 2, "", "存摺摘要"),
	REMARK(1, 0, 16, "", "附註"),
	ACCOUNT_ID(1, 0, 10, "", "帳號ID"),
	ACCOUNT_NAME(1, 0, 60, "", "帳號戶名"),
	PHONE1(1, 0, 14, "", "聯絡電話1"),
	PHONE2(1, 0, 14, "", "聯絡電話2"),
	TXN_STATUS(1, 0, 2, "", "執行結果"),
	ACCOUNT_BAL(1, 0, 17, "", "帳號餘額"),
	ERROR_CODE(1, 0, 4, "", "錯誤代碼"),
	JRNL_NO(1, 0, 9, "", "交易序號");

	/** 資料類型 */
	private int type;

	/** 是否必填 */
	private int required;

	/** 檔案格式長度 */
	private int length;

	/** 資料庫存放欄位 */
	private String columnName;

	/** 欄位名稱 說明 */
	private String name;

	PBSResultTxFileDefinition(int type, int required, int length, String columnName, String name) {
		this.type = type;
		this.required = required;
		this.length = length;
		this.columnName = columnName;
		this.name = name;
	}

	@Override
	public String getId() {
		return name();
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public int getRequired() {
		return required;
	}

	@Override
	public String getColumnName() {
		return columnName;
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public String getName() {
		return name;
	}

}
