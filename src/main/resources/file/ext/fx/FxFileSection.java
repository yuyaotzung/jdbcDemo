package com.cosmos.file.ext.fx;

import com.cosmos.file.bo.FileSection;
import com.cosmos.type.FieldGroup;
import com.cosmos.type.PayerKind;
import com.ibm.tw.commons.util.StringUtils;

/**
 * <p>
 * 外幣含付款資料、及收款資料 File Section
 * </p>
 * 
 * @author monica
 * @version 1.0, 2013/11/20
 * @see
 * @since
 */
public class FxFileSection extends FileSectionWrapperBase {

	public FxFileSection(FileSection fileSection) {

		super(fileSection);
	}

	public FxFileSection(FieldGroup fieldGroup) {
		super(new FileSection());
		fileSection.setFieldGroup(fieldGroup);
	}

	/**
	 * 取得自行或跨行
	 * 
	 * @return
	 */
	public PayerKind getPayerKind() {

		String payeeBankName1 = getPayeeBankName1();

		String payeeSwiftCode = getPayeeSwiftCode();

		return isIntraBank(payeeBankName1, payeeSwiftCode);
	}

	/**
	 * 用戶自定序號
	 * 
	 * @return
	 */
	public String getCustomSn() {
		return getFieldValue("customSn");
	}

	/**
	 * 付款人統編
	 * 
	 * @return
	 */
	public String getPayerUid() {
		return getFieldValue("payerUid");
	}

	/**
	 * 付款人英文名稱
	 * 
	 * @return
	 */
	public String getPayerEngName() {
		return getFieldValue("payerEngName");
	}

	/**
	 * 付款人英文地址
	 * 
	 * @return
	 */
	public String getPayerEngAddress() {
		return getFieldValue("payerEngAddress");
	}

	/**
	 * 付款日期
	 * 
	 * @return
	 */
	public String getTxDate() {
		return getFieldValue("txDate");
	}

	/**
	 * 付款帳號
	 * 
	 * @return
	 */
//	public String getPayerAccountNo() {
//
//		if (getFieldValue("payerAccountNo") == null) {
//			return null;
//		}
//		else {
//
//			String payerAccount = StringUtils.trim(getFieldValue("payerAccountNo"));
//
//			if (StringUtils.length(payerAccount) == 12) {
//
//				payerAccount = StringUtils.leftPad(payerAccount, 14, "0");
//
//			}
//
//			return payerAccount;
//		}
//
//	}
	
	/**
	 * 付款帳號
	 * 1.帳號為16碼的規格.是新增的規格.(不是原來的14改為16喔)
	 * 2.
	 * 2.1 取帳號時.如trim後長度為12 or 13,請最左邊補0補為14碼
	 * 2.2 取帳號時.如trim後長度為15 or 16,請抓取最右邊14碼.
	 * TODO:測試時要連舊版的公版一起測測看
	 * @return
	 */
	public String getPayerAccountNo() {

		if (getFieldValue("payerAccountNo") == null) {
			return null;
		}
		else {

			String payerAccount = StringUtils.trim(getFieldValue("payerAccountNo"));

			if (StringUtils.length(payerAccount) == 12 || StringUtils.length(payerAccount) == 13) {

				payerAccount = StringUtils.leftPad(payerAccount, 14, "0");

			}
			else if(StringUtils.length(payerAccount) == 15 || StringUtils.length(payerAccount) == 16){
				payerAccount =  StringUtils.right(payerAccount, 14);
			}

			return payerAccount;
		}

	}	

	/**
	 * 付款幣別
	 * 
	 * @return
	 */
	public String getPayerCcy() {
		return getFieldValue("txAmtCcy");
	}

	/**
	 * 收款帳號
	 * 
	 * @return
	 */
	public String getPayeeAccountNo() {
		return getFieldValue("payeeAccountNo");
	}

	/**
	 * 交易金額
	 * 
	 * @return
	 */
	public String getTxAmt() {
		return getFieldValue("txAmtFr");
	}

	/**
	 * 收款人戶名-1
	 * 
	 * @return
	 */
	public String getPayeeName1() {
		return getFieldValue("payeeName1");
	}

	/**
	 * 收款人戶名-2
	 * 
	 * @return
	 */
	public String getPayeeName2() {
		return getFieldValue("payeeName2");
	}

	/**
	 * 收款人地址/電話-1
	 * 
	 * @return
	 */
	public String getPayeeAddrPhone1() {
		return getFieldValue("payeeAddrPhone1");
	}

	/**
	 * 收款人地址/電話-2
	 * 
	 * @return
	 */
	public String getPayeeAddrPhone2() {
		return getFieldValue("payeeAddrPhone2");
	}

	/**
	 * 收款銀行 Swift Code
	 * 
	 * @return
	 */
	public String getPayeeSwiftCode() {
		String code = getFieldValue("payeeSwiftCode");
		
		if (StringUtils.isNotBlank(code)){
			// 若為 8 碼，直接幫它加上 XXX
			if (code.length() == 8) {
				code = code + "XXX";
			}
		}

		return code;
	}

	/**
	 * 收款銀行名稱-1
	 * 
	 * @return
	 */
	public String getPayeeBankName1() {
		return getFieldValue("payeeBankName1");
	}

	/**
	 * 收款銀行名稱-1
	 * 
	 * @return
	 */
	public void setPayeeBankName1(String value) {
		setFieldValue("payeeBankName1", value);
	}

	/**
	 * 收款銀行名稱-2
	 * 
	 * @return
	 */
	public String getPayeeBankName2() {
		return getFieldValue("payeeBankName2");
	}

	/**
	 * 收款銀行名稱-2
	 * 
	 * @return
	 */
	public void setPayeeBankName2(String value) {
		setFieldValue("payeeBankName2", value);
	}

	/**
	 * 收款銀行地址-1
	 * 
	 * @return
	 */
	public String getPayeeBankAddr1() {
		return getFieldValue("payeeBankAddr1");
	}

	/**
	 * 收款銀行地址-2
	 * 
	 * @return
	 */
	public String getPayeeBankAddr2() {
		return getFieldValue("payeeBankAddr2");
	}

	/**
	 * 銀行清算代碼 CNAPS
	 * 
	 * @return
	 */
	public String getCnaps() {
		return getFieldValue("cnaps");
	}

	/**
	 * 中間銀行 Swift Code
	 * 
	 * @return
	 */
	public String getMiddleSwiftCode() {
		String code = getFieldValue("middleSwiftCode");
		
		if (StringUtils.isNotBlank(code)){
			// 若為 8 碼，直接幫它加上 XXX
			if (code.length() == 8) {
				code = code + "XXX";
			}
		}

		return code;
	}

	/**
	 * 收款銀行國別
	 * 
	 * @return
	 */
	public String getPayeeCountry() {
		return getFieldValue("payeeCountry");
	}

	/**
	 * 受款人身份別 1:政府 2:公營事業 3:民間 4:他人帳戶 5:本人帳戶
	 * 
	 * @return
	 */
	public String getSelfFlag() {
		// 系統預設為 3
		return StringUtils.isBlank(getFieldValue("selfFlag")) ? "3" : getFieldValue("selfFlag");
	}

	/**
	 * 匯款性質
	 * 
	 * @return
	 */
	public String getSourceOfFund() {
		return getFieldValue("sourceOfFund");
	}

	/**
	 * 交易類別代號
	 * 
	 * @return
	 */
	public String getTxntTypeId() {
		return getFieldValue("txntTypeId");
	}

	/**
	 * 匯出匯款方式
	 * 
	 * @return
	 */
	public String getFullPayment() {
		// 允許空白，系統預設為N
		// return StringUtils.isBlank(getFieldValue("fullPayment")) ? "N" :
		// getFieldValue("fullPayment");

		// 2016/3/9: 改成由檔案設定的識別字元判斷，此處不解析
		return getFieldValue("fullPayment");
	}

	/**
	 * 匯款附言
	 * 
	 * @return
	 */
	public String getPaymentdetails1() {
		return getFieldValue("paymentdetails1");
	}

	// public String getPaymentdetails2() {
	// return getFieldValue("paymentdetails2");
	// }
	//
	// public String getPaymentdetails3() {
	// return getFieldValue("paymentdetails3");
	// }
	//
	// public String getPaymentdetails4() {
	// return getFieldValue("paymentdetails4");
	// }

	/**
	 * 收款人Email
	 * 
	 * @return
	 */
	public String getPayeeEmail() {
		return getFieldValue("payeeEmail");
	}

	/**
	 * 手續費負擔
	 * 
	 * @return
	 */
	public String getChargeAmtType() {
		return getFieldValue("chargeAmtTypeFr");
	}

	/**
	 * 收款人統編
	 * 
	 * @return
	 */
	public String getPayeeId() {
		return getFieldValue("payeeId");
	}
	
	/**
	 * 費用付款帳號
	 * 1.帳號為16碼的規格.是新增的規格.(不是原來的14改為16喔)
	 * 2.
	 * 2.1 取帳號時.如trim後長度為12 or 13,請最左邊補0補為14碼
	 * 2.2 取帳號時.如trim後長度為15 or 16,請抓取最右邊14碼.
	 * @return
	 */
	public String getFeeActNo() {

		if (getFieldValue("feeActNo") == null) {
			return null;
		}
		else {

			String feeActNo = StringUtils.trim(getFieldValue("feeActNo"));

			if (StringUtils.length(feeActNo) == 12 || StringUtils.length(feeActNo) == 13) {
				//左邊補0補為14碼	
				feeActNo = StringUtils.leftPad(feeActNo, 14, "0");

			}
			else if(StringUtils.length(feeActNo) == 15 || StringUtils.length(feeActNo) == 16){
				//取最右邊14碼
				feeActNo = StringUtils.right(feeActNo, 14);
			}

			return feeActNo;
		}
	}
	
	/**
	 * 費用付款幣別
	 * 
	 * @return
	 */
	public String getFeeCcy() {

		return getFieldValue("feeCcy");
	}
}
