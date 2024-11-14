<!DOCTYPE html>
<html lang="en">
<head>
    <title>Navbar</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <style>
        .right_box {
            position: absolute;
            right: 10px;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Blackjack</a>
    <div class="collapse navbar-collapse" id="navbarNav">
        <div class="right_box">
<#--            <#if isAuthenticated()?? && !isAuthenticated()>-->
<#--                <ul class="nav justify-content-end">-->
<#--                    <li class="btn-group mx-auto" role="group" aria-label="Basic outlined example">-->
<#--                        <a class="btn btn-primary" href="/login" role="button">Sign In</a>-->
<#--                        <a class="btn btn-dark" href="/register" role="button">Sign Up</a>-->
<#--                    </li>-->
<#--                </ul>-->
<#--            <#else>-->
<#--                <ul class="nav justify-content-end">-->
<#--                    <form action="/logout" method="post">-->
<#--                        <button class="btn btn-primary" name="logout" value="out">Log out</button>-->
<#--                    </form>-->
<#--                </ul>-->
<#--            </#if>-->

        </div>
    </div>
</nav>

<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3"
        crossorigin="anonymous"></script>
<script>
    $('.nav-link').on('click', function (event) {
        event.preventDefault();
        const url = $(this).data('target');
        location.replace(url);
    });
</script>
</body>
</html>