const API = "http://localhost:8090";
const email = localStorage.getItem("userEmail");

async function loadConnections() {
    const select = document.getElementById("receiverEmail");
    try {
        const response = await fetch(`${API}/connections/${encodeURIComponent(email)}`);
        const users = await response.json();
        users.forEach(user => {
            select.innerHTML += `<option value="${user.email}">${user.name} (${user.email})</option>`;
        });
        if (!users.length) select.innerHTML = '<option value="">No accepted connections yet</option>';
    } catch (e) { console.error(e); }
}

async function loadMaterials() {
    try {
        const received = await (await fetch(`${API}/study-materials/received/${encodeURIComponent(email)}`)).json();
        const sent = await (await fetch(`${API}/study-materials/sent/${encodeURIComponent(email)}`)).json();
        renderMaterials("receivedMaterials", received, true);
        renderMaterials("sentMaterials", sent, false);
    } catch (e) { console.error(e); }
}

function renderMaterials(elementId, materials, received) {
    const container = document.getElementById(elementId);
    if (!materials.length) { container.innerHTML = "<p>No study materials yet.</p>"; return; }
    container.innerHTML = materials.map(m => `
        <div class="match-card material-card">
            <h3>${m.title}</h3>
            <p>${m.description || "No description"}</p>
            <p><strong>File:</strong> ${m.fileName}</p>
            <p>${received ? "From" : "To"}: ${received ? m.senderEmail : m.receiverEmail}</p>
            <a class="download-btn" href="${API}/study-materials/download/${m.id}">Download</a>
        </div>`).join("");
}

document.getElementById("materialForm").addEventListener("submit", async e => {
    e.preventDefault();
    const file = document.getElementById("materialFile").files[0];
    const formData = new FormData();
    formData.append("senderEmail", email);
    formData.append("receiverEmail", document.getElementById("receiverEmail").value);
    formData.append("title", document.getElementById("materialTitle").value);
    formData.append("description", document.getElementById("materialDescription").value);
    formData.append("file", file);
    try {
        const response = await fetch(`${API}/study-materials/send`, { method: "POST", body: formData });
        const data = await response.text();
        if (!response.ok) throw new Error(data);
        alert("Study material sent successfully!");
        e.target.reset();
        loadMaterials();
    } catch (error) { alert(error.message); }
});

loadConnections();
loadMaterials();
