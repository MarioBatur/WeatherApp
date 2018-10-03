<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vremenska prognoza</title>

</head>

<body>

<form:form action="processForm" modelAttribute="Weather" method="GET">
    Unesite lokaciju: <form:input required="required" path="name"/>
    <input type="submit" value="Submit"/>
</form:form>

<c:if test="${not empty currentWeather.name}">

    Grad: ${currentWeather.name}
    Država: ${currentWeather.sys.country}
    Temperatura: ${currentWeather.main.temp}
    Pritisak: ${currentWeather.main.pressure}
    Vlažnost: ${currentWeather.main.humidity}
    Trenutna temperatura: ${currentWeather.main.temp}
    Vjetar: ${currentWeather.wind.speed}
    Izlazak sunca: ${sunriseTime.hours}:${sunriseTime.minutes}
    Zalazak sunca: ${sunsetTime.hours}:${sunsetTime.minutes}


    <c:forEach items="${currentWeather.weather}" var="element">
        ${element.description}
        <img src="http://openweathermap.org/img/w/${element.icon}.png"
             alt="Weather icon">
    </c:forEach>

</c:if>

<c:if test="${not empty Error}">
    Molimo vas pružite točnu lokaciju
</c:if>


</body>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        var elements = document.getElementsByTagName("INPUT");
        for (var i = 0; i < elements.length; i++) {
            elements[i].oninvalid = function (e) {
                e.target.setCustomValidity("");
                if (!e.target.validity.valid) {
                    e.target.setCustomValidity("Polje ne smije biti prazno");
                }
            };
            elements[i].oninput = function (e) {
                e.target.setCustomValidity("");
            };
        }
    })
</script>
</html>
