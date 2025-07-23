alert("JS loaded");

function onClickNaverLoginButton() {
    alert("Naver login button clicked");
    window.location.href = `http://localhost:8080/oauth2/authorization/naver?redirect-url=${location.protocol}//${location.host}`;
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