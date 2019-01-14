# NLP_Audiology.src.AudiologyNLP

Junior Design project for the Centers for Disease Control and Prevention

Release Notes v0.1

New Features
Upload PNG files to be analyzed and have key audiology features extracted.
Scanned results from uploaded PNG files can be edited and saved.
Final extracted features exported as csv format. 

Bug Fixes
None. Initial release.

Known Bugs and Defects
Required conversion from PDF to PNG prior to scanning.
Need better models for extracting medical history, test results and diagnosis.
Proper database implementation required.
Registration for new patient required.
Requires very clear medical record for an input.
Stored extracted features need to be accessible and displayable within the application.



Install Guide  Audiology NLP v0.1

PRE-REQUISITES: 
You must have JDK 10 installed and configured for our java application.See: https://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html for details.

DEPENDENCIES:
Download and install Tesseract. See: https://github.com/tesseract-ocr/tesseract/wiki 
Download and install IntelliJ IDEA on your system. See: https://www.jetbrains.com/idea/  

DOWNLOAD:
Download Audiology NLP project from our github link: https://github.com/ezhao7/AudiologyNLP.git 

BUILD:
Copy the AudiologyNLP directory (downloaded in previous step) to any location you desire, start IntelliJ and choose import project option. Select AudiologyNLP and keep clicking next till the project is created in IntelliJ IDEA. 
Once you are in IntelliJ with the project created, click Build -> Make Project from the main menu.

RUNNING APPLICATION:
When running this project for the first time in IntelliJ, find App.java under src/main/java.com, right click App and select Run ‘App.main()’.
If you’ve run the project in the past, simply click Run from the main menu to restart the application.
                

