let Lists = [];
const input = document.querySelector("#myInput");
const combo = document.querySelector(".searchCombo");

function closeAllLists(elmnt) {
  /*close all autocomplete lists in the document,
  except the one passed as an argument:*/
  var x = document.getElementsByClassName("autocomplete-items");
  for (var i = 0; i < x.length; i++) {
    if (elmnt != x[i] && elmnt != input.value) {
    x[i].parentNode.removeChild(x[i]);
  }
}
}

function autoSearch(arr){
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
      if (arr[i].toUpperCase().includes(val.toUpperCase())) {
        /*create a DIV element for each matching element:*/
        b = document.createElement("DIV");
        /*make the matching letters bold:*/
        b.innerHTML = arr[i].substr(0,arr[i].indexOf(val));
        b.innerHTML += "<strong>" + arr[i].substr(arr[i].indexOf(val), val.length) + "</strong>";
        if(arr[i].indexOf(val)+val.length!==arr[i].length){
        b.innerHTML += arr[i].substr(val.length,arr[i].length);
      }
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


function check(){
  if(combo.value==1){
     return 1;
  }else if(combo.value==2){
    return 2;
  }
}

function getList(event){
  Lists=[];
const optValue=check();
var url;
if(optValue==1){
  url = 'http://localhost:8085/sendNumberLike?number='
}else{
  url = 'http://localhost:8085/sendNameLike?name='
}
fetch(
  url+input.value
  )
.then(function(response) {
  return response.json();
})
.then(function(myJson) {
  for(var i in myJson){
    if(optValue==1){
      Lists.push(myJson[i].number);
    }else{
      Lists.push(myJson[i].name);
    }
  }
  console.log(Lists);
  autoSearch(Lists);
}).catch(function(err){
  console.log(err);
}
)};

function init(){
  input.addEventListener("input",getList);
}

init();