/**
 * Ajax 리퀘스트 편리하게 사용하기 위해 필요한 파일
 * 리뷰, 댓글 즐겨찾기, 좋아요 등 Ajax가 사용되는 페이지는 헤드에
 * <script src="/js/api/ajaxCall.js"> 추가해야됨
 * 모든 페이지에 적용되도록 templateMain.html, templateSub.html 등에 추가해놔도 상관없을듯
 */
'use strict';

const request = {
	
	get(url) {
		return fetch(url);	//fetch(url,{method:'GET'});
	},
	
	post(url,payload) {
		return fetch(url,{
			method: 'POST',
			headers:{'content-Type' : 'application/json'},
			body : JSON.stringify(payload)
		})
	},
	
	mpost(url,formData) {
		return fetch(url,{
			method: 'POST',
			headers:{},
			body : formData
		})
	},
		
	patch(url,payload) {
		return fetch(url,{
			method: 'PATCH',
			headers:{'content-Type' : 'application/json'},
			body : JSON.stringify(payload)
		})
	},
	
	mpatch(url,formData) {
		return fetch(url,{
			method: 'PATCH',
			headers:{},
			body : formData
		})
	},
	
	delete(url){
		return fetch(url, {method: 'DELETE'});
	}

}
