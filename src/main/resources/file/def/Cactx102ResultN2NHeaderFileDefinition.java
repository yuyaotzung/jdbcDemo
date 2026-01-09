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
 * <p> 多扣多入Header </p>
 *
 * @author  Bear
 * @version 1.0, 2018/4/9
 * @see	    
 * @since 
 */
public enum Cactx102ResultN2NHeaderFileDefinition implements IFileDefinition{
	/** 欄位代號(類型, 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	BRANCH_NO(1, 0, 5, "", "交易使用的分行號碼"),
	TELLER_NO(1, 0, 8, "", "交易使用的行員號碼"),
	TERM_NO(1, 0, 3, "", "交易使用的端末機號"),
	TX_DATE(1, 0, 8, "", "交易日期")
	;
	
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
	
	Cactx102ResultN2NHeaderFileDefinition(int type, int required, int length, String columnName, String name) {
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



 