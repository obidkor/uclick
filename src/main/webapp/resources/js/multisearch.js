/**
 * 
 */
const searchForm = document.querySelector(".searchForm");
const searchCombo = document.querySelector(".searchCombo");
const searchInput = document.querySelector(".searchBox");

function handleSubmit(event){
//	event.preventDefault();
	const currentValue = searchInput.value;
	const currentCombo = searchCombo.value;
	if(currentValue.includes(",")){
			searchForm.action = "multiList.html";
	}
	searchForm.submit();
}

function init() {
  searchForm.addEventListener("submit", handleSubmit);
  
  
}


init();