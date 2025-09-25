function proc_set_up() {
	// 등록 및 수정 페이지에서 첨부파일 기능이 존재하는 경우
	// 파일 태그에 해당 태그의 변화를 감지하는 이벤트를 달아주고 변화가 감지되면 첨부한 파일을 서버에 업로드한다.
	let input_file_tag = document.querySelector('input[type=file]');
	input_file_tag.addEventListener('change', upload_file);
	
	// 수정 페이지에서 해당 게시글에 첨부파일이 존재하는 경우
	// 첨부파일을 삭제할 수 있도록 삭제 버튼에 이벤트를 달아준다.
	let dels = document.querySelectorAll('[class*="det_file_"]');
	if(dels == null) return;
	
	for(delt of dels) {
		delt.addEventListener('click', delete_file);
	}
};

function vision_file_list(file, e) {
	let file_name = file.name;
	let file_key = file.key;
	
	// <span class="fnm">file.name</span>
	let span_node = document.createElement('span');
	span_node.classList.add('fnm');
	span_text = document.createTextNode(file_name);
	span_node.appendChild(span_text);
	
	// <input type="hidden" name="key" value="file.key">
	let input_node = document.createElement('input');
	input_node.classList.add('key');
	input_node.setAttribute('type', 'hidden');
	input_node.setAttribute('value', file.key);
	
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
	file_list.appendChild(input_node);
	file_list.insertAdjacentHTML('beforeend', btn);
	
	// 파일 삭제 버튼에 삭제 이벤트 부착
	let btn_det_file = document.querySelector('.det_file_'+ file_vol);
	btn_det_file.addEventListener('click', delete_file);
	// input[type=file] 초기화
	e.target.value = '';
	
	// 파일 이름에 다운로드 이벤트 부착
	attach_to_download_e();
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
	btn.previousElementSibling.previousElementSibling.remove();
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
			if(data.message != '') {
				alert(data.message);
				e.target.value = '';
			}
		}
	})
	.catch((error) => {
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
	}
	
	return file_lst;
};

function attach_to_download_e() {
	let file_names = document.querySelectorAll('span[class="fnm"]');
	for(let file_name of file_names) {
		file_name.addEventListener('click', download_file);
	}
}

function download_file(e) {
	let file_name = e.target.innerText;
	let file_key = e.target.nextElementSibling.value;
	
	let form_data = {
		'key' : file_key
		, 'name' : file_name
	};
	
	fetch('/file/down', {
		'method' : 'POST',
		headers : {'Content-Type' : 'application/json'},
		body : JSON.stringify(form_data),
	})
	.then((response) => {
		if(!response.ok) {
			throw Error(response);
		}
	    // Content-Disposition 헤더에서 파일명 추출
	    const contentDisposition = response.headers.get('Content-Disposition');
	    const fileNameRegex = /filename="?([^"]+)"?/i;
	    const matches = fileNameRegex.exec(contentDisposition);
	    file_name = matches && matches[1];
	    file_name = decodeURI(file_name);
    	return response.blob();		
	})
	.then((blob) => {
	    const url = window.URL.createObjectURL(blob);
	    const link = document.createElement('a');
	    link.href = url;
	    link.setAttribute('download', file_name || 'download.bin'); // 파일명이 없을 경우 기본값 설정
	    document.body.appendChild(link);
	    link.click();
	    window.URL.revokeObjectURL(url);
	})
	.catch((error) => {
		alert("파일 다운로드에 실패하였습니다.");
		console.log(error);
	});	
};
