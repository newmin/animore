package com.proj.animore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.proj.animore.dto.MemberDTO;
import com.proj.animore.dto.board.GoodBoardDTO;
import com.proj.animore.dto.business.BusinessLoadDTO;
import com.proj.animore.dto.business.FavoriteReq;
import com.proj.animore.form.BusiModifyForm;
import com.proj.animore.form.LoginMember;
import com.proj.animore.form.ModifyForm;
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
   
   //즐겨찾기 목록
   @GetMapping("/mypageFavorites")
   public String mypage(HttpServletRequest request,
         Model model) {
            
      HttpSession session = request.getSession(false);
      if(session == null || session.getAttribute("loginMember") == null){
        return "redirect:/login";
      }
       LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
       String id = loginMember.getId();
       log.info(id);

       List<FavoriteReq> favoritelist = favoriteSVC.favoriteList(id);
       model.addAttribute("mtype",loginMember.getMtype());
       model.addAttribute("Favorite",favoritelist);

      return "mypage/mypageFavorites";
   }

   //회원탈퇴처리
   @DeleteMapping("/mypageDel")
   public String mypageDel(
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
         memberSVC.outMember(loginMember.getId(), pw);
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
   //@GetMapping("/mypageGood")
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
   //@GetMapping("/mybusiModify")
   public String mybusiModify(HttpServletRequest request,
		      Model model) {
	   HttpSession session = request.getSession(false);
	      if(session == null || session.getAttribute("loginMember") == null){
	    	  
	        return "redirect:/login";
	      }
	      
	      LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
	       
	       loginMember.getId();
	      
	       
	      //내업체정보 가져오기
	       
	      
//	       
//	      BusinessLoadDTO businessLoadDTO = businessSVC.findBusiByBnum(businessLoadDTO.getBnum());
//	      
//	      BusiModifyForm busiModifyForm = new BusiModifyForm();
//	      
//	      BeanUtils.copyProperties(businessLoadDTO, busiModifyForm);
//	      
//	      model.addAttribute("busiModifyForm",busiModifyForm);
//	      
//	      log.info(busiModifyForm.toString());
//	      
	      
	   
	      return "/mypage/mybusiModify";
   }
   //내업체수정처리
   @PatchMapping("/mybusiModify")
   public String mybusiModify(@Valid @ModelAttribute BusiModifyForm busiModifyForm,
         BindingResult bindingResult,
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
         
         businessSVC.modifyBusi(businessLoadDTO.getBnum(),businessLoadDTO);
         
        
         
//        BeanUtils.copyProperties(modifyForm, mdto);
//         
//         memberSVC.modifyMember(loginMember.getId(), mdto);
//         log.info("=={},{}",loginMember.getId(), mdto);

         return "redirect:/mypage/mybusiModify";
   }
   
}