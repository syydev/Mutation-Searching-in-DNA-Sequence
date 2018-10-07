package Project;

import java.io.IOException;

public class Project {
    PhoneBook phoneBook;
    SearchAlgorithm algorithm;

    String read_file = "input3.txt";
    /*
     * Text File Size
     * input1.txt : 10,000
     * input2.txt : 100,000
     * input3.txt : 1,000,000
     * input4.txt : 10,000,000
     * input5.txt : 30,000,000
     * input6.txt : 60,000,000
     */
    int read_num = 100000;
    int read_length = 90;
    double mutation_rate = 0.01;

    Project() throws IOException {
        this.phoneBook = new PhoneBook(this);
        System.out.print("Mutation Num Before Search : ");
        System.out.println(checking(phoneBook.origin, phoneBook.my));
        this.algorithm = new SearchAlgorithm(this);
        System.out.print("Mutation Num After Search  : ");
        System.out.println(checking(phoneBook.origin, phoneBook.my));
    }

    int checking(StringBuilder reference, StringBuilder my) {
        int check = 0;
        for (int i = 0; i < reference.length(); i++)
            if (reference.charAt(i) != my.charAt(i))
                check++;
        return check;
    }
}
