package com.cosmos.file.ext.fx;

import com.cosmos.file.bo.FileSection;
import com.cosmos.type.PayerKind;
import com.ibm.tw.commons.util.StringUtils;

/**
 * <p>
 * 外幣收款資料 File Section
 * </p>
 * 
 * @author monica
 * @version 1.0, 2013/11/19
 * @see
 * @since
 */
public class FxPayeeFileSection extends FileSectionWrapperBase {

	public FxPayeeFileSection(FileSection fileSection) {

		super(fileSection);
	}

	/**
	 * 取得自行或跨行
	 * 
	 * @return
	 */
	public PayerKind getPayerKind() {

		String payeeBankName1 = getPayeeBankName1();

		String payeeSwiftCode = StringUtils.isBlank(getPayeeSwiftCode()) ? "" : getPayeeSwiftCode();

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
	 * CNAPS (收款銀行清算代碼)
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
	 * 匯款附言-1
	 * 
	 * @return
	 */
	public String getPaymentdetails1() {
		return getFieldValue("paymentdetails1");
	}

	// /**
	// * 匯款附言-2
	// *
	// * @return
	// */
	// public String getPaymentdetails2() {
	// return getFieldValue("paymentdetails2");
	// }
	//
	// /**
	// * 匯款附言-3
	// *
	// * @return
	// */
	// public String getPaymentdetails3() {
	// return getFieldValue("paymentdetails3");
	// }
	//
	// /**
	// * 匯款附言-4
	// *
	// * @return
	// */
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
}
