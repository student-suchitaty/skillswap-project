const API = "http://localhost:8090";

const userEmail =
    localStorage.getItem("userEmail");


// ===============================
// SEND CONTACT MESSAGE
// ===============================

document
    .getElementById("contactForm")
    .addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();


            const category =
                document
                    .getElementById("category")
                    .value;


            const subject =
                document
                    .getElementById("subject")
                    .value
                    .trim();


            const message =
                document
                    .getElementById("message")
                    .value
                    .trim();


            if (!userEmail) {

                alert(
                    "Please login first."
                );

                return;

            }


            try {

                const response =
                    await fetch(
                        `${API}/contact`,
                        {

                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body: JSON.stringify({

                                userEmail:
                                    userEmail,

                                category:
                                    category,

                                subject:
                                    subject,

                                message:
                                    message

                            })

                        }
                    );


                if (!response.ok) {

                    throw new Error(
                        "Failed to send message"
                    );

                }


                document
                    .getElementById("result")
                    .innerHTML =
                    "Message sent successfully! ✅";


                document
                    .getElementById("contactForm")
                    .reset();


                // Refresh support requests
                loadSupportRequests();

            }


            catch (error) {

                console.error(error);


                document
                    .getElementById("result")
                    .innerHTML =
                    "Unable to send message. Please try again.";

            }

        }
    );



// ===============================
// LOAD USER SUPPORT REQUESTS
// ===============================

async function loadSupportRequests() {

    const container =
        document.getElementById(
            "mySupportRequests"
        );


    if (!userEmail) {

        container.innerHTML =
            "<p>Please login to view your support requests.</p>";

        return;

    }


    try {

        const response =
            await fetch(
                `${API}/contact/user?email=${encodeURIComponent(userEmail)}`
            );


        if (!response.ok) {

            throw new Error(
                "Unable to load support requests"
            );

        }


        const messages =
            await response.json();


        if (messages.length === 0) {

            container.innerHTML = `
                <p>
                    You have not submitted any support requests yet.
                </p>
            `;

            return;

        }


        container.innerHTML = messages
            .map(message => {


                const statusClass =
                    message.status === "RESOLVED"
                        ? "resolved"
                        : "pending";


                return `

                    <div class="match-card">


                        <h3>
                            ${message.subject}
                        </h3>


                        <p>

                            <strong>
                                Category:
                            </strong>

                            ${message.category}

                        </p>


                        <p>

                            <strong>
                                Your Problem:
                            </strong>

                            ${message.message}

                        </p>


                        <p>

                            <strong>
                                Status:
                            </strong>

                            <span class="${statusClass}">
                                ${message.status}
                            </span>

                        </p>


                        ${
                            message.status === "RESOLVED"

                            ?

                            `

                            <div>

                                <p>

                                    <strong>
                                        Admin Response:
                                    </strong>

                                </p>


                                <p>
                                    ${
                                        message.adminResponse
                                        || "No response available."
                                    }
                                </p>


                                <p>

                                    <strong>
                                        Resolved At:
                                    </strong>

                                    ${
                                        message.resolvedAt
                                        || "Not available"
                                    }

                                </p>

                            </div>

                            `

                            :

                            `

                            <p>

                                <strong>
                                    Admin Response:
                                </strong>

                                Waiting for administrator response...

                            </p>

                            `

                        }


                    </div>

                `;

            })
            .join("");

    }


    catch (error) {

        console.error(
            "Support request error:",
            error
        );


        container.innerHTML = `
            <p>
                Unable to load your support requests.
                Please try again later.
            </p>
        `;

    }

}



// ===============================
// LOAD WHEN PAGE OPENS
// ===============================

loadSupportRequests();