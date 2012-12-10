function addClass(curr_id_sel, selection, class_to_add) {
    var div_content = $('#code #content'); //on met à jour div_content
    var text = div_content.html();

    //on trouve le vrai début de la sélection dans le code html
    var start = findIndex(selection.start, text);
    var end = findIndex(selection.end, text);

    var html_before = text.substring(0, start);
    var html_middle = text.substring(start, end);
    var html_after = text.substring(end, text.length);
    
    var new_html = html_before + '<span id="sel_' + curr_id_sel + '" class="' + class_to_add + '">' + html_middle + '</span>' + html_after;
    div_content.html(new_html);
}
    
function removeClass(id_selection) {
    var to_replace = $('#sel_' + id_selection);
    to_replace.replaceWith(to_replace.html());
}

function findIndex(index, text) {
    var i = 0;
    var nb_char_balises = 0;
    var trouve = false;

    while (!trouve && i < text.length) {
        if ((i - nb_char_balises) == index) {
            trouve = true;
        } else if (text.charAt(i) == '&') {
            //c'est un seul caractère transformé en non html
            while (text.charAt(i) != ';') {
                //on le passe complètement en le comptant comme non caractère
                i++;
                nb_char_balises++;
            }
            i++;
        } else if (text.charAt(i) == '<') {
            //c'est un <span> en html
            while (text.charAt(i) != '>') {
                //on le passe en le comptant comme balises
                i++;
                nb_char_balises++;
            }
            i++;
            nb_char_balises++;
        } else {
            i++;
        }
    }

    return i;
}

function escapeHTML(string) {
    return string.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
}
