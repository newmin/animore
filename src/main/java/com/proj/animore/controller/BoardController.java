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
import com.proj.animore.form.board.modifyBoardForm;
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
		list.add(new Code("F","자유게시판"));
		list.add(new Code("M","벼룩시장"));
		list.add(new Code("P","내새끼 보세요"));
		return list;
	}

	//페이징버튼클릭시 게시글 목록출력
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
		
	   //요청페이지가 없으면 1페이지로
		if(reqPage == null) reqPage = 1;
		//log.info("cate,reqPage,searchType,keyword={}",cate,reqPage,searchType,keyword);
		//사용자가 요청한 페이지번호
		fc.getRc().setReqPage(reqPage);
		
		//검색어유무
		if((searchType == null || searchType.equals(""))
				&&(keyword == null || keyword.equals(""))
				&&(cate.equals("P"))) {
			log.info("검색없는갤러리클릭!");

			//게시판 전체레코드수
			fc2.setTotalRec(boardSVC.totalRecordCount(cate));
			
			
			list = boardSVC.list(cate,
								fc.getRc().getStartRec(),
								fc.getRc().getEndRec());
			
			nlist = boardSVC.noticeList(cate);	
			log.info("cate:{}",cate);
			log.info("list:{}",list);

			model.addAttribute("fc",fc2);
			
		}else if((searchType == null || searchType.equals(""))
				&&(keyword == null || keyword.equals(""))) {

		//게시판 전체레코드수
		fc.setTotalRec(boardSVC.totalRecordCount(cate));
		
		list = boardSVC.list(cate, 
							fc.getRc().getStartRec(),
							fc.getRc().getEndRec());
		log.info("list:{}",list);
		
		nlist = boardSVC.noticeList(cate);	
		
		model.addAttribute("fc",fc);
			
		}else if
		//갤러리타입일경우
		 (cate.equals("P")) {
			log.info("갤러리클릭!");

			//게시판 전체레코드수
			fc2.setTotalRec(boardSVC.totalRecordCount(cate, searchType, keyword));
			
			
			list = boardSVC.list(new SearchDTO(
					cate,
					fc.getRc().getStartRec(),
					fc.getRc().getEndRec(),
					searchType,keyword)
					);
			
			fc2.setSearchType(searchType);
			fc2.setKeyword(keyword);
			
			
			log.info("list:{}",list);

			model.addAttribute("fc",fc2);
			
			
		}else  {
			log.info("그외카테고리클릭");

		//게시판 전체레코드수
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
	
	//게시글 조회
	@GetMapping("/post/{bnum}")
	public String post(@PathVariable Integer bnum,
										Model model,
										HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		if(loginMember == null) return "redirect:/login";
		String id = loginMember.getId();
		//해당회원이 해당글 좋아요여부확인
		int isGoodBoard = goodBoardSVC.isGoodBoard(bnum, id);
	    model.addAttribute("good",isGoodBoard);
	    
	    //해당글 공지여부확인
	    boolean isNotice = boardSVC.isNotice(bnum);
	    model.addAttribute("notice",isNotice);
	    log.info("isNotice:{}",isNotice);
	    
		//조회시 조회수 하나씩 증가
		boardSVC.upBhit(bnum);
		
		//fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/member/");	
		BoardReqDTO boardReqDTO = boardSVC.findBoardByBnum(bnum);
		model.addAttribute("post",boardReqDTO);
		
		
		//게시글 조회시 해당 게시글의 댓글목록도 함께 불러옴.
		List<RboardListReqDTO> replyList = rboardSVC.all(bnum);
		model.addAttribute("reply", replyList);
		
		
		log.info("replyList:{}",replyList);
		
		return "board/boardDetail";
	}
	
	//게시글 작성화면 출력
	@GetMapping("")
	public String addPost(@RequestParam String cate,
						@ModelAttribute BoardForm boardForm,
						HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);

		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		if(loginMember == null) return "redirect:/login";
		
		boardForm.setBcategory(cate);
			
		return "board/addBoardForm";
	}
	
	//게시글 등록처리
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
		//boardForm 의 값이 boardDTO에 복사됨
		BeanUtils.copyProperties(boardForm, boardDTO);
		//첨부파일 메타정보 추출
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
	
	//답글양식출력
	@GetMapping("/reply/{bnum}")
	public String replyForm(@PathVariable int bnum,
							Model model,
							HttpServletRequest request) {
		ReplyForm replyForm = new ReplyForm();
		
//		//세션에서 회원정보 가져오기
//		HttpSession session = request.getSession(false);
//		if(session != null && session.getAttribute("loginMember") != null) {
//			LoginMember loginMember =
//						(LoginMember)session.getAttribute("loginMember");
//			
//			replyForm.setId(loginMember.getId());
//		}
		BoardReqDTO pBoardDTO = boardSVC.findBoardByBnum(bnum);
		
		replyForm.setPbnum(pBoardDTO.getBnum());
		replyForm.setBcategory(pBoardDTO.getBcategory());
		replyForm.setBtitle("답글 : "+pBoardDTO.getBtitle());
		
		model.addAttribute("replyForm",replyForm);
		
		return "board/replyForm";
	}
	//답글작성처리
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
		
		//첨부파일 메타정보 추출
		fileStore.setFilePath("d:/upload/board/");
		List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(replyForm.getFiles());
		//UploadFileDTO 변환	
		boardDTO.setFiles(convert(storedFiles));
		
		int rbnum = boardSVC.reply(loginMemberId,boardDTO);
		
		redirectAttributes.addAttribute("bnum",rbnum);
		
		return "redirect:/board/post/{bnum}";
		
	}
	
	
	//게시글수정양식출력
	@GetMapping("/modify/{bnum}")
	public String modifyPostForm(@PathVariable Integer bnum,
								Model model) {
		
		
		model.addAttribute("boardForm",boardSVC.findBoardByBnum(bnum));
		log.info("boardForm:{}",boardSVC.findBoardByBnum(bnum));
		return "board/modifyBoardForm";
	}
	
	//게시글 수정 처리
	@PatchMapping("/modify/{bnum}")
	public String modifyPost(@PathVariable Integer bnum,
							@Valid @ModelAttribute modifyBoardForm modifyForm,
							BindingResult bindingResult,
							RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		
		if(bindingResult.hasErrors()) {
			return "board/modifyBoardForm";
		}
		
		fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/board/");	
		BoardDTO boardDTO = new BoardDTO();
		
		//첨부파일 메타정보 추출
		List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(modifyForm.getFiles());
				
		boardDTO.setFiles(convert(storedFiles));
		
		BeanUtils.copyProperties(modifyForm, boardDTO);
		
		
		int modifyedBnum = boardSVC.modifyBoard(bnum, boardDTO);
		redirectAttributes.addAttribute("bnum",modifyedBnum);

		return "redirect:/board/post/{bnum}";
		
	}
	//게시글 삭제처리
	@ResponseBody
	@DeleteMapping("/{bnum}")
	public Result deletePost(@PathVariable Integer bnum) {
		
		boardSVC.deleteBoard(bnum);
		
		return new Result ("00","ok",bnum);
	}
	
	//게시글첨부파일삭제
	@ResponseBody
	@DeleteMapping("/attach/{sfname}")
	public Result deleteFile(@PathVariable String sfname) {
		
		fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/board/");	
		if(fileStore.deleteFile(sfname)) {
			boardUploadFileDAO.deleteFileBySfname(sfname);
		}else {
		return new Result("01","nok","파일삭제실패!");
		}
		return new Result("00","ok","파일삭제성공!");
	}
	
//	//제목으로 게시글 검색
//	@GetMapping("/{cate}/search/title")
//	public String searchByBtitle(@PathVariable String cate,
//								@RequestParam String btitle,
//								@RequestParam(required = false) Integer reqPage,
//								Model model) {
//		List<BoardReqDTO> list = null;
//		
//		if(reqPage ==null) reqPage =1;
//		
//		//갤러리타입일경우
//		if(cate.equals("P")) {
//		log.info("갤러리클릭!");
//		//사용자가 요청한 페이지번호
//		pc2.getRc().setReqPage(reqPage);
//		//게시판 전체레코드수
//		pc2.setTotalRec(boardSVC.totalRecordCount(cate));
//		//페이징 계산
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
//			log.info("그외카테고리클릭");
//		//사용자가 요청한 페이지번호
//		pc.getRc().setReqPage(reqPage);
//		//게시판 전체레코드수
//		pc.setTotalRec(boardSVC.totalRecordCount(cate));
//		//페이징 계산
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
//	//닉네임으로 게시글 검색
//	@ResponseBody
//	@GetMapping("/search/nickname/{bcategory}")
//	public Result2 searchByNickname(@PathVariable String bcategory,
//									@RequestParam String nickname,
//									@RequestParam(required = false) Integer reqPage,
//									HttpServletRequest request) {
//		
//		//요청페이지가 없으면 1페이지로
//		if(reqPage == null) reqPage = 1;
//		//사용자가 요청한 페이지번호
//		pc.getRc().setReqPage(reqPage);
//		//게시판 전체레코드수
//		pc.setTotalRec(boardSVC.totalRecordCountBybtitle(bcategory, nickname));
//		//페이징 계산
//		pc.calculatePaging(); 
//		
//		List<BoardReqDTO> list = boardSVC.findBoardByNickname(bcategory,nickname,
//																pc.getRc().getStartRec(),
//																pc.getRc().getEndRec());
//		log.info("bcategory:{}",bcategory);
//		Result2 result = new Result2();
//		if (list.size() == 0) {
//			result.setRtcd("01");
//			result.setRtmsg("게시글이 없습니다.");
//		} else {
//			result.setRtcd("00");
//			result.setRtmsg("성공");
//			result.setData(list);
//			result.setData2(pc);
//		}
//		
//		log.info("result:{}",result);
//		return result;
//	}
//	//본문으로 게시글 검색
//	@ResponseBody
//	@GetMapping("/search/content/{bcategory}")
//	public Result2 searchByBcontent(@PathVariable String bcategory,
//									@RequestParam String bcontent,
//									@RequestParam(required = false) Integer reqPage,
//									HttpServletRequest request) {
//		
//		//요청페이지가 없으면 1페이지로
//		if(reqPage == null) reqPage = 1;
//		//사용자가 요청한 페이지번호
//		pc.getRc().setReqPage(reqPage);
//		//게시판 전체레코드수
//		pc.setTotalRec(boardSVC.totalRecordCountBybtitle(bcategory, bcontent));
//		//페이징 계산
//		pc.calculatePaging(); 
//		
//		List<BoardReqDTO> list = boardSVC.findBoardByBcontent(bcategory,bcontent,
//															pc.getRc().getStartRec(),
//															pc.getRc().getEndRec());
//		log.info("bcategory:{}",bcategory);
//		Result2 result = new Result2();
//		if (list.size() == 0) {
//			result.setRtcd("01");
//			result.setRtmsg("게시글이 없습니다.");
//		} else {
//			result.setRtcd("00");
//			result.setRtmsg("성공");
//			result.setData(list);
//			result.setData2(pc);
//		}
//		
//		log.info("result:{}",result);
//		return result;
//	}
//	
	//좋아요클릭시
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
			result.setRtmsg("좋아요 추가되었습니다.");
			result.setData(bgoodCnt);
		} else {
			result.setRtcd("00");
			result.setRtmsg("좋아요 제거되었습니다.");
			result.setData(bgoodCnt);
		}

		return result;
	}
	//공지버튼클릭시
	@ResponseBody
	@GetMapping("/notice/{bnum}")
	public Result postNotice(@PathVariable int bnum) {
		boolean res = boardSVC.isNotice(bnum);
		Result result = new Result();
		if(res ==false) {
			boardSVC.addNotice(bnum);
			result.setRtcd("01");
			result.setRtmsg("공지추가되었습니다.");
		}else {
			result.setRtcd("00");
			result.setRtmsg("공지삭제되었습니다..");
			boardSVC.delNotice(bnum);
		}
		return result;
	}
	
}
	
