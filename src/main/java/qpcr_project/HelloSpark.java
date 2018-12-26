package qpcr_project;
import static spark.Spark.*;

import org.json.JSONException;
import org.json.JSONObject;

import qpcr_project.*;
import spark.Request;
import spark.Response;
import spark.ModelAndView;
import java.util.HashMap;


public class HelloSpark {
	public static void main(String[] args) {
		port(80);
		staticFileLocation("/website");
		//get("/", (req, res) -> "/website/homePage.html");
		get("/", (req, res) -> {
			res.redirect("homePage.html"); return null;
		});
		// get("/", (req, res) -> new ModelAndView(new HashMap(), "../website/homePage.html"), new ThymeleafTemplateEngine());
		post("/firstClick", (req, res) -> processData(req, res)); // initial request from user to return ctr selection choices
		post("/secondClick", (req, res) -> packageData(req, res)); // second request from user after ctr selection to return processed data (as json)
		// js is downloaded from the website and run locally on browser
		// take final data structure from "chain" and turn it into json to send to front end
	}

	// lookup redirect so that "/" can redirect to homepage
	public static Object processData(Request req, Response res) {
		System.out.println("\n\n firstClick Request");
		System.out.println(req.body());
		String json = req.body();
		try {
			Datasheet datasheet = new Datasheet(json);
			return datasheet.getAllGenes(); // return list of genes to choose the ctr(s)
		} catch (JSONException e) { // instead give it my special exception type I made in a new class that inherits exception
			//TODO: handle exception
			System.out.println(e.getMessage());
			res.status(404);
			res.body(e.getMessage());
			return res.body();
		}
	}

	public static Object packageData(Request req, Response res) {
		System.out.println("\n\n secondClick Request");
		System.out.println(req.body());
		String jsonExcel = req.body();
		try {
			GraphingDataAll processedData = new GraphingDataAll(jsonExcel);
			return processedData.stringifyAllData();
		} catch (JSONException e) {
			System.out.println(e.getMessage());
			res.status(404);
			res.body(e.getMessage());
			return res.body();
		}
	}
}