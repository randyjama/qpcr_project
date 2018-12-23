# qpcr_project
A tool to automatically analyze qPCR data.

How To Run:
-In /src/qpcr_project type "gradle run" to launch the server
-The project is listening on port 4567
-In the browser, go to "http://localhost:4567/homePage.html"

How It Works:
This project is based on my experience from working as a research
assistant at the Vancouver Prostate Centre. More detailed instructions
will come later, but for now: 

-copy and paste the 3 columns in the Excel spreadsheet qPCR results titled 
	-Sample Name
	-Target Name
	-CT
(do not include the titles themselves, only the values)
-next, click convert data and select the controls and hit analyze
-the analyzed data now appears in the text box
-click the copy button and paste it into an excel sheet
-Voila! Freshly analyzed qPCR results ready to graph