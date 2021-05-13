<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.Payment"%>
<%
//Save---------------------------------
if (request.getParameter("nameOnCard") != null)
{
	Payment payObj = new Payment();
    String stsMsg = "";
//Insert--------------------------
if (request.getParameter("hidItemIDSave") == "")
{
stsMsg = payObj.insertPayment(request.getParameter("nameOnCard"),
request.getParameter("cardNumber"),
request.getParameter("expiredDate"),
request.getParameter("cvv"));

}
else//Update----------------------
{
stsMsg = payObj.updatePayment(request.getParameter("hidItemIDSave"),
request.getParameter("nameOnCard"),
request.getParameter("cardNumber"),
request.getParameter("expiredDate"),
request.getParameter("cvv"));
}
session.setAttribute("statusMsg", stsMsg);
}
//Delete-----------------------------
if (request.getParameter("hidPaymentIDDelete") != null)
{
	Payment payObj = new Payment();
String stsMsg =
payObj.deletePayment(request.getParameter("hidPaymentIDDelete"));
session.setAttribute("statusMsg", stsMsg);
}
    
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/payment.js"></script>
<title>Payment Management</title>
</head>
<body>
<h1>Payment Management</h1>
<form id="formPayment" name="formPayment" method="post" action="payments.jsp">
 Name on card:
<input id="nameOnCard" name="nameOnCard" type="text"
 class="form-control form-control-sm">
<br> Card number:
<input id="cardNumber" name="cardNumber" type="text"
 class="form-control form-control-sm">
<br> Expired date:
<input id="expiredDate" name="expiredDate" type="text"
 class="form-control form-control-sm">
<br> cvv:
<input id="cvv" name="cvv" type="text"
 class="form-control form-control-sm">
<br>
<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
<input type="hidden" id="hidPaymentIDSave" name="hidPaymentIDSave" value="">
</form>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>


<br>
<div id="divPaymentsGrid">
<%

Payment payObj = new Payment();
 out.print(payObj.readPayment());
%>
</div>
</body>
</html>