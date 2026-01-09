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
 * <p>  外幣整批扣自入自、複委託扣帳-扣客戶入發動者(凱基) FOOTER </p>
 *
 * @author  Bear
 * @version 1.0, 2018/6/5
 * @see	    
 * @since 
 */
public enum Csftx401AResultFooterFileDefinition implements IFileDefinition{
	/** 欄位代號(類型 - 1:文字(X) 2:整數(9) 3:小數(9並顯示設定欄), 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	DATA_INDICATOR(1, 0, 2, "", "DATA_INDICATOR"),
	TOTAL_COUNT(2, 0, 6, "", "明細總筆數"),
	TOTAL_AMT(2, 0, 17, "", "明細總金額"),//9(14)V9(3)
	FILLER(1, 0, 975, "", "FILLER"),
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
	
	Csftx401AResultFooterFileDefinition(int type, int required, int length, String columnName, String name) {
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



 