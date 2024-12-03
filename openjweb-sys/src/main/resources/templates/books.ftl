<!DOCTYPE HTML>
<html>
<head>
    <title>图书</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>

</head>

<body>
<table border="1">
    <tr>
        <td>书名</td>
        <td>作者</td>
    </tr>
    <#if books??&&(books?size>0)>
        <#list books as book>
            <tr>
                <td>${book.name}</td>
                <td>${book.author}</td>
            </tr>
        </#list>
    </#if>
</table>
</body>
</html>
