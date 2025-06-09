function proc_set_up() {
	let input_file_tag = document.querySelector('input[type=file]');
	input_file_tag.addEventListener('change', detect_changing_tag);
};

function detect_changing_tag(e) {
	upload_file(e);
};

function vision_file_list(e) {
	let file_path = e.target.value;
	let file_name = parsing_file_path(file_path);
	
	// node 생성
	let span_node = document.createElement('span');
	span_text = document.createTextNode(file_name);
	span_node.appendChild(span_text);
	
	// 파일 리스트 css 속성 변경(display)
	let file_list = document.querySelector('.att_file_lst');
	file_list.style.display = 'block';
	// 파일 리스트에 생성한 노드 삽입
	file_list.appendChild(span_node);
	file_list.insertAdjacentHTML('beforeend', '<button class="det_file">삭제</button>');
	
	// 파일 삭제 버튼에 삭제 이벤트 부착
	let btn_det_file = document.querySelector('.det_file');
	btn_det_file.addEventListener('click', detach_file_tag);
	// input[type=file] 초기화
	e.target.value = '';	
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

function upload_file(e) {
	let att_form = document.querySelector('#att-file-form');
	let form_data = new FormData(att_form);
	
	let file_name = e.target.value;
	
	if(file_name == '') {
		alert('첨부할 파일을 선택하여 주십시오.');
		return;
	}
	
	fetch('/file/upload', {
		'method' : 'POST',
		headers : {
			//'Content-Type' : 'multipart/form-data'
			//, 'Process-Data' : false
			'enctype' : 'multipart/form-data'
			//, 'cache' : false
			//, timeout : 600000
		},
		body : form_data,
	})
	.then(response => response.json())
	.then((data) => {
	
	})
	.catch((error) => {
		alert("데이터를 가지고 오는데 실패하였습니다.");
		console.log(error);
	});
};

function delete_file() {
	
};
