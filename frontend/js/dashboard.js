const email =
localStorage.getItem("userEmail");

document.getElementById(
"welcomeText"
).innerText =
"Welcome " + email;