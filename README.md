# UI-Logger
Basic module to capture all UI interactions

It is often needed to follow what user does when using the application.

1. In order to follow user decisions 
2. In order to trace back errors occured in the application

This project includes an application and a module. The module captures all touch events generated on the main window and delivers them to the listener registered in the creation of the UILogger class. 

In order to use the module it is needed to create a new instance of UILogger passing the main activity.

