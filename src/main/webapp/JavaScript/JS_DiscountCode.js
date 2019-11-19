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
                    var errorTemplate = $('#ErrorTemplate').html();
                    // On combine le template avec le résultat de la requête
                    var processedTemplate = Mustache.to_html(template, {records: result.records});
                    var processedErrorTemplate = Mustache.to_html(errorTemplate, {error:result.error});
                    // On affiche le résultat dans la page
                    $('#discounts').html(processedTemplate);
                    $('#error').html(processedErrorTemplate);
                },
        error: showError
    });
}


//Fonction pour ajouter un élément à la table et empécher le redirect
$(function() {
    $('form').submit(function() {
        var rate = document.getElementById("GETrate").value;
        var code = document.getElementById("GETcode").value;
        console.log(rate,code);
        $.ajax(
                {
            type: 'POST',
            url: 'DiscountCodeServlet?code='+code+'&rate='+rate+'&action=ADD',
            success: // La fonction qui traite les résultats
                function (result) {
                    // Le code source du template est dans la page
                    var template = $('#DiscountsTemplate').html();
                    var errorTemplate = $('#ErrorTemplate').html();
                    // On combine le template avec le résultat de la requête
                    var processedTemplate = Mustache.to_html(template, {records: result.records});
                    var processedErrorTemplate = Mustache.to_html(errorTemplate, {error:result.error});
                    // On affiche le résultat dans la page
                    $('#discounts').html(processedTemplate);
                    $('#error').html(processedErrorTemplate);
                },
        error: showError
        });
        
        return false;
    }); 
});


function delet(clicked_id){
    $.ajax({
        url: "DiscountCodeServlet?code="+clicked_id+"&action=DELETE",
        dataType: "json",
        success: // La fonction qui traite les résultats
                function (result) {
                    // Le code source du template est dans la page
                    var template = $('#DiscountsTemplate').html();
                    var errorTemplate = $('#ErrorTemplate').html();
                    // On combine le template avec le résultat de la requête
                    var processedTemplate = Mustache.to_html(template, {records: result.records});
                    var processedErrorTemplate = Mustache.to_html(errorTemplate, {error:result.error});
                    // On affiche le résultat dans la page
                    $('#discounts').html(processedTemplate);
                    $('#error').html(processedErrorTemplate);
                },
        error: showError
    });
 };


function showError(xhr, status, message) {
			alert("Erreur: " + status + " : " + message);
		}
showDiscounts();

