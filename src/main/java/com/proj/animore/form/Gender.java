package com.proj.animore.form;

public enum Gender {
	M("남"),F("여");

	private final String decode;
	
	Gender(String decode) {
		this.decode = decode;
	}
	
	public String getName() {
		return name();
	}
	
	public String getDecode() {
		return decode;
	}
}