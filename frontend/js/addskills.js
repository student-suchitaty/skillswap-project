const skillForm =
document.getElementById("skillForm");

skillForm.addEventListener(
"submit",
async function(e){

    e.preventDefault();

    const skillData = {

        email:
        localStorage.getItem("userEmail"),

        offeredSkill:
        document.getElementById(
        "offeredSkill").value,

        wantedSkill:
        document.getElementById(
        "wantedSkill").value

    };

    console.log(skillData);

    try{

        const response = await fetch(

        "http://localhost:8090/addSkill",

        {

            method: "POST",

            headers: {

                "Content-Type":
                "application/json"

            },

            body: JSON.stringify(skillData)

        });

        const data =
        await response.json();

        console.log(data);

        alert("Skills Added Successfully");

    }

    catch(error){

        console.log(error);

        alert("Failed To Add Skills");

    }

});