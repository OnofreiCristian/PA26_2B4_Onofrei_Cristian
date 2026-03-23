<!DOCTYPE html>
<html>
<head>
    <title>Catalog Report</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #dddddd; text-align: left; padding: 8px; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <h1>My Resource Catalog</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Author</th>
            <th>Year</th>
            <th>Location</th>
        </tr>
        <#list resources as res>
        <tr>
            <td>${res.id}</td>
            <td>${res.title}</td>
            <td>${res.author}</td>
            <td>${res.year?c}</td> <td><a href="${res.location}">${res.location}</a></td>
        </tr>
        </#list>
    </table>
</body>
</html>