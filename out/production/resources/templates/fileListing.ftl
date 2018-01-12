<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<body>

<h1>Uploaded files</h1>

<#if resumes?? >
  <#list resumes as resume>
  File name = ${resume.resumeName} <br>
  </#list>
</#if>

</body>
</html>