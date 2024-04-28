package com.display.backoffice.sym.rabbitmq.models;

public enum SendType {

	REQ("REQ", "전송요청"), 
	RES("REO", "전송완료"), 
	REF("REF", "전송실패"), 
	RPQ("RPQ", "수신요청"), 
	RPS("RPS", "수신완료"), 
	RPF("RPF", "수신실패"),
	TYGT("GET", "get"),
	TYPO("POST", "post"),
	TYPU("PUT", "put"),
	TYDE("DELETE", "delete"); 
	
	
	
	
	private String code; 
	private String name; 
		SendType(String code, String name) { 
		this.code = code; 
		this.name = name; 
	} 
	public String getCode() { 
		return this.code; 
	} 
	public String getName() { 
		return this.name; 
	}
	
}
