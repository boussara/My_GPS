# My_GPS

function insert_value(request){
  
   var ss=SpreadsheetApp.openByUrl("https://docs.google.com/spreadsheets/d/1h0rRK_oJl3YCJiCww9J0Upj6tY-AqrizYxmfVjb50bE/edit#gid=0");
   var sheet = ss.getSheetByName("gps");
  
 var id = sheet.getLastRow()+1;
  var usernames = request.parameter.usernames;
  var passewords = request.parameter.passewords;
  var codebarres = request.parameter.codebarres;
  
  var flag=1;
  var lr= sheet.getLastRow();
  for(var i=1;i<=lr;i++){
    var id1 = sheet.getRange(i, 2).getValue();
    if(id1==id){
      flag=0;
  var result="Id already exist..";
    } }
  //add new row with recieved parameter from client
  if(flag==1){
  var d = new Date();
   // var currentTime = d.toLocaleString();
  var rowData = sheet.appendRow([id,usernames,passewords,codebarres]);  
  var result="Insertion successful";
  }
     result = JSON.stringify({
    "result": result
  });  
    
  return ContentService
  .createTextOutput(request.parameter.callback + "(" + result + ")")
  .setMimeType(ContentService.MimeType.JAVASCRIPT);   
  }
 
