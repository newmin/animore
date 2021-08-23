package com.proj.animore.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 쿠키에 들어갈 정보
 * @author mypc
 *
 */
@Data
@AllArgsConstructor
public class LoginMember {
	private String id;
	private String nickname;
	private String mtype;
}
