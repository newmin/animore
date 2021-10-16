package com.proj.animore.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proj.animore.common.file.FileStore;
import com.proj.animore.common.file.MetaOfUploadFile;
import com.proj.animore.common.mail.MailService;
import com.proj.animore.common.util.PasswordGeneratorCreator;
import com.proj.animore.dto.MemberDTO;
import com.proj.animore.dto.MyAniDTO;
import com.proj.animore.dto.business.BcategoryDTO;
import com.proj.animore.dto.business.BusiUploadFileDTO;
import com.proj.animore.dto.business.BusinessDTO;
import com.proj.animore.form.ChangePwForm;
import com.proj.animore.form.FindIdForm;
import com.proj.animore.form.FindPwForm;
import com.proj.animore.form.JoinBusinessForm;
import com.proj.animore.form.JoinMemberForm;
import com.proj.animore.svc.MemberSVC;
import com.proj.animore.svc.MyAniSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberSVC memberSVC;
	private final MyAniSVC myAniSVC;
	private final MailService ms;
	private final FileStore fileStore;

	/**
	 * 회원가입유형선택
	 * 
	 * @return
	 */
	@GetMapping("/join")
	public String joinSelectMtype(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		// 로그인한 상태(세션있음)로 회원가입유형선택 페이지 요청시 메인페이지로 보냄
		if (session != null && session.getAttribute("loginMember") != null)	return "redirect:/";
		// 로그인 안한 상태(세션없음)로 회원가입유형선택 페이지 요청해야 페이지로 이동
		return "member/joinSelectMtype";
	}

	/**
	 * 회원가입양식
	 * 
	 * @param mtype
	 * @param model
	 * @return
	 */
	@GetMapping("/join/{mtype}")
	public String joinForm(@ModelAttribute JoinMemberForm joinMemberForm,
							@ModelAttribute JoinBusinessForm joinBusinessForm,
							HttpServletRequest request,
							@PathVariable("mtype") String mtype, Model model) {
		HttpSession session = request.getSession(false);

		// 로그인한 상태(세션있음)로 회원가입양식 페이지 요청시 메인페이지로 보냄
		if (session != null && session.getAttribute("loginMember") != null)	return "redirect:/";

		// 회원유형 선택화면에서 회원유형값{mtype} 받아서 조인폼으로 넘겨주게 됨.
		// joinForm에서 th:if문을 사용하여
		// 회원유형값이 N이면 일반회원 가입양식,
		// 회원유형값이 S이면 특수회원 가입양식이 보여지도록 함.
		if (mtype.equals("normal"))		mtype = "N";
		if (mtype.equals("special"))	mtype = "S";

		model.addAttribute("mtype", mtype);
		return "member/joinForm";
	}

	@PostMapping("/join/N")
	public String join(@Valid @ModelAttribute JoinMemberForm joinMemberForm,
			BindingResult bindingResult) throws IllegalStateException, IOException {
		
		if (!joinMemberForm.getPw().equals(joinMemberForm.getPw2())) {
			bindingResult.rejectValue("pw2", "pw2", "비밀번호가 일치하지 않습니다.");
		}
		if (bindingResult.hasErrors()) {
			log.info("errors={}", bindingResult);
			return "member/joinForm";
		}
		
		MemberDTO memberDTO = new MemberDTO();
		BeanUtils.copyProperties(joinMemberForm,memberDTO);
		memberDTO.setMtype("N");
		
		if(!joinMemberForm.getFile().isEmpty()) {
			fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/member/");		
			MetaOfUploadFile storedFile = fileStore.storeFile(joinMemberForm.getFile());
			memberDTO.setStore_fname(storedFile.getStore_fname());
			memberDTO.setUpload_fname(storedFile.getUpload_fname());
			memberDTO.setFsize(storedFile.getFsize());
			memberDTO.setFtype(storedFile.getFtype());
		}
		
		memberSVC.joinMember(memberDTO);
		
//		MyAniDTO myAniDTO = new MyAniDTO();
//		BeanUtils.copyProperties(joinMemberForm,myAniDTO);
//		
//		myAniSVC.registerMyAni(myAniDTO);
		return "redirect:/";
	}

	@PostMapping("/join/S")
	public String join2(@Valid @ModelAttribute JoinMemberForm joinMemberForm,
						@Valid @ModelAttribute JoinBusinessForm joinBusinessForm,
						BcategoryDTO bcategoryDTO)  throws IllegalStateException, IOException {
		
		MemberDTO memberDTO = new MemberDTO();
		BeanUtils.copyProperties(joinMemberForm,memberDTO) ;
				
		memberDTO.setMtype("S");
		
		if(!joinMemberForm.getFile().isEmpty()) {
			fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/member/");		
			MetaOfUploadFile storedFile = fileStore.storeFile(joinMemberForm.getFile());
			memberDTO.setStore_fname(storedFile.getStore_fname());
			memberDTO.setUpload_fname(storedFile.getUpload_fname());
			memberDTO.setFsize(storedFile.getFsize());
			memberDTO.setFtype(storedFile.getFtype());	
		} 
		
		BusinessDTO businessDTO = new BusinessDTO();
		BeanUtils.copyProperties(joinBusinessForm,businessDTO);
		
		if(!joinBusinessForm.getFiles().isEmpty() || joinBusinessForm.getFiles().size()>0) {
			fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/business/");		
			List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(joinBusinessForm.getFiles());
			businessDTO.setFiles(convert(storedFiles));
		} 
		
		memberSVC.joinMember(memberDTO, businessDTO, bcategoryDTO);
		return "redirect:/";
	}
	
	//메타정보 → 업로드 정보
	private BusiUploadFileDTO convert(MetaOfUploadFile attatchFile) {
		BusiUploadFileDTO uploadFileDTO = new BusiUploadFileDTO();
		BeanUtils.copyProperties(attatchFile, uploadFileDTO);
		return uploadFileDTO;
	}
	
	private List<BusiUploadFileDTO> convert(List<MetaOfUploadFile> uploadFiles) {
		List<BusiUploadFileDTO> list = new ArrayList<>();

		for(MetaOfUploadFile file : uploadFiles) {
			BusiUploadFileDTO uploadFIleDTO = convert(file);
			list.add( uploadFIleDTO );
		}		
		return list;
	}
	

	@GetMapping("/{id}")
	public String mypage() {
		return "mypage/mypage";
	}
	
	/**
	 * 아이디찾기양식
	 * @return
	 */
	@GetMapping("/findId")
	public String findIdForm(@ModelAttribute FindIdForm findIdForm) {
		return "member/findIdForm";
	}
	//아이디찾기 처리
	@PostMapping("/findId")
	public String findId(@ModelAttribute FindIdForm findIdForm, Model model) {
		List<String> ids = memberSVC.findId(findIdForm);
		model.addAttribute("ids",ids);
		return "member/findedIdResult";
	}
	
	
	/**
	 * 비밀번호찾기양식
	 * @return
	 */
	@GetMapping("/findPW")
	public String findPWForm(@ModelAttribute FindPwForm findPwForm) {
		return "member/findPWForm";
	}
	
	/**
	 * 비밀번호찾기 - 아이디,이름,이메일 입력받음
	 * @param findPwForm
	 * @return
	 */
	@PostMapping("/findPW")
	public String findPW(
			@ModelAttribute FindPwForm findPwForm,
			HttpServletRequest request,
			Model model
			/* ,RedirectAttributes redirectAttributes */) {
		
		ChangePwForm changePwForm = memberSVC.findPw(findPwForm);
		
		//정보가 없으면
		if(changePwForm == null) {
			return "redirect:/member/findPW";
		}
		MemberDTO memberDTO = memberSVC.findMemberById(findPwForm.getId());
		
		
    //1)임의의 비밀번호 생성
    String tmpPw = PasswordGeneratorCreator.generator(7);
    
//    //2)임시비밀번호로 회원의 비밀번호 변경
    memberSVC.changePw(findPwForm.getEmail(), changePwForm.getPw(), tmpPw);

    //생성된 비밀번호 이메일 전송
    String subject = "신규 비밀번호 전송";
    
    //로긴주소
    StringBuilder url = new StringBuilder();
    url.append("http://" + request.getServerName());
    url.append(":" + request.getServerPort());
    url.append(request.getContextPath());
    url.append("/login");
    
    //메일본문내용
    StringBuilder sb = new StringBuilder();
    sb.append("<!DOCTYPE html>");
    sb.append("<html lang='ko'>");
    sb.append("<head>");
    sb.append("  <meta charset='UTF-8'>");
    sb.append("  <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
    sb.append("  <title>임시 비밀번호 발송</title>");
    sb.append("</head>");
    sb.append("<body>");
    sb.append("  <h1>신규비밀번호</h1>");
    sb.append("  <p>아래 비밀번호로 로그인 하셔서 비밀번호를 변경하세요</p>");
    sb.append("  <p>비밀번호 :" + tmpPw + "</p>");
    sb.append("  <a href='"+ url +"'>로그인</a>");
    sb.append("</body>");
    sb.append("</html>");
    
    ms.sendMail(findPwForm.getEmail(), subject , sb.toString());
    
    model.addAttribute("info", "가입된 이메일로 임시비밀번호가 발송되었습니다.");
    
    return "member/changePWSuccess";
    
//		//정보가 DB와 일치할 경우
//		redirectAttributes.addAttribute("id", changePwForm.getId());
//		log.info(changePwForm.getId());
//		return "redirect:/member/findPW/{id}";	//비밀번호변경양식
	}
		
	/**
	 * 비밀번호변경양식
	 * @param changePWForm
	 * @return
	 */
//	@GetMapping("/findPW/{id}")
//	public String changePWForm(
//			@ModelAttribute ChangePwForm changePWForm,
//			@PathVariable String id) {
//		
//		
//		return "member/changePWForm";
//	}

	/**
	 * 비밀번호변경처리
	 * @param changePWForm
	 * @param id
	 * @return
	 */
//	@PatchMapping("/findPW/{id}")
//	public String changePW(
//			@PathVariable String id,
//			@Valid @ModelAttribute ChangePwForm changePWForm,
//			BindingResult bindingResult) {
//		
//		if(!changePWForm.getPw().equals(changePWForm.getPwChk())) {
//			bindingResult.reject("pwChk","새 비밀번호 확인이 일치하지 않습니다.");
//			return "member/changePWForm";
//		}
//		
//		int result = memberSVC.changePW(id,changePWForm);
//		if(result == 0) {
//			return "member/changePWForm";
//		}
//		
//		return "redirect:/member/changePWSuccess";
//	}
//	@GetMapping("/changePWSuccess")
//	public String changePWSuccess() {
//		
//		return "/member/changePWSuccess";
//	}
}