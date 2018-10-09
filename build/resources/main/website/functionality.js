/**
 * Return a JSON list of the selected ctr values to be sent to the backend.
 * Only has one key "name": String
 * Select is an HTML select element.
 * Partial credit to: 
 * https://stackoverflow.com/questions/5866169/how-to-get-all-selected-values-of-a-multiple-select-box
 */
function getCtrs(select) {
    var rows = convertPastedData();
    // var selectedCtrs = [];
    var options = select && select.options;
    var opt;
  
    for (var i=0, iLen=options.length; i<iLen; i++) {
      opt = options[i];
      if (opt.selected) {
          var ctr = {ctr: opt.text};
          rows.push(ctr);
          //result.push(opt.value || opt.text);
      }
    }
    console.log(rows);
    
    /**
     * request for secondClick. Receivs finalized json-data of the 
     * top level java class (GraphingDataAll) to parse and show the user
     */
    // console.log(JSON.stringify(rows));
    var request = new XMLHttpRequest();
    request.open('POST', '/secondClick');
    request.setRequestHeader('Content-Type', 'application/json');
    request.onload = function() {
        if (this.readyState == 4 && this.status == 200) {
            // Success
            // alert("secondClick sent!");
            var json = JSON.parse(this.response); // response should be the finalized data from secondClick
            console.log(json);
            // call JSON-format function of finalized data
            formatFinalizedData(json);

        }
        else {
            alert(this.response);
        }
    }
    request.onerror = function() {
        alert('Server is down.');
    };
    request.send(JSON.stringify(rows));

    return JSON.stringify(rows);
}

/**********************************************************************************************************/

/**
 * Returns a JSON String of the pasted excelData before firstClick
 */
function convertPastedData (){
    var txt = document.getElementById("data area").value;
    var lines = txt.split("\n");
    var rows = [];
    for (var index in lines) {
        var rowSplit = lines[index].split("\t");
        var sample = {SampleName: rowSplit[0], TargetName: rowSplit[1], CT: parseFloat(rowSplit[2])};
        rows.push(sample);
    }
    return rows;
}

/**********************************************************************************************************/

/**
 * firstClick functionality
 */
function test() {
    var rows = convertPastedData();
    /**
     * request for firstClick. Receives list of genes to select as controls
     */
    // console.log(JSON.stringify(rows));
    var request = new XMLHttpRequest();
    request.open('POST', '/firstClick');
    request.setRequestHeader('Content-Type', 'application/json');
    request.onload = function() {
        if (this.readyState == 4 && this.status == 200) {
            // Success
            // alert("firstClick sent!");
            var json = JSON.parse(this.response); // parse the json received from backend (in this case the gene list)
            console.log(json); // at this point, you have the gene list. put it in the homePage.html ctrForm for user selection
            createSelectInput(json); // add gene list to ctrForm for user selection
        }
        else {
            alert(this.response);
        }
    }
    request.onerror = function() {
        alert('Server is down.');
    };
    request.send(JSON.stringify(rows));
}

/**********************************************************************************************************/

/**
 * Alters the select multiple box to include the genes found in the pasted Excel data.
 * Mean to be used after firstClick.
 * @param geneList a parsed json gene list with the only key being "ctr"
 */
function createSelectInput(geneList) {
    for(var i = 0; i < geneList.length; i++) {
        var gene = geneList[i];
        console.log(gene.name);
        var selectMultiple = document.getElementById("ctrForm");
        var option = document.createElement("option");
        option.text = gene.name;
        selectMultiple.append(option);
        // selectMultiple.add(item.name);
    }
}

/**********************************************************************************************************/

/**
 * Process finalized JSON results and display them in the results textbox;
 * essentially a stringify function with proper spacing to paste back into Excel.
 * (provide copy-all button functionality)
 * @param jsonFinalData data returned after secondClick (json converted to array of packets)
 */
function formatFinalizedData(jsonFinalData) {
    var formattedString;
    for (var i = 0; i < jsonFinalData.length; i++) {
        var packetString = "\t" + jsonFinalData[i][1].gene + "\t\t" + jsonFinalData[i][0].ctr + "\n";
        packetString += "\tGOI avg Ct\tGOI stdev\tref avg Ct\trefstdev\t\t#VALUE!\tst err\t\t^^Ct\terr down\terr up\t\tRQ\terr down\terr up\n";
        for (var j = 2; j < jsonFinalData[i].length; j++) {
            packetString += jsonFinalData[i][j].sampleName + "\t" + jsonFinalData[i][j].goiAvgCt + "\t" + jsonFinalData[i][j].goiStDev + "\t" + jsonFinalData[i][j].refAvgCt + "\t" + jsonFinalData[i][j].refStDev + "\t\t" + jsonFinalData[i][j].value + "\t" + jsonFinalData[i][j].stErr + "\t\t" + jsonFinalData[i][j].ct + "\t" + jsonFinalData[i][j].ctErrDown + "\t" + jsonFinalData[i][j].ctErrUp + "\t\t" + jsonFinalData[i][j].rq + "\t" + jsonFinalData[i][j].rqErrDown + "\t" + jsonFinalData[i][j].rqErrUp + "\n";
        }
        console.log(packetString);
        formattedString += packetString + "\n";
    }
    document.getElementById('dataArea').value = formattedString;
}

/**********************************************************************************************************/

function copyText() {
    /* Get the text field */
    var copyText = document.getElementById('dataArea');

    /* Select the text field */
    copyText.select();

    /* Copy the text inside the text field */
    document.execCommand("copy");
}