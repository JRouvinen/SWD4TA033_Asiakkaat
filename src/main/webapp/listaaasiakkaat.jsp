<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<title>Asiakaslistaus</title>
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
<table id="list">
	<thead>
		<tr style="background-color:slateblue;">
			<th colspan="6" class="center"><a href='lisaaasiakas.jsp'><span style=color:black;font-weight:bold>Lisää uusi asiakas</span></a></th>
			  
		</tr>	
		<tr style="background-color:slateblue;">
			<th style=text-align:right>Hakusana:</th>
			<th style=text-align:right colspan="3"><input type="text" id="searchwrd"></th>
			<th><input type="button" value="hae" id="searchbtn"></th>
		</tr>			
		<tr>
			<th> Asiakas nro. </th>
			<th> Etunimi </th>
			<th> Sukunimi </th>
			<th> Puhelin </th>
			<th> S-posti </th>
			<th></th>							
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>
<script>
$(document).ready(function(){
	
	haeAsiakkaat();
	
	$("#newcustomer").click(function(){
		document.location="lisaaasiakas.jsp";
	});
	
	$("#searchbtn").click(function(){		
		haeAsiakkaat();
	});
	$(document.body).on("keydown", function(event){
		  if(event.which==13){ //if Enter do find
			  haeAsiakkaat();
		  }
	});
	$("#searchwrd").focus();//focus cursor into search
});	
	
	
function haeAsiakkaat(){
	$("#list tbody").empty();
	$.ajax({url:"asiakkaat/"+$("#searchwrd").val(), type:"GET", dataType:"json", success:function(result){//Funktio palauttaa tiedot json-objektina		
		console.log(result);
		var backg_grey = 1;
		$.each(result.asiakkaat, function(i, field){  
        	var htmlStr;
        	console.log(i);
        	if (backg_grey == 1) {
        		htmlStr+="<tr style= background-color:grey id='line_"+field.asiakasnro+"';>";
        		backg_grey = 0;
        	} else {
        		htmlStr+="<tr style= background-color:lightgrey;   id='line_"+field.asiakasnro+"';>";
        		backg_grey = 1;
        	}
        	htmlStr+="<td><span style=text-align:center;>"+field.asiakasnro+"</span></td>";
        	htmlStr+="<td>"+field.etunimi+"</td>";
        	htmlStr+="<td>"+field.sukunimi+"</td>";
        	htmlStr+="<td>"+field.puhelin+"</td>";
        	htmlStr+="<td>"+field.sposti+"</td>";
        	//htmlStr+="<td><span style=color:tomato;font-weight:bold class='poista' onclick=poista('"+field.asiakasnro+"') >Poista</span></td>"; -> luo oman sarakkeen
        	htmlStr+="<td><input style=color:tomato;font-weight:bold type=button value=Poista id=deletebtn onclick=poista('"+field.asiakasnro+"')></td>"; //-> luodaan nappi poistolle 
        	htmlStr+="</tr>";
        	$("#list tbody").append(htmlStr);
        });	
    }});
}
function poista(asiakasnro){
	if(confirm("Poista asiakas " + asiakasnro +"?")){
		$.ajax({url:"asiakkaat/"+asiakasnro, type:"DELETE", dataType:"json", success:function(result) { //result on joko {"response:1"} tai {"response:0"}
	        if(result.response==0){
	        	$("#ilmo").html("Asiakkaan poisto epäonnistui.");
	        }else if(result.response==1){
	        	$("#line_"+asiakasnro).css("background-color", "red"); //color red for deleted line
	        	alert("Asiakkaan " + asiakasnro +" poisto onnistui.");
				haeAsiakkaat();        	
			}
	    }});
	}
}

</script>
</body>
</html>