const saveForm = document.querySelector(".userSaveForm");
const submitBtn = document.querySelector(".submitBtn");
const phoneList = document.querySelector(".phoneList");


function deleteForm(event){
  const btn = event.target;
  const li = btn.parentNode;
  phoneList.removeChild(li); 
} 

function addForm(){
	const input = document.createElement("input");
	const delBtn =document.createElement("button");
	delBtn.type="button";
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

function handleSubmit(){
	saveForm.submit();
}

function loadAddBtn(){
	const addBtn = document.createElement("button");
	const li =document.createElement("li");
	addBtn.innerText = "전화기 추가";
	addBtn.type="button"
	phoneList.appendChild(li);
	li.appendChild(addBtn);
	addBtn.addEventListener("click", addForm);
}

function init(){
loadAddBtn();
submitBtn.addEventListener("click",handleSubmit)
}

init();

console.log('fdsafdsafds');