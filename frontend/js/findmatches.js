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

                    <<button onclick="sendRequest(localStorage.getItem('userEmail'), '${match.email}', '${match.offeredSkill}')">
                          Send Request
                    </button>

                </div>
            `;
        });

    } catch (error) {
        console.log(error);
    }
}

async function sendRequest(sender, receiver, subject) {

    const request = {
        senderEmail: sender,
        receiverEmail: receiver,
        subject: subject,
        status: "Pending"
    };

    try {
        const response = await fetch(
            "http://localhost:8090/send-request",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(request)
            }
        );

        const data = await response.json().catch(() => ({}));

        if (!response.ok) {
            throw new Error(
                data.message || "Request could not be sent."
            );
        }

        alert("Request sent successfully!");

    } catch (error) {
        console.error("Request error:", error);
        alert(error.message || "Error sending request.");
    }
}

