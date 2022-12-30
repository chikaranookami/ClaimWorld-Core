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
        forbiddenWords.add("imcel");
        forbiddenWords.add("1mcel");
        forbiddenWords.add("1mc3l");
        forbiddenWords.add("imc3l");
        forbiddenWords.add("imcl");
        forbiddenWords.add("1mcl");

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
        forbiddenWords.add("sinp");
        forbiddenWords.add("s1np");

        forbiddenWords.add("pedal");
        forbiddenWords.add("p3dal");
        forbiddenWords.add("ped4l");
        forbiddenWords.add("p3d4l");
        forbiddenWords.add("pedał");
        forbiddenWords.add("p3dał");
        forbiddenWords.add("ped4ł");
        forbiddenWords.add("p3d4ł");
    }
}
