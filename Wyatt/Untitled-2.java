import static spark.Spark.*;

public class HelloWorld {
    public static void main(String[] args) {
        get("/", (req, res) -> serverHtml(req, res));
        get("/style.css", (req, res) -> serveCss(req, res));

        get("/api/calculate", (req, res) -> ExperientUnderstander.generateGraphs())
    }
}