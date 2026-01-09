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
 * <p> 證券款檔案上傳至代收付 Header檔案格式</p>
 *
 * @author  monica
 * @version 1.0, Feb 23, 2018
 * @see	    
 * @since 
 */
public enum PBSResultHeaderFileDefinition implements IFileDefinition {
	
	/** 欄位代號(類型, 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	BATCH_NO(1, 0, 24, "", "批號PBMT-BATCH-NO"),
	HEAD_SEC_AGENT_CODE(1, 0, 5, "", "母公司券商代號"),
	SEC_AGENT_CODE(1, 0, 5, "", "委託單位代號(券商代號)"),
	ACCOUNT_NO(1, 0, 16, "", "證券清算帳號"),
	BRANCH_NO(1, 0, 5, "", "分行號碼"),
	TELLER_NO(1, 0, 8, "", "行員號碼"),
	TERM_NO(1, 0, 3, "", "交易端末機號"),
	TOTAL_COUNT(1, 0, 15, "", "轉帳總筆數"),
	TX_DATE(1, 0, 8, "", "轉帳日期(扣款日期)"),
	TRANS_TYPE(1, 0, 3, "", "轉帳業務種類");
	
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
	
	PBSResultHeaderFileDefinition(int type, int required, int length, String columnName, String name) {
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



 