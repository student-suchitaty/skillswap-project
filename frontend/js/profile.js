console.log("profile.js loaded");

async function loadProfile() {

    const email =
    localStorage.getItem("userEmail");

    try {

        const response = await fetch(
            "http://localhost:8090/user/" + email
        );
        console.log(response.status);

        const user = await response.json();

        console.log(user);

        document.getElementById(
            "userName"
        ).innerText = user.name;

        document.getElementById(
            "userEmail"
        ).innerText = user.email;

    }

    catch(error) {

        console.log(error);

    }

}

loadProfile();