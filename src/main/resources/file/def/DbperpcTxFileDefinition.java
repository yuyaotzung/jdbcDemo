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
 * 整批匯款界面檔案規格 Tx檔案格式
 * </p>
 * 
 * @author monica
 * @version 1.0, Feb 23, 2018
 * @see
 * @since
 */
public enum DbperpcTxFileDefinition implements IFileDefinition {

	/** 欄位代號(類型, 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	payeeBankId(1, 1, 7, "", "解款行代號 數字"),
	payeeAccountNo(1, 1, 14, "", "帳      號 數字左靠右補空白"),
	txAmt(1, 1, 11, "", "交易金額 數字右靠,前導零"),
	payeeName(1, 0, 50, "", "收款人戶名 中文碼左靠右補空白"),
	payerName(1, 0, 50, "", "滙款人戶名 中文碼左靠右補空白"),
	payeeMemo(1, 0, 14, "", "附      言"),
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

	DbperpcTxFileDefinition(int type, int required, int length, String columnName, String name) {
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
