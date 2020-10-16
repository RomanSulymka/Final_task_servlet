<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customTags" prefix="ctg"%>

<!DOCTYPE html>
<html>
<head>
     <%@include file="/WEB-INF/jsp/include.jsp" %>
     <style type="text/css"><%@include file="/resources/css/style.css"%></style>

     <fmt:setLocale value="${sessionScope.localization}"/>
     <fmt:setBundle basename="localization.local" var="local"/>

     <fmt:bundle basename="localization">
         <fmt:message key="local.follow" var="follow"/>
     </fmt:bundle>
</head>

<body>

<div class="footer" id="footercontext" align="center">
    <div class="row">
          <div class="col-sm-4"></div>
          <div class="col-sm-4"></div>
          <div class="col-sm-4">${follow}
              <a href="https://twitter.com" target="_blank">
                  <img src="resources/image/twitter.png" alt="twitter" class="twitter"/>
              </a>
              <a href="https://www.facebook.com" target="_blank">
                  <img src="resources/image/facebook.png" alt="facebook" class="facebook"/>
              </a>
              <a href="https://www.instagram.com" target="_blank">
                  <img src="resources/image/camera.png" alt="camera" class="camera"/>
              </a>
          </div>
    </div>
    <div class="row">
          <div class="col-sm-4"></div>
                <div class="col-sm-4"><ctg:CopyRight/></div>
          <div class="col-sm-4"></div>
    </div>
</div>

</body>
</html>