function populateDepartments() {
    var selectedStructureId = document.getElementById("policeStructure").value;

    if (selectedStructureId !== "") {
        // Realizează o cerere către server pentru a obține departamentele
        fetch("/request/admin/show-departments-script/" + selectedStructureId)
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
        // Realizează o cerere către server pentru a obține departamentele
        fetch("/request/admin/show-subunits-script/" + selectedStructureId)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                let structuresDropdown = document.getElementById("structuresDropdown");
                departmentDropdown.innerHTML = "";

                let defaultOption = document.createElement("option");
                defaultOption.value = "";
                defaultOption.text = "Selectați subunitatea";
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
                let selectedStructureId = structuresDropdown.value;
                document.getElementById("structuresDropdown").setAttribute("th:value", selectedStructureId);
            })
            .catch(error => {
                console.error("Eroare la obținerea subunitatilor:", error);
            });
    }
}
