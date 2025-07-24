alert("JS loaded");

const REDIRECT_URI = `http://localhost:8080`;

function onClickNaverLoginButton() {
    alert("Naver login button clicked");
    window.location.href = `http://localhost:8080/oauth2/authorization/naver?redirectUri=${REDIRECT_URI}`;
}

function onClickGetDataButton() {
    alert("Get data button clicked");
    fetch(`http://localhost:8080/my`,
        {
            method: "GET",
            credentials: "include"
        })
        .then((data) => {
            alert(data);
        })
        .catch((error) => {
            alert(error);
        });
}