const skillForm = document.getElementById("skillForm");

const offeredSkillSelect = document.getElementById("offeredSkill");
const wantedSkillSelect = document.getElementById("wantedSkill");


// Load approved skills
async function loadApprovedSkills() {

    try {

        const response = await fetch(
            "http://localhost:8090/skills/approved"
        );

        if (!response.ok) {
            throw new Error("Could not load approved skills");
        }

        const skills = await response.json();

        // Clear existing options
        offeredSkillSelect.innerHTML = "";
        wantedSkillSelect.innerHTML = "";


        // Default option
        const offeredDefault = document.createElement("option");

        offeredDefault.value = "";
        offeredDefault.textContent = "Select Skill You Can Teach";
        offeredDefault.disabled = true;
        offeredDefault.selected = true;

        offeredSkillSelect.appendChild(offeredDefault);


        const wantedDefault = document.createElement("option");

        wantedDefault.value = "";
        wantedDefault.textContent = "Select Skill You Want to Learn";
        wantedDefault.disabled = true;
        wantedDefault.selected = true;

        wantedSkillSelect.appendChild(wantedDefault);


        // Add approved skills
        skills.forEach(function(skill) {

            const offeredOption = document.createElement("option");

            offeredOption.value = skill.name;

            offeredOption.textContent =
                skill.name + " (" + skill.category + ")";

            offeredSkillSelect.appendChild(offeredOption);


            const wantedOption = document.createElement("option");

            wantedOption.value = skill.name;

            wantedOption.textContent =
                skill.name + " (" + skill.category + ")";

            wantedSkillSelect.appendChild(wantedOption);

        });

    } catch (error) {

        console.error("Error loading skills:", error);

        offeredSkillSelect.innerHTML =
            '<option value="">Failed to load skills</option>';

        wantedSkillSelect.innerHTML =
            '<option value="">Failed to load skills</option>';

    }
}



// Add Skills
skillForm.addEventListener("submit", async function(e) {

    e.preventDefault();


    const email = localStorage.getItem("userEmail");

    const offeredSkill = offeredSkillSelect.value;
    const wantedSkill = wantedSkillSelect.value;


    // Check login
    if (!email) {

        alert("Please login first.");

        return;
    }


    // Check skill selection
    if (!offeredSkill || !wantedSkill) {

        alert("Please select both skills.");

        return;
    }


    const skillData = {

        email: email,

        offeredSkill: offeredSkill,

        wantedSkill: wantedSkill

    };


    console.log("Skill data:", skillData);


    try {

        const response = await fetch(
            "http://localhost:8090/addSkill",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(skillData)
            }
        );


        if (!response.ok) {

            throw new Error(
                "Failed to add skills"
            );

        }


        const data = await response.json();

        console.log("Saved skill:", data);


        alert("Skills Added Successfully!");


        // Reset form
        skillForm.reset();

    } catch (error) {

        console.error("Error:", error);

        alert("Failed to Add Skills");

    }

});



// Request a new skill
async function requestNewSkill() {


   const email = localStorage.getItem("userEmail");

   // Check login
   if (!email) {
      alert("Please login first.");
       return;
   }
   
    // Ask user for the new skill
    const skillName = prompt(
       "Enter the name of the skill you want to request:"
    );


    // User cancelled
    if (skillName === null) {
       return;
    }

    const cleanedSkillName = skillName.trim();

    // Empty skill
   if (!cleanedSkillName) {
      alert("Please enter a skill name.");
       return;
    }
   try {
       const response = await fetch(
           "http://localhost:8090/skill-requests",
           {
               method: "POST",
               headers: {
                  "Content-Type": "application/json"
               },
               body: JSON.stringify({
                 skillName: cleanedSkillName,
                  requestedBy: email
              })
           }
        );

        // Read the backend response
         const data = await response.json().catch(() => ({}));

        // Show the actual backend error 
        if (!response.ok) {
           throw new Error(
               data.message ||
              "Skill request could not be submitted."
            );
        }

        console.log("Skill request submitted:", data);

        alert(
           "Skill request submitted successfully! " +
           "Admin will review your request."
        );

    } catch (error) {
        console.error("Skill request error:", error);

        alert(
            error.message === "Failed to fetch"
            ? "Cannot connect to the server. Please check whether the backend is running."
            : error.message || "Unable to submit skill request."
         );
    }


}



// Load skills when page opens
loadApprovedSkills();