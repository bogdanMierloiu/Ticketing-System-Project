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
                defaultOption.text = "Selectați departamentul";
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
                document.getElementById("departmentDropdown").setAttribute("th:value", selectedDepartmentId);
            })
            .catch(error => {
                console.error("Eroare la obținerea departamentelor:", error);
            });
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
                defaultOption.text = "Selectați subunitatea de poliție";
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
                document.getElementById("structuresDropdown").setAttribute("th:value", selectedStructureSubunitId); // Modificați "departmentDropdown" în "structuresDropdown"
            })
            .catch(error => {
                console.error("Eroare la obținerea subunităților:", error);
            });
    }
}