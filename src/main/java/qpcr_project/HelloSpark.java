import static spark.Spark.*;

import org.json.JSONException;
import qpcr_project.*;
import spark.Request;
import spark.Response;

public class HelloSpark {
	public static void main(String[] args) {
		staticFileLocation("/website");
		get("/", (req, res) -> "Hello User!");
		post("/process", (req, res) -> processDatasheet(req, res));
		// js is downloaded from the website and run locally on browser
		// take final data structure from "chain" and turn it into json to send to front end
	}

	// lookup redirect so that "/" can redirect to homepage
	public static Object processDatasheet(Request req, Response res) {
		System.out.println("\n\n New Request");
		System.out.println(req.body());
		String json = req.body();
		try {
			Datasheet datasheet = new Datasheet(json);
			return "{\"Randy\": 26}"; // END RESULT HERE FROM PROCESSING DATA
		} catch (JSONException e) { // instead give it my special exception type I made in a new class that inherits exception
			//TODO: handle exception
			System.out.println(e.getMessage());
			res.status(404);
			res.body(e.getMessage());
			return res.body();
		}
		// Datasheet jsonString = new Datasheet(json);
	}
}
