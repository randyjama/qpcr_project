
function test() {
    var txt = document.getElementById("data area").value;
    // var txtString = JSON.stringify(txt);
    // console.log(JSON.stringify(txt));
    var lines = txt.split("\n");
    // console.log(lines[4]);
    // console.log(lines);
    var rows = [];
    for (var index in lines) {
        var rowSplit = lines[index].split("\t");
        var sample = {SampleName: rowSplit[0], TargetName: rowSplit[1], CT: parseFloat(rowSplit[2])};
        // console.log(rowSplit);
        rows.push(sample);
    }
    console.log(JSON.stringify(rows));
    var request = new XMLHttpRequest();
    request.open('POST', '/process');
    request.setRequestHeader('Content-Type', 'application/json');

    request.onload = function() {
        if (this.status == 200) {
            // Success
            alert("Data Received!");
            var json = JSON.parse(this.response);
            console.log(json);
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