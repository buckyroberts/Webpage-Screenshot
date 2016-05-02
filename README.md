![](http://i.imgur.com/94hDwLg.jpg)

# Overview

Webpage Screenshot is a JavaFX program that lets you to enter the URL of a webpage and it will take a screenshot and
save it as a .png image.

## To build a .jar file

Follow these steps to build the .jar file in Intellij.

* File > Save All.
* Run driver or class with main method.
* File > Project Structure.
* Select Tab "Artifacts".
* Click green plus button near top of window.
* Select JAR from Add drop down menu. Select "From modules with dependencies"
* Select main class.
* The radio button should be selecting "extract to the target JAR." Press OK.
* Check the box "Build on make"
* Press apply and OK.
* From the main menu, select the build dropdown.
* Select the option build artifacts.

## Usage

To use from the command line, pass in the file name (for the output image) and URL as the first and second argument

```
java -jar Capture.jar homepage https://thenewboston.com/
```

Also, you must have a folder called "images" in the same directory where the Capture.jar file is saved. This is where
the final images will be saved.