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
 * 薪資臨櫃 Tx檔案格式
 * </p>
 * 
 * @author monica
 * @version 1.0, Feb 23, 2018
 * @see
 * @since
 */
public enum SalaryTxFileDefinition implements IFileDefinition {

	/** 欄位代號(類型, 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	payeeAccountNo(1, 1, 14, "", "收款帳號"),
	payeeUid(1, 0, 11, "", "收款統編"),
	mark(1, 0, 1, "", "薪資轉帳代碼為'S',股息轉帳代碼為'A'"),
	txDate(1, 1, 6, "", "轉帳日期 (YYMMDD), 不可為假日 需>=今日"),
	transtype(1, 0, 4, "", "入帳為 '1115', 扣帳為 '1315'"),
	txAmt(1, 0, 11, "", "交易金額"),
	payerUid(1, 0, 8, "", "公司統一編號"),
	mark1(1, 0, 3, "", "業務種類 (XYY)"),
	payeeName(1, 0, 16, "", "戶名(可輸入8個中文)"),
	mark2(1, 0, 6, "", "全部補 0");

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

	SalaryTxFileDefinition(int type, int required, int length, String columnName, String name) {
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
