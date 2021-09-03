package com.proj.animore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import com.proj.animore.dto.FavoriteDTO;
import com.proj.animore.dto.FavoriteReq;
import com.proj.animore.dto.MemberDTO;
import com.proj.animore.form.LoginMember;
import com.proj.animore.form.ModifyForm;
import com.proj.animore.svc.FavoriteSVC;
import com.proj.animore.svc.MemberSVC;

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
   //즐겨찾기 목록
   @GetMapping("/mypageFavorites")
   public String mypage(HttpServletRequest request,
         Model model) {
            
      HttpSession session = request.getSession(false);
       LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
       String id = loginMember.getId();
       log.info(id);

       List<FavoriteReq> favoritelist = favoriteSVC.favoriteList(id);

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
   @GetMapping("/mypage")
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
   
   
   
   return "redirect:/mypageModify";
}
   
   //개인정보 수정처리
   @PatchMapping("/mypage")
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
         if(!memberSVC.isMemember(loginMember.getId(), modifyForm.getPw())) {
            bindingResult.rejectValue("pw", "error.member.modifyForm", "비밀번호가 잘못입력되었습니다.");
         }
         if(bindingResult.hasErrors()) {
            log.info("errors={}",bindingResult);
            return "mypage/modifyForm";
         }
         MemberDTO mdto = new MemberDTO();
         memberSVC.modifyMember(loginMember.getId(), mdto);
         log.info("=={},{}",loginMember.getId(), mdto);

         return "redirect:/mypage/mypageModify";
   }
   
}