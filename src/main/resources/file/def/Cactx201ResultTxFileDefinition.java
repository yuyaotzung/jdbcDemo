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
 * 整批薪資入帳
 * </p>
 * 
 * @author Bear
 * @version 1.0, 2018/4/26
 * @see
 * @since
 */
public enum Cactx201ResultTxFileDefinition implements IFileDefinition {
	/** 欄位代號(類型, 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	BATCH_NO(1, 0, 24, "", "批號PBMT-BATCH-NO"),
	SERNO(1, 0, 5, "", "流水號"),
	PAY_CHANEL(1, 0, 1, "", "付款通路"),
	RECEIVE_BANK_CODE(1, 0, 7, "", "收款銀行代號"),
	PAYEE_ACCT_NO(1, 0, 14, "", "入薪員工入帳帳號"),
	PAYEE_UID(1, 0, 10, "", "入薪員工身分證號"),
	PAYEE_NAME(6, 0, 120, "", "入薪員工戶名"),
	TXN_AMT(1, 0, 13, "", "入帳金額"),
	FEE(1, 0, 5, "", "櫃員手續費/匯款手續費"),
	POST_SCRIPT(6, 0, 120, "", "附言"),
	RET_CODE(1, 0, 4, "", "薪資入帳回覆代碼"),
	FEP_RESULT(1, 0, 4, "", "匯款處理結果"),
	FEP_NO(1, 0, 7, "", "匯款編號"),
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

	Cactx201ResultTxFileDefinition(int type, int required, int length, String columnName, String name) {
		this.type = type;
		this.required = required;
		this.length = length;
		this.columnName = columnName;
		this.name = name;
	}

	@Override
	public String getColumnName() {
		return columnName;
	}

	@Override
	public String getId() {
		return name();
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getRequired() {
		return required;
	}

	@Override
	public int getType() {
		return type;
	}

}
