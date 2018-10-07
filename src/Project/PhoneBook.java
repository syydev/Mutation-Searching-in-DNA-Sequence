package Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.nio.*;
import java.nio.file.*;
import java.nio.channels.*;

public class PhoneBook {
    Project pj;
    int buf = 1000; // read buffer
    StringBuilder origin = new StringBuilder(); // original genome
    StringBuilder my = new StringBuilder(); // my genome
    HashMap<Long, ArrayList<Integer>> pb = new HashMap<>(); // phone book
    ArrayList<Long> q = new ArrayList<>(10);
    int m;

    PhoneBook(Project pj) throws IOException {
        this.pj = pj;
        this.m = pj.read_length;
        this.readText(pj.read_file);
        this.makeMutation();
    }

    void readText(String url) throws IOException {
        Path path = Paths.get(url);
        FileChannel channel = FileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(buf);
        int index = 0;
        long hash = 0, quadra = 1;
        for (int i = 1; i < m / 3; i++)
            quadra *= 4;
        char c;

        while (channel.read(byteBuffer) > 0) {
            byteBuffer.flip();
            for (int i = 0; i < byteBuffer.limit(); i++) {
                c = (char) byteBuffer.get(i);
                origin.append(c);
                my.append(c);
                hash *= 4;
                hash += toQuadratic(c);
                q.add(toQuadratic(c));
                if (q.size() >= m / 3 && c >= 0) {
                    if (!pb.containsKey(hash))
                        pb.put(hash, new ArrayList<Integer>());
                    pb.get(hash).add(index);
                    hash -= q.get(0) * quadra;
                    q.remove(0);
                    index++;
                }
                if (index != 0 && index % 1000000 == 0)
                    System.out.println(index);
            }
            byteBuffer.clear();
        }
        System.out.println("Read Text length : " + (index - 1 + m / 3));
        System.out.println("Make Phone Book Complete!");
    }

    void makeMutation() {
        Random rand = new Random();
        for (int i = 0; i < origin.length() * pj.mutation_rate;) {
            int c = rand.nextInt(4), d = rand.nextInt(origin.length());
            char chr = my.charAt(d);
            if (c == 0 && chr != 'A'){
                my.setCharAt(d, 'A');
                i++;
            }
            else if (c == 1 && chr != 'C'){
                my.setCharAt(d, 'C');
                i++;
            }
            else if (c == 2 && chr != 'G') {
                my.setCharAt(d, 'G');
                i++;
            }
            else if (c == 3 && chr != 'T') {
                my.setCharAt(d, 'T');
                i++;
            }
        }
        System.out.println("Make Mutation Complete!");
    }

    long toQuadratic(char c) {
        if (c == 'A') return 0;
        else if (c == 'C') return 1;
        else if (c == 'G') return 2;
        else if (c == 'T') return 3;

        return -1;
    }
}
