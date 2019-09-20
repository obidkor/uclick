const searchForm = document.querySelector(".searchForm");
const searchCombo = document.querySelector(".searchCombo");
const searchInput = document.querySelector(".searchBox");
var searchList = document.querySelector(".searchList");
const autoComplete = document.querySelector(".autoComplete");

var searchesList = "searches1";//defalut


let searches = [];
let searches1 = [];
let searches2 = [];



function deletesearch(event) {
  const btn = event.target;
  const li = btn.parentNode;

  searchList.removeChild(li);
  const cleansearches = searches.filter(function(search) {
    return search.id !== parseInt(li.id);
  });
  searches = cleansearches;
  savesearches();
}

function savesearches() {
  localStorage.setItem(searchesList, JSON.stringify(searches));
}

function paintsearch(text) {
  const li = document.createElement("li");
  const delBtn = document.createElement("button");
  const span = document.createElement("span");
  const newId = searches.length + 1;
  delBtn.innerText = "❌";
  delBtn.addEventListener("click", deletesearch);
  span.innerText = text;
  li.appendChild(delBtn);
  li.appendChild(span);
  li.id = newId;
  searchList.appendChild(li);
  const searchObj = {
    text: text,
    id: newId
  };
  searches.push(searchObj);
  savesearches();
}
function showToggle(event){
    if(searchInput.value===""){
        searchList.classList.toggle("hide",false);
    }else{
        searchList.classList.toggle("hide",true);
        //fetch하기
    }
}

function hide(event){
    searchList.classList.toggle("hide",true);
    //fetch한거도 숨기기

}

function handleSubmit(event) {
	event.preventDefault();
	const currentValue = searchInput.value;
	paintsearch(currentValue);
	searchInput.value = "";
}

function loadsearches() {
  const loadedsearches = localStorage.getItem(searchesList);
  if (loadedsearches !== null) {
    const parsedsearches = JSON.parse(loadedsearches);
    parsedsearches.forEach(function(search) {
      paintsearch(search.text);
    });
  }
}

function check(event){
if(searchCombo.value==1){
	searchesList = "searches1";
	searches = searches1;
	searchList = document.querySelector(".searchList");
}else if(searchCombo.value==2){
	searchesList = "searches2";
	searches = searches2;
	searchList = document.querySelector(".searchList2");
}
}

function init() {
  loadsearches();
  searchForm.addEventListener("submit", handleSubmit);
  searchInput.addEventListener("focus",showToggle);
  searchInput.addEventListener("blur",hide);
  searchInput.addEventListener("keyup",hide);
  searchCombo.addEventListener("change",check);
  
}


init();