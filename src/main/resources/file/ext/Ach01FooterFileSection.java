/*
 * ===========================================================================
 * IBM Confidential
 * AIS Source Materials
 *
 *
 * (C) Copyright IBM Corp. 2011.
 *
 * ===========================================================================
 */
package com.cosmos.file.ext;

import com.cosmos.file.ext.IAchFileDefinition;

/**
 * <p>
 * ACH提出電子檔檔案規格
 * </p>
 *
 * @author shawn
 * @version 1.0, 2013/9/26
 * @see
 * @since
 */
public enum Ach01FooterFileSection  implements IAchFileDefinition {

	/** 欄位代號(類型, 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	EOF(1,0, 3, "", "尾錄別"),
	CDATA(1,0, 6, "", "資料代號"),
	TDATE(1,0, 8, "TDATE", "處理日期"),
	SORG(1,0, 7, "SORG", "發送單位代號"),
	RORG(1,0, 7, "RORG", "接收單位代號"),
	TCOUNT(1,0, 8, "", "總筆數"),
	TAMT(1,0, 16, "", "總金額"),
	FILLER(1,0, 105, "Filler", "備用");

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

	/**
	 * Constructor
	 *
	 * @param code
	 * @param memo
	 */
	Ach01FooterFileSection(int type, int required, int length, String columnName, String name) {
		this.type = type;
		this.required = required;
		this.length = length;
		this.columnName = columnName;
		this.name = name;
	}

	/**
	 * 取得id
	 *
	 * @return
	 */
	public String getId() {
		return name();
	}

	/**
	 * 取得 type
	 *
	 * @return 傳回 type
	 */
	public int getType() {
		return type;
	}

	/**
	 * 取得 length
	 *
	 * @return 傳回 length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * 取得 name
	 *
	 * @return 傳回 name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 取得 length
	 * 
	 * @return 傳回 length
	 */
	public int getRequired() {
		return required;
	}

}
