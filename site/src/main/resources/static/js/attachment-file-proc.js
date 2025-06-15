function proc_set_up() {
	let input_file_tag = document.querySelector('input[type=file]');
	input_file_tag.addEventListener('change', detect_changing_tag);
};

function detect_changing_tag(e) {
	upload_file(e);
};

function vision_file_list(file, e) {
	let file_name = file.name;
	let file_key = file.key;
	
	// node 생성
	let span_node = document.createElement('span');
	span_text = document.createTextNode(file_name);
	span_node.appendChild(span_text);
	
	// 파일 리스트 css 속성 변경(display)
	let file_list = document.querySelector('.att_file_lst');
	file_list.style.display = 'block';
	
	// 파일 리스트에서 몇번째 파일인지 확인
	let file_vol = document.querySelectorAll('.att_file_lst span');
	if(file_vol == null) file_vol = 0 
	else file_vol = file_vol.length;
	
	// 삭제버튼
	let btn = '<button class="det_file_'+ file_vol +'" param="'+ file_key +'">삭제</button>';
	
	// 파일 리스트에 생성한 노드 삽입
	file_list.appendChild(span_node);
	file_list.insertAdjacentHTML('beforeend', btn);
	
	// 파일 삭제 버튼에 삭제 이벤트 부착
	let btn_det_file = document.querySelector('.det_file_'+ file_vol);
	btn_det_file.addEventListener('click', delete_file);
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
			'enctype' : 'multipart/form-data'
		},
		body : form_data,
	})
	.then(response => response.json())
	.then((data) => {
		if(data.result) {
			vision_file_list(data.file, e);
		} else {
			alert("업로드에 실패하였습니다.\n 오류가 반복되면 관리자에게 신고하여 주십시오.");
		}
	})
	.catch((error) => {
		alert("업로드에 실패하였습니다.");
		console.log(error);
	});
};

function delete_file(e) {
	
	let file_key = e.target.getAttribute('param');
	let form_data = {
		'key' : file_key
		, 'category' : document.querySelector('input[name="category"]').value
	};
	
	fetch('/file/delete', {
		'method' : 'POST',
		headers : {'Content-Type' : 'application/json'},
		body : JSON.stringify(form_data),
	})
	.then(response => response.json())
	.then((data) => {
		if(data.result) {
			detach_file_tag(e);
		} else {
			alert("삭제에 실패하였습니다.\n 오류가 반복되면 관리자에게 신고하여 주십시오.");
		}
	})
	.catch((error) => {
		alert("삭제에 실패하였습니다.");
		console.log(error);
	});	
	
};

function getFileList() {
	let category = document.querySelector('input[name="category"]').value;
	
	let buttons = document.querySelectorAll('.att_file_lst button');
	let spans = document.querySelectorAll('.att_file_lst span');
	
	if(buttons == null) return;
	
	let file_lst_leng = buttons.length;
	let file_lst = new Array(file_lst_leng);
	
	for(i = 0; i < file_lst_leng; i++) {
		let file = {'key' : null, 'name' : null };
		let key = buttons[i].getAttribute('param');
		let name = spans[i].innerText;
		file['key'] = key;
		file['name'] = name;
		file['category'] = category;
		file_lst[i] = file;
		console.log(file);
	}
	
	return file_lst;
};
