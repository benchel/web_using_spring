// 로딩화면 보이기
function show_loading_gif() {
	let mask = document.querySelector('.mask');
	mask.style.display = 'inline-flex';
};


// 로딩화면 숨김
function hide_loading_gif() {
	let mask = document.querySelector('.mask');
	mask.style.display = 'none';
};