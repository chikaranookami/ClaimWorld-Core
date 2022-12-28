package claimworld.net.supporter.utils;

import java.util.ArrayList;
import java.util.List;

public class StreamerUtils {

    private static StreamerUtils instance = null;

    public static StreamerUtils getInstance() {
        if (instance == null) instance = new StreamerUtils();
        return instance;
    }

    private final List<String> forbiddenWords = new ArrayList<>();

    public List<String> getForbiddenWords() {
        return forbiddenWords;
    }

    public StreamerUtils() {
        forbiddenWords.add("incel");
        forbiddenWords.add("1ncel");
        forbiddenWords.add("1nc3l");
        forbiddenWords.add("inc3l");
        forbiddenWords.add("incl");
        forbiddenWords.add("1ncl");

        forbiddenWords.add("virgin");
        forbiddenWords.add("v1rg1n");
        forbiddenWords.add("v1rgin");
        forbiddenWords.add("virg1n");
        forbiddenWords.add("virgn");
        forbiddenWords.add("v1rgn");
        forbiddenWords.add("wirgin");
        forbiddenWords.add("w1rg1n");
        forbiddenWords.add("w1rgin");
        forbiddenWords.add("wirg1n");
        forbiddenWords.add("wirgn");
        forbiddenWords.add("w1rgn");

        forbiddenWords.add("simp");
        forbiddenWords.add("s1mp");

        forbiddenWords.add("nigg4");
        forbiddenWords.add("nigga");
        forbiddenWords.add("niga");
        forbiddenWords.add("nig4");
        forbiddenWords.add("n1gga");
        forbiddenWords.add("n1ga");
        forbiddenWords.add("n1g4");

        forbiddenWords.add("pedal");
        forbiddenWords.add("p3dal");
        forbiddenWords.add("ped4l");
        forbiddenWords.add("p3d4l");
    }
}
