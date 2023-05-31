                                // MAGIC THINGS


function togglePolicemanAuthorization() {
    var requestTypeSelect = document.getElementById("requestType");
    var selectedOption = requestTypeSelect.options[requestTypeSelect.selectedIndex].text;
    var policemanAuthorizationDiv = document.getElementById("policemanAuthorization");

    if (selectedOption === "solicitare cont intrapol-b") {
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

function populateSubunits() {
    var selectedStructureId = document.getElementById("policeStructure").value;

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

                data.map(function(subunit) { // Modificați "structure" în "subunit"
                    let option = document.createElement("option");
                    option.value = subunit.id; // Modificați "department.id" în "subunit.id"
                    option.text = subunit.subunitName; // Modificați "department.departmentName" în "subunit.subunitName"
                    return option;
                }).forEach(function(option) {
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

                data.map(function(department) {
                    let option = document.createElement("option");
                    option.value = department.id;
                    option.text = department.departmentName;
                    return option;
                }).forEach(function(option) {
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

function setInitialDate(){
    // Obține data curentă
      var currentDate = new Date();

    // Formatează data într-un format compatibil cu LocalDate din Java
    var formattedDate = currentDate.getFullYear() + '-' + ('0' + (currentDate.getMonth() + 1)).slice(-2) + '-' + ('0' + currentDate.getDate()).slice(-2);

    // Setează valoarea câmpului de introducere a datei la data curentă
      document.getElementById("currentDate").value = formattedDate;
}

window.addEventListener("DOMContentLoaded", setInitialDate);

// AUTORIZATION POLICE STRUCTURE CHIEF

function onDecisionChange() {
    var decision = document.getElementById("decision").value;


    if (decision === "reject") {
        // Afiseaza fereastra modală
        $('#rejectModal').modal('show');
    } else if (decision === "approve") {
        // Ascunde fereastra modală
        $('#rejectModal').modal('hide');
        // Golește câmpul de observații
        document.getElementById("observation").value = "";
        // Trimitere cerere Ajax către ruta "/request/structure-chief-decision/{requestId}"
        submitDecisionForm();
    }
}

function submitDecisionForm() {
    var requestId = document.getElementById("requestId").value;
    var decision = document.getElementById("decision").value;

    // Trimitere cerere Ajax către ruta "/structure-chief-decision/{requestId}"
    $.ajax({
        url: "/request/structure-chief-decision/" + requestId,
        type: "POST",
        data: { decision: decision },
        success: function(response) {
            // Succes - gestionează răspunsul
            alert("Cererea a fost aprobată cu succes!");
            // Refresh pagina
            window.location.reload();
        },
        error: function(xhr, status, error) {
            // Eroare - gestionează eroarea
           alert("A apărut o eroare la aprobarea cererii:", error);
        }
    });
}

function submitRejectForm() {
    var requestId = document.getElementById("requestId").value;
    var decision = "reject";
    var observation = document.getElementById("observation").value.trim();

    if(observation === ""){
    // Mesaj de eroare pt observation gol
    alert("Va rugam sa completati motivul respingerii:")
    return;
    }

    // Trimitere cerere Ajax către ruta "/request/structure-chief-decision/{requestId}"
    $.ajax({
        url: "/request/structure-chief-decision/" + requestId,
        type: "POST",
        data: { decision: decision, observation: observation },
        success: function(response) {
            // Succes - gestionează răspunsul
            alert("Cererea a fost respinsă cu succes!");
            // Închide fereastra modală
            $('#rejectModal').modal('hide');
             window.location.reload();
        },
        error: function(xhr, status, error) {
            // Eroare - gestionează eroarea
            alert("A apărut o eroare la respingerea cererii:", error);
            // Închide fereastra modală
            $('#rejectModal').modal('hide');
        }
    });
}

// AUTORIZATION SECURITY STRUCTURE

function onDecisionChangeSecurity() {
    var decision = document.getElementById("securityDecision").value;

    if (decision === "reject") {
        // Afiseaza fereastra modală
        $('#rejectModalForSecurity').modal('show');
    } else if (decision === "approve") {
        // Ascunde fereastra modală
        $('#rejectModalForSecurity').modal('hide');
        // Golește câmpul de observații
        document.getElementById("observation").value = "";
        // Trimitere cerere Ajax către ruta "/request/structure-chief-decision/{requestId}"
        submitDecisionFormSecurity();
    }
}

function submitDecisionFormSecurity() {
    var requestId = document.getElementById("requestSecurityId").value;
        var decision = document.getElementById("securityDecision").value;

        // Trimitere cerere Ajax către ruta "/security-decision/{requestId}"
        $.ajax({
            url: "/request/security-decision/" + requestId,
            type: "POST",
            data: { decision: decision },
            success: function(response) {
                // Succes - gestionează răspunsul
                alert("Cererea a fost aprobată cu succes!");
                // Refresh pagina
                window.location.reload();
            },
            error: function(xhr, status, error) {
                // Eroare - gestionează eroarea
               alert("A apărut o eroare la aprobarea cererii:", error);
            }
        });
    }

function submitRejectFormSecurity() {
    var requestId = document.getElementById("requestSecurityId").value;
    var decision = "reject";
    var observation = document.getElementById("securityObservation").value.trim();

    if(observation === ""){
        // Mesaj de eroare pt observation gol
        alert("Va rugam sa completati motivul respingerii:")
        return;
        }

    // Trimitere cerere Ajax către ruta "/request/security-decision/{requestId}"
    $.ajax({
        url: "/request/security-decision/" + requestId,
        type: "POST",
        data: { decision: decision, observation: observation },
        success: function(response) {
            // Succes - gestionează răspunsul
            alert("Cererea a fost respinsă cu succes!");
            // Închide fereastra modală
            $('#securityRejectModalLabel').modal('hide');
             window.location.reload();
        },
        error: function(xhr, status, error) {
            // Eroare - gestionează eroarea
            alert("A apărut o eroare la respingerea cererii:", error);
            // Închide fereastra modală
            $('#securityRejectModalLabel').modal('hide');
        }
    });
}


// IT STRUCTURE

function onDecisionChangeIT() {
    var decision = document.getElementById("itDecision").value;


    if (decision === "reject") {
        // Afiseaza fereastra modală
        $('#rejectModalForIT').modal('show');
    } else if (decision === "approve") {
        // Ascunde fereastra modală
        $('#rejectModalForIT').modal('hide');
        // Golește câmpul de observații
        document.getElementById("itObservation").value = "";
        // Trimitere cerere Ajax către ruta "/request/it-decision/{requestId}"
        $('#specialistAssign').modal('show');
    }
}

function submitDecisionFormIT() {
    var requestId = document.getElementById("requestITId").value;
        var decision = document.getElementById("itDecision").value;
        var observation = "";
        var itSpecialistId = document.getElementById("itSpecialistId").value;

        // Trimitere cerere Ajax către ruta "/security-decision/{requestId}"
        $.ajax({
            url: "/request/it-decision/" + requestId,
            type: "POST",
            data: { decision: decision, observation: observation, itSpecialistId: itSpecialistId},
            success: function(response) {
                // Succes - gestionează răspunsul
                alert("Cererea a fost aprobata si repartizata cu succes!");
                // Refresh pagina
                window.location.reload();
            },
            error: function(xhr, status, error) {
                // Eroare - gestionează eroarea
               alert("A apărut o eroare la aprobarea cererii:", error);
            }
        });
    }

function submitRejectFormIT() {
    var requestId = document.getElementById("requestITId").value;
    var decision = "reject";
    var observation = document.getElementById("itObservation").value.trim();
    var itSpecialistId = 0;

    if(observation === ""){
        // Mesaj de eroare pt observation gol
        alert("Va rugam sa completati motivul respingerii:")
        return;
        }


    // Trimitere cerere Ajax către ruta "/request/it-decision/{requestId}"
    $.ajax({
        url: "/request/it-decision/" + requestId,
        type: "POST",
        data: { decision: decision, observation: observation, itSpecialistId: itSpecialistId},
        success: function(response) {
            // Succes - gestionează răspunsul
            alert("Cererea a fost respinsă cu succes!");
            // Închide fereastra modală
            $('#rejectModalForIT').modal('hide');
             window.location.reload();
        },
        error: function(xhr, status, error) {
            // Eroare - gestionează eroarea
            alert("A apărut o eroare la respingerea cererii:", error);
            // Închide fereastra modală
            $('#rejectModalForIT').modal('hide');
        }
    });
}


