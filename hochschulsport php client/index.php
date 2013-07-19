<html>
<head> 
<h1>Hochschulsport</h1>
</head>

<hr style="color: red; background: blue; height: 15px;" /></br></br>

<body>

  <?php
		include '/lib/xmlrpc.inc';
		
		// Get the id of the current question and set the next question id
		$question_id;
		if(!empty($_GET['question_id'])) {
			// Set the next Question
			$question_id = $_GET['question_id'] + 1;
		} else {
			// First Question ID
			$question_id = 1;
		}

		// Make an object to represent our server.
		$server = new xmlrpc_client("http://localhost:8080");

		// Send a message to the server.
		$question_id_value = new xmlrpcval($question_id , 'int');  // Question ID as an xmlrpc value object
		
		// Get the answer of the previous question.
		$previous_answer_id_value;
		if(!empty($_GET['previous_answer'])) {
			// Set the next Question
			$previous_answer_id_value = new xmlrpcval($_GET['previous_answer'], 'int');
		} else {
			//There is no previous answer
			$previous_answer_id_value = new xmlrpcval(0, 'int'); 
		}
		
		
		$message = new xmlrpcmsg('Adviser.getQuestion', array($question_id_value, $previous_answer_id_value));
		$result = $server->send($message);

		// Process the response.
		if (!$result) {
			print "<p>Could not connect to server.</p>";
		} elseif ($result->faultCode()) {
			print "<p>XML-RPC Fault #" . $result->faultCode() . ": " .
				$result->faultString();
		} else {	
			
			// Get the First Question. The Question has the index 0 in the array
			$reply_array = $result->value();
			$question = "<p>".$reply_array->arraymem(0)->serialize()."</p>"; // The question has the index 0 in the array

			// Create the radio Buttons
			$radio  = "<form action='index.php'>";
			$radio .= "<p>";
			$reply_array_size = $reply_array->arraysize();
			for ($i=1; $i < $reply_array_size; $i++){
				$reply_array_value = $reply_array->arraymem($i);
				$radio_value = $reply_array_value->serialize();
				$radio .= "<input type='radio' name='previous_answer' value='$i'> $radio_value<br>";
			}
			$radio .= "<input type='hidden' name='question_id' value=$question_id>";
			$radio .= "</p>";
			$radio .= "<input type='submit' value='Weiter'>";
			$radio .= "</form>";

			echo $question;
			echo $radio;
		}
  		
		/** SECOND QUESTION **/

  
  ?>

</body>
</html>