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
 * <p> TODO description</p>
 *
 * @author  Bear 
 * @version 1.0, 2018/4/16
 * @see	    
 * @since 
 */
public enum Cactx901ResultFooterFileDefinition implements IFileDefinition{
	
	/** 欄位代號(類型, 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	TOTAL_COUNT(1, 0, 6, "", "上傳總筆數"),
	TOTAL_AMT(1, 0, 17, "", "上傳總金額")//9(14)V9(3)
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
	
	Cactx901ResultFooterFileDefinition(int type, int required, int length, String columnName, String name) {
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



 