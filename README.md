# TAFE-Traffic-Monitor
### Semester 2 - ICTPRG523 Apply advanced programming skills in another language

## Instructions

You have been asked by the council of I.T. Town to build a working manual data-entry prototype of a network-based Traffic Monitoring Application.  Your prototype will provide the framework for a more automated solution.  A screen design prototype has been prepared and presented below and on the following pages.

## Requirements

* Monitoring Station
  * Connect to the Monitoring Server over a network.
  * Emulate actions of moniroting station, including:
    * Count nummber of vehicles passing since last reading
    * Calculate the average number of vehicles per lane
    * Determine the average speed of vehicles
  * Transmit the readings to the Monotoring Server using a [Submit] button
  * At least 2 versions of the monitoring station needs to be run simultainously od different networked computers, connected to the same Monotoring Server
* Monitoring Server
  * Make connections to multipel instances of Monitoring Stations
  * Display notificaitons of recieving readings from the stations
  * Display and update a table of readings
  * Sort the readings shown in the table in the following ways:
    * By Location, then by Time
    * By Average number of vehicles
    * By Average Velocity, then by Time
  * Each sort operatino is to be carried out using a differetn sort algorithm
  * Add readings into a DoubleLinked List (Coded, not inbuilt java), and display list.
  * Add readings into a Binary Tree (Coded, not inbuilt Java)
  * Allow display and saving of the elements in the Binary Tree:
    * In In-Order Traversal
    * In Pre-Order Traversal
    * In Post-Order Traversal
* Optional Extension
  * Display the Binary Tree using 2D graphics.
  
## Sample Reading Data

ID|Reading Time|Location ID|Number of Lanes|Total Number Of Vehicles|Average Number of Vehicles Per Lane
-|-|-|-|-|-
1|6:00:00 AM|1|3|27|9
2|6:00:00 AM|2|2|16|8
3|7:00:00 AM|1|3|30|10
4|7:00:00 AM|2|2|20|10
5|8:00:00 AM|1|3|36|12
6|8:00:00 AM|2|2|22|11
7|9:00:00 AM|1|3|33|11
8|9:00:00 AM|2|2|18|9
9|10:00:00 AM|1|3|24|8
10|10:00:00 AM|2|2|14|7

## Programmign Oblications
In relation to this project, you are required to implement and demonstrate clear competence in the following aspects of programming:

1.	Design and build the application based on the specifications on the previous pages with appropriate low coupling and high cohesion, and object-oriented programming techniques wherever possible.  Outline / present a procedure for the development of your application.  (Note that this procedure needs to account for this being developed in and for a GUI environment.). Includes testing and debugging issues.

2.	Appropriate design  and application of the following data structures:
	*	Arrays of objects
	*	Double Linked Lists of objects
	*	Binary Trees of objects
	*	Hash table
	*	…plus a search facility for each.
Includes testing and debugging issues.	(Elements 1, 2, 4, 5, 6, 7 & 8)

3.	Appropriate implementation and application of three (3) common sort algorithms.
	(Elements 2, 5, 6, 7 & 8)

4.	Appropriate implementation and application of program communication:
	*	inter-process communication through at least one mechanism.
	*	operating system 'signals' to be captured and responded to.	(Elements 3, 4, 6, 7 & 8)

5.	Utilise a third-party library in the construction of your application indicating how and where you have used the library, and how and where you have referenced the third-party library’s documentation. For instance, you might locate and utilise a third party library that:
	*	graphs the matching of the associated pairs, or:
	*	assists with the graphical presentation or analysis of your doubly linked list, binary tree and/or hashing algorithm. 
Includes testing and debugging issues.

6.	Clear evidence of appropriately planned and structured testing and debugging.	(Element 7)

7.	Appropriate internal and external technical documentation: 	(Element 6)
	*	add code headers presenting section headings, author, version control, etc.
	*	add useful comments throughout your code that are likely to prompt others as to the purpose and focus of each code segment and/or to remind you of your current thinking / logic at a later time.
	*	generate applicable external documentation such as class diagrams and programming documentation
	*	outline in detail and provide evidence of how you have managed version control throughout the development of your project solution.

8.	Maintain appropriate ongoing communication with your manager and client (including emails) to ensure you stay on track and receive and action client and manager feedback. The technical jargon and detail within your emails needs to be appropriate to your recipients. Specifically, you would be required to:
	1.	Email your manager before the commencement of this project to secure the project specification.
	1.	Prepare an email to your manager and then (separately) to your client regarding your proposed third party library, notifying them about the associated licence agreement.  The client will need to understand the benefit(s) of the inclusion of this library in their project.
	1.	Prepare a closing email to your manager and then (separately) to your client regarding the success of your project development endeavours.  You should suggest any areas of concern or risk to your manager for inclusion in the development team’s risk matrix.
