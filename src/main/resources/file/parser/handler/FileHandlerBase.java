/*
 * ===========================================================================
 * IBM Confidential
 * AIS Source Materials
 * 
 * 
 * (C) Copyright IBM Corp. 2016.
 *
 * ===========================================================================
 */
package com.cosmos.file.parser.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cosmos.file.FileFormatDetail;
import com.cosmos.file.FileFormatNew;
import com.cosmos.file.bo.FileDoc;
import com.cosmos.type.FieldGroup;
import com.cosmos.util.LineSeparator;



/**
 * <p> 檔案Parse共同處理程式</p>
 *
 * @author  Ally
 * @version 1.0, 2016/1/13
 * @see	    
 * @since 
 */
public class FileHandlerBase {
	/** 檔案格式設定 */
	protected FileFormatNew format = null;
	
	protected FileDoc fileDoc = null;
	
	protected static Logger logger = Logger.getLogger(FileFormatNew.class);
	/** 檔案格式明細檔 (依FieldGroup分群) */
	protected Map<FieldGroup, List<FileFormatDetail>> detailGroup = new HashMap<FieldGroup, List<FileFormatDetail>>();

	
	
	/**
	 * 將上傳檔案依照換行符號切分
	 * 
	 * @param fileContent
	 * @return
	 */
	protected List<byte[]> getFileRecords(byte[] fileContent) {
		List<byte[]> fileRecords = null;
		if (contains(fileContent, LineSeparator.Windows.getBytes())) {
			fileRecords = split(fileContent, LineSeparator.Windows.getBytes());
		}
		else if (contains(fileContent, LineSeparator.Macintosh.getBytes())) {
			fileRecords = split(fileContent, LineSeparator.Macintosh.getBytes());
		}
		else if (contains(fileContent, LineSeparator.Unix.getBytes())) {
			fileRecords = split(fileContent, LineSeparator.Unix.getBytes());
		}
		else {
			fileRecords = new ArrayList<byte[]>();
			fileRecords.add(fileContent);
		}
		return fileRecords;
	}

	/**
	 * 
	 * @return
	 */
	protected String getLineSeparator() {

		String OS = System.getProperty("os.name").toLowerCase();

		if (OS.indexOf("win") >= 0) {
			return LineSeparator.Windows;
		}
		else if (OS.indexOf("mac") >= 0) {
			return LineSeparator.Macintosh;
		}
		else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) {
			return LineSeparator.Unix;
		}
		return "";
	}
	
	/**
	 * 搜尋byte陣列是否包含目標陣列
	 * 
	 * @param array
	 * @param search
	 * @return
	 */
	protected boolean contains(byte[] array, byte[] search) {
		return indexOf(array, search) != -1;
	}
	

	/**
	 * 搜尋目標陣列在byte陣列中出現的index
	 * 
	 * 若不包含則回傳-1
	 * 
	 * @param array
	 * @param search
	 * @return
	 */
	protected int indexOf(byte[] array, byte[] search) {
		for (int i = search.length; i <= array.length; i++) {
			byte[] subArray = subArray(array, i - search.length, i);
			if (equals(subArray, search)) {
				return i - search.length;
			}
		}
		return -1;
	}

	/**
	 * 取得byte陣列的子陣列
	 * 
	 * @param array
	 * @param start
	 * @return
	 */
	protected byte[] subArray(byte[] array, int start) {
		return subArray(array, start, array.length);
	}

	/**
	 * 取得byte陣列的子陣列
	 * 
	 * @param array
	 * @param start
	 * @param end
	 * @return
	 */
	protected byte[] subArray(byte[] array, int start, int end) {
		int length = end - start;

		if (length <= 0) {
			return new byte[0];
		}
		
		if(start > array.length || end > array.length){
			return  new byte[0];
		}
		
		byte[] subArray = new byte[length];
		for (int i = 0; i < length; i++) {
			subArray[i] = array[i + start];
		}
		return subArray;
	}

	/**
	 * 比較兩byte陣列是否相同
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	protected boolean equals(byte[] array1, byte[] array2) {
		if (array1.length != array2.length) {
			return false;
		}
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] != array2[i]) {
				return false;
			}
		}
		return true;
	}	
	
	/**
	 * 將byte陣列依據separator切成多個byte陣列
	 * 
	 * @param array
	 * @param separator
	 * @return
	 */
	
	protected List<byte[]> split(byte[] array, byte[] separator) {
		List<byte[]> list = new ArrayList<byte[]>();
		int index = indexOf(array, separator);
		while (index >= 0) {
			byte[] subArray = subArray(array, 0, index);
			array = subArray(array, index + separator.length);
			index = indexOf(array, separator);

			list.add(subArray);
		}

		if (array.length > 0) {
			list.add(array);
		}
		return list;
	}

}



 
