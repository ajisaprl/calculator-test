# Automate Calculator Functionality
This automation script is using Cucumber Framework and Selenium WebDriver with Java implementation.
![alt text](https://i.postimg.cc/yNnNCrMk/Screen-Shot-2020-02-24-at-13-46-17.png "result")

## Target URL
https://www.online-calculator.com/full-screen-calculator/

## Interacting with element
Since the application embeded inside canvas, The interaction is performed by keyboard events from [Actions Class in Selenium](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/interactions/Actions.html)

## Assertion
We can trean canvas as an image. Thus, the assertion is conducted by:
1. taking screenshot of the result
2. Cropping the result image
3. Save it into local machine
4. Using [Tesseract](https://github.com/tesseract-ocr/tesseract) OCR (Optical character recognition) to recognize numbers in the image
5. Compare Expected result from .feature file to the recognized numbers.

## Tesseract Traineddata
For this case, we can use the provided [eng character traineddata](https://github.com/tesseract-ocr/tessdata/blob/master/eng.traineddata)
