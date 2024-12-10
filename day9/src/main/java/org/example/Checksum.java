package org.example;

public class Checksum {
    public static int checksum(String input) {
        // "0099811188827773336446555566.............."
        var charArray = input.toCharArray();
        var sum = 0;
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            if (ch >= '0' && ch <= '9') {
                var in = Integer.parseInt(String.valueOf(ch));
                sum += in * i;
            } else {
                continue;
            }

        }
        return sum;
    }
}
