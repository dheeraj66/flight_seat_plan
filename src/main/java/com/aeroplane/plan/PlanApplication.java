package com.aeroplane.plan;


import java.util.Scanner;

public class PlanApplication {

	public static void main(String[] args) {
			int m, n, i, j, totalPassengersCount;
			Scanner sc=new Scanner(System.in);
			System.out.println("Enter the number of passengers in the queue: ");
			totalPassengersCount = sc.nextInt();
			System.out.println("Enter the number of rows of the Seat Plan Meta Data: ");
			m = sc.nextInt();
			System.out.println("Enter the number of columns of the Seat Plan Meta Data: ");
			n = sc.nextInt();
			int array[][] = new int[m][n];
			System.out.println("Enter the elements of the Seat Plan Meta Data Array: ");
			for (i = 0; i < m; i++)
				for (j = 0; j < n; j++)
					array[i][j] = sc.nextInt();
			SeatPlanGenerator seatPlanGenerator =new SeatPlanGenerator();
			seatPlanGenerator.generateSeatPlan(array,totalPassengersCount);
	}



}
