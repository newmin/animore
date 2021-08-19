const $heart = document.querySelector('.boardForm__likeIcon i');

$heart.addEventListener('click',e=>{
    $heart.classList.toggle('toggleColorChange');
    $heart.classList.toggle('far');
    $heart.classList.toggle('fas');
});