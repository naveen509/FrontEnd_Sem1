/**
 * 
 */
$(document).ready(function()
{
if ($("#alertSuccess").text().trim() == "")
 {
 $("#alertSuccess").hide();
 }
 $("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{

// Clear alerts---------------------
 $("#alertSuccess").text("");
 $("#alertSuccess").hide();
 $("#alertError").text("");
 $("#alertError").hide();
// Form validation-------------------
var status = validatePaymentForm();
if (status != true)
 {
 $("#alertError").text(status);
 $("#alertError").show();
 return;
 }
// If valid------------------------
var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";
 $.ajax(
 {
 url : "PaymentsAPI",
 type : type,
 data : $("#formPayment").serialize(),
 dataType : "text",
 complete : function(response, status)
 {
 
  location.reload(true);
  onPaymentSaveComplete(response.reseText, status);

 }
 }); 
});
// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
$("#hidPaymentIDSave").val($(this).data("paymentid"));
 $("#nameOnCard").val($(this).closest("tr").find('td:eq(0)').text());
 $("#cardNumber").val($(this).closest("tr").find('td:eq(1)').text());
 $("#expiredDate").val($(this).closest("tr").find('td:eq(2)').text());
 $("#cvv").val($(this).closest("tr").find('td:eq(3)').text());
});

$(document).on("click", ".btnRemove", function(event)
{
 $.ajax(
 {
 url : "PaymentsAPI",
 type : "DELETE",
 data : "paymentid=" + $(this).data("paymentid"),
 dataType : "text",
 complete : function(response, status)
 {

  location.reload(true);
 onPaymentDeleteComplete(response.responseText, status);

 }
 });
});


// CLIENT-MODEL================================================================
function validatePaymentForm()
{
// nameOnCard
if ($("#nameOnCard").val().trim() == "")
 {
 return "Insert Card name.";
 }
// cardNumber
if ($("#cardNumber").val().trim() == "")
 {
 return "Insert Card number.";
 } 
// ex date------------------------
if ($("#expiredDate").val().trim() == "")
 {
 return "Insert date.";
 }
 // cvv
if ($("#cvv").val().trim() == "")
 {
 return "Insert cvv.";
 }
return true;
}



function onPaymentSaveComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully saved.");
 $("#alertSuccess").show();
 $("#divPaymentsGrid").html(resultSet.data);

 } else if (resultSet.status.trim() == "error")
 {
 $("#alertError").text(resultSet.data);
 $("#alertError").show();
 }
 } else if (status == "error")
 {
 $("#alertError").text("Error while saving.");
 $("#alertError").show();
 } else
 {
 $("#alertError").text("Unknown error while saving..");
 $("#alertError").show();
 } 

 $("#hidPaymentIDSave").val("");
 $("#formPayment")[0].reset();
}

function onPaymentDeleteComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully deleted.");
 $("#alertSuccess").show();
 $("#divPaymentsGrid").html(resultSet.data);
 } else if (resultSet.status.trim() == "error")
 {
 $("#alertError").text(resultSet.data);
 $("#alertError").show();
 }
 } else if (status == "error")
 {
 $("#alertError").text("Error while deleting.");
 $("#alertError").show();
 } else
 {
 $("#alertError").text("Unknown error while deleting..");
 $("#alertError").show();
 }
}   
 