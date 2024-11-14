<!DOCTYPE html>
<html lang="en">
<head>
    <title>Register Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<#include "part/navbar.ftlh">

<div class="container">
    <h2>Register</h2>
    <#if errors??>
        <ul style="color: red;">
            <#list errors as error>
                <li>${error.defaultMessage}</li>
            </#list>
        </ul>
    </#if>

    <form action="/register" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" id="username" name="username">
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" name="password">
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <button type="submit" class="btn btn-primary">Register</button>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</body>
</html>