<%@ page import="info.magnolia.module.servletsanity.support.ServletAssert" %>
<%@ taglib prefix="cms" uri="http://magnolia-cms.com/taglib/templating-components/cms"%>

<%
    // We don't test the query strings here because Tomcat and Jetty disagrees on what their values should be. Jetty
    // combines them making it p=12&a=2 while Tomcat simply uses the part given to the forward which is p=12

    ServletAssert.printRequestInfo(request, response, "nestedParagraph.jsp");
    ServletAssert.assertIsInclude(request);
    ServletAssert.assertRequestUri(request, "/servlet-sanity/pages/template.jsp");
    ServletAssert.assertServletPath(request, "/servlet-sanity/pages/template.jsp");
    ServletAssert.assertPathInfo(request, null);
//    ServletAssert.assertQueryString(request, "p=12");

    ServletAssert.assertAttribute(request, "javax.servlet.forward.request_uri",  request.getContextPath() + "/sanity-test-page.html");
    ServletAssert.assertAttribute(request, "javax.servlet.forward.servlet_path", "/sanity-test-page.html");
    ServletAssert.assertAttribute(request, "javax.servlet.forward.path_info",    null);
//    ServletAssert.assertAttribute(request, "javax.servlet.forward.query_string", "a=2");

    ServletAssert.assertAttribute(request, "javax.servlet.include.request_uri",  request.getContextPath() + "/servlet-sanity/components/nestedParagraph.jsp");
    ServletAssert.assertAttribute(request, "javax.servlet.include.servlet_path", "/servlet-sanity/components/nestedParagraph.jsp");
    ServletAssert.assertAttribute(request, "javax.servlet.include.path_info",    null);
    ServletAssert.assertAttribute(request, "javax.servlet.include.query_string", "r=56");

    ServletAssert.assertParameter(request, "a", "2");
    ServletAssert.assertParameter(request, "p", "12");
    ServletAssert.assertParameter(request, "q", "34");
    ServletAssert.assertParameter(request, "r", "56");
%>

<div style="border:1px solid black;padding:30px">
    <p>NestedParagraph</p>
</div>
