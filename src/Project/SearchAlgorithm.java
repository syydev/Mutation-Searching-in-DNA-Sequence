package Project;

import java.util.Random;
import java.util.HashSet;

public class SearchAlgorithm {
    Project pj;
    HashSet<Integer> check_list = new HashSet<Integer>();
    int miss = 2;
    int m;

    SearchAlgorithm(Project pj) {
        this.pj = pj;
        this.m = pj.read_length;
        long start, end;

        start = System.currentTimeMillis();
        for (int itr = 0; itr < pj.read_num; itr++) {
            StringBuilder[] p = makePattern();
            int mismatch;
            long hash = 0;

            // case 0
            for (int i = 0; i < m / 3; i++) {
                hash *= 4;
                hash += toQuadratic(p[0].charAt(i));
            }
            if (pj.phoneBook.pb.containsKey(hash)) {
                for (int j = 0; j < pj.phoneBook.pb.get(hash).size(); j++) {
                    int index = this.pj.phoneBook.pb.get(hash).get(j);
                    if (check_list.contains(index) || index >= pj.phoneBook.my.length() - m)
                        continue;
                    mismatch = 0;
                    mismatch = brutesearch(p[1], index + m / 3, mismatch);
                    if (mismatch > miss)
                        continue;
                    mismatch = brutesearch(p[2], index + m * 2 / 3, mismatch);
                    if (mismatch > miss)
                        continue;
                    int k = index;
                    for (int i = k; i < k + m; i++)
                        this.pj.phoneBook.my.setCharAt(i, this.pj.phoneBook.origin.charAt(i));
                    check_list.add(index);
                    break;
                }
            }

            // case 1
            hash = 0;
            for (int i = 0; i < m / 3; i++) {
                hash *= 4;
                hash += toQuadratic(p[1].charAt(i));
            }
            if (pj.phoneBook.pb.containsKey(hash)) {
                for (int j = 0; j < pj.phoneBook.pb.get(hash).size(); j++) {
                    int index = this.pj.phoneBook.pb.get(hash).get(j);
                    if (check_list.contains(index) || index >= pj.phoneBook.my.length() - m * 2 / 3 || index <= m / 3)
                        continue;
                    mismatch = 0;
                    mismatch = brutesearch(p[0], index - m / 3, mismatch);
                    if (mismatch > miss)
                        continue;
                    mismatch = brutesearch(p[2], index + m / 3, mismatch);
                    if (mismatch > miss)
                        continue;
                    int k = (index - m / 3);
                    for (int i = k; i < k + m; i++)
                        this.pj.phoneBook.my.setCharAt(i, this.pj.phoneBook.origin.charAt(i));
                    check_list.add(index);
                    break;
                }
            }

            // case 2
            hash = 0;
            for (int i = 0; i < m / 3; i++) {
                hash *= 4;
                hash += toQuadratic(p[2].charAt(i));
            }
            if (pj.phoneBook.pb.containsKey(hash)) {
                for (int j = 0; j < pj.phoneBook.pb.get(hash).size(); j++) {
                    int index = this.pj.phoneBook.pb.get(hash).get(j);
                    if (check_list.contains(index) || index >= pj.phoneBook.my.length() - m / 3 || index <= m * 2 / 3)
                        continue;
                    mismatch = 0;
                    mismatch = brutesearch(p[0], index - m * 2 / 3, mismatch);
                    if (mismatch > miss)
                        continue;
                    mismatch = brutesearch(p[1], index - m / 3, mismatch);
                    if (mismatch > miss)
                        continue;
                    int k = (index - m * 2 / 3);
                    for (int i = k; i < k + m; i++)
                        this.pj.phoneBook.my.setCharAt(i, this.pj.phoneBook.origin.charAt(i));
                    check_list.add(index);
                    break;
                }
            }

        }
        /* Brute Force Algorithm
        for (int itr = 0; itr < pj.read_num; itr++) {
            StringBuilder p = new StringBuilder();
            Random rand = new Random();
            int r = rand.nextInt(pj.phoneBook.my.length() - m);
            p.append(pj.phoneBook.my.substring(r, r + m));
            int m = p.length();
            for(int i = 0 ; i < pj.phoneBook.origin.length() - m; i++) {
                int mismatch = 0;
                for (int j = 0; j < m; j++) {
                    if (pj.phoneBook.origin.charAt(i + j) != p.charAt(j)) {
                        mismatch++;
                        if (mismatch > miss) break;
                    }
                    if(j == m - 1)
                        for (int k = i; k < i + m; k++)
                            this.pj.phoneBook.my.setCharAt(k, this.pj.phoneBook.origin.charAt(k));
                }
            }
        }*/
        end = System.currentTimeMillis();
        System.out.println("Running Time : " + (end - start) / 1000.0 + " second");
    }

    StringBuilder[] makePattern() {
        StringBuilder p = new StringBuilder();
        Random rand = new Random();
        int i = rand.nextInt(pj.phoneBook.my.length() - m);
        p.append(pj.phoneBook.my.substring(i, i + m));
        StringBuilder[] str = new StringBuilder[3];
        for (int j = 0; j < 3; j++) {
            str[j] = new StringBuilder();
            str[j].append(p.substring(j * m / 3, j * m / 3 + m / 3));
        }
        return str;
    }

    long toQuadratic(char c) {
        if (c == 'A') return 0;
        else if (c == 'C') return 1;
        else if (c == 'G') return 2;
        else if (c == 'T') return 3;

        return -1;
    }

    int brutesearch(StringBuilder p, int a, int mismatch) { // p : Pattern String, a : Text String
        int m = p.length();
        for (int i = 0; i < m; i++) {
            if (pj.phoneBook.origin.charAt(i + a) != p.charAt(i)) {
                mismatch++;
                if (mismatch > miss) return mismatch;
            }
        }
        return mismatch;
    }

}
