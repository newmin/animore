package com.proj.animore.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HTTP API 응답메세지 구조
 * @author mypc
 * 
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
	private String rtcd;
	private String rtmsg;
	private T data;
}