<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="scripts/main.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/jquery.validate.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Muuta asiakas</title>
<style>
.allright{
	text-align: right;
}
.center{
	text-align: center;
}
body {
  background-color: whitesmoke;
}
table {
	text-align: center;
	background-color:slateblue
	
}

tr {
	background-color:slateblue;"
}
</style>
</head>
<body>
<form id="chgcustomer">
	<table>
		<thead>	
			<tr style="background-color:slateblue;">	
				<th colspan="5" class="center"><a href='listaaasiakkaat.jsp'><span style=color:black;font-weight:bold>Takaisin listaukseen</span></a></th>
			</tr>		
			<tr style="background-color:slateblue;">
				<th>Asiakas nro.</th>
				<th>Etunimi</th>
				<th>Sukunimi</th>
				<th>Puhelin</th>
				<th>S-posti</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><input type="text" name="asiakasid" id="asiakasid"></td>
				<td><input type="text" name="etun" id="etun"></td>
				<td><input type="text" name="sukun" id="sukun"></td>
				<td><input type="text" name="puh" id="puh"></td>
				<td><input type="text" name="sposti" id="sposti"></td> 
				<td><input type="submit" id="change" value="Muuta tiedot"></td>
			</tr>
		</tbody>
	</table>
	<input type="hidden" name="vanhaasiakasid" id="vanhaasiakasid">	
</form>
<span id="info"></span>
</body>
<script>
$(document).ready(function(){
	$("#back").click(function(){
		document.location="listaaasiakkaat.jsp";
	});
	//get information of tha customer. call back-end GET-method
	//GET /asiakkaat/getone/customerid
	var asiakasnro = requestURLParam("asiakasnro"); 	
	$.ajax({url:"asiakkaat/getone/"+asiakasnro, type:"GET", dataType:"json", success:function(result){	
		$("#vanhaasiakasid").val(result.asiakasid);
		$("#asiakasid").val(result.asiakasid);		
		$("#etun").val(result.etun);	
		$("#sukun").val(result.sukun);
		$("#puh").val(result.puhelin);
		$("#sposti").val(result.sposti);			
    }});
	$("#chgcustomer").validate({						
		rules: {
			asiakasnro:  {
				required: true,
				minlength: 1,
				number: true,
				
			},
			etun:  {
				required: true,
				minlength: 2,
				
				
			},	
			sukun:  {
				required: true,
				minlength: 2,
				
				
			},
			puh:  {
				required: true,
				minlength: 6,
				number: true
				
			},	
			sposti:  {
				required: true,
				minlength: 6,
				email: true
			}	
		},
		
		messages: {
			etun: {     
				required: "Kenttä on tyhjä",
				minlength: "Liian lyhyt",
				
			},
			sukun: {
				required: "Kenttä on tyhjä",
				minlength: "Liian lyhyt",
				
			},
			puh: {
				required: "Kenttä on tyhjä",
				minlength: "Liian lyhyt",
				number: "Numero ei ole kelvollinen"
			},
			sposti: {
				required: "Kenttä on tyhjä",
				minlength: "Liian lyhyt",
				email: "Sähköposti ei ole kelvollisessa muodossa"
				
			}
		},			
		submitHandler: function(form) {	
			updateInformation();
		}		
	}); 	
});
//tällä funktiolla lisätään tietoja. tässä kutsutaan Asiakkaat POST-metodia -> kutsun mukana lähetetään uudet tiedot json-stringin�.
function updateInformation(){	
	var formJsonStr = formDataJsonStr($("#chgcustomer").serializeArray()); //serialisoidaan lomakkeen tiedot json-stringiksi
	$.ajax({url:"asiakkaat/", data:formJsonStr, type:"PUT", dataType:"json", success:function(result) { //result on joko {"response:1"} tai {"response:0"}       
		if(result.response==0){
		console.log("Change fail")
      	$("#info").html("Asiakkaan päivittäminen epäonnistui.");
      }else if(result.response==1){
  		console.log("Change success")
  		$("#info").html("Asiakkaan päivittäminen onnistui");
  		$(asiakasid).val('');
  		$(etun).val('');
  		$(sukun).val('');
  		$(puh).val('');
  		$(sposti).val('');
      	
      	
		}
  }});	
}
</script>
</html>