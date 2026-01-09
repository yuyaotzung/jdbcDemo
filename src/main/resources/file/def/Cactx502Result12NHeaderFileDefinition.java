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
 * 外幣一扣多入 Header檔案格式
 * </p>
 * 
 * @author monica
 * @version 1.0, Jul 9, 2018
 * @see
 * @since
 */
public enum Cactx502Result12NHeaderFileDefinition implements IFileDefinition {

	/** 欄位代號(類型 - 1:文字(X) 2:整數(9) 3:小數(9並顯示設定欄), 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	BRANCH_NO(2, 0, 5, "", "交易使用的分行號碼(通常為主辦行)"),
	TELLER_NO(2, 0, 8, "", "交易使用的行員號碼"),
	TERM_NO(2, 0, 3, "", "交易使用的端末機號"),
	TX_DATE(2, 0, 8, "", "交易日期"),
	PAY_ACCT(2, 0, 14, "", "付款人帳號"),
	PAY_ACCT_CURRCODE(1, 0, 3, "", "付款帳號幣別"),
	RateType1(2, 0, 2, "", "轉出匯率類型"),
	PAY_NAME(1, 0, 76, "", "付款人戶名"),
	PAYER_RESULTSYSID(1, 0, 6, "", "一扣結果系統代碼"),
	PAYER_RESULT(1, 0, 8, "", "一扣結果"),
	CHG_ACNO(1, 0, 14, "", "付款人手續費帳號"),
	CHGCCY(1, 0, 3, "", "付款人手續費帳號幣別");
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

	private Cactx502Result12NHeaderFileDefinition(int type, int required, int length, String columnName, String name) {
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
