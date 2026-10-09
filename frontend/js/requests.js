console.log("requests.js loaded");

async function loadRequests() {
    const email = localStorage.getItem("userEmail");
    const container = document.getElementById("requestContainer");
    if (!email) { container.innerHTML = "<p>Please login first.</p>"; return; }

    try {
        const response = await fetch("http://localhost:8090/my-requests/" + encodeURIComponent(email));
        const data = await response.json();
        container.innerHTML = "";

        if (!data.length) {
            container.innerHTML = "<p>No requests yet.</p>";
            return;
        }

        data.forEach(request => {
            const received = request.receiverEmail.toLowerCase() === email.toLowerCase();
            const otherUser = received ? request.senderEmail : request.receiverEmail;
            const actions = received && request.status === "Pending"
                ? `<button class="accept-btn" onclick="updateRequest('${request.id}','Accepted')">Accept</button>
                   <button class="reject-btn" onclick="updateRequest('${request.id}','Rejected')">Reject</button>`
                : "";

            container.innerHTML += `
                <div class="match-card request-card">
                    <h3>${received ? "Request from" : "Request to"}: ${otherUser}</h3>
                    <p>Status: <strong>${request.status}</strong></p>
                    ${actions}
                </div>`;
        });
    } catch (error) {
        console.error(error);
        container.innerHTML = "<p>Unable to load requests.</p>";
    }
}

async function updateRequest(id, status) {
    const email = localStorage.getItem("userEmail");
    try {
        const response = await fetch(
            `http://localhost:8090/requests/${id}/status?status=${status}&receiverEmail=${encodeURIComponent(email)}`,
            { method: "PUT" }
        );
        const data = await response.json();
        if (!response.ok) throw new Error(data.message || "Unable to update request");
        alert(`Request ${status.toLowerCase()} successfully.`);
        loadRequests();
    } catch (error) {
        alert(error.message);
    }
}

loadRequests();
