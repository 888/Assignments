import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class GradeHistogram {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        int interval;
        try {
            interval = Integer.parseInt(args[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Enter interval size:");
            interval = input.nextInt();
        }
        int buckets = 101 / interval + 1;
        int[] scores = new int[buckets];
        int percent;
        int index;
        int display = 100;
        int count = 0;
        File file = new File(args[0]);
        Scanner grades = new Scanner(file);
        while (grades.hasNextLine()) {
            String line = grades.nextLine();
            String[] breaks = line.split(",");
            String studentscore  = breaks[1];
            percent = Integer.parseInt(studentscore.trim());
            index = (100 - percent) / interval;
            scores[index]++;
        } int counter1 = 0;
        int counter2 = 0;
        for ( ; counter1 < scores.length; counter1++) {
            if (display >= interval) {
                if (display > 99 && display - interval + 1 > 9) {
                    System.out.printf("%d - %d | ",
                        display, display - interval + 1);
                } else if (display > 99 && display - interval + 1 <= 9) {
                    System.out.printf("%d -  %d | ",
                        display, display - interval + 1);
                } else if (display > 9 && display - interval + 1 > 9) {
                    System.out.printf(" %d - %d | ", display,
                        display - interval + 1);
                } else if (display > 9 && display - interval + 1 <= 9) {
                    System.out.printf(" %d -  %d | ",
                        display, display - interval + 1);
                } else if (display <= 9 && display - interval + 1
                    <= 9 && display != 0) {
                    System.out.printf("  %d -  %d | ",
                        display, display - interval + 1);
                }
            } else {
                if (display > 9 && display - interval <= 9) {
                    System.out.printf(" %d -  0 | ", display);
                } else if (display > 99 && display - interval <= 9) {
                    System.out.printf("%d -  0 | ", display);
                } else {
                    System.out.printf("  %d -  0 | ", display);
                }
            }
            for ( ; counter2 < scores[counter1]; counter2++) {
                System.out.print("[]");
            } System.out.print("\n");
            counter2 = 0;
            display = display - interval;
        }
    }
}