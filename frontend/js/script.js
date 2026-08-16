// ===============================
// SkillSwap JavaScript
// ===============================

console.log("SkillSwap Frontend Loaded Successfully");
// -------------------------------
// REGISTER FUNCTION
// -------------------------------

const registerForm = document.querySelector("#registerForm");

if(registerForm){

    registerForm.addEventListener("submit", function(event){

        event.preventDefault();

        const name = document.querySelector("#registerName").value;
        const email = document.querySelector("#registerEmail").value;
        const password = document.querySelector("#registerPassword").value;

        if(name === "" || email === "" || password === ""){

            alert("Please fill all fields");

            return;
        }

        alert("Registration Successful");
        localStorage.setItem("username", name);

        window.location.href = "login.html";

    });

}



// -------------------------------
// LOGIN FUNCTION
// -------------------------------

const loginForm = document.querySelector("#loginForm");

if(loginForm){

    loginForm.addEventListener("submit", function(event){

        event.preventDefault();

        const email = document.querySelector("#loginEmail").value;
        const password = document.querySelector("#loginPassword").value;

        if(email === "" || password === ""){

            alert("Please enter email and password");

            return;
        }

        alert("Login Successful");

        window.location.href = "dashboard.html";

    });

}



// -------------------------------
// ADD SKILL FUNCTION
// -------------------------------

const skillForm = document.querySelector("#skillForm");

if(skillForm){

    skillForm.addEventListener("submit", function(event){

        event.preventDefault();

        const offeredSkill =
            document.querySelector("#offeredSkill").value;

        const wantedSkill =
            document.querySelector("#wantedSkill").value;

        if(offeredSkill === "" || wantedSkill === ""){

            alert("Please enter both skills");

            return;
        }

        const skillsContainer =
            document.querySelector(".skills");

        // Create Offered Skill

        const newSkill1 = document.createElement("div");

        newSkill1.classList.add("skill");

        newSkill1.innerText = offeredSkill;

        skillsContainer.appendChild(newSkill1);

        // Create Wanted Skill

        const newSkill2 = document.createElement("div");

        newSkill2.classList.add("skill");

        newSkill2.innerText = wantedSkill;

        skillsContainer.appendChild(newSkill2);

        alert("Skills Added Successfully");

        skillForm.reset();

    });

}



// -------------------------------
// SEND REQUEST BUTTONS
// -------------------------------

const requestButtons =
    document.querySelectorAll(".match-card button");

requestButtons.forEach(function(button){

    button.addEventListener("click", function(){

        button.innerText = "Request Sent";

        button.style.background = "green";

        button.disabled = true;

    });

});



// -------------------------------
// DASHBOARD WELCOME MESSAGE
// -------------------------------

const welcomeMessage =
document.querySelector("#welcomeMessage");

if(welcomeMessage){

    const username =
    localStorage.getItem("username");

    welcomeMessage.innerText =
    `Welcome, ${username} 👋`;

}