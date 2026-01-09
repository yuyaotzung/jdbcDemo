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
 * 證券臨櫃 Tx檔案格式
 * </p>
 * 
 * @author monica
 * @version 1.0, Feb 23, 2018
 * @see
 * @since
 */
public enum StockTxFileDefinition implements IFileDefinition {

	/** 欄位代號(類型, 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	payerAccountNo(1, 1, 14, "", "轉帳帳號 不足14碼右靠左補0"),
	payerUid(1, 1, 11, "", "客戶之統一編號(左靠右補空白,空白之ASCII值為'32')"),
	payeeTransId(1, 1, 1, "", "轉帳代碼 為'B''"),
	txDate(1, 1, 6, "", "轉帳日期 (YYMMDD, 交割次營業日)"),
	payeeAccountTypeId(1, 1, 4, "", "入帳為 '1115', 扣帳為 '1315'"),
	txAmt(1, 1, 11, "", "交易金額"),
	payeeUid(1, 1, 8, "", "券商之統一編號  OR  公司統一編號"),
	pbsOwnerId(1, 0, 6, "", "客戶證券戶號"),
	shopId(1, 0, 4, "", "營業員代號"),
	tel(1, 0, 10, "", "電話號碼"),
	stocksId(1, 0, 5, "", "股票代碼");

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

	StockTxFileDefinition(int type, int required, int length, String columnName, String name) {
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
