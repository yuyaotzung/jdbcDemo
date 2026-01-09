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
 * L9-00043B02
 */

/**
 * <p>
 * 外幣整批扣自入自、複委託扣帳-扣客戶入發動者(凱基) TX
 * </p>
 * 
 * @author Bear
 * @version 1.0, 2018/6/1
 * @see
 * @since
 */
public enum Csftx401AResultTxFileDefinition implements IFileDefinition {
	/** 欄位代號(類型 - 1:文字(X) 2:整數(9) 3:小數(9並顯示設定欄), 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	DATA_INDICATOR(1, 0, 2, "", "Data Indicator"),
	BATCH_NO(2, 0, 24, "", "批號PBMT-BATCH-NO"), // 前面補0
	SERNO(2, 0, 5, "", "流水號"),
	SEQ(2, 0, 6, "", "檔案內明細資料的序號"),
	PAYER_ACCT_NO(2, 0, 16, "", "轉出帳號"),
	PAYER_CURRENCY(1, 0, 3, "", "轉出帳號幣別"),
	PAYEE_ACCT_NO(2, 0, 16, "", "入帳帳號"),
	PAYEE_CURRENCY(1, 0, 3, "", "轉入帳號幣別"),
	TX_AMT(2, 0, 17, "", "交易金額"),
	TX_CURRENCY(1, 0, 3, "", "交易幣別"),
	COLUMN11(2, 0, 11, "", "轉出匯率"),
	COLUMN12(2, 0, 11, "", "轉入匯率"),
	COLUMN13(1, 0, 16, "", "匯率議價編號"),
	COLUMN14(1, 0, 12, "", "遠匯/換匯編號"),
	COLUMN15(2, 0, 11, "", "牌告/成交匯率"),
	COLUMN16(2, 0, 11, "", "成本匯率"),
	COLUMN17(2, 0, 18, "", "等值USD金額"),
	COLUMN18(2, 0, 11, "", "兌換USD匯率"),
	COLUMN19(1, 0, 1, "", "兌換USD買/賣匯"),
	COLUMN20(2, 0, 18, "", "等值TWD金額"),
	COLUMN21(2, 0, 11, "", "兌換TWD匯率"),
	COLUMN22(1, 0, 1, "", "兌換TWD買/賣匯"),
	COLUMN23(2, 0, 18, "", "等值本位幣轉出成本金額"),
	COLUMN24(2, 0, 11, "", "對本位幣轉出成本匯率"),
	COLUMN25(2, 0, 18, "", "等值本位幣轉入成本金額"),
	COLUMN26(2, 0, 11, "", "對本位幣轉入成本匯率"),
	COLUMN28(2, 0, 8, "", "訂約日期"),
	COLUMN27(1, 0, 1, "", "議價種類"),
	COLUMN29(1, 0, 3, "", "被報價幣幣別"),
	COLUMN30(1, 0, 1, "", "30	匯率類型"),
	PROMO_CODE(1, 0, 2, "", "提示碼"),
	REMARKS(1, 0, 24, "", "附註"),
	VALIDATE(1, 0, 1, "", "是否檢核代收付起迄日"),
	CHECK_ID(1, 0, 10, "", "檢核ID"),
	CHECK_NAME(1, 0, 60, "", "檢核戶名"),
	COLUMN36(1, 0, 300, "", "銷帳資料"),
	COLUMN37(1, 0, 1, "", "央媒申報"),
	COLUMN38(1, 0, 12, "", "外匯交易編號"),
	COLUMN39(1, 0, 2, "", "身分別"),
	COLUMN40(1, 0, 2, "", "證件類型"),
	COLUMN41(1, 0, 11, "", "證件號碼"),
	COLUMN42(2, 0, 8, "", "出生日期"),
	COLUMN43(2, 0, 8, "", "居留證有效日期"),
	COLUMN44(2, 0, 8, "", "居留證核發日期"),
	COLUMN45(2, 0, 3, "", "匯款分類"),
	COLUMN46(1, 0, 1, "", "匯款分類69X細分類"),
	COLUMN47(1, 0, 2, "", "匯款地國別"),
	COLUMN48(1, 0, 1, "", "有無申報書"),
	COLUMN49(1, 0, 1, "", "檢附核准函"),
	COLUMN50(1, 0, 1, "", "檢附相關文件"),
	COLUMN51(1, 0, 1, "", "結購結售外匯性質"),
	COLUMN52(1, 0, 1, "", "交易類型"),
	COLUMN73(1, 0, 1, "", "國外受款/匯款人身份別"),
	COLUMN74(1, 0, 2, "", "外國人國別"),
	COLUMN53(1, 0, 4, "", "指定銀行"),
	COLUMN54(1, 0, 7, "", "送件編號"),
	COLUMN55(1, 0, 1, "", "申報註記"),
	COLUMN56(1, 0, 2, "", "身分別"),
	COLUMN57(1, 0, 2, "", "證件類型"),
	COLUMN58(1, 0, 11, "", "證件號碼"),
	COLUMN59(2, 0, 8, "", "出生日期"),
	COLUMN60(2, 0, 8, "", "居留證有效日期"),
	COLUMN61(2, 0, 8, "", "居留證核發日期"),
	COLUMN62(2, 0, 3, "", "匯款分類"),
	COLUMN63(1, 0, 1, "", "匯款分類69X細分類"),
	COLUMN64(1, 0, 2, "", "匯款地國別"),
	COLUMN65(1, 0, 1, "", "有無申報書"),
	COLUMN66(1, 0, 1, "", "檢附核准函"),
	COLUMN67(1, 0, 1, "", "檢附相關文件"),
	COLUMN68(1, 0, 1, "", "結購結售外匯性質"),
	COLUMN69(1, 0, 1, "", "交易類型"),
	COLUMN75(1, 0, 1, "", "國外受款/匯款人身份別"),
	COLUMN76(1, 0, 2, "", "外國人國別"),
	COLUMN70(1, 0, 4, "", "指定銀行"),
	COLUMN71(1, 0, 7, "", "送件編號"),
	COLUMN72(1, 0, 1, "", "申報註記"),
	BANCS_STAT(2, 0, 4, "", "BaNCS中心執行結果BANCS-STAT"),
	JRNL_NO(2, 0, 9, "", "交易序號"),
	ACCT_NO(2, 0, 16, "", "實際交易帳號"),
	AMOUNT(2, 0, 17, "", "實際扣款金額"),
	CORR_STAT(2, 0, 9, "", "沖正交易"),
	//Add D format
	COLUMN80(1, 0, 20, "", "代收付流水號"),
	COLUMN81(1, 0, 24, "", "轉入方備註"),
	COLUMN82(2, 0, 2, "", "圈存理由"),
	COLUMN83(2, 0, 8, "", "原圈存日期"),
	COLUMN84(2, 0, 9, "", "原圈存交易序號"),
	COLUMN85(1, 0, 5, "", "券商集保代號"),
	
	FILLER(1, 0, 68, "", "FILLER"), ;

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

	Csftx401AResultTxFileDefinition(int type, int required, int length, String columnName, String name) {
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
