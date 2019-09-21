const saveForm = document.querySelector(".userSaveForm");
const submitBtn = document.querySelector(".submitBtn");
const phoneList = document.querySelector(".phoneList");
const addBtn = document.querySelector(".addBtn");


function deleteForm(event){
  const btn = event.target;
  const li = btn.parentNode.parentNode;
  phoneList.removeChild(li); 
} 

function addForm(){
	const input = document.createElement("input");
	const delBtn =document.createElement("button");
	delBtn.type="button";
	const tr =document.createElement("tr");
	const td =document.createElement("td");
	const inputTd = document.createElement("td");
	delBtn.innerText = "추가 취소";
	td.innerText = "number";
	input.type = "text";
	input.classname = "phone";
	input.name = "number"; 
	phoneList.appendChild(tr);
	tr.appendChild(td);
	tr.appendChild(inputTd);
	inputTd.appendChild(input);
	inputTd.appendChild(delBtn);
	delBtn.addEventListener("click", deleteForm);
  
}

function handleSubmit(){
	saveForm.submit();
}

function loadAddBtn(){
	addBtn.addEventListener("click", addForm);
}

function init(){
loadAddBtn();
submitBtn.addEventListener("click",handleSubmit)
}

init();

console.log('fdsafdsafds');