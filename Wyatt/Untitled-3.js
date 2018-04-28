// in your html

<button click="tryToSubmit">Submit</button>

// in your js

func tryToSubmit() {
    // get all the data from text boxes

    var results = []
    for (var i = 0; i < numberOfRows; i++) {
        results +=
      { "sn": nameColumn[i],
        "ct": ctColumn[i],
        "treatment": treatmentColumn[i]
      }
      
      result = Result.new(nameColumn[i], ctColumn[i], DataTransferItem[i])
      result["sn"]
      console.log(result)

    Ajax.get('/api/calculate', JSON.stringify(results));
}
