function proc_set_up() {
	let input_file_tag = document.querySelector('input[type=file]');
	input_file_tag.addEventListener('change', detect_changing_tag);
};

function detect_changing_tag(e) {
	vision_file_list(e);
};

function vision_file_list(e) {
	let file_path = e.target.value;
	let file_name = parsing_file_path(file_path);
	
	let span_node = document.createElement('span');
	span_text = document.createTextNode(file_name);
	span_node.appendChild(span_text);
	
	let file_list = document.querySelector('.att_file_lst');
	file_list.style.display = 'block';
	file_list.appendChild(span_node);
	file_list.insertAdjacentHTML('beforeend', '<button class="det_file">삭제</button>');
	
	let btn_det_file = document.querySelector('.det_file');
	btn_det_file.addEventListener('click', detach_file_tag);	
};

function parsing_file_path(file_path) {
	let file_name = file_path;
	let index = 0;
	// 파일 이름만 남을 때까지 파싱
	if(file_name.indexOf('\\') > -1) {
		index = file_name.lastIndexOf('\\');
		file_name = file_name.substring(index+1);
	}
	return file_name;
};

function detach_file_tag(e) {
	let btn = e.target;
	btn.previousElementSibling.remove();
	btn.remove();
};

function upload_file() {
	
};

function delete_file() {
	
};
