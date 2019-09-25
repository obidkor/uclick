/**
 * 
 */
const searchForm = document.querySelector(".searchForm");
const searchCombo = document.querySelector(".searchCombo");
const searchInput = document.querySelector(".searchBox");

function handleSubmit(event){//submit action 경로 변경
	const currentValue = searchInput.value;
	const currentCombo = searchCombo.value;
	if(currentValue.includes(",")){//쉼표가 잇어야함
			searchForm.action = "multiList.html";
	}
	searchForm.submit();
}

function init() {
  searchForm.addEventListener("submit", handleSubmit);//submit가 발생하면
  
  
}


init();