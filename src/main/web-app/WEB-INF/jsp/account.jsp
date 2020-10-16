<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
   <head>
   <title>Login</title>
        <%@include file="header.jsp" %>
        <%@ include file="include.jsp" %>
        <style type="text/css"><%@include file="/resources/css/style.css"%></style>

        <fmt:setLocale value="${sessionScope.localization}"/>
        <fmt:setBundle basename="localization.local" var="local"/>

        <fmt:bundle basename="localization">
            <fmt:message key="local.name" var="name"/>
            <fmt:message key="local.surname" var="surname"/>
            <fmt:message key="local.money" var="money"/>
            <fmt:message key="local.email" var="email"/>
            <fmt:message key="local.discount" var="discount"/>
            <fmt:message key="local.login" var="login"/>
            <fmt:message key="local.logOut" var="logOut"/>
            <fmt:message key="local.updateBalance" var="updateBalance"/>
            <fmt:message key="local.toUserMenu" var="toUserMenu"/>
        </fmt:bundle>

   </head>

   <body>

      <form action="Controller" method="GET"  align="center" style="margin: 15px">
           <input type="hidden" name="command" value="logout" />
           <input type="submit" class="btn btn-warning" value="${logOut}"/>
      </form>

           <div align="center" style="margin-top: 15px; margin-bottom: 170px">
               <table width=600px border="1"  style="border: 3px ridge DarkBlue" >
                   <tr align="center" style="color: DarkBlue; font-weight: bold; font-size: 16px; font-style: italic">
                       <td><p>${name}</p></td>
                       <td><p>${username}</p></td>
                   </tr>
                   <tr align="center" style="color: DarkBlue; font-weight: bold; font-size: 16px; font-style: italic">
                        <td><p>${surname}</p></td>
                        <td><p>${usersurname}</p></td>
                   </tr>
                   <tr align="center" style="color: DarkBlue; font-weight: bold; font-size: 16px; font-style: italic">
                        <td><p>${money}</p></td>
                        <td><p>${usermoney}</p></td>
                   </tr>
                   <tr align="center" style="color: DarkBlue; font-weight: bold; font-size: 16px; font-style: italic">
                        <td><p>${discount}</p></td>
                        <td><p>${userdiscount} %</p></td>
                   </tr>
                   <tr align="center" style="color: DarkBlue; font-weight: bold; font-size: 16px; font-style: italic">
                        <td><p>${email}</p></td>
                        <td><p>${useremail}</p></td>
                   </tr>
                   <tr align="center" style="color: DarkBlue; font-weight: bold; font-size: 16px; font-style: italic">
                        <td><p>${login}</p></td>
                        <td><p>${userlogin}</p></td>
                   </tr>
                   <tr align="center" style="color: DarkBlue; font-weight: bold; font-size: 16px; font-style: italic">
                        <td><p>${updateBalance}</p></td>
                        <td>
                        <form action="Controller" method="GET"  align="center" style="margin: 15px">
                             <input type="hidden" name="command" value="update_balance" />
                             <input type="text" name="newBalance" placeholder="0.00" title="${money}"/>
                             <input type="submit" class="btn btn-success" value="${updateBalance}"/>
                        </form>
                        <p>${acceptedMessage}</p>
                        </td>
                   </tr>
                   <tr>
                        <td></td>
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