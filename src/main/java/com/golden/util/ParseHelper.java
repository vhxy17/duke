package com.golden.util;

import com.golden.exceptions.BotException;
import com.golden.exceptions.ParseException;
import com.golden.exceptions.parseErrors.IllegalArgumentException;
import com.golden.exceptions.parseErrors.MissingArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseHelper {
    private static final Pattern WS = Pattern.compile("\\s+");
    // Finds the *first* " /<non-space>" boundary
    private static final Pattern FIRST_SLASH_SECTION = Pattern.compile("\\s/(?=\\S)");
    // Splits remaining text *at* every " /<non-space>" boundary, *keeping* the slash argument
    private static final Pattern NEXT_SLASH_SECTIONS  = Pattern.compile("(?=\\s/\\S)");

    private ParseHelper(){}

    public static void requireArgs(String[] parts, int n, String what) throws ParseException {
        if (parts == null) {
            throw new ParseException(String.format("%s expected (no arguments provided).", what));
        }
        if (parts.length > n){
            throw new ParseException(String.format("too many arguments. Only %d argument(s) expected. %s", n, what));
        }
        if (parts.length < n) {
            throw new MissingArgumentException( what + " is missing!!");
        }
    }

    /** Split on any whitespace (spaces, tabs, newlines), collapsing runs. */
    public static String[] splitOnWhitespaces(String input) throws MissingArgumentException {
        if (input == null || input.isBlank()){
            throw new MissingArgumentException("Expected some input!");
        }
        return WS.split(input.trim());
    }

    /**
     * Split input into:
     *  [0]: leading text before any "/section"
     *  [1..]: each "/section" including the leading slash, e.g. "/from monday"
     *
     * If no "/section" is found, returns a single-element array [trimmedInput].
     */
    public static String[] splitOnSlashSections(String input) throws MissingArgumentException {
        if (input == null) {
            throw new MissingArgumentException("Expected some input!");
        }
        String s = input.trim();
        if (s.isEmpty()){
            throw new MissingArgumentException("Expected some input!");
        }

        Matcher m = FIRST_SLASH_SECTION.matcher(s);
        if (!m.find()) {
            // no "/section" found → whole string is the head
            return new String[]{s};
        }

        // head is everything before the first " /<non-space>"
        String head = s.substring(0, m.start()).trim();
        // tail starts AT the slash (drop the preceding space, keep the slash)
        String tail = s.substring(m.start() + 1); // +1 skips the space before '/'

        // Now split tail into sections at each " /<non-space>" boundary (lookahead keeps the slash)
        String[] rawSections = NEXT_SLASH_SECTIONS.split(tail);

        // Clean up: ensure each section starts with "/", trim trailing spaces
        List<String> out = new ArrayList<>(1 + rawSections.length);
        if (!head.isEmpty()) out.add(head);

        for (String sec : rawSections) {
            String t = sec.trim();
            if (t.isEmpty()) continue;
            // Guarantee a leading slash
            if (t.charAt(0) != '/') t = "/" + t.replaceFirst("^\\s*/?", "");
            out.add(t);
        }

        return out.toArray(new String[0]);
    }
}
