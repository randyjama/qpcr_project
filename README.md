# qpcr_project
A tool to automatically analyze qPCR data.

www.quantifypcr.com

Motivations:
This project is based on my experience from working as a research
assistant at the Vancouver Prostate Centre. I set up and ran countless
qPCR gene amplification tests, all of which needed to be analyzed by hand
using Excel to eliminate outliers within each sample set. This process 
took me at least an hour to do properly. With quantifypcr.com this process
can be done in seconds with a few button clicks.

How To Run Locally:
-Be sure to have gradle installed into your system first
-In /src/qpcr_project type "gradle run" to launch the server
-The project is listening on port 80
-In the browser, simply type in "localhost" to automatically connect
to port 80 (the default http port)

How It Works:
-copy and paste the 3 columns in the Excel spreadsheet qPCR results titled 
	-Sample Name
	-Target Name
	-CT
(do not include the titles themselves, only the values)
-next, click convert data and select the controls and hit analyze
-the analyzed data now appears in the text box
-click the copy button and paste it into an excel sheet
-Voila! Freshly analyzed qPCR results ready to graph
