package com.proj.animore.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class LoginMember {
	private String id;
	private String pw;
	private String nickname;
	private String mtype;
}
