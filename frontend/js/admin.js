const API = "http://localhost:8090";

const adminEmail = localStorage.getItem("userEmail");

const q = `?adminEmail=${encodeURIComponent(adminEmail)}`;


// ===============================
// LOAD ADMIN DATA
// ===============================

async function loadAdmin() {

    try {

        const [
            users,
            skills,
            requests,
            materials,
            contactMessages,
            skillRequests
        ] = await Promise.all([

            fetch(`${API}/admin/users${q}`)
                .then(r => r.json()),

            fetch(`${API}/admin/skills${q}`)
                .then(r => r.json()),

            fetch(`${API}/admin/requests${q}`)
                .then(r => r.json()),

            fetch(`${API}/admin/materials${q}`)
                .then(r => r.json()),

            fetch(`${API}/contact`)
                .then(r => r.json()),
            

            fetch(`${API}/skill-requests/pending`)
                 .then(r => r.json())    

        ]);


        // ===============================
        // DASHBOARD STATISTICS
        // ===============================

        document.getElementById("stats").innerHTML = `

            <div class="card">
                <h3>Users</h3>
                <p>
                    ${
                        users.filter(
                            u =>
                            (u.role || "USER")
                            .toUpperCase() !== "ADMIN"
                        ).length
                    }
                </p>
            </div>

            <div class="card">
                <h3>Skills</h3>
                <p>${skills.length}</p>
            </div>

            <div class="card">
                <h3>Requests</h3>
                <p>${requests.length}</p>
            </div>

            <div class="card">
                <h3>Materials</h3>
                <p>${materials.length}</p>
            </div>

        `;


        // ===============================
        // USERS
        // ===============================

        document.getElementById("users").innerHTML =

            users.map(u => `

                <div class="match-card">

                    <strong>
                        ${u.name}
                    </strong>

                    —
                    ${u.email}

                    (${u.role || "USER"})

                    ${
                        u.role !== "ADMIN"

                        ?

                        `
                        <button
                            class="reject-btn"
                            onclick="deleteUser('${u.id}')">

                            Delete

                        </button>
                        `

                        :

                        ""
                    }

                </div>

            `).join("")

            ||

            "<p>No users.</p>";


        // ===============================
        // REQUESTS
        // ===============================

        document.getElementById("requests").innerHTML =

            requests.map(r => `

                <div class="match-card">

                    ${r.senderEmail}

                    →

                    ${r.receiverEmail}

                    —

                    <strong>
                        ${r.status}
                    </strong>

                    <button
                        class="reject-btn"
                        onclick="deleteRequest('${r.id}')">

                        Remove

                    </button>

                </div>

            `).join("")

            ||

            "<p>No requests.</p>";


        // ===============================
        // STUDY MATERIALS
        // ===============================

        document.getElementById("materials").innerHTML =

            materials.map(m => `

                <div class="match-card">

                    ${m.title}

                    —

                    ${m.senderEmail}

                    →

                    ${m.receiverEmail}

                    <button
                        class="reject-btn"
                        onclick="deleteMaterial('${m.id}')">

                        Remove

                    </button>

                </div>

            `).join("")

            ||

            "<p>No materials.</p>";
        
        // ===============================
        // SKILL REQUESTS
        // ===============================
         document.getElementById("skillRequests").innerHTML =

           skillRequests.map(r => `

              <div class="match-card">

                 <h3>${r.skillName}</h3>

                 <p>
                     <strong>Requested By:</strong>
                     ${r.requestedBy}
                </p>

                <p>
                   <strong>Status:</strong>
                    ${r.status}
                </p>

                <button
                  onclick="approveSkillRequest('${r.id}')">  Approve
                </button>

                <button
                   class="reject-btn"
                    onclick="rejectSkillRequest('${r.id}')">Reject

                 </button>

              </div>

            `).join("")
            ||

            "<p>No pending skill requests.</p>";    


        // ===============================
        // CONTACT MESSAGES
        // ===============================

        document.getElementById("contactMessages").innerHTML =

            contactMessages.map(m => `

                <div class="match-card">

                    <h3>
                        ${m.subject}
                    </h3>


                    <p>
                        <strong>User:</strong>
                        ${m.userEmail}
                    </p>


                    <p>
                        <strong>Category:</strong>
                        ${m.category}
                    </p>


                    <p>
                        <strong>Problem:</strong>
                        ${m.message}
                    </p>


                    <p>
                        <strong>Status:</strong>
                        ${m.status}
                    </p>


                    ${
                        m.status === "PENDING"

                        ?

                        `

                        <label>
                            <strong>
                                Admin Response
                            </strong>
                        </label>

                        <textarea
                            id="response-${m.id}"
                            rows="4"
                            placeholder="Write a response to the user..."
                            style="width:100%; margin-top:8px; margin-bottom:10px;"
                        ></textarea>


                        <button
                            onclick="resolveMessage('${m.id}')">

                            Resolve Problem

                        </button>

                        `

                        :

                        `

                        <div>

                            <p>
                                <strong>
                                    Admin Response:
                                </strong>
                            </p>

                            <p>
                                ${m.adminResponse || "No response added."}
                            </p>

                            <p>
                                <strong>
                                    Resolved At:
                                </strong>

                                ${m.resolvedAt || "Not available"}
                            </p>

                        </div>

                        `

                    }


                    <br>


                    <button
                        class="reject-btn"
                        onclick="deleteMessage('${m.id}')">

                        Delete

                    </button>

                </div>

            `).join("")

            ||

            "<p>No contact messages.</p>";

    }


    catch (e) {

        alert(
            "Admin data could not be loaded. Login as admin and start the backend."
        );

        console.error(e);

    }

}


// ===============================
// DELETE USER
// ===============================

async function deleteUser(id) {

    if (confirm("Delete this user?")) {

        await fetch(
            `${API}/admin/users/${id}${q}`,
            {
                method: "DELETE"
            }
        );

        loadAdmin();

    }

}


// ===============================
// DELETE REQUEST
// ===============================

async function deleteRequest(id) {

    if (confirm("Remove this request?")) {

        await fetch(
            `${API}/admin/requests/${id}${q}`,
            {
                method: "DELETE"
            }
        );

        loadAdmin();

    }

}


// ===============================
// DELETE STUDY MATERIAL
// ===============================

async function deleteMaterial(id) {

    if (confirm("Remove this study material?")) {

        await fetch(
            `${API}/admin/materials/${id}${q}`,
            {
                method: "DELETE"
            }
        );

        loadAdmin();

    }

}


// ===============================
// RESOLVE CONTACT MESSAGE
// ===============================

async function resolveMessage(id) {

    const responseBox =
        document.getElementById(`response-${id}`);

    const adminResponse =
        responseBox.value.trim();


    if (!adminResponse) {

        alert(
            "Please write a response before resolving the problem."
        );

        responseBox.focus();

        return;

    }


    if (
        !confirm(
            "Are you sure you want to resolve this problem?"
        )
    ) {

        return;

    }


    try {

        const response = await fetch(
            `${API}/contact/${id}/resolve?adminResponse=${encodeURIComponent(adminResponse)}`,
            {
                method: "PUT"
            }
        );


        if (!response.ok) {

            throw new Error(
                "Failed to resolve contact message."
            );

        }


        alert(
            "Problem resolved successfully! ✅"
        );


        await loadAdmin();

    }


    catch (error) {

        console.error(
            "Resolve error:",
            error
        );

        alert(
            "Unable to resolve the problem."
        );

    }

}


// ===============================
// DELETE CONTACT MESSAGE
// ===============================

async function deleteMessage(id) {

    if (
        !confirm(
            "Delete this contact message?"
        )
    ) {

        return;

    }


    try {

        const response = await fetch(
            `${API}/contact/${id}`,
            {
                method: "DELETE"
            }
        );


        if (!response.ok) {

            throw new Error(
                "Failed to delete message."
            );

        }


        await loadAdmin();

    }


    catch (error) {

        console.error(
            "Delete error:",
            error
        );

        alert(
            "Unable to delete the message."
        );

    }

}
// ===============================
// APPROVE SKILL REQUEST
// ===============================

async function approveSkillRequest(id) {

    if (!confirm("Approve this skill request?")) {
        return;
    }

    try {

        const response = await fetch(
            `${API}/skill-requests/${id}/approve${q}`,
            {
                method: "PUT"
            }
        );

        if (!response.ok) {
            throw new Error("Failed to approve skill request.");
        }

        alert("Skill approved successfully! ✅");

        await loadAdmin();

    } catch (error) {

        console.error(
            "Approve skill request error:",
            error
        );

        alert("Unable to approve skill request.");

    }
}


// ===============================
// REJECT SKILL REQUEST
// ===============================

async function rejectSkillRequest(id) {

    if (!confirm("Reject this skill request?")) {
        return;
    }

    try {

        const response = await fetch(
            `${API}/skill-requests/${id}/reject${q}`,
            {
                method: "PUT"
            }
        );

        if (!response.ok) {
            throw new Error("Failed to reject skill request.");
        }

        alert("Skill request rejected.");

        await loadAdmin();

    } catch (error) {

        console.error(
            "Reject skill request error:",
            error
        );

        alert("Unable to reject skill request.");

    }
}


// ===============================
// START ADMIN DASHBOARD
// ===============================

loadAdmin();