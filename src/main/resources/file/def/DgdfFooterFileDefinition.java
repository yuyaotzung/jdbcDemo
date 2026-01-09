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
 * 證券臨櫃檔案格式尾筆檔案格式
 * </p>
 * 
 * @author monica
 * @version 1.0, Feb 23, 2018
 * @see
 * @since
 */
public enum DgdfFooterFileDefinition implements IFileDefinition {

	/** 欄位代號(類型, 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	mark(1, 0, 14, "", "全部填入0"),
	totalCount(1, 0, 11, "", "轉帳總筆數(右靠,左補0)"),
	mark1(1, 0, 1, "", "轉帳代碼 B"),
	txDate(1, 1, 6, "", "轉帳日期 (YYMMDD)"),
	payeeAccountTypeId(1, 0, 4, "", "入帳為 1115, 扣帳為 1315"),
	totalAmt(1, 0, 11, "", "轉帳總金額(以元為單位,右靠左邊補0)"),
	payerUid(1, 0, 8, "", "公司統一編號"),
	transType(1, 0, 3, "", "轉帳業務種類"),
	mark2(1, 0, 22, "", "全部填入0");

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

	DgdfFooterFileDefinition(int type, int required, int length, String columnName, String name) {
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
