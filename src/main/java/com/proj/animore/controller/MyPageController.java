package com.proj.animore.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proj.animore.common.file.FileStore;
import com.proj.animore.common.file.MetaOfUploadFile;
import com.proj.animore.dao.UpLoadFileDAO;
import com.proj.animore.dto.MemberDTO;
import com.proj.animore.dto.UpLoadFileDTO;
import com.proj.animore.dto.board.GoodBoardDTO;
import com.proj.animore.dto.business.BusinessLoadDTO;
import com.proj.animore.dto.business.FavoriteReq;
import com.proj.animore.form.BusiModifyForm;
import com.proj.animore.form.JoinMemberForm;
import com.proj.animore.form.LoginMember;
import com.proj.animore.form.ModifyForm;
import com.proj.animore.form.ProfileForm;
import com.proj.animore.svc.MemberSVC;
import com.proj.animore.svc.board.GoodBoardSVC;
import com.proj.animore.svc.business.BusinessSVC;
import com.proj.animore.svc.business.FavoriteSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/mypage")
@RequiredArgsConstructor
@SessionAttributes("id")
public class MyPageController {
   private final FavoriteSVC favoriteSVC;
   private final MemberSVC memberSVC;
   private final BusinessSVC businessSVC;
   private final GoodBoardSVC goodBoardSVC;
   private final UpLoadFileDAO upLoadFileDAO;
   private final FileStore fileStore;
	//메타정보 → 업로드 정보

   //회원탈퇴처리
   @DeleteMapping("/mypagePwModify")
   public String changePW(
         @RequestParam String pw,
         HttpServletRequest request,
         Model model) {
      log.info("회원탈퇴");
      
      Map<String, String> errors = new HashMap<>();
      
      if(pw == null || pw.trim().length() == 0) {
         errors.put("pw", "비밀번호를 입력하세요");
         model.addAttribute("errors", errors);
//         return "mypage/memberOutForm";
         return null;
      }
      
      HttpSession session = request.getSession(false);
      if(session == null) return "redirect:/login";
      
      LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
      //회원존재유무 확인
      if(memberSVC.isMemember(loginMember.getId(), pw)) {
         //탈퇴
         memberSVC.outMember(loginMember.getId());
      }else {
         errors.put("global", "비밀번호가 잘못되었습니다.");
         model.addAttribute("errors", errors);
      }
      
      if(!errors.isEmpty()) {
//         return "mypage/memberOutForm";
         return null;
      }
      
      session.invalidate();
      
      return "redirect:/";
   }
   //프로필 조회
//   @GetMapping("/profile")
//	public String profileEditForm( HttpSession session, Model model ) {
//		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
//		log.info("loginmember:{}",loginMember);
//		MemberDTO memberDTO = memberSVC.findMemberById(loginMember.getId());
//		UpLoadFileDTO upLoadFileDTO = upLoadFileDAO.getFileByRid(String.valueOf(loginMember.getId()));
//		
//		ProfileForm profileForm = new ProfileForm();
//		profileForm.setNickname(memberDTO.getNickname());
//		profileForm.setSavedImgFile(upLoadFileDTO);
//		
//		model.addAttribute("profileForm", profileForm);
//		return "mypage/profileEditForm";
//	}
   
   //프로필 수정
   @PatchMapping("/mypageFavorites")
   public String mypageProfile(@Valid @ModelAttribute ProfileForm profileForm,
		    HttpServletRequest request,
			BindingResult bindingResult)throws IllegalStateException, IOException {
	   
	   MemberDTO memberDTO = new MemberDTO();
	   HttpSession session = request.getSession(false);
	   
	   BeanUtils.copyProperties(profileForm,memberDTO);
		
	   if(!profileForm.getFile().isEmpty()) {
			fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/member/");		
			MetaOfUploadFile storedFile = fileStore.storeFile(profileForm.getFile());
			memberDTO.setStore_fname(storedFile.getStore_fname());
			memberDTO.setUpload_fname(storedFile.getUpload_fname());
			memberDTO.setFsize(storedFile.getFsize());
			memberDTO.setFtype(storedFile.getFtype());
		}
	   return "mypage/mypageFavorites";
   }
   
   //즐겨찾기목록
   @GetMapping("/mypageFavorites")
   public String mypage( @ModelAttribute  ProfileForm profileForm, HttpServletRequest request,
         Model model) throws IllegalStateException, IOException { 
            
      HttpSession session = request.getSession(false);
      if(session == null || session.getAttribute("loginMember") == null){
        return "redirect:/login";
      }
       LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
       String id = loginMember.getId();
       log.info(id);
       MemberDTO memberDTO = new MemberDTO();
       
       
       memberDTO = memberSVC.findMemberById(id);
       fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/member/");
       List<FavoriteReq> favoritelist = favoriteSVC.favoriteList(id);
       model.addAttribute("profileForm", profileForm);
       model.addAttribute("mtype",loginMember.getMtype());
       model.addAttribute("Favorite",favoritelist);
       
		
       
	   model.addAttribute("profileForm", memberDTO);
	   log.info(memberDTO.getStore_fname());
	   

     return "mypage/mypageFavorites";
  }	
	//메타정보 → 업로드 정보
	private UpLoadFileDTO convert(MetaOfUploadFile attatchFile) {
		UpLoadFileDTO uploadFileDTO = new UpLoadFileDTO();
		BeanUtils.copyProperties(attatchFile, uploadFileDTO);
		return uploadFileDTO;
	}

//   //회원탈퇴처리-APIMypageController로 옮김. 추후 마이페이지 html로 변경시 해당 컨트롤러 사용 예정 -km
//   @DeleteMapping("/mypageDel")
//   public String mypageDel(
//         @RequestParam String pw,
//         HttpServletRequest request,
//         Model model) {
//      log.info("회원탈퇴");
//      
//      Map<String, String> errors = new HashMap<>();
//      
//      if(pw == null || pw.trim().length() == 0) {
//         errors.put("pw", "비밀번호를 입력하세요");
//         model.addAttribute("errors", errors);
////         return "mypage/memberOutForm";
//         return null;
//      }
//      
//      HttpSession session = request.getSession(false);
//      if(session == null) return "redirect:/login";
//      
//      LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
//      //회원존재유무 확인
//      if(memberSVC.isMemember(loginMember.getId(), pw)) {
//         //탈퇴
//         memberSVC.outMember(loginMember.getId(), pw);
//      }else {
//         errors.put("global", "비밀번호가 잘못되었습니다.");
//         model.addAttribute("errors", errors);
//      }
//      
//      if(!errors.isEmpty()) {
////         return "mypage/memberOutForm";
//         return null;
//      }
//      session.invalidate();
//      
//      return "redirect:/";
//   }
   //개인정보 수정양식
//   @GetMapping("/mypageModify")
public String modifyMember(HttpServletRequest request,
      Model model) {
      log.info("회원양식 호출");
      HttpSession session  = request.getSession(false);
      LoginMember loginMember = 
            (LoginMember)session.getAttribute("loginMember");
      
      if(loginMember == null)return "redirect:/login";
      
      //회원정보 가져오기
      
      MemberDTO memberDTO = memberSVC.findMemberById(loginMember.getId());
      
      ModifyForm modifyForm = new ModifyForm();
      BeanUtils.copyProperties(memberDTO, modifyForm);
      
      model.addAttribute("modifyForm",modifyForm);
      
      log.info(modifyForm.toString());
   
   
   return "/mypage/mypageModify";
}

   
   //개인정보 수정처리
//   @PatchMapping("/mypageModify")
   public String modifyMember(@Valid @ModelAttribute ModifyForm modifyForm,
         BindingResult bindingResult,
         HttpServletRequest request) {
	   
         log.info("회원수정처리 호출됨");
         HttpSession session = request.getSession(false);
         LoginMember loginMember =
               (LoginMember)session.getAttribute("loginMember");
         log.info("회원 수정 처리:{}"+loginMember.toString());
         
         //세션이 없으면 로그인 페이지로 이동
         if(loginMember == null) return "redirect:/login";
         
         //비밀번호를 잘못 입력 했을경우
//         if(!memberSVC.isMemember(loginMember.getId(), modifyForm.getPw())) {
//            bindingResult.rejectValue("pw", "error.member.MyEditForm", "비밀번호가 잘못입력되었습니다.");
//         }
         if(bindingResult.hasErrors()) {
            log.info("errors={}",bindingResult);
            return "mypage/modifyForm";
         }
         MemberDTO mdto = new MemberDTO();
         
         BeanUtils.copyProperties(modifyForm, mdto);
         
         memberSVC.modifyMember(loginMember.getId(), mdto);
         log.info("=={},{}",loginMember.getId(), mdto);

         return "redirect:/mypage/mypageModify";
         
         
   }
   
   //내업체 목록
   //@GetMapping("/mybusilist")
   public String modifyBusi(HttpServletRequest request,
		      Model model) {
	   
	      HttpSession session = request.getSession(false);
	      if(session == null || session.getAttribute("loginMember") == null){
	        return "redirect:/login";
	      }
	       LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
	       
	       String id = loginMember.getId();
	       
	       log.info(id);

	       List<BusinessLoadDTO> mybusiList = businessSVC.mybusiList(id);
	       
	 
	       model.addAttribute("mybusiList",mybusiList);

	      return "mypage/mybusilist";
		}
   
   //좋아요
  // @GetMapping("/mypageGood")
   public String mypageGood(HttpServletRequest request,
		      Model model){
	   HttpSession session = request.getSession(false);
	      if(session == null || session.getAttribute("loginMember") == null){
	    	  
	        return "redirect:/login";
	      }
	       LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
	       
	       String id = loginMember.getId();
	       
	       log.info(id);
	       
	      List<GoodBoardDTO> goodBoardList = goodBoardSVC.goodBoardList(id);
	      model.addAttribute("goodBoardList",goodBoardList);
	      
	      log.info(goodBoardList.toString());
	   
	   return "mypage/mypageGood";
   
   }
   //내업체 수정양식
   @GetMapping("/mybusiModify/{bnum}")
   public String mybusiModify(@PathVariable Integer bnum ,HttpServletRequest request,
		      Model model) {
	   HttpSession session = request.getSession(false);
	      if(session == null || session.getAttribute("loginMember") == null){
	    	  
	        return "redirect:/login";
	      }
	      
	      LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
	       
	       loginMember.getId();
	      
	       
	      //내업체정보 가져오기
	       
	            
	       BusinessLoadDTO businessLoadDTO = new BusinessLoadDTO();
	       businessLoadDTO = businessSVC.findBusiByBnum(bnum);  
	      
	       model.addAttribute("mybusiModify", businessLoadDTO);
	   
	      return "/mypage/mybusiModify";
   }
   
   //내업체수정처리
   @PatchMapping("/mybusiModify/{bnum}")
   public String mybusiModify(@PathVariable Integer bnum ,@Valid @ModelAttribute  BusiModifyForm busiModifyForm,
         BindingResult bindingResult,
     	RedirectAttributes redirectAttributes,
         HttpServletRequest request) {
	   
         log.info("회원수정처리 호출됨");
         
         HttpSession session = request.getSession(false);
         LoginMember loginMember =
               (LoginMember)session.getAttribute("loginMember");
         
         
         //세션이 없으면 로그인 페이지로 이동
         if(loginMember == null) return "redirect:/login";
         
         
         if(bindingResult.hasErrors()) {
        	 
            log.info("errors={}",bindingResult);
            return "mypage/busiModifyForm";
         }
         
         
         BusinessLoadDTO businessLoadDTO = new BusinessLoadDTO();
         
         BeanUtils.copyProperties(busiModifyForm, businessLoadDTO);
         
     	BusinessLoadDTO modibusiBnum = businessSVC.modifyBusi(bnum, businessLoadDTO);
		redirectAttributes.addAttribute("bnum",modibusiBnum.getBnum());
		
         return "redirect:/mypage/mybusiModify/{bnum}";
   }
}