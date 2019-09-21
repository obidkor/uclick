var searchList = document.querySelector(".searchList");

var searchesList = "searches";//defalut


let searches = [];



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
  const a = document.createElement("a");
  const delBtn = document.createElement("button");
  const span = document.createElement("span");
  const newId = searches.length + 1;
  delBtn.innerText = "❌";
  delBtn.addEventListener("click", deletesearch);
  span.innerText = text;
  a.appendChild(span);
  a.appendChild(delBtn);
  a.id = newId;
  a.value=text;
  searchList.appendChild(a);
  a.addEventListener("click",function(e){
    searchInput.value=a.value
  })
  const searchObj = {
    text: text,
    id: newId
  };
  searches.push(searchObj);
  savesearches();
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

function init() {
  loadsearches();
  searchForm.addEventListener("submit", handleSubmit);
  
}


init();