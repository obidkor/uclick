//div로 출력될 arr
let Lists = [];
//변수
const input = document.querySelector("#myInput");
const combo = document.querySelector(".searchCombo");

function closeAllLists(elmnt) {//출력된 div를 지우는 함수
  /*close all autocomplete lists in the document,
  except the one passed as an argument:*/
  var x = document.getElementsByClassName("autocomplete-items");
  for (var i = 0; i < x.length; i++) {
    if (elmnt != x[i] && elmnt != input.value) {
    x[i].parentNode.removeChild(x[i]);
  }
}
}

function autoSearch(arr){//리스트에서 div를 만들어 화면에 출력
    var a, b, i, val = document.getElementById("myInput").value;
    /*close any already open lists of autocompleted values*/
    closeAllLists();
    if (!val) { return false;}
    currentFocus = -1;
    /*create a DIV element that will contain the items (values):*/
    a = document.createElement("DIV");
    a.setAttribute("id", document.getElementById("myInput").id + "autocomplete-list");
    a.setAttribute("class", "autocomplete-items");
    /*append the DIV element as a child of the autocomplete container:*/
    document.getElementById("myInput").parentNode.appendChild(a);
    /*for each item in the array...*/
    
    for (i = 0; i < arr.length; i++) {
      /*check if the item starts with the same letters as the text field value:*/
      if (arr[i].substr(arr[i].search(new RegExp(val,"i")), val.length).toUpperCase() == val.toUpperCase()){
        /*create a DIV element for each matching element:*/
        b = document.createElement("DIV");
        
        
        /*make the matching letters bold:*/
        b.innerHTML = arr[i].substr(0,arr[i].search(new RegExp(val,"i")));
        b.innerHTML += "<strong>" + arr[i].substr(arr[i].search(new RegExp(val,"i")), val.length) + "</strong>";
        b.innerHTML += arr[i].substr(arr[i].search(new RegExp(val,"i"))+val.length,arr[i].length);
      
        
        
        /*insert a input field that will hold the current array item's value:*/
        b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
        // b.tagName="input";
        /*execute a function when someone clicks on the item value (DIV element):*/
            b.addEventListener("click", function(e) {
            /*insert the value for the autocomplete text field:*/
            input.value = e.target.querySelector("input").value;
            /*close the list of autocompleted values,
            (or any other open lists of autocompleted values:*/
            closeAllLists();
        });
        a.appendChild(b);
      }
    }
}


function check(){//콤보박스 체크 함수
  if(combo.value==1){
     return 1;
  }else if(combo.value==2){
    return 2;
  }
}


function getList(event){
const optValue=check();//콤보박스 체크
var url;
if(optValue==1){//콤보박스에 따른 url 변경
  url = 'http://localhost:8085/sendNumberLike?number='
}else{
  url = 'http://localhost:8085/sendNameLike?name='
}
fetch(
  url+input.value
  )//
.then(function(response) {
  return response.json();//리턴된 값을 json으로 파싱
})
.then(function(myJson) {
	Lists=[];//리스트 초기화
  for(var i in myJson){
    if(optValue==1){//콤보박스에 따라 다른 값을 List에 넣어준다.
      Lists.push(myJson[i].number);
    }else{
      Lists.push(myJson[i].name);
    }
  }
  console.log(Lists);
  autoSearch(Lists);//List에 따라 autocomplete div 출력
}).catch(function(err){
  console.log(err);//에러처리
}
)};

function init(){
  input.addEventListener("input",getList);//입력될때마다 출력될 List가 채워진다.(5개)
}

init();