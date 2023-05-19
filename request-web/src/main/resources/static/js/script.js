       function populateDepartments() {
            var policeStructureId = $('#policeStructure').find(':selected').val();

            // Efectuează solicitarea către server pentru a obține departamentele
            fetch('admin/show-departments/' + policeStructureId)
                .then(response => response.text())
                .then(html => {
                    // Inserează răspunsul HTML în containerul departamentelor
                    $('#departmentContainer').html(html);
                })
                .catch(error => {
                    console.log('A apărut o eroare în solicitarea AJAX: ' + error);
                });
        }

