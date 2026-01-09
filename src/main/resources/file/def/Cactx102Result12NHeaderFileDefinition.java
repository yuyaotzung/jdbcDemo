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
 * 一扣多入Header
 * </p>
 * 
 * @author Bear
 * @version 1.0, 2018/4/9
 * @see
 * @since
 */
public enum Cactx102Result12NHeaderFileDefinition implements IFileDefinition {
	/** 欄位代號(類型, 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	BRANCH_NO(1, 0, 5, "", "交易使用的分行號碼"),
	TELLER_NO(1, 0, 8, "", "交易使用的行員號碼"),
	TERM_NO(1, 0, 3, "", "交易使用的端末機號"),
	TX_DATE(1, 0, 8, "", "交易日期"),
	PAY_ACCT(1, 0, 14, "", "付款人帳號"),
	PAY_NAME(6, 0, 120, "", "付款人戶名"),
	PAYER_UID(1, 0, 10, "", "付款人統編"),
	PAYER_RESULTSYSID(1, 0, 6, "", "一扣結果系統代碼"),
	PAYER_RESULT(1, 0, 8, "", "一扣結果");

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

	Cactx102Result12NHeaderFileDefinition(int type, int required, int length, String columnName, String name) {
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
