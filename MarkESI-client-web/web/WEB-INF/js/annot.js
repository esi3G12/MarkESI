$(document).ready(function() {
    var annotations_div = $('#annotations');
    var text_div = $('#code #content');
    
    function  appendAnnot (annot)
    {
        var div = '<div class="annotation">' +
        '<div class="text">' + annot.text + '</div>' +
        '<div class="date">' + annot.date + '</div>' +
        '<div id="sels">';
        
        for (var i = 0; i < annot.selections.length; i++) {
            var sel = annot.selections[i];
            div += '<div class="selection">' + 
            escapeHTML(text_div.text().substring(sel.start, sel.end)) +
            '</div>';
        }
        
        div += '</div></div>';
        annotations_div.append(div);
    }
    
    function getParameter (name)
    {
        return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
    }
    
    $.getJSON("/MarkESI-client-web/json?action=get&fileName=" + getParameter("fileName"),
        function(data){
            $.each(data, function(key, val) {
                $.each(val, function(key, v) {
                    appendAnnot(v);
                });
            });
        }
        );
});
