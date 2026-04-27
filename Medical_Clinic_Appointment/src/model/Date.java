package model;

import java.util.Scanner;

public class Date {
    private int day;
    private int month;
    private int year;

    // Traduction de lireDate -> readDate
    public void readDate(Scanner sc) {
        System.out.println("--- Enter Date Details ---");
        System.out.print("Day: ");
        this.day = sc.nextInt();
        System.out.print("Month: ");
        this.month = sc.nextInt();
        System.out.print("Year: ");
        this.year = sc.nextInt();
        sc.nextLine(); // Clear the buffer
    }
}
