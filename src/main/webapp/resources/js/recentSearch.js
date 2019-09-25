var searchList = document.querySelector(".searchList");//검색목록 출력될 div

var searchesList = "searches";//setItem에 들어갈변수


let searches = []; //저장될 arr



function deletesearch(event) {//searches에 id 검사해서 지움//출력도 지움 
  const btn = event.target;
  const li = btn.parentNode;

  searchList.removeChild(li);//div에서 지움
  const cleansearches = searches.filter(function(search) {
    return search.id !== parseInt(li.id); //id가 다르면 걸러서 리턴
  });
  searches = cleansearches;//지워진 리스트를 다시 덮음
  savesearches();//저장
}

function savesearches() {
  localStorage.setItem(searchesList, JSON.stringify(searches));//리스트를 로컬 스토리지에 저장
}

function paintsearch(text) {//div에 들어갈 목록 출력
  //div에 들어갈 elements
  const a = document.createElement("a");
  const delBtn = document.createElement("button");
  const span = document.createElement("span");
  const newId = searches.length + 1; //함수실행시 마다 id는 1씩늘려준다
  
  //element의 속성들
  delBtn.innerText = "❌";
  delBtn.addEventListener("click", deletesearch);
  span.innerText = text;
  
  //출력
  a.appendChild(span);
  a.appendChild(delBtn);
  a.id = newId;
  a.value=text;
  searchList.appendChild(a);
  
  //div 클릭시 검색창으로 value가 이동
  a.addEventListener("click",function(e){
    searchInput.value=a.value
  })
  
  //arr에 들어갈 object
  const searchObj = {
    text: text,
    id: newId
  };
  searches.push(searchObj);//넣고
  savesearches();//저장
}

function handleSubmit(event) {
	const currentValue = searchInput.value;
	paintsearch(currentValue);
}

function loadsearches() {
  const loadedsearches = localStorage.getItem(searchesList);//로컬스토리지에서 내용을 가져와서
  if (loadedsearches !== null) {
    const parsedsearches = JSON.parse(loadedsearches);//내용을 json으로 파싱
    parsedsearches.forEach(function(search) {//json 하나하나마다
      paintsearch(search.text);//내용출력 함수 실행
    });
  }
}

function init() {//시작
  loadsearches();
  searchForm.addEventListener("submit", handleSubmit);
  
}


init();