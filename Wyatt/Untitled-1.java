
class Result {
    private String name;
    private String ct;
    private String sampleName;
    private String targetName;

    public void setName(String newName) {
        if (name == NULL || name == "") {
          throw RuntimeException("name must not be empty")
        }
        name = newName;
    }

    public String getName() {
        return name;
    }
}

class ExperimentUnderstander {
    public ArrayList<Result> parseResults(nameFile, ctFile, sampleFile, targetFile) {
        // do file reading stuff
        while (nameFile.hasNext()) {
            String nameFromCurrentLin = nameFile.getNextLine();
            if (!ctFile.hasNext()) {
                throw something;
            }
            String ctFromCurrentLin = ctFile.getNextLine();

            if (!sampleFile.hasNext()) {
                throw somethingElse;
            }
            STring sampleNameFromCurrentLine = sampleFile.getNextLine();

            results.add(
                new Result(nameFromCurrentLin, ctFromCurrentLin, sampleNameFromCurrentLine, targetNameFromCurrentLine)
            )
        }

        // decide what to do, if ctFile or samplefile still have data???
    }

    public void static generateGraphs() {
        ArrayList<Result> results = Something.parseResults(filename);
        for(result in results) {
            do_something_with_result(result);

        }

        ArrayList<ArrayList<String>> results = blah();
        for (result in results) {
            if (result[0] == "autism shot") { 
                continue; // ignore these rows, because anti-vax is bs
            }

            // really handle real results
        }

        return Json.generate(dataPoints);
    }
}
