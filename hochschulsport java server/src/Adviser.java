/**
 * Adviser Class
 * 
 * The php client can call those methods
 * 
 */
public class Adviser {
	
	
	 public String[] getQuestion(int question_id, int previous_answer_id ) {
		 		 
		 String[] question;
		 
		 switch(question_id)
         {
           case 1:
        	   question = new String[3] ;
        	   question[0] = "Möchten Sie lieber Mannschaftssport oder Einzelsport machen?"; 
        	   question[1] = "Mannschaftssport"; 
        	   question[2] = "Einzelsport"; 
               break;
           case 2:
        	   switch(previous_answer_id) {
        	   
        	   case 1:
        		   question = new String[4] ;
                   question[0] = "Hier Steht: Frage nummer 2, wenn die Antwort 1 war"; 
                   question[1] = "Auswahl 1"; 
            	   question[2] = "Auswahl 2"; 
            	   question[3] = "Auswahl 3"; 
            	   break;

        	   case 2: 
        		   question = new String[6] ;
                   question[0] = "Hier Steht: Frage nummer 2, wenn die Antwort 2 war";
                   question[1] = "Auswahl 1"; 
            	   question[2] = "Auswahl 2"; 
            	   question[3] = "Auswahl 3"; 
            	   question[4] = "Auswahl 4"; 
            	   question[5] = "Auswahl 5"; 
            	   break;

               default:
            	   question = new String[4] ;
                   question[0] = "Eingabe ist falsch.";
        	   }
        	   break;
        	   
           case 3:
        	   question = new String[4] ;
               question[0] = "Frage 3"; break;
           case 4:
        	   question = new String[4] ;
               question[0] = "Frage 4"; break;
           case 5:
        	   question = new String[4] ;
               question[0] = "Frage 5"; break;
           default:
        	   question = new String[4] ;
               question[0] = "Eingabe ist falsch.";
           }
				 
		 return question;
	 }


	
}
