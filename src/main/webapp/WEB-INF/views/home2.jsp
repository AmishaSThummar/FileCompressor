<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>File Upload Form</title>
</head>
<body>
    <h2>Upload the file which you want to decompress</h2>
    <form action="/File-Compressor/decompress" method="post" enctype="multipart/form-data">
        <input type="file" name="file2" />
        <input type="submit" value="Upload" />
    </form>
</body>
</html>
