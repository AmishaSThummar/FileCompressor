<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>File Upload Form</title>
</head>
<body>
    <h2>Upload the file which you want to compress</h2>
    <form action="/File-Compressor/compress" method="post" enctype="multipart/form-data">
        <input type="file" name="file" />
        <input type="submit" value="Upload" />
    </form>
</body>
</html>
