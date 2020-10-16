<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
   <head>
   <title>Finish page</title>
        <%@include file="header.jsp" %>
        <%@ include file="include.jsp" %>
        <style type="text/css"><%@include file="/resources/css/style.css"%></style>

        <fmt:setLocale value="${sessionScope.localization}"/>
        <fmt:setBundle basename="localization.local" var="local"/>

        <fmt:bundle basename="localization">
            <fmt:message key="local.logOut" var="logOut"/>
            <fmt:message key="local.finishMessage" var="finishMessage"/>
            <fmt:message key="local.countryOrderMessage" var="countryOrderMessage"/>
            <fmt:message key="local.totalPriceMessage" var="totalPriceMessage"/>
            <fmt:message key="local.toUserMenu" var="toUserMenu"/>
        </fmt:bundle>

   </head>

   <body>
        <form action="Controller" method="GET"  align="center" style="margin: 15px">
            <input type="hidden" name="command" value="logout" />
            <input type="submit" class="btn btn-success" value="${logOut}"/>
       </form>

        <div align="center" style="margin-top: 50px; margin-bottom: 400px">
            <table width=800px border="1"  style="border: 3px ridge DarkBlue">
                <tr>
                    <td align="center" style="color: DarkBlue; font-weight: bold; font-size: 16px; font-style: italic">
                        <p>${userName}${finishMessage}</p>
                        <p>${countryOrderMessage}${countryOrder}</p>
                        <p>${totalPriceMessage}${totalPrice}</p>
                    </td>
                </tr>
                <tr>
                    <td>
                       <form action="Controller" method="GET"  align="center">
                            <input type="hidden" name="command" value="to_user_menu" />
                            <button class="btn btn-success" type="submit">${toUserMenu}</button>
                        </form>
                    </td>
                </tr>
            </table>
         </div>

   <%@ include file="footer.jsp" %>
   </body>

</html>