

const email = localStorage.getItem("userEmail");

// Welcome Message
const welcomeElement = document.getElementById("welcomeMessage");

if (welcomeElement) {
    welcomeElement.innerText = email
        ? "Welcome " + email
        : "Welcome";
}

// Load Dashboard Data
async function loadDashboard() {
    if (!email) {
        document.getElementById("mySkillsList").innerHTML =
            "<p>Please login to view your skills.</p>";
        return;
    }

    try {
        // Fetch all skills from MongoDB
        const response = await fetch("http://localhost:8090/skills");

        if (!response.ok) {
            throw new Error("Unable to load skills.");
        }

        const allSkills = await response.json();

        // Filter skills belonging to the logged-in user
        const mySkills = allSkills.filter(
            skill => skill.email &&
            skill.email.toLowerCase() === email.toLowerCase()
        );

        // Count offered and wanted skills
        const offeredSkills = mySkills.filter(
            skill => skill.offeredSkill
        );

        const wantedSkills = mySkills.filter(
            skill => skill.wantedSkill
        );

        document.getElementById("skillsOfferedCount").innerText =
            offeredSkills.length;

        document.getElementById("skillsWantedCount").innerText =
            wantedSkills.length;

        // Display My Skills
        const mySkillsList = document.getElementById("mySkillsList");
        mySkillsList.innerHTML = "";

        if (mySkills.length === 0) {
            mySkillsList.innerHTML =
                "<p>No skills added yet.</p>";
        } else {
            mySkills.forEach(skill => {
                const item = document.createElement("div");
                item.className = "skill-item";

                item.innerText =
                    "Offering: " + skill.offeredSkill +
                    " | Learning: " + skill.wantedSkill;

                mySkillsList.appendChild(item);
            });
        }

        // Fetch actual requests for the logged-in user
       const requestResponse = await fetch(
          `http://localhost:8090/my-requests/${encodeURIComponent(email)}`
        );

       if (!requestResponse.ok) {
          throw new Error("Unable to load requests.");
       }

       const requests = await requestResponse.json();

       // Count requests involving the logged-in user
       document.getElementById("requestsCount").innerText =
         requests.length;

      // Find matching users based on the logged-in user's wanted skills
      const wantedSkillNames = [
        ...new Set(
           mySkills
             .map(skill => skill.wantedSkill)
             .filter(Boolean)
             .map(skill => skill.trim())
        )
      ];

      let totalMatches = 0;

     for (const wantedSkill of wantedSkillNames) {
       const matchResponse = await fetch(
            `http://localhost:8090/findMatches/${encodeURIComponent(wantedSkill)}`
       );
  
       if (!matchResponse.ok) {
           throw new Error("Unable to load matching skills.");
       }

       const matches = await matchResponse.json();

      // Exclude the logged-in user's own skill records
       const otherUsers = matches.filter(
          match =>
            match.email &&
            match.email.toLowerCase() !== email.toLowerCase()
       );

        totalMatches += otherUsers.length;
     }

    document.getElementById("matchesFoundCount").innerText = totalMatches;

  

     

    } catch (error) {
        console.error("Dashboard error:", error);

        document.getElementById("mySkillsList").innerHTML =
            "<p>Unable to load skills. Please check the backend.</p>";
    }
}

loadDashboard();

