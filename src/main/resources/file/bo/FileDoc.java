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
package com.cosmos.file.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ibm.tw.commons.exception.ActionException;
import com.ibm.tw.commons.id.ICommonCode;

/**
 * <p>
 * 檔案資料
 * </p>
 * 
 * @author Leo
 * @version 1.0, 2011/6/8
 * @see
 * @since
 */
public class FileDoc {

	/** 明細 */
	private List<FileDetail> details = new ArrayList<FileDetail>();

	/** 錯誤 */
	private ActionException exception;
	
	/** 總金額 */
	private Map<String,BigDecimal> txAmtMap = new HashMap <String, BigDecimal>();

	public List<FileDetail> getDetails() {
		return details;
	}

	public ActionException getException() {
		return exception;
	}

	public void setException(ActionException exception) {
		this.exception = exception;
	}

	/**
	 * 是否有錯誤
	 * 
	 * @return
	 */
	public boolean isError() {

		return isSelfError() || isDetailError();
	}

	private boolean isSelfError() {
		// 無例外
		if (exception == null) {
			return false;
		}

		// 例外無錯誤代碼
		if (exception.getStatus() == null) {
			return false;
		}

		String errorCode = exception.getStatus().getStatusCode();

		// 錯誤代碼為空或0
		if (StringUtils.isBlank(errorCode) || StringUtils.equals(errorCode, ICommonCode.SUCCESS_STATUS_CODE)) {
			return false;
		}
		// 有錯誤代碼
		else {
			return true;
		}
	}

	private boolean isDetailError() {
		// 是否有明細錯誤
		for (FileDetail fileDetail : details) {
			if (fileDetail.isError()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 取得錯誤
	 * 
	 * 包含自己或子陣列
	 * 
	 * @return
	 */
	public ActionException getError() {

		if (isSelfError()) {
			return getException();
		}
		else {
			for (FileDetail fileDetail : details) {
				if (fileDetail.isError()) {
					return fileDetail.getError();
				}
			}
			return null;
		}
	}

	/**
	 * 加入明細資料
	 * 
	 * @param detail
	 */
	public void addDetail(FileDetail detail) {
		details.add(detail);
	}

	/**
	 * 依據行號取得明細資料
	 * 
	 * 行號編號由1開始
	 * 
	 * @param index
	 * @return
	 */
	public FileDetail getDetail(int rowNo) {
//		for (FileDetail fileDetail : details) {
//			if (fileDetail.getRowNo() == rowNo) {
//				return fileDetail;
//			}
//		}
//
//		return null;
		//2016/1/25: 有startRow設定後，使用行號來取不太對。總之fileDoc內已是有效資料，循序取就好。
		return details.get(rowNo - 1);
	}

	/**
	 * 取得第一筆明細資料
	 * 
	 * @return
	 */
	public FileDetail getFirstDetail() {
		//return getDetail(1);
		return details.get(0);
	}

	/**
	 * 取得最後一筆明細資料
	 * @return
	 */
	public FileDetail getLastDetail() {
		return getDetail(rows());
	}

	/**
	 * 筆數
	 * @return
	 */
	public int rows() {
		return details.size();
	}

	
	public Map<String, BigDecimal> getTxAmtMap() {
		return txAmtMap;
	}

	
	public void setTxAmtMap(Map<String, BigDecimal> txAmtMap) {
		this.txAmtMap = txAmtMap;
	}

	
}
