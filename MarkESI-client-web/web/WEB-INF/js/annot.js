$(document).ready(function() {
    var annotations_div = $('#annotations');
    var annot;
    
    function newAnnotObject(n_date, n_text, n_sels) {
        var new_annot = {
            date: n_date,
            text: n_text,
            sels: n_sels
        };
        return new_annot;
    }
    
    function newSelectionObject(n_text, n_start, n_end, n_length, n_isInUse, n_annot) {
        var new_sel = {
            text: n_text,
            start: n_start,
            end: n_end,
            length: n_length,
            isInUse: n_isInUse,
            annotation: n_annot
        };
        return new_sel;
    }
    
    function copy(sel) {
        return newSelectionObject(sel.text, sel.start, sel.end, sel.length, sel.isInUse, sel.annotation);
    }
    
    var sels = new Array();
    sels.push(newSelectionObject("nimp", 10, 25, 15, true, 2));
    sels.push(newSelectionObject("4", 20, 35, 15, true, 2));
    sels.push(newSelectionObject("5", 60, 65, 5, true, 2));
    annot = newAnnotObject('19/11/2012', 'iscing ultrices augue, sed rutrum mi pretium ac. Nullam nunc lacus, rutrum eget facilisis vel, ullamco', sels);
    
    function  appendAnnot (annot)
    {
        var div = '<div class="annotation">' +
            '<div class="text">' + annot.text + '</div>' +
            '<div class="date">' + annot.date + '</div>' +
            '<div id="sels">';
        
        for (var i = 0; i < annot.sels.length; i++) {
            div += '<div class="selection">' + annot.sels[i].text + '</div>';
        }
        
        div += '</div></div>';
        annotations_div.append(div);
    }
    
    function getParameter (name)
    {
        return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
    }
    
    $.getJSON("/MarkESI-client-web/json&fileName=" + "test",//getParameter("fileName"),
        function(data){
          console.log(data);
        }
    );
    
    appendAnnot(annot);
    appendAnnot(annot);
    appendAnnot(annot);
});
