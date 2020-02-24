@calculator-functionality
Feature: Test online calculator scenarios

  @basic-operator
  Scenario Outline: Test subtraction, division and CE functionalities
    Given Open chrome browser and start application
    When I enter following values for "basic" operation
      |value1 | <value1>|
      |value2 | <value2>|
      |operator | <operator>|
    And I press "ENTER" button
    Then I should be able to see the right result
      |	expected |<expected>|
    When I press "CE" button
    Then The result should reset
    And user close the browser
    Examples:
      | value1  		| value2 		| operator			| expected	|
      | 	20 			|   20			|		+			| 40        |
      | 	20 			|   20			|		-			| 0         |
      | 	20 			|   20			|		*			| 400       |
      | 	20 			|   20			|		/			| 1         |

    @advance-operator
    Scenario Outline: Test subtraction, division and CE functionalities
      Given Open chrome browser and start application
      When I enter following values for "advance" operation
        |value1 | <value1>|
        |button | <button>|
      Then I should be able to see the right result
        |	expected |<expected>|
      And user close the browser
      Examples:
        | value1  		| button 		| expected			|
        | 	10			|   %			|		0.1			|
        | 	36 			|   √			|		6			|
