/**
 * 
 */const clockContainer = document.querySelector(".clock"),
  clockTitle = clockContainer.querySelector("h4");

function getTime() {
  //시간 얻기
  const date = new Date();
  const minutes = date.getMinutes();
  const hours = date.getHours();
  const seconds = date.getSeconds();
  //출력규칙
  clockTitle.innerText = `${hours < 10 ? `0${hours}` : hours}:${
    minutes < 10 ? `0${minutes}` : minutes
  }:${seconds < 10 ? `0${seconds}` : seconds}`;
}

function init() {
  getTime();
  setInterval(getTime, 1000);//1초마다 실행
}

init();