package com.proj.animore.form;

public enum Gender {
	MALE("남자"),FEMALE("여자");

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