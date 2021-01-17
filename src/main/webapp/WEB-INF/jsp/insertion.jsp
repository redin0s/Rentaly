<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
    <script src="../../js/import.js"></script>
    <title>ProvaArticolo</title>
</head>

<body>
    <div class="header-blue">
        <script src="../../js/header.js"></script>
        
        <div class="row">
            <div class="col-md-9">
            	<span class="navbar-text"> 
                	Title
            	</span>
            </div>
            <div class="col-md-3">
           		<span class="navbar-text"> 
                	Cost
                </span>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <div class="carousel slide" data-ride="carousel" id="carousel-1">
                    <div class="carousel-inner" role="listbox">
                        <div class="carousel-item active"><img class="w-100 d-block" src="https://cdn.bootstrapstudio.io/placeholders/1400x800.png" alt="Slide Image"></div>
                        <div class="carousel-item"><img class="w-100 d-block" src="https://cdn.bootstrapstudio.io/placeholders/1400x800.png" alt="Slide Image"></div>
                        <div class="carousel-item"><img class="w-100 d-block" src="https://cdn.bootstrapstudio.io/placeholders/1400x800.png" alt="Slide Image"></div>
                    </div>
                    <div><a class="carousel-control-prev" href="#carousel-1" role="button" data-slide="prev"><span class="carousel-control-prev-icon"></span><span class="sr-only">Previous</span></a><a class="carousel-control-next" href="#carousel-1" role="button"
                            data-slide="next"><span class="carousel-control-next-icon"></span><span class="sr-only">Next</span></a></div>
                    <ol class="carousel-indicators">
                        <li data-target="#carousel-1" data-slide-to="0" class="active"></li>
                        <li data-target="#carousel-1" data-slide-to="1"></li>
                        <li data-target="#carousel-1" data-slide-to="2"></li>
                    </ol>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <p>sqm</p>
            </div>
            <div class="col">
                <p>maxL</p>
            </div>
            <div class="col">
                <p>cont</p>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <p>desc</p>
            </div>
        </div>

    </div>

</body>

</html>