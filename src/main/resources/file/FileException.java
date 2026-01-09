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
package com.cosmos.file;

import com.cosmos.code.CibErrorCode;
import com.ibm.tw.commons.exception.ActionException;
import com.ibm.tw.commons.id.ICommonCode;

/**
 * <p>
 * 檔案上下傳錯誤
 * </p>
 * 
 * @author Leo
 * @version 1.0, 2011/6/8
 * @see
 * @since
 */
@SuppressWarnings("serial")
public class FileException extends ActionException {

	public FileException(String msg, CibErrorCode error) {
		super(error.getSystemId(), error.getErrorCode(), ICommonCode.SEVERITY_ERROR, error.getMemo() + ": " + msg);
	}

	public void setParam(String paramKey, String paramValue) {
		super.putParam(paramKey, paramValue);
	}
}
