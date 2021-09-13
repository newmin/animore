/**로그인 후 원래 페이지로 이동
 * 헤더의 로그인 버튼 클릭시
 * 현재의 url을 읽어서 로그인창으로 전달
 * 로그인 후, 기존페이지로 재이동
 */
 
 const login_f = e=> {
	console.log('로그인버튼')
	const currUrl = window.location.href; // http://localhost:8081/
	const url = currUrl.substring(22);
	location.href=`/login?redirectUrl=${url}`;
}
 
 const $login = document.querySelector('.nav__loginBtn');
 $login?.addEventListener('click',login_f);