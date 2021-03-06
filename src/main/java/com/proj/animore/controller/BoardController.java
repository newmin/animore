package com.proj.animore.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proj.animore.common.file.FileStore;
import com.proj.animore.common.file.MetaOfUploadFile;
import com.proj.animore.common.paging.FindCriteria;
import com.proj.animore.dao.board.BoardUploadFileDAO;
import com.proj.animore.dto.board.BoardDTO;
import com.proj.animore.dto.board.BoardReqDTO;
import com.proj.animore.dto.board.BoardUploadFileDTO;
import com.proj.animore.dto.board.RboardListReqDTO;
import com.proj.animore.dto.board.SearchDTO;
import com.proj.animore.form.Code;
import com.proj.animore.form.LoginMember;
import com.proj.animore.form.Result;
import com.proj.animore.form.board.BoardForm;
import com.proj.animore.form.board.ReplyForm;
import com.proj.animore.form.board.ModifyBoardForm;
import com.proj.animore.svc.board.BoardSVC;
import com.proj.animore.svc.board.GoodBoardSVC;
import com.proj.animore.svc.board.RboardSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	private final BoardSVC boardSVC;
	private final RboardSVC rboardSVC;
	private final GoodBoardSVC goodBoardSVC;
	private final FileStore fileStore;
	private final BoardUploadFileDAO boardUploadFileDAO;
	@Autowired
	@Qualifier("fc10")
	private FindCriteria fc;
	@Autowired
	@Qualifier("fc8")
	private FindCriteria fc2;
	
	@ModelAttribute("bcategoryCode")
	public List<Code> bcategory(){
		List<Code> list = new ArrayList<>();
		list.add(new Code("Q","Q&A"));
		list.add(new Code("F","???????????????"));
		list.add(new Code("M","????????????"));
		list.add(new Code("P","????????? ?????????"));
		return list;
	}

	//???????????????????????? ????????? ????????????
	@GetMapping({"/{cate}",
				"{cate}/{reqPage}",
				"{cate}/{reqPage}/{searchType}/{keyword}"})
	public String boardpList(@PathVariable(required = false) String cate,
							@PathVariable(required = false) Integer reqPage,
							@PathVariable(required = false) String searchType,
							@PathVariable(required = false) String keyword,
							Model model) {
//	     if(bcategory.equals("Q"))   bcategory="Q";
//	     if(bcategory.equals("M"))   bcategory="M";
//	     if(bcategory.equals("F"))   bcategory="F";
//	     if(bcategory.equals("P"))   bcategory="P";
		
		 List<BoardReqDTO> list = null;
		 List<BoardReqDTO> nlist = null;
		
		 fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/board/");
	   //?????????????????? ????????? 1????????????
		if(reqPage == null) reqPage = 1;
		//log.info("cate,reqPage,searchType,keyword={}",cate,reqPage,searchType,keyword);
		//???????????? ????????? ???????????????
		fc.getRc().setReqPage(reqPage);
		fc2.getRc().setReqPage(reqPage);
		
		//???????????????(?????????)
		if((searchType == null || searchType.equals(""))
				&&(keyword == null || keyword.equals(""))
				&&(cate.equals("P"))) {
			log.info("???????????????????????????!");

			//????????? ??????????????????
			fc2.setTotalRec(boardSVC.totalRecordCount(cate));
			
			
			list = boardSVC.list(cate,
								fc2.getRc().getStartRec(),
								fc2.getRc().getEndRec());
			
			nlist = boardSVC.noticeList(cate);	
			log.info("cate:{}",cate);
			log.info("list:{}",list);

			model.addAttribute("fc",fc2);
		//??? ??? ?????????????????? ????????????	
		}else if((searchType == null || searchType.equals(""))
				&&(keyword == null || keyword.equals(""))) {

		//????????? ??????????????????
		fc.setTotalRec(boardSVC.totalRecordCount(cate));
		
		list = boardSVC.list(cate, 
							fc.getRc().getStartRec(),
							fc.getRc().getEndRec());
		log.info("list:{}",list);
		
		nlist = boardSVC.noticeList(cate);	
		
		model.addAttribute("fc",fc);
			
		}else if
		//????????????????????????
		 (cate.equals("P")) {
			log.info("???????????????!");

			//????????? ??????????????????
			fc2.setTotalRec(boardSVC.totalRecordCount(cate, searchType, keyword));
			
			
			list = boardSVC.list(new SearchDTO(
					cate,
					fc2.getRc().getStartRec(),
					fc2.getRc().getEndRec(),
					searchType,keyword)
					);
			
			fc2.setSearchType(searchType);
			fc2.setKeyword(keyword);
			
			
			log.info("list:{}",list);

			model.addAttribute("fc",fc2);
			
			
		}else  {
			log.info("????????????????????????");

		//????????? ??????????????????
		fc.setTotalRec(boardSVC.totalRecordCount(cate, searchType, keyword));

		
	    list = boardSVC.list(new SearchDTO(
	    		cate,
	    		fc.getRc().getStartRec(),
	    		fc.getRc().getEndRec(),
	    		searchType,keyword)
	    		);
	    
	  
	    fc.setSearchType(searchType);
		fc.setKeyword(keyword);
		
	    
	    model.addAttribute("fc",fc);
		
	}
		model.addAttribute("boardForm",list);
		model.addAttribute("notice",nlist);
		return "board/board";
	}
	
	//????????? ??????
	@GetMapping("/post/{bnum}")
	public String post(@PathVariable Integer bnum,
										Model model,
										HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		//if(loginMember != null) 
		String id = loginMember.getId();
		//??????????????? ????????? ?????????????????????
		int isGoodBoard = goodBoardSVC.isGoodBoard(bnum, id);
	    model.addAttribute("good",isGoodBoard);
	    
	    //????????? ??????????????????
	    boolean isNotice = boardSVC.isNotice(bnum);
	    model.addAttribute("notice",isNotice);
	    log.info("isNotice:{}",isNotice);
	    
		//????????? ????????? ????????? ??????
		boardSVC.upBhit(bnum);
		
		//fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/member/");	
		BoardReqDTO boardReqDTO = boardSVC.findBoardByBnum(bnum);
		model.addAttribute("post",boardReqDTO);
		
		
		//????????? ????????? ?????? ???????????? ??????????????? ?????? ?????????.
		List<RboardListReqDTO> replyList = rboardSVC.all(bnum);
		model.addAttribute("reply", replyList);
		
		
		log.info("replyList:{}",replyList);
		
		return "board/boardDetail";
	}
	
	//????????? ???????????? ??????
	@GetMapping("")
	public String addPost(@RequestParam String cate,
						@ModelAttribute BoardForm boardForm,
						HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);

		//LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		if(session == null) return "redirect:/login";
		
		boardForm.setBcategory(cate);
			
		return "board/addBoardForm";
	}
	
	//????????? ????????????
	@PostMapping("")
	@Transactional
	public String addpost(@Valid @ModelAttribute BoardForm boardForm,
							BindingResult bindingResult,
							HttpServletRequest request,
							RedirectAttributes redirectAttributes) throws IllegalStateException, IOException  {
		
		HttpSession session = request.getSession(false);
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String loginMemberId = loginMember.getId();
		
		
		if(bindingResult.hasErrors()) {return "board/addBoardForm";}
		
		//boardSVC.addBoard(loginMemberId,boardDTO);
		log.info("boardForm:{}",boardForm);
		
		fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/board/");	
		
		BoardDTO boardDTO = new BoardDTO();
		//boardForm ??? ?????? boardDTO??? ?????????
		BeanUtils.copyProperties(boardForm, boardDTO);
		//???????????? ???????????? ??????
		List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(boardForm.getFiles());
		
		boardDTO.setFiles(convert(storedFiles));
		
		BoardReqDTO stored = boardSVC.addBoard(loginMemberId,boardDTO);
		stored.setFiles(boardDTO.getFiles());
		log.info("stored:{}",stored);
		redirectAttributes.addAttribute("bnum",stored.getBnum());
		
		
	
		return "redirect:/board/post/{bnum}";

	}
	private BoardUploadFileDTO convert(MetaOfUploadFile attatchFile) {
		BoardUploadFileDTO boardUploadFileDTO = new BoardUploadFileDTO();
		BeanUtils.copyProperties(attatchFile, boardUploadFileDTO);
		return boardUploadFileDTO;
	}
	
	private List<BoardUploadFileDTO> convert(List<MetaOfUploadFile> uploadFiles){
		List<BoardUploadFileDTO> list = new ArrayList<>();
		
		for(MetaOfUploadFile file : uploadFiles) {
			BoardUploadFileDTO uploadFileDTO = convert(file);
			list.add(uploadFileDTO);
		}
		return list;
	}
	
	//??????????????????
	@GetMapping("/reply/{bnum}")
	public String replyForm(@PathVariable int bnum,
							Model model,
							HttpServletRequest request) {
		ReplyForm replyForm = new ReplyForm();
		
		//???????????? ???????????? ????????????
//		HttpSession session = request.getSession(false);		
//		if(session != null && session.getAttribute("loginMember") != null) {
//			LoginMember loginMember =
//						(LoginMember)session.getAttribute("loginMember");
//			
//			replyForm.setId(loginMember.getId());
//		}
		BoardReqDTO pBoardDTO = boardSVC.findBoardByBnum(bnum);
		if(pBoardDTO.getBcategory().equals("Q")) {
		
		replyForm.setPbnum(pBoardDTO.getBnum());
		replyForm.setBcategory(pBoardDTO.getBcategory());
		replyForm.setBtitle("?????? : "+pBoardDTO.getBtitle());
		
		model.addAttribute("replyForm",replyForm);
		
		return "board/replyForm";
		}
		return "redirect:/board/Q";	
	}
	//??????????????????
	@PostMapping("/reply/{bnum}")
	public String reply(@PathVariable("bnum") int pbnum,
						@Valid @ModelAttribute ReplyForm replyForm,
						BindingResult bindingResult,
						RedirectAttributes redirectAttributes,
						HttpServletRequest request) throws IllegalStateException, IOException {
		
		HttpSession session = request.getSession(false);
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String loginMemberId = loginMember.getId();
		
		if(bindingResult.hasErrors()) {
			return "board/replyForm";
		}
		BoardDTO boardDTO = new BoardDTO();
		BeanUtils.copyProperties(replyForm, boardDTO);
		
		BoardReqDTO pBoardDTO = boardSVC.findBoardByBnum(pbnum);
		boardDTO.setPbnum(pBoardDTO.getBnum());
		boardDTO.setBgroup(pBoardDTO.getBgroup());
		boardDTO.setBstep(pBoardDTO.getBstep());
		boardDTO.setBindent(pBoardDTO.getBindent());
		
		//???????????? ???????????? ??????
		fileStore.setFilePath("d:/upload/board/");
		List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(replyForm.getFiles());
		//UploadFileDTO ??????	
		boardDTO.setFiles(convert(storedFiles));
		
		int rbnum = boardSVC.reply(loginMemberId,boardDTO);
		
		redirectAttributes.addAttribute("bnum",rbnum);
		
		return "redirect:/board/post/{bnum}";
		
	}
	
	
	//???????????????????????????
	@GetMapping("/modify/{bnum}")
	public String modifyPostForm(@PathVariable Integer bnum,
								Model model,
								HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String loginMemberId = loginMember.getId();
		
		BoardReqDTO boardReqDTO = boardSVC.findBoardByBnum(bnum);
		
		if(loginMemberId.equals(boardReqDTO.getId()) || loginMemberId.equals("admin@animore.com")) {
		
		model.addAttribute("boardForm",boardReqDTO);
		log.info("boardForm:{}",boardSVC.findBoardByBnum(bnum));
		return "board/modifyBoardForm";
		}
		return "index";
	}
	
	//????????? ?????? ??????
	@PatchMapping("/modify/{bnum}")
	public String modifyPost(@PathVariable Integer bnum,
							@Valid @ModelAttribute ModifyBoardForm modifyForm,
							BindingResult bindingResult,
							RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		
		if(bindingResult.hasErrors()) {
			return "board/modifyBoardForm";
		}
		
		fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/board/");	
		BoardDTO boardDTO = new BoardDTO();
		
		//???????????? ???????????? ??????
		List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(modifyForm.getFiles());
				
		boardDTO.setFiles(convert(storedFiles));
		
		BeanUtils.copyProperties(modifyForm, boardDTO);
		
		
		int modifyedBnum = boardSVC.modifyBoard(bnum, boardDTO);
		redirectAttributes.addAttribute("bnum",modifyedBnum);

		return "redirect:/board/post/{bnum}";
		
	}
	//????????? ????????????
	@ResponseBody
	@DeleteMapping("/{bnum}")
	public Result deletePost(@PathVariable Integer bnum,
								HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String loginMemberId = loginMember.getId();
		
		BoardReqDTO delBoard = boardSVC.findBoardByBnum(bnum);
		if(loginMemberId.equals(delBoard.getId()) || loginMemberId.equals("admin@animore.com")) {
		
		boardSVC.deleteBoard(bnum);
		
		return new Result ("00","ok",bnum);
		} 
		return new Result("01","nok","???????????? ???????????????.");
	}
	
	//???????????????????????????
	@ResponseBody
	@DeleteMapping("/attach/{sfname}")
	public Result deleteFile(@PathVariable String sfname) {
		
		fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/board/");	
		if(fileStore.deleteFile(sfname)) {
			boardUploadFileDAO.deleteFileBySfname(sfname);
		}else {
		return new Result("01","nok","??????????????????!");
		}
		return new Result("00","ok","??????????????????!");
	}
	
//	//???????????? ????????? ??????
//	@GetMapping("/{cate}/search/title")
//	public String searchByBtitle(@PathVariable String cate,
//								@RequestParam String btitle,
//								@RequestParam(required = false) Integer reqPage,
//								Model model) {
//		List<BoardReqDTO> list = null;
//		
//		if(reqPage ==null) reqPage =1;
//		
//		//????????????????????????
//		if(cate.equals("P")) {
//		log.info("???????????????!");
//		//???????????? ????????? ???????????????
//		pc2.getRc().setReqPage(reqPage);
//		//????????? ??????????????????
//		pc2.setTotalRec(boardSVC.totalRecordCount(cate));
//		//????????? ??????
//		pc2.calculatePaging();
//		
//		list = boardSVC.findBoardByBtitle(cate, btitle, 
//											pc2.getRc().getStartRec(),
//											pc2.getRc().getEndRec());
//		
//		model.addAttribute("search",list);
//		log.info("list:{}",list);
//		
//
//		model.addAttribute("pc",pc2);
//		
//		
//		}else {
//			log.info("????????????????????????");
//		//???????????? ????????? ???????????????
//		pc.getRc().setReqPage(reqPage);
//		//????????? ??????????????????
//		pc.setTotalRec(boardSVC.totalRecordCount(cate));
//		//????????? ??????
//		pc.calculatePaging();
//		
//		list = boardSVC.findBoardByBtitle(cate, btitle, 
//				pc2.getRc().getStartRec(),
//				pc2.getRc().getEndRec());
//	    
//	    
//	    model.addAttribute("search",list);
//	    model.addAttribute("pc",pc);
//		
//	}
//		return "board/searchList";
//	}
//
//	//??????????????? ????????? ??????
//	@ResponseBody
//	@GetMapping("/search/nickname/{bcategory}")
//	public Result2 searchByNickname(@PathVariable String bcategory,
//									@RequestParam String nickname,
//									@RequestParam(required = false) Integer reqPage,
//									HttpServletRequest request) {
//		
//		//?????????????????? ????????? 1????????????
//		if(reqPage == null) reqPage = 1;
//		//???????????? ????????? ???????????????
//		pc.getRc().setReqPage(reqPage);
//		//????????? ??????????????????
//		pc.setTotalRec(boardSVC.totalRecordCountBybtitle(bcategory, nickname));
//		//????????? ??????
//		pc.calculatePaging(); 
//		
//		List<BoardReqDTO> list = boardSVC.findBoardByNickname(bcategory,nickname,
//																pc.getRc().getStartRec(),
//																pc.getRc().getEndRec());
//		log.info("bcategory:{}",bcategory);
//		Result2 result = new Result2();
//		if (list.size() == 0) {
//			result.setRtcd("01");
//			result.setRtmsg("???????????? ????????????.");
//		} else {
//			result.setRtcd("00");
//			result.setRtmsg("??????");
//			result.setData(list);
//			result.setData2(pc);
//		}
//		
//		log.info("result:{}",result);
//		return result;
//	}
//	//???????????? ????????? ??????
//	@ResponseBody
//	@GetMapping("/search/content/{bcategory}")
//	public Result2 searchByBcontent(@PathVariable String bcategory,
//									@RequestParam String bcontent,
//									@RequestParam(required = false) Integer reqPage,
//									HttpServletRequest request) {
//		
//		//?????????????????? ????????? 1????????????
//		if(reqPage == null) reqPage = 1;
//		//???????????? ????????? ???????????????
//		pc.getRc().setReqPage(reqPage);
//		//????????? ??????????????????
//		pc.setTotalRec(boardSVC.totalRecordCountBybtitle(bcategory, bcontent));
//		//????????? ??????
//		pc.calculatePaging(); 
//		
//		List<BoardReqDTO> list = boardSVC.findBoardByBcontent(bcategory,bcontent,
//															pc.getRc().getStartRec(),
//															pc.getRc().getEndRec());
//		log.info("bcategory:{}",bcategory);
//		Result2 result = new Result2();
//		if (list.size() == 0) {
//			result.setRtcd("01");
//			result.setRtmsg("???????????? ????????????.");
//		} else {
//			result.setRtcd("00");
//			result.setRtmsg("??????");
//			result.setData(list);
//			result.setData2(pc);
//		}
//		
//		log.info("result:{}",result);
//		return result;
//	}
//	
	//??????????????????
	@ResponseBody
	@GetMapping("/good/{bnum}")
	public Result addGoodBoard(@PathVariable int bnum,
								HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String id = loginMember.getId();
		int res= goodBoardSVC.isGoodBoard(bnum,id);
		if(res==0) {
			goodBoardSVC.addGoodBoard(id, bnum);
			goodBoardSVC.upGoodBoardCnt(bnum);
		}else {
			goodBoardSVC.delGoodBoard(bnum, id);
			goodBoardSVC.downGoodBoardCnt(bnum);
		}
		Integer bgoodCnt= goodBoardSVC.GoodBoardCnt(bnum);
		Result result = new Result();
		if (res == 0) {
			result.setRtcd("01");
			result.setRtmsg("????????? ?????????????????????.");
			result.setData(bgoodCnt);
		} else {
			result.setRtcd("00");
			result.setRtmsg("????????? ?????????????????????.");
			result.setData(bgoodCnt);
		}

		return result;
	}
	//?????????????????????
	@ResponseBody
	@GetMapping("/notice/{bnum}")
	public Result postNotice(@PathVariable int bnum) {
		boolean res = boardSVC.isNotice(bnum);
		Result result = new Result();
		if(res ==false) {
			boardSVC.addNotice(bnum);
			result.setRtcd("01");
			result.setRtmsg("???????????????????????????.");
		}else {
			result.setRtcd("00");
			result.setRtmsg("???????????????????????????..");
			boardSVC.delNotice(bnum);
		}
		return result;
	}
	
}
	
