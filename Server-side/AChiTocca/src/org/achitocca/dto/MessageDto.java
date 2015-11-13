package org.achitocca.dto;

public class MessageDto {
	public String result="";
	public int code=0;
	public Object detail=null;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getDetail() {
		return detail;
	}
	public void setDetail(Object detail) {
		this.detail = detail;
	}
	
	
}
