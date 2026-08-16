const registerForm =
document.getElementById("registerForm");

registerForm.addEventListener(
"submit",
async function(e){

    e.preventDefault();

    const user = {

        name: document.getElementById(
        "registerName").value,

        email: document.getElementById(
        "registerEmail").value,

        password: document.getElementById(
        "registerPassword").value
    };

    console.log(user);

    try{

        const response = await fetch(

        "http://localhost:8090/register",

        {

            method: "POST",

            headers: {

                "Content-Type":
                "application/json"

            },

            body: JSON.stringify(user)

        });

        const data =
        await response.json();

        console.log(data);

        alert("Registration Successful");

        window.location.href =
        "login.html";

    }

    catch(error){

        console.log("ERROR:", error);

        alert("Registration Failed");

    }

});