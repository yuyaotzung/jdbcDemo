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
 * 多扣多入Tx
 * </p>
 * 
 * @author Bear
 * @version 1.0, 2018/4/9
 * @see
 * @since
 */
public enum Cactx102ResultN2NTxFileDefinition implements IFileDefinition {
	/** 欄位代號(類型, 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	BATCH_NO(1, 0, 24, "", "批號PBMT-BATCH-NO"),
	SERNO(1, 0, 5, "", "流水號"),
	PAY_CHANEL(1, 0, 1, "", "付款通路"),
	PAYER_ACCOUNT_NO(1, 0, 14, "", "付款人帳號"),
	PAYER_NAME(6, 0, 120, "", "付款人戶名"),
	PAYER_UID(1, 0, 10, "", "付款人統編"),
	RECEIVE_BANK_CODE(1, 0, 7, "", "收款銀行代號"),
	ROLL_IN_ACCT(1, 0, 16, "", "轉入帳號"),
	AMOUNT(1, 0, 17, "", "交易金額"), // 9(14)V9(3)
	PROMO_CODE(1, 0, 2, "", "提示碼"),
	REMARKS(1, 0, 24, "", "存摺附註"),
	CHECK_ID(1, 0, 10, "", "檢核ID"),
	CHECK_NAME(6, 0, 120, "", "檢核戶名"),
	FEE(1, 0, 5, "", "櫃員手續費/匯款手續費"),
	POST_SCRIPT(6, 0, 120, "", "附言"),
	BANCS_STAT(1, 0, 4, "", "BaNCS中心執行結果BANCS-STAT"),
	FEP_RESULT(1, 0, 4, "", "匯款處理結果"),
	FEP_NO(1, 0, 7, "", "匯款編號"),
	JRNL_NO(1, 0, 9, "", "交易序號"),
	REMARKS1(1, 0, 24, "", "轉入方備註");

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

	Cactx102ResultN2NTxFileDefinition(int type, int required, int length, String columnName, String name) {
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
