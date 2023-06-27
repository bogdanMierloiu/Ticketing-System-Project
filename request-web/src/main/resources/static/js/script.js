// MAGIC THINGS

window.addEventListener("DOMContentLoaded", function(){
 var currentDateInput = document.getElementById("currentDate");
 if(currentDateInput){
 setInitialDate();
 }
});

function setInitialDate() {
    // Obține data curentă
    var currentDate = new Date();

    // Formatează data într-un format compatibil cu LocalDate din Java
    var formattedDate = currentDate.getFullYear() + '-' + ('0' + (currentDate.getMonth() + 1)).slice(-2) + '-' + ('0' + currentDate.getDate()).slice(-2);

    // Setează valoarea câmpului de introducere a datei la data curentă
    document.getElementById("currentDate").value = formattedDate;
}

if (window.location.pathname === "/request/add-request-form") {
    // Apelăm funcția populatePoliceStructures() doar pe pagina de add-request
    populateStructures();
    setInitialDate();
}

function togglePolicemanAuthorization() {
    var requestTypeSelect = document.getElementById("requestType");
    var selectedOption = requestTypeSelect.options[requestTypeSelect.selectedIndex].text;
    var policemanAuthorizationDiv = document.getElementById("policemanAuthorization");

    if (selectedOption === "Solicitare cont de acces S.I.C. W.A.N. POLITIA ROMANA") {
        policemanAuthorizationDiv.style.display = "block";
        document.getElementById('certificate').required = true;
        document.getElementById('certificateValidFrom').required = true;
        document.getElementById('certificateValidUntil').required = true;
    } else {
        policemanAuthorizationDiv.style.display = "none";
        document.getElementById('certificate').required = false;
        document.getElementById('certificateValidFrom').required = false;
        document.getElementById('certificateValidUntil').required = false;
    }
}

function populateStructures() {
    var selectedStructureId = document.getElementById("policeStructureDropDown").getAttribute("th:field");
    if (selectedStructureId !== "") {
        fetch("/request/admin/show-structures-script")
            .then(response => response.json())
            .then(data => {
                let structuresDropdown = document.getElementById("policeStructureDropDown");
                structuresDropdown.innerHTML = "";

                let defaultOption = document.createElement("option");
                defaultOption.value = "";
                defaultOption.text = "Selectati structura de politie";
                structuresDropdown.appendChild(defaultOption);

                data.map(function (structure) {
                    let option = document.createElement("option");
                    option.value = structure.id;
                    option.text = structure.structureName;
                    return option;
                }).forEach(function (option) {
                    structuresDropdown.appendChild(option);
                });
            })
            .catch(error => {
                console.error("Eroare la obținerea structurilor de poliție:", error);
            });
    }
}


function populateSubunits() {
    var selectedStructureId = document.getElementById("policeStructureDropDown").value;

    if (selectedStructureId !== "") {
        // Realizează o cerere către server pentru a obține subunitățile
        fetch("/request/admin/show-subunits-script/" + selectedStructureId)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                let structuresDropdown = document.getElementById("structuresDropdown");
                let departmentDropdown = document.getElementById("structuresDropdown"); // Modificați "departmentDropdown" în "structuresDropdown"

                structuresDropdown.innerHTML = ""; // Modificați "departmentDropdown" în "structuresDropdown"

                let defaultOption = document.createElement("option");
                defaultOption.value = "";
                defaultOption.text = "Selectati subunitatea de politie";
                structuresDropdown.appendChild(defaultOption); // Modificați "departmentDropdown" în "structuresDropdown"

                data.map(function (subunit) { // Modificați "structure" în "subunit"
                    let option = document.createElement("option");
                    option.value = subunit.id; // Modificați "department.id" în "subunit.id"
                    option.text = subunit.subunitName; // Modificați "department.departmentName" în "subunit.subunitName"
                    return option;
                }).forEach(function (option) {
                    structuresDropdown.appendChild(option); // Modificați "departmentDropdown" în "structuresDropdown"
                });

                // Actualizează valoarea câmpului policemanRequest.policeStructureSubunitId cu id-ul subunității selectate
                let selectedStructureSubunitId = structuresDropdown.value; // Modificați "selectedStructureId" în "selectedStructureSubunitId"
                document.getElementById("structuresDropdown").value = selectedStructureSubunitId;
            })
            .catch(error => {
                console.error("Eroare la obținerea subunităților:", error);
            });
    }
}


function populateDepartments() {
    var selectedSubunitStructureId = document.getElementById("structuresDropdown").value;

    if (selectedSubunitStructureId !== "") {
        // Realizează o cerere către server pentru a obține departamentele
        fetch("/request/admin/show-departments-script/" + selectedSubunitStructureId)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                let departmentDropdown = document.getElementById("departmentDropdown");
                departmentDropdown.innerHTML = "";

                let defaultOption = document.createElement("option");
                defaultOption.value = "";
                defaultOption.text = "Selectati linia de munca";
                departmentDropdown.appendChild(defaultOption);

                data.map(function (department) {
                    let option = document.createElement("option");
                    option.value = department.id;
                    option.text = department.departmentName;
                    return option;
                }).forEach(function (option) {
                    departmentDropdown.appendChild(option);
                });

                // Actualizează valoarea câmpului policemanRequest.departmentId cu id-ul departamentului selectat
                let selectedDepartmentId = departmentDropdown.value;
                document.getElementById("departmentDropdown").value = selectedDepartmentId;
            })
            .catch(error => {
                console.error("Eroare la obținerea departamentelor:", error);
            });
    }
}


// AUTORIZATION POLICE STRUCTURE CHIEF

function onPoliceStructureDecisionChange(element) {
    var decision = element.value;
    var requestId = element.parentElement.querySelector('[name="requestId"]').value;

    if (decision === "reject") {
        $('#rejectPoliceStructureModal').modal('show');
        document.getElementById("rejectPoliceStructureModal").setAttribute("data-request-id", requestId);
    } else if (decision === "approve") {
        // Ascunde fereastra modală
        $('#rejectPoliceStructureModal').modal('hide');
        // Golește câmpul de observații
        document.getElementById("policeStructureObservation").value = "";
        // Trimitere cerere Ajax către ruta "/request/structure-chief-decision/{requestId}"
        submitDecisionForm(requestId);
    }
}

function submitDecisionForm(requestId) {
    var decision = "approve";
    var observation = "";

    // Trimitere cerere Ajax către ruta "/request/structure-chief-decision/{requestId}"
    $.ajax({
        url: "/request/structure-chief-decision/" + requestId,
        type: "POST",
        data: {decision: decision, observation: observation},
        success: function (response) {
            // Succes - gestionează răspunsul
            alert("Cererea a fost aprobată cu succes!");
            // Refresh pagina
            window.location.reload();
        },
        error: function (xhr, status, error) {
            alert("A apărut o eroare la aprobarea cererii:", error);
            window.location.reload();
        }
    });
}


function submitRejectForm() {
    var decision = "reject";
    var observation = document.getElementById("policeStructureObservation").value.trim();
    var requestId = document.getElementById("rejectPoliceStructureModal").getAttribute("data-request-id");

    if (observation === "") {
        // Mesaj de eroare pentru observație goală
        alert("Vă rugăm să completați motivul respingerii:");
        return;
    }

    // Trimitere cerere Ajax către ruta "/request/structure-chief-decision/{requestId}"
    $.ajax({
        url: "/request/structure-chief-decision/" + requestId,
        type: "POST",
        data: {decision: decision, observation: observation},
        success: function (response) {
            // Succes - gestionează răspunsul
            alert("Cererea a fost respinsă cu succes!");
            // Închide fereastra modală
            $('#rejectModal').modal('hide');
            window.location.reload();
        },
        error: function (xhr, status, error) {
          alert("A apărut o eroare la aprobarea cererii:", error);
          window.location.reload();
        }
    });
}


// AUTORIZATION SECURITY STRUCTURE

function onDecisionChangeSecurity(element) {
    var decision = element.value;
    var requestId = element.parentElement.querySelector('[name="requestId"]').value;


    if (decision === "reject") {
        // Afiseaza fereastra modală
        document.getElementById("rejectModalForSecurity").setAttribute("data-request-id", requestId);
        $('#rejectModalForSecurity').modal('show');
    } else if (decision === "approve") {
        // Ascunde fereastra modală
        $('#rejectModalForSecurity').modal('hide');
        // Golește câmpul de observații
        document.getElementById("securityObservation").value = "";
        // Trimitere cerere Ajax către ruta "/request/structure-chief-decision/{requestId}"
        submitDecisionFormSecurity(requestId);
    }
}

function submitDecisionFormSecurity(requestId) {
    var decision = "approve";
    var observation = "";


    // Trimitere cerere Ajax către ruta "/security-decision/{requestId}"
    $.ajax({
        url: "/request/security-decision/" + requestId,
        type: "POST",
        data: {decision: decision, observation: observation},
        success: function (response) {
            // Succes - gestionează răspunsul
            alert("Solicitarea a fost aprobată cu succes!");
            // Refresh pagina
            window.location.reload();
        },
        error: function (xhr, status, error) {
            // Verificați răspunsul de eroare și tratați eroarea specifică
            if (xhr.status === 500) {
                var errorMessage = xhr.responseText;
                if (errorMessage.includes("Solicitarea nu este aprobata de seful structurii de politie emitente!")) {
                    // Tratați excepția în funcție de nevoile dvs.
                    alert("Solicitarea nu este aprobata de seful structurii de politie emitente!");
                } else {
                    // Alte erori specifice
                    alert("A apărut o eroare");
                }
                window.location.reload();
            }
        }
    });
}

function submitRejectFormSecurity() {
    var decision = "reject";
    var observation = document.getElementById("securityObservation").value.trim();
    var requestId = document.getElementById("rejectModalForSecurity").getAttribute("data-request-id");

    if (observation === "") {
        // Mesaj de eroare pentru observație goală
        alert("Vă rugăm să completați motivul respingerii:");
        return;
    }

    // Trimitere cerere Ajax către ruta "/request/security-decision/{requestId}"
    $.ajax({
        url: "/request/security-decision/" + requestId,
        type: "POST",
        data: {decision: decision, observation: observation},
        success: function (response) {
            // Succes - gestionează răspunsul
            alert("Cererea a fost respinsă cu succes!");
            // Închide fereastra modală
            $('#securityRejectModalLabel').modal('hide');
            window.location.reload();
        },
        error: function (xhr, status, error) {
            if (xhr.status === 500) {
                var errorMessage = xhr.responseText;
                if (errorMessage.includes("Solicitarea nu este aprobata de seful structurii de politie emitente!")) {
                 alert("Solicitarea nu este aprobata de seful structurii de politie emitente!");
            } else {
               alert("A apărut o eroare");
            }
            $('#securityRejectModalLabel').modal('hide');
            window.location.reload();
            }
        }
    });
}


// IT STRUCTURE

function onDecisionChangeIT(element) {
    var decision = element.value;
    var requestId = element.parentElement.querySelector('[name="requestId"]').value;
    console.log(decision);
    console.log(requestId);

     if (decision === "reject") {
            // Setează valoarea requestId în atributul data-request-id al modalului
            document.getElementById("rejectModalForIT").setAttribute("data-request-id", requestId);
            // Afiseaza fereastra modală
            $('#rejectModalForIT').modal('show');
        } else if (decision === "approve") {
            // Setează valoarea requestId în atributul data-request-id al modalului
            document.getElementById("specialistAssign").setAttribute("data-request-id", requestId);
            // Ascunde fereastra modală
            $('#rejectModalForIT').modal('hide');
            // Golește câmpul de observații
            document.getElementById("itObservation").value = "";
            // Trimitere cerere Ajax către ruta "/request/it-decision/{requestId}"
            $('#specialistAssign').modal('show');
        }
}

function submitDecisionFormIT() {
    var decision = "approve";
    var observation = "";
    var itSpecialistId = document.getElementById("itSpecialistId").value;
    var requestId = document.getElementById("specialistAssign").getAttribute("data-request-id");


    // Trimitere cerere Ajax către ruta "/request/it-decision/{requestId}"
    $.ajax({
        url: "/request/it-decision/" + requestId,
        type: "POST",
        data: { decision: decision, observation: observation, itSpecialistId: itSpecialistId },
        success: function (response) {
            // Succes - gestionează răspunsul
            alert("Cererea a fost aprobată și repartizată cu succes!");
            // Refresh pagina
            window.location.reload();
        },
        error: function (xhr, status, error) {
            if (xhr.status === 500) {
              var errorMessage = xhr.responseText;
              if (errorMessage.includes("Solicitarea nu este aprobata de structura de securitate!")) {
                  alert("Solicitarea nu este aprobata de structura de securitate!");
              } else {
                  alert("A apărut o eroare");
              }
            window.location.reload();
        }
    }
});
}

function submitRejectFormIT() {
    var decision = "reject";
    var observation = document.getElementById("itObservation").value.trim();
    var itSpecialistId = 0;
    var requestId = document.getElementById("rejectModalForIT").getAttribute("data-request-id");

    if (observation === "") {
        // Mesaj de eroare pentru observație goală
        alert("Vă rugăm să completați motivul respingerii.");
        return;
    }

    // Trimitere cerere Ajax către ruta "/request/it-decision/{requestId}"
    $.ajax({
        url: "/request/it-decision/" + requestId,
        type: "POST",
        data: { decision: decision, observation: observation, itSpecialistId: itSpecialistId },
        success: function (response) {
            // Succes - gestionează răspunsul
            alert("Cererea a fost respinsă cu succes!");
            // Închide fereastra modală
            $('#rejectModalForIT').modal('hide');
            window.location.reload();
        },
        error: function (xhr, status, error) {
            if (xhr.status === 500) {
                var errorMessage = xhr.responseText;
                if (errorMessage.includes("Solicitarea nu este aprobata de structura de securitate!")) {
                    alert("Solicitarea nu este aprobata de structura de securitate!");
                } else {
                    alert("A apărut o eroare");
                }
            }
            $('#rejectModalForIT').modal('hide');
            window.location.reload();
        }
    });
}


// Hide password

function togglePasswordVisibility() {
    var passwordInput = document.getElementById("password");
    var showPasswordButton = document.getElementById("showPasswordButton");

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        showPasswordButton.textContent = "Ascunde";
    } else {
        passwordInput.type = "password";
        showPasswordButton.textContent = "Arata";
    }
}

// Upload document
if (window.location.pathname.includes("add-request-form")) {
    const fileInput = document.getElementById('fileInput');
    const fileNameElement = document.getElementById('fileName');
    $('#files').change(function() {
         const filename = this.files[0].name;
         fileNameElement.textContent = filename;
         console.log(filename);
     });
}

if (window.location.pathname.includes("add-request-form") ||
    window.location.pathname.includes("add-request-form-second") ||
    window.location.pathname.includes("add-commitment-form") ||
    window.location.pathname.includes("update-commitment")) {
    const fileInput = document.getElementById('fileInput');
    const fileNameElement = document.getElementById('fileName');
    $('#files').change(function() {
        const filename = this.files[0].name;
        fileNameElement.textContent = filename;
        console.log(filename);
    });
}














