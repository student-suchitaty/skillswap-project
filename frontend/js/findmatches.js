async function findMatches() {

    const skill = document.getElementById("searchSkill").value;
    const container = document.getElementById("matchesContainer");

    container.innerHTML = "";

    try {

        const response = await fetch("http://localhost:8090/findMatches/" + skill);

        const data = await response.json();

        data.forEach(function(match) {

            container.innerHTML += `
                <div class="match-card">

                    <h3>${match.offeredSkill}</h3>

                    <p>Wants: ${match.wantedSkill}</p>

                    <button onclick="sendRequest(localStorage.getItem('email'), '${match.email}')">
                        Send Request
                    </button>

                </div>
            `;
        });

    } catch (error) {
        console.log(error);
    }
}
async function sendRequest(sender, receiver) {

    const request = {
        senderEmail: sender,
        receiverEmail: receiver,
        status: "pending"
    };

    try {

        const response = await fetch("http://localhost:8090/send-request", {

            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(request)
        });

        const data = await response.text(); // IMPORTANT FIX
        console.log(data);

        alert("Request Sent Successfully");

    } catch (error) {
        console.log(error);
        alert("Error sending request");
    }
}