<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
   <head>
   <title>User menu</title>
        <%@include file="header.jsp" %>
        <%@ include file="include.jsp" %>
        <style type="text/css"><%@include file="/resources/css/style.css"%></style>

        <fmt:setLocale value="${sessionScope.localization}"/>
        <fmt:setBundle basename="localization.local" var="local"/>

        <fmt:bundle basename="localization">
           <fmt:message key="local.logOut" var="logOut"/>
           <fmt:message key="local.viewAllVaucher" var="viewAllVaucher"/>
           <fmt:message key="local.country" var="country"/>
           <fmt:message key="local.dateFrom" var="dateFrom"/>
           <fmt:message key="local.dateTo" var="dateTo"/>
           <fmt:message key="local.tour.type" var="tourtype"/>
           <fmt:message key="local.tour.price" var="tourprice"/>
           <fmt:message key="local.tour.hot" var="tourhot"/>
           <fmt:message key="local.hotel.name" var="hotelname"/>
           <fmt:message key="local.hotel.pricePerDay" var="hotelpricePerDay"/>
           <fmt:message key="local.transport" var="transport"/>
           <fmt:message key="local.chooseVaucher" var="chooseVaucher"/>
           <fmt:message key="local.priceColumn" var="priceColumn"/>
           <fmt:message key="local.book" var="book"/>
           <fmt:message key="local.send" var="send"/>
           <fmt:message key="local.account" var="account"/>
           <fmt:message key="local.createOrderTable" var="createOrderTable"/>
           <fmt:message key="local.cancelOrder" var="cancelOrder"/>
           <fmt:message key="local.myOrders" var="myOrders"/>
        </fmt:bundle>

   </head>

   <body>
    <form action="Controller" method="GET"  align="center" style="margin: 15px">
         <input type="hidden" name="command" value="logout" />
         <input type="submit" class="btn btn-warning" value="${logOut}"/>
    </form>

        <div align="center">
            <table width=800px border="1"  style="border: 3px ridge DarkBlue">
                <tr>
                    <td>
                        <p align="center" style="color: DarkBlue; font-weight: bold; font-size: 16px; font-style: italic">
                                <a href="Controller?command=choose_vaucher" class="button">${chooseVaucher}</a>
                        </p>
                    </td>
                    <td>
                        <p align="center" style="color: DarkBlue; font-weight: bold; font-size: 16px; font-style: italic">
                             <a href="Controller?command=show_order_by_user_id" class="button">${myOrders}</a>
                        </p>
                    </td>
                     <td>
                         <p align="center" style="color: DarkBlue; font-weight: bold; font-size: 16px; font-style: italic">
                               <a href="Controller?command=view_account" class="button">${account}</a>
                         </p>
                     </td>
                </tr>
            </table>
         </div>


<div align="center" style="margin-top: 5px; margin-bottom: 5px">
<form action="Controller" method="post">
<input type="hidden" name="command" value="book_vaucher" />
<input type="hidden" name="id" value="${sessionScope.id}" />
<!------- CREATE VAUCHER ORDER TABLE ------------->
<table>
<caption style="color: GreenYellow; font-weight: bold">${createOrderTable}</caption>
    <tr>
        <td>
            <table border="1"  style="border: 3px ridge DarkBlue">
            <tr align="center" style="font-weight: bold">
               <td>№</td>
               <td>${country}</td>
               <td>${dateFrom}</td>
               <td>${dateTo}</td>
               <td>${tourtype}</td>
               <td>${tourprice}</td>
               <td>${tourhot}</td>
               <td>${hotelname}</td>
               <td>${hotelpricePerDay}</td>
               <td>${transport}</td>
           </tr>
           <c:forEach var="vaucher" items="${vauchers}" varStatus="status">
               <tr height="39">
                   <td><c:out value="${status.count}"/></td>
                   <td><c:out value="${vaucher.country}"/></td>
                   <td><c:out value="${vaucher.dateFrom}"/></td>
                   <td><c:out value="${vaucher.dateTo}"/></td>
                   <td><c:out value="${vaucher.tour.type}"/></td>
                   <td><c:out value="${vaucher.tour.price}"/></td>
                   <td><c:out value="${vaucher.tour.hot}"/></td>
                   <td><c:out value="${vaucher.hotel.name}"/></td>
                   <td><c:out value="${vaucher.hotel.pricePerDay}"/></td>
                   <td><c:out value="${vaucher.transport}"/></td>
           </tr>
           </c:forEach>
           </table>
       </td>

       <td>
           <table border="1"  style="border: 3px ridge DarkBlue">
           <tr align="center" style="font-weight: bold;">
                <td>${priceColumn}</td>
           </tr>
           <c:forEach var="vaucherPrice" items="${vaucherPrice}">
           <tr height="39" align="center">
                <td style="margin: 5px"><c:out value="${vaucherPrice}"/></td>
            </tr>
            </c:forEach>
           </table>
       </td>
       <td>
           <table border="1"  style="border: 3px ridge DarkBlue">
           <tr align="center" style="font-weight: bold;">
                <td>${book}</td>
           </tr>

           <c:forEach var="vaucher" items="${vauchers}">
           <tr>
                <td>
                    <label><input type="radio" name="idvaucher" value="${vaucher.id}">${book}</label>
                </td>
            </tr>
            </c:forEach>
           </table>
       </td>
    </tr>
</table>
    <div align="middle" style="margin-top: 10px">
        <input type="submit" class="btn btn-success" value="${send}"/>
    </div>
</form>
<hr align="center" width="90%" size="10" color="GreenYellow" />

<!------- CANCEL VAUCHER ORDER TABLE ------------->
<form action="Controller" method="post">
<input type="hidden" name="command" value="cancel_order" />
<input type="hidden" name="id" value="${sessionScope.id}" />
<table>
<caption style="color: GreenYellow; font-weight: bold">${myOrders}</caption>
    <tr>
        <td>
            <table border="1"  style="border: 3px ridge DarkBlue">
            <tr align="center" style="font-weight: bold">
               <td>№</td>
               <td>${country}</td>
               <td>${dateFrom}</td>
               <td>${dateTo}</td>
               <td>${tourtype}</td>
               <td>${tourhot}</td>
               <td>${hotelname}</td>
               <td>${transport}</td>
               <td>${priceColumn}</td>
               <td>${cancelOrder}</td>
           </tr>
           <c:forEach var="myOrder" items="${ordersByUserId}" varStatus="status">
           <tr height="39">
                   <td><c:out value="${status.count}"/></td>
                   <td><c:out value="${myOrder.vaucher.country}"/></td>
                   <td><c:out value="${myOrder.vaucher.dateFrom}"/></td>
                   <td><c:out value="${myOrder.vaucher.dateTo}"/></td>
                   <td><c:out value="${myOrder.vaucher.tour.type}"/></td>
                   <td><c:out value="${myOrder.vaucher.tour.hot}"/></td>
                   <td><c:out value="${myOrder.vaucher.hotel.name}"/></td>
                   <td><c:out value="${myOrder.vaucher.transport}"/></td>
                   <td><c:out value="${myOrder.totalPrice}"/></td>
                   <td>
                       <label><input type="radio" name="myOrderId" value="${myOrder.id}">${cancelOrder}</label>
                   </td>
           </tr>
           </c:forEach>
           </table>
       </td>
    </tr>
</table>
    <div align="middle" style="margin-top: 10px">
        <input type="submit" class="btn btn-success" value="${send}"/>
   </div>
</form>
</div>
<hr align="center" width="90%" size="10" color="GreenYellow" />

<div align="center" style="margin-bottom: 400px">
    <h3 style="color: red; font-weight: bold">${notEnouthMoneyMessage}</h3>
    <h2 align="center" style="color: red; font-weight: bold">${errorUserMenu}</h2>
</div>

   <%@ include file="footer.jsp" %>
   </body>
</html>