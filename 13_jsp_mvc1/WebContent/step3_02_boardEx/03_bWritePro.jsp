<%@page import="step3_00_boardEx.BoardDAO"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>03_bWritePro</title>
</head>
<body>
	<%
		request.setCharacterEncoding("utf-8");
	%>
		<jsp:useBean id="boardDTO" class="step3_00_boardEx.BoardDTO">
			<jsp:setProperty name="boardDTO" property="*"/>
		</jsp:useBean>
	<!-- 스프링은 객체 생성 jsp는 파라미터로 받아오는 용도로 사용 --> 
	<%
		BoardDAO.getInstance().insertBoard(boardDTO);
	%>
		<script>
			alert("등록되었습니다.");
			location.href="04_bList.jsp"
		</script>
	
</body>
</html>