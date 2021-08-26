package com.proj.animore.svc;

import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.proj.animore.dao.MemberDAO;
import com.proj.animore.form.FindIdForm;

@SpringBootTest
public class MemberSVCImplTest{

	@Autowired
	private MemberSVC msvc;
	@Autowired
	private MemberDAO mdao;
	
	public void findId() {
		FindIdForm idForm = new FindIdForm();
		idForm.setEmail("이사람");
		idForm.setEmail("user@test.com");
		mdao.findId(idForm);
//		Assertions assertthatAssertions
	}
}
