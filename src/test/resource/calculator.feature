Feature: Test online calculator scenarios
  Scenario Outline: Test subtraction, division and CE functionalities
    Given Open chrome browser and start application
    When I enter following values and press enter button
      |value1 | <value1>|
      |value2 | <value2>|
      |operator | <operator>|
    Then I should be able to see
      |	expected |<expected>|
    Examples:
      | value1  		| value2 		| operator			| expected	|
      | 	6 			|   50			|		+			| 12        |