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
 * FISC整批代收格式(明細錄)
 * </p>
 * 
 * @author Hank
 * @version 1.0, 2016/106
 * @see
 * @since
 */
public enum CactxB01ResultTxFileDefinition implements IFileDefinition {

	/** 欄位代號(類型, 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	SECNO(1, 0, 1, "", "區別碼"),
	PUNIT(1, 0, 8, "", "委託單位"),
	RUNIT(1, 0, 8, "", "收件單位"),
	TXDATE(1, 0, 7, "", "指定入 /扣帳日"),
	PAYMENT_TYPE(1, 0, 5, "", "費用類別"),
	TXNO(1, 0, 10, "", "交易序號"),
	CHK1(1, 0, 1, "", "固定為+號"),
	TXAMT(1, 0, 13, "", "交易金額"),
	PUNIT_SEC(1, 0, 1, "", "委託單位區別"),
	PUNIT_NO(1, 0, 8, "", "委託單位代號"),
	EXCDATE(1, 0, 7, "", "實際入/扣帳日期"),
	STATUSCODE(1, 1, 4, "", "回應代碼"),
	PAYER_BANKID(1, 0, 7, "", "轉帳行代碼"),
	PAYER_ACCNO(1, 0, 16, "", "轉帳號"),
	PAYER_ID(1, 0, 10, "", "帳戶ID"),
	PAYMENT_ID(1, 0, 16, "", "銷帳編號"),
	PAYMENT_CODE(1, 0, 4, "", "費用代號"),
	BANK_DATA(1, 0, 20, "", "銀行專用區"),
	BU_DATA(1, 0, 54, "", "事業單位專用資料區");

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
	CactxB01ResultTxFileDefinition(int type, int required, int length, String columnName, String name) {
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
