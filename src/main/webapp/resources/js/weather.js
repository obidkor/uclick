const weather = document.querySelector(".weather");

const API_KEY = "4a38136bf09396e504ffcbbe998ae577";//my key
const COORDS = 'coords';

function getWeather(lat, lng) {
  fetch(
    `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lng}&appid=${API_KEY}&units=metric`
  ).then(function(response){
    return response.json();//json으로 변환
  }).then(function(json){
    const temperature = Math.ceil(json.main.temp);//기온
    const place=json.name//장소
    const weather1 = json.weather[0].main;//날시
    weather.innerText=`${temperature}'C ${place} ${weather1}`;//출력!
  });

}

function saveCoords(coordsObj){
  localStorage.setItem(COORDS, JSON.stringify(coordsObj));//위치 경위도저장 함수

}

function handleGeoSuccess(position){
  const latitude = position.coords.latitude;//위도
  const longitude = position.coords.longitude;//경도
  const coordsObj = {//로컬스토리지에 저장될 object
    latitude,
    longitude
  };
  saveCoords(coordsObj);//저장후
  getWeather(latitude, longitude);//날씨 얻어오자
}

function handleGeoError(){
  console.log('위치를 얻을 수 없음');
}

function askForCoords(){
  navigator.geolocation.getCurrentPosition(handleGeoSuccess, handleGeoError);//현재 위치 요청 navigator
}

function loadCoords(){
  const loadedCoords = localStorage.getItem(COORDS);
  if(loadedCoords === null){//현재위치가 있냐없냐
    askForCoords();//있으면 넣고 
  } else{
    const parsedCoords = JSON.parse(loadedCoords);//없으면 파싱후 
    getWeather(parsedCoords.latitude, parsedCoords.longitude);//날씨 api호출

  }
}

function init(){
  loadCoords();
}

init();