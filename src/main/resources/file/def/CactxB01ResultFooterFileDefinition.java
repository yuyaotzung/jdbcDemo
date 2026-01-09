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
package com.cosmos.file.def;

/**
 * <p>
 * FISC整批代收格式(尾錄)
 * </p>
 * 
 * @author Hank
 * @version 1.0, 2016/106
 * @see
 * @since
 */
public enum CactxB01ResultFooterFileDefinition implements IFileDefinition {

	/** 欄位代號(類型, 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	SECNO(1, 0, 1, "", "區別碼"),
	PUNIT(1, 0, 8, "", "委託單位"),
	RUNIT(1, 0, 8, "", "收件單位"),
	TXDATE(1, 0, 7, "", "指定入 /扣帳日"),
	PAYMENTTYPE(1, 0, 5, "", "費用類別"),
	CHK2(1, 0, 1, "", "固定為+號"),
	TOT_AMT(1, 0, 15, "", "交易總金額"),
	TOT_NUM(1, 0, 10, "", "交易總筆數"),
	CHK3(1, 0, 1, "", "固定為+號"),
	DEAL_AMT(1, 0, 15, "", "成交總金額"),
	DEAL_NUM(1, 0, 10, "", "成交總筆數"),
	CHK4(1, 0, 1, "", "固定為+號"),
	UNDEAL_AMT(1, 0, 15, "", "未成交總金額"),
	UNDEAL_NUM(1, 0, 10, "", "未成交總筆數"),
	FILLER(1, 0, 93, "RESERVED2", "保留欄");

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
	CactxB01ResultFooterFileDefinition(int type, int required, int length, String columnName, String name) {
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
