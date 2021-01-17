<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
    <script src="../../js/import.js"></script>
    <title>Accedi - Rentaly</title>
</head>

<body>
    <div>
        <div class="header-blue">
            <nav class="navbar navbar-light navbar-expand-md navigation-clean-search">
                <div class="container-fluid"><a class="navbar-brand" href="#">Rentaly</a><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
                    <div class="collapse navbar-collapse"
                        id="navcol-1">
                        <ul class="nav navbar-nav">
                            <li class="nav-item" role="presentation"><a class="nav-link" href="#">Chi siamo</a></li>
                            <li class="nav-item dropdown"><a class="dropdown-toggle nav-link" data-toggle="dropdown" aria-expanded="false" href="#">Dropdown </a>
                                <div class="dropdown-menu" role="menu"><a class="dropdown-item" role="presentation" href="#">First Item</a><a class="dropdown-item" role="presentation" href="#">Second Item</a><a class="dropdown-item" role="presentation" href="#">Third Item</a></div>
                            </li>
                        </ul>
                        <form class="form-inline mr-auto" target="_self">
                            <div class="form-group"><label for="search-field"><i class="fa fa-search"></i></label><input class="form-control search-field" type="search" id="search-field" name="search"></div>
                        </form><span class="navbar-text"> </span></div>
                </div>
            </nav>
            <div class="container hero">
                <form method="POST" action="doLogin">
                <div class="row">
                    <div class="col-12 col-lg-6 col-xl-5 offset-md-0 offset-xl-1"><input type="text" id="u" name="user" default="Username"></div>
                </div>
                <div class="row">
                    <div class="col-12 col-lg-6 col-xl-5 offset-md-0 offset-xl-1"><input type="password" id="p" name="pass"></div>
                </div>
                <div class="row">
                    <div class="col-12 col-lg-6 col-xl-5 offset-md-0 offset-xl-1"><input class="btn btn-primary" type="submit" value="Accedi" id="doLogin"></div>
                </div>
                </form>
                <div class="row">
                    <div class="col">
                        <p>Non hai ancora un account? <a href="register">Registrati</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
