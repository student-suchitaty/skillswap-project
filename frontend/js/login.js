const loginForm =
document.getElementById("loginForm");

loginForm.addEventListener(
"submit",
async function(e){

    e.preventDefault();

    const user = {

        email: document.getElementById(
        "loginEmail").value,

        password: document.getElementById(
        "loginPassword").value
    };

    console.log(user);

    try{

        const response = await fetch(

        "http://localhost:8090/login",

        {

            method: "POST",

            headers: {

                "Content-Type":
                "application/json"

            },

            body: JSON.stringify(user)

        });

        const data =
        await response.text();

        console.log(data);

        if(data === "Login Successful"){

            alert("Login Successful");

            localStorage.setItem(
            "userEmail",
            user.email
            );

            window.location.href =
            "dashboard.html";

        }

        else{

            alert(data);

        }

    }

    catch(error){

        console.log(error);

        alert("Login Failed");

    }

});