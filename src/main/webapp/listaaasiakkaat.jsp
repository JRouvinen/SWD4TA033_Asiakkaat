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
</style>

</head>
<body>
<table id="list">
	<thead>	
		<tr style="background-color:slateblue;">
			<th class="allright">Hakusana:</th>
			<th colspan="2"><input type="text" id="hakusana"></th>
			<th><input type="button" value="hae" id="hakunappi"></th>
		</tr>			
		<tr style="background-color:slateblue;">
			<th>Etunimi</th>
			<th>Sukunimi</th>
			<th>Puhelin</th>
			<th>S-posti</th>							
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>
<script>
$(document).ready(function(){
	
	haeAsiakkaat();
	
	$("#hakunappi").click(function(){		
		haeAsiakkaat();
	});
	$(document.body).on("keydown", function(event){
		  if(event.which==13){ //if Enter do find
			  haeAsiakkaat();
		  }
	});
	$("#hakusana").focus();//focus cursor into search
});	
	
	
function haeAsiakkaat(){
	$("#list tbody").empty();
	$.ajax({url:"asiakkaat/"+$("#hakusana").val(), type:"GET", dataType:"json", success:function(result){//Funktio palauttaa tiedot json-objektina		
		console.log(result);
		var backg_grey = 1;
		$.each(result.asiakkaat, function(i, field){  
        	var htmlStr;
        	console.log(i);
        	if (backg_grey == 1) {
        		htmlStr+="<tr style= background-color:grey;>";
        		backg_grey = 0;
        	} else {
        		htmlStr+="<tr style= background-color:lightgrey;>";
        		backg_grey = 1;
        	}
        	htmlStr+="<td>"+field.etunimi+"</td>";
        	htmlStr+="<td>"+field.sukunimi+"</td>";
        	htmlStr+="<td>"+field.puhelin+"</td>";
        	htmlStr+="<td>"+field.sposti+"</td>";  
        	htmlStr+="</tr>";
        	$("#list tbody").append(htmlStr);
        });	
    }});
}


</script>
</body>
</html>