<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Scanner"%>
<%
    String file = (String) request.getAttribute("file");
    Scanner scanner = new Scanner(file);
    int i = 1;
%>

<div id="file_panel" class="big_div">
    <h2><%=request.getAttribute("fileName")%></h2>
    <table id="file">
        <tr>
            <td id="nums" style="vertical-align: top;">
                <pre><%
                    while (scanner.hasNextLine()) {
                        scanner.nextLine();
                        out.println(i++);
                    }
                    %></pre>
            </td>
            <td id="code" style="vertical-align: top;">
                <pre id="content"><%=file%></pre>
            </td>
        </tr>
    </table>
</div>

</script>
<script type="text/javascript">
    <%@include file="../js/file.js"%>
</script>
