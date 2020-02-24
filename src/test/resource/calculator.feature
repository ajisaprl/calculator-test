@calculator-functionality
Feature: Test online calculator scenarios

  @basic-operator
  Scenario Outline: Test basic operator and CE functionalities
    Given Open chrome browser and start application
    When I enter following values for "basic" operation
      |value1 | <value1>|
      |value2 | <value2>|
      |operator | <operator>|
    And I press "ENTER" button
    Then I should be able to see the right result
      |	expected |<expected>|
    When I press "C" button
    Then The result should reset
    And user close the browser
    Examples:
      | value1  		| value2 		| operator			| expected	|
      | 	20 			|   20			|		+			| 40        |
      | 	20 			|   20			|		-			| 0         |
      | 	20 			|   20			|		*			| 400       |
      | 	20 			|   20			|		/			| 1         |
      | 	20 			|   0.5			|		+			| 20.5      |
      | 	20 			|   0			|		/			| Error     |

    @advance-operator
    Scenario Outline: Test percentage and square root functionalities
      Given Open chrome browser and start application
      When I enter following values for "advance" operation
        |value1 | <value1>|
        |button | <button>|
      Then I should be able to see the right result
        |	expected |<expected>|
      And user close the browser
      Examples:
        | value1  		| button 		|     expected		|
        | 	10			|   %			|		0.1			|
        | 	36 			|   √			|		6			|

    @number-test
    Scenario Outline: Test all the number button and decimal
      Given Open chrome browser and start application
      When I enter following values for "numbers" operation
        |value1 | <value1>|
        |button | <button>|
      Then I should be able to see the right result
        |	expected |<expected>|
      And user close the browser
      Examples:
        |     value1    | button 	|    expected		|
        | 	123456789	|   =		|	123 456 789		|
        | 	1.5			|   =		|		1.5			|

  @delete-test
  Scenario Outline: Test delete number
    Given Open chrome browser and start application
    When I enter following values for "numbers" operation
      |value1 | <value1>|
      |button | <button>|
    And I press "DELETE" button
    Then I should be able to see the right result
      |	expected |<expected>|
    And user close the browser
    Examples:
      |     value1      | button 	|    expected		|
      | 	123   	    |   DELETE  |	    12      	|
