
Server -|                                                                           |- Respond by convert json
        |- JSON -> ArrayList<Results> -> ArrayList<Triplet> -> ArrayList<X, Y> ->   |
CLI ----|                                                                           |- Print each one (sorted)

class Result {
    public bool isPartOfSameTriplet(Result other) {
        return result.sample == other.sample && result.target == other.target;
    }
}

results;

while (!results.empty()) {
    result = results.shift();

    tmpArray
    for(other in results) {
        if (result.isPartOfSameTriplet(other)) {
            results.remove(other)
            tmpArray.add(other)
        }
    }

    // verify tmpARray has length == 2
    triplets.add(new Triplet(result, tmpArray[0], tmpArray[1]));
}

"FALSE\t1 shCTR\tB-actin\nFALSE..."