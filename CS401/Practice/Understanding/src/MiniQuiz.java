/** Demonstrates the use of a class that implements an interface. */

import java.util.Scanner;

public class MiniQuiz {
	public static void main(String[] args) {
		Question q1, q2;
		String possible;
		
		Scanner kb = new Scanner(System.in);
		
		q1 = new Question("What is the capital of Pennsylvania?",
				"Harrisburg");
		q1.setComplexity(4);
		
		q2 = new Question("What is the capital of Ohio?",
				"Columbus");
		q2.setComplexity(5);

		System.out.print(q1.getQuestion());
		
		System.out.println(" (Level: "+q1.getComplexity()+")");
		
		possible = kb.nextLine();
		
		if (q1.correctAnswer(possible))
			System.out.println("Correct");
		
		else System.out.println("Wrong, the correct answer is"
				+ q1.getAnswer());

		System.out.print(q2.getQuestion());
		
		System.out.println(" (Level: "+q2.getComplexity()+")");
		
		possible = kb.nextLine();
		
		if (q2.correctAnswer(possible))
			System.out.println("Correct");
		else System.out.println("Wrong, the correct answer is"
				+ q2.getAnswer());
	}
}