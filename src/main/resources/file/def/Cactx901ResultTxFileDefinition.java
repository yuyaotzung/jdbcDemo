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
 * TODO description
 * </p>
 * 
 * @author Bear
 * @version 1.0, 2018/4/16
 * @see
 * @since
 */
public enum Cactx901ResultTxFileDefinition implements IFileDefinition {

	/** 欄位代號(類型, 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	BATCH_NO(1, 0, 24, "", "批號PBMT-BATCH-NO"),
	SERNO(1, 0, 5, "", "流水號"),
	PAYER_ACCT_NO(1, 0, 16, "", "轉出帳號"),
	PAYEE_ACCT_NO(1, 0, 16, "", "入帳帳號"), // 清算帳號(入帳帳號)
	AMOUNT(1, 0, 17, "", "交易金額"),
	PROMO_CODE(1, 0, 2, "", "提示碼"),
	REMARKS(1, 0, 16, "", "存摺附註"),
	CHECK_ID(1, 0, 10, "", "檢核ID"),
	CHECK_NAME(1, 0, 60, "", "檢核戶名"),
	BANCS_STAT(1, 0, 4, "", "BaNCS中心執行結果"),
	JRNL_NO(1, 0, 9, "", "交易序號"),
	PAYER_DATA(1, 0, 300, "", "銷帳資料");

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

	Cactx901ResultTxFileDefinition(int type, int required, int length, String columnName, String name) {
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
