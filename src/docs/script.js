const saveFrom = document.querySelector(".userSaveForm");
const phoneList = document.querySelector(".phoneList");
let index = 0;

function deleteForm(){
	const btn = document.querSelector(`${index}`)
	const li = btn.parentNode;
	phoneList.removeChild(li);
}

function addForm(){
	const input = document.createElement("input");
	const delBtn =document.createElement("button");
	const li =document.createElement("li");
  const newId = index+1;
	delBtn.innerText = "추가 취소";
	input.type = "text";
	input.classname = "phone";
	input.name = "number"; 
  input.id = newId;
	phoneList.appendChild(li);
	li.appendChild(input);
	li.appendChild(delBtn);
	delBtn.addEventListener("click", deleteForm);
  
}

function loadAddBtn(){
	const addBtn = document.createElement("button");
	const li =document.createElement("li");
	addBtn.innerText = "전화기 추가";
	phoneList.appendChild(li);
	li.appendChild(addBtn);
	addBtn.addEventListener("click", addForm);
}

function init(){
loadAddBtn();
}

init()