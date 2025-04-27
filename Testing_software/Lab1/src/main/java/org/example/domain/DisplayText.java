package org.example.domain;

public class DisplayText {
    private final char[] content;

    private DisplayText(char[] content) {
        this.content = content;
    }

    public static DisplayText fromParts(Object... parts) {
        int totalLength = 0;
        for (Object part : parts) {
            totalLength += part.toString().length();
        }

        char[] result = new char[totalLength];
        int position = 0;

        for (Object part : parts) {
            char[] partChars = part.toString().toCharArray();
            System.arraycopy(partChars, 0, result, position, partChars.length);
            position += partChars.length;
        }

        return new DisplayText(result);
    }

    public boolean contains(Object part) {
        char[] partChars = part.toString().toCharArray();
        outer: for (int i = 0; i <= content.length - partChars.length; i++) {
            for (int j = 0; j < partChars.length; j++) {
                if (content[i + j] != partChars[j]) {
                    continue outer;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return new String(content);
    }

}
