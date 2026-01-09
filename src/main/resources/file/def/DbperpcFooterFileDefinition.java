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
 * 整批匯款界面檔案規格 尾筆檔案格式
 * </p>
 * 
 * @author monica
 * @version 1.0, Feb 23, 2018
 * @see
 * @since
 */
public enum DbperpcFooterFileDefinition implements IFileDefinition {

	/** 欄位代號(類型, 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	totalCount(1, 0, 7, "", "轉帳總筆數(右靠,左補0) 數字右靠, 前導零"),
	txDate(1, 1, 6, "", "轉帳日期  數字 YYMMDD 格式, YY 為民國"),
	mark1(1, 0, 8, "", "空白字元"),
	totalAmt(1, 0, 11, "", "總金額(以元為單位,右靠左邊補0) 數字右靠, 前導零"),
	mark2(1, 0, 50, "", "空白字元"),
	payerName(1, 0, 50, "", "匯款人戶名"),
	mark3(1, 0, 14, "", "空白字元"),
	mark(1, 0, 1, "", "分隔符號*");

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

	DbperpcFooterFileDefinition(int type, int required, int length, String columnName, String name) {
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
