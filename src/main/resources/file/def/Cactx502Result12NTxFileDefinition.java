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
 * 外幣一扣多入 Tx檔案格式
 * </p>
 * 
 * @author monica
 * @version 1.0, Jul 9, 2018
 * @see
 * @since
 */
public enum Cactx502Result12NTxFileDefinition implements IFileDefinition {

	/** 欄位代號(類型 - 1:文字(X) 2:整數(9) 3:小數(9並顯示設定欄), 是否必填 ,長度, 資料庫欄位, 欄位名稱(說明)) */
	PBMT_BATCH_NO(2, 0, 24, "", "批號PBMT-BATCH-NO"),
	PBMT_SERNO(2, 0, 5, "", "流水號"),
	PAY_CHANEL(1, 0, 1, "", "付款通路"),
	RECEIVE_BANK_CODE(2, 0, 7, "", "收款銀行代號"),

	ROLL_IN_ACCT(1, 0, 34, "", "轉入帳號"),

	ROLL_IN_ACCT_CURRCODE(1, 0, 3, "", "轉入帳號幣別"),
	AMOUNT(2, 0, 17, "", "交易金額"), // 9(14)V9(3)
	PROMO_CODE(1, 0, 2, "", "提示碼"),
	REMARKS(1, 0, 16, "", "存摺附註"),
	PAYEE_NAME(1, 0, 70, "", "收款人戶名"),
	FEE(2, 0, 5, "", "櫃員手續費/匯款手續費"),
	CBRClass1(1, 0, 3, "", "匯款分類一1"),
	sourceOfFundSub(1, 0, 1, "", "匯款分類細項"),
	CBRSubCode1(1, 0, 1, "", "匯款分類69x細分類1"),
	CBRREQ1(1, 0, 1, "", "申報註記1"),
	CBRTxnType1(1, 0, 1, "", "交易類型1"),
	CBRCountry1(1, 0, 2, "", "付款人國別1"),
	CBRCountry2(1, 0, 2, "", "收款人國別2"), //轉帳-收款人國別,匯款-匯款地國別
	CBRREQ2(1, 0, 1, "", "申報註記2"),
	POST_SCRIPT(1, 0, 76, "", "附言"),
	BANCS_STAT(2, 0, 4, "", "BaNCS中心執行結果BANCS-STAT"),
	NEFX_STAT(1, 0, 6, "", "匯款處理結果(他行)"),
	ESB_STAT(1, 0, 8, "", "ESB_STAT"),
	TxntNo(1, 0, 16, "", "交易編號"),
	JRNL_NO(2, 0, 9, "", "交易序號"),
	payeeBankName(1, 0, 70, "", "受款銀行名稱"),
	payeeBankAddr(1, 0, 70, "", "受款銀行地址"),
	commission(2, 0, 13, "", "手續費扣款幣金額"), // 9(11).9(02)
	commissionBcu(2, 0, 13, "", "手續費本位幣金額"), // 9(11).9(02)
	postage(2, 0, 13, "", "郵電費扣款幣金額"), // 9(11).9(02)
	postageBcu(2, 0, 13, "", "郵電費本位幣金額"), // 9(11).9(02)
	totChg(2, 0, 13, "", "費用總金額"), // 9(11).9(02)
	totPayAmt(2, 0, 15, "", "總扣款金額"), // 9(13).9(02)
	equUsdAmt(2, 0, 15, "", "等值美金金額"), // 9(13).9(02)
	equTwdAmt(2, 0, 15, "", "等值台幣金額"), // 9(13).9(02)
	payeeEmail(1, 0, 200, "", "收款人Email"),
	payeeAddr(1, 0, 70, "", "受款人地址"),
	payeeTel(1, 0, 35, "", "受款人電話"),
	cnaps(1, 0, 14, "", "大陸現代化支付系統帳號"),
	paymentDetails(1, 0, 140, "", "附言"),
	cbTradeType(1, 0, 1, "", "外匯性質"),
	oppoKind(1, 0, 1, "", "受款人身份別"),
	payeeBankSwift(1, 0, 11, "", "受款銀行SWIFT"),
	payeeBankAcno(1, 0, 34, "", "受款銀行清算代碼,與CNAPS只能其中一個有值。"),
	intrBankSwift(1, 0, 11, "", "中間銀行SWIFT"),
	deductFeeFlag(1, 0, 1, "", "內扣外繳,Y：內扣。非Y：外繳"),
	applicationId(1, 0, 4, "", "發動端平台代號"), // CB:企網銀
	custName(1, 0, 70, "", "客戶英文名稱"),
	custAddr(1, 0, 70, "", "客戶英文地址"),
	txntType(1, 0, 1, "", "交易類型"), // 1. 即時自行匯款,2. 即時跨行匯款,3. 預約自行匯款,4. 預約跨行匯款,5.
	// 整批自行匯款
	bsnUnit(1, 0, 4, "", "客戶所屬分行別"),
	validDate(1, 0, 8, "", "交易生效日"),
	remitCcy(1, 0, 3, "", "匯款幣別"),
	payCcy(1, 0, 3, "", "付款幣別"),
	exCntrno(1, 0, 16, "", "議價編號"),
	exrate(2, 0, 12, "", "成交匯率"), // 9(05).9(07)
	spreadBSpot(2, 0, 12, "", "買匯匯率優惠"), // 9(05).9(07)
	spreadSSpot(2, 0, 12, "", "賣匯匯率優惠"), // 9(05).9(07)
	sourceOfFund(1, 0, 3, "", "匯款分類"),
	fullPayment(1, 0, 1, "", "匯出匯款方式"), // Y: 需全額到行,I: 間接匯款,R: 國內匯款RTGS
	custTel(1, 0, 14, "", "客戶聯絡電話"),
	custBirthday(2, 0, 8, "", "客戶生日"),
	custKindCbMemo(1, 0, 2, "", "客戶身份別"),
	idType(1, 0, 2, "", "證件類型(付款人)"),
	idNo(1, 0, 11, "", "證件號碼(付款人)"),
	rsdntIssueDate(2, 0, 8, "", "居留證核發日期(付款人)(DDMMYYYY)"),
	rsdntValidDate(2, 0, 8, "", "居留證有效期限(付款人)"),
	custBirthday2(2, 0, 8, "", "客戶生日(收款人) (DDMMYYYY)"),
	custKindCbMemo2(1, 0, 2, "", "客戶身份別(收款人)"),
	idType2(1, 0, 2, "", "證件類型(收款人)"),
	idNo2(1, 0, 11, "", "證件號碼(收款人)"),
	rsdntIssueDate2(2, 0, 8, "", "居留證核發日期(收款人) (DDMMYYYY)"),
	rsdntValidDate2(2, 0, 8, "", "居留證有效期限(收款人) (DDMMYYYY)"),
	UUID(1, 0, 32, "", "UUID"),
	Custid(1, 0, 16, "", "Custid"),
	CustidSub(1, 0, 16, "", "CustidSub"),
	BdSeq(1, 0, 12, "", "水單號碼欄位"),
	CHECK_ID(1, 0, 10, "", "檢核ID"),
	TBB_STAT(1, 0, 4, "", "代收付狀態"),
	
	excost(2, 0, 12, "", "成本匯率"),
	payExrate(2, 0, 12, "", "成交買匯匯率"),
	payExcost(2, 0, 12, "", "成本買匯匯率"),
	remitExrate(2, 0, 12, "", "成交賣匯匯率"),
	remitExcost(2, 0, 12, "", "成本賣匯匯率"),
	stdExrate(2, 0, 12, "", "牌告成交匯率"),
	stdExcost(2, 0, 12, "", "牌告成本匯率"),
	chgExrate(2, 0, 12, "", "手續費成交匯率"),
	chgExcost(2, 0, 12, "", "手續費成本匯率"),
	payAmt(2, 0, 17, "", "付款金額 "),
	remitAmt(2, 0, 17, "", "匯款金額 ");
	
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

	Cactx502Result12NTxFileDefinition(int type, int required, int length, String columnName, String name) {
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
