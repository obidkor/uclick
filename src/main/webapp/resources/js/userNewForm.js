//변수 지정
const saveForm = document.querySelector(".userSaveForm");
const submitBtn = document.querySelector(".submitBtn");
const phoneList = document.querySelector(".phoneList");
const addBtn = document.querySelector(".addBtn");


function deleteForm(event){//input tag 지우기
  const btn = event.target;
  const li = btn.parentNode.parentNode;
  
  //삭제
  phoneList.removeChild(li); 
} 

function addForm(){//input 추가
	//elemeent 만들기
	const input = document.createElement("input");
	const delBtn =document.createElement("button");
	delBtn.type="button";
	const tr =document.createElement("tr");
	const td =document.createElement("td");
	const inputTd = document.createElement("td");
	
	//속성지정
	delBtn.innerText = "추가 취소";
	td.innerText = "number";
	input.type = "text";
	input.classname = "phone";
	input.name = "number"; 
	
	//appendchild
	phoneList.appendChild(tr);
	tr.appendChild(td);
	tr.appendChild(inputTd);
	inputTd.appendChild(input);
	inputTd.appendChild(delBtn);
	delBtn.addEventListener("click", deleteForm);//삭제버튼 이벤트리스너
  
}

function handleSubmit(){//submit
	saveForm.submit();
}

function loadAddBtn(){//click->addBtn실행
	addBtn.addEventListener("click", addForm);
}

function init(){//시작
loadAddBtn();
submitBtn.addEventListener("click",handleSubmit)
}

init();
