console.log("requests.js loaded");

async function loadRequests() {

    const email = localStorage.getItem("userEmail");

    const container =
    document.getElementById("requestContainer");

    try {

        const response = await fetch(
            "http://localhost:8090/my-requests/" + email
        );

        const data = await response.json();

        console.log(data);

        container.innerHTML = "";

        data.forEach(function(request) {

            container.innerHTML += `

                <div class="match-card">

                    <h3>${request.senderEmail}</h3>

                    <p>Status: ${request.status}</p>

                </div>

            `;
        });

    }

    catch(error) {

        console.log(error);

    }
}

loadRequests();