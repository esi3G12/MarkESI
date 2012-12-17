$(document).ready(function() {
    var text_div = $('#code #content');
    var selections_div = $('#selections');
    var annot_text = $('#annotation');
    var button_add_annot = $('#add_annot');
    var no_selection_div = $('#no_selection');
    var curr_selection;
    var selections = new Array();
    var curr_id_sel = 0;
    var type_message = {
        SUCCESS: 0,
        ERROR: 1,
        INFO: 2
    };

    text_div.bind('mouseup', function() {
        newSelection();
    });

    button_add_annot.click(function(event) {
        ajouterAnnotation();
        event.preventDefault();
    });

    function newSelectionObject(n_text, n_start, n_end, n_length, n_isInUse) {
        var new_sel = {
            text: n_text,
            start: n_start,
            end: n_end,
            length: n_length,
            isInUse: n_isInUse
        };

        return new_sel;
    }

    //définition de la sélection courante
    curr_selection = newSelectionObject("", 0, 0, 0, true);

    function copy(sel) {
        return newSelectionObject(sel.text, sel.start, sel.end, sel.length, sel.isInUse);
    }

    function notification(message, type) {
        var prefix = '<span class="notif_type ';
        switch (type) {
            case type_message.SUCCESS:
                prefix += 'success';
                break;
            case type_message.ERROR:
                prefix += 'error';
                break;
            case type_message.INFO:
                prefix += 'info';
                break;
        }
        prefix += '"></span>';

        $.createNotification({
            content: prefix + message
        });
    }

    function newSelection() {
        var sel = text_div.selection();
        
        if (sel.start != sel.end) {
            //une sélection a bien été effectuée
            curr_selection.text = escapeHTML(text_div.text().substring(sel.start, sel.end));
            curr_selection.start = sel.start;
            curr_selection.end = sel.end;
            curr_selection.length = sel.end - sel.start;
            text_div.selection(0, 0); //désélectionne la sélection courante
            if (!isSelectionCorrect(curr_selection)) {
                //traitement en cas d'erreur
                notification('Cette s&eacute;lection n\'est pas valide !', type_message.ERROR);
            } else {
                addSelection();
            }
        }
    }

    function addSelection() {
        if (aucuneSelection()) {
            no_selection_div.hide();
        }

        //on ajoute la sélection à la liste des sélections
        selections.push(copy(curr_selection));

        //on rajoute un objet dans la liste des sélections
        selections_div.append(generateDivSel());

        //Rajoute pour la nouvelle annotation la fonctionnalité de suppression
        $('.close_selection:last input[name=close]').click(function() {
            supprimerSelection($(this), true);
        });

        //on met en évidence la sélection dans le texte
        addClass(curr_id_sel, curr_selection, 'cur_sel');

        //Pour finir, on incrémente l'id de la sélection courante
        curr_id_sel++;
    }

    function aucuneSelection() {
        return $('.selection').length === 0;
    }

    function supprimerSelection(input, takeoff) {
        //appel du FrontController en ajax pour la suppression de la sélection
        //en passant en argument l'intervalle de celle-ci
        input.parent().parent().slideUp(200, function() {
            var id_of_sel = $(this).find('input[name=id_sel]').val();

            if (takeoff) {
                selections[id_of_sel].isInUse = false;
                removeClass(id_of_sel);
            }

            $(this).remove();
            if (aucuneSelection()) {
                no_selection_div.slideDown();
            }
        });
    }

    function ajouterAnnotation() {
        if ($('#annotation').val() === '') {
            notification('Le texte de l\'annotation ne peut pas &ecirc;tre vide !', type_message.ERROR);
            return;
        }

        if (aucuneSelection()) {
            notification('Il faut avoir au moins une s&eacute;lection à annoter !', type_message.ERROR);
            return;
        }

        //ajout de l'annotation via le FrontController
        $("#json").text(JSON.stringify(selections));
        $("#sel_form").submit();
    }

    function setNoSelection() {
        $('.close_selection input[name=close]').each(function() {
            supprimerSelection($(this), false);
        });
    }

    function generateDivSel() {
        var sel_div = '<div class="selection"><div class="content"><code>';
        sel_div += curr_selection.text;
        sel_div += '</code></div><div class="close_selection">';
        sel_div += '<input type="hidden" name="id_sel" value="' + curr_id_sel + '"/>';
        sel_div += '<input class="close_button" name="close" type="button" value=""/></div>';
        sel_div += '</div>';

        return sel_div;
    }

    function isSelectionCorrect(curr_selection) {
        if (curr_selection.text.match(/^ +$/))
            return false;
        
        var error = false;
        var i = 0;
        while (!error && i < selections.length) {
            if (selections[i].isInUse) {
                //on vérifie pour chaque sélection qu'elle est bien distincte à la nouvelle
                //si des sélection se croise == erreur
                error = (curr_selection.start >= selections[i].start && curr_selection.start <= selections[i].end) 
                || (curr_selection.end >= selections[i].start && curr_selection.end <= selections[i].end) 
                || (curr_selection.start <= selections[i].start && curr_selection.end >= selections[i].end);
            }

            i++;
        }

        return !error;
    }
});