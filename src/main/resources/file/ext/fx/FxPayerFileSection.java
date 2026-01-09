package com.cosmos.file.ext.fx;

import com.cosmos.file.bo.FileSection;
import com.ibm.tw.commons.util.StringUtils;

/**
 * <p>
 * 外幣付款資料 File Section
 * </p>
 * 
 * @author monica
 * @version 1.0, 2013/11/19
 * @see
 * @since
 */
public class FxPayerFileSection extends FileSectionWrapperBase {

	public FxPayerFileSection(FileSection fileSection) {

		super(fileSection);
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
	 * 付款幣別代碼
	 * 
	 * @return
	 */
	public String getTxAmtCcy() {

		return getFieldValue("txAmtCcy");
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
