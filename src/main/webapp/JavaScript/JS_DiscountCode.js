function showDiscounts() {
    // Quel est l'état sélectionné ?;	
    // On fait un appel AJAX pour chercher les clients de cet état
    $.ajax({
        url: "DiscountCodeServlet",
        dataType: "json",
        success: // La fonction qui traite les résultats
                function (result) {
                    // Le code source du template est dans la page
                    var template = $('#DiscountsTemplate').html();
                    // On combine le template avec le résultat de la requête
                    var processedTemplate = Mustache.to_html(template, {records: result.records});
                    // On affiche le résultat dans la page
                    $('#discounts').html(processedTemplate);
                },
        error: showError
    });
}

$("button").click(function() {
    alert(this.id); // or alert($(this).attr('id'));
});




function showError(xhr, status, message) {
			alert("Erreur: " + status + " : " + message);
		}
showDiscounts();

