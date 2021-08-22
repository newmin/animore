package com.proj.animore.form;

import lombok.Data;

@Data
public class BoardForm {

	private int bnum;
	private String id;
	private String bcategory;
	private String btitle;
	private String bcontent;
	private int bhit;
	private int bgood;
	private int breply;
	private int bgroup;
}
