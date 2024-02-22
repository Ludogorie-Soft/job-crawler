package com.ludogoriesoft.job.crawler.JobCrawler.util;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JobOfferFilterUtil {

    private JobOfferFilterUtil() {
    }

    private final static Set<String> EXCLUDED_WORDS = Set.of("angular", "react", "vue", "frontend needed");
    private final static Pattern C2C_PATTERN = Pattern.compile("\\bcorp\\s+to\\s+corp\\b");
    private final static Pattern EXCLUDED_WORDS_PATTERN = Pattern.compile("\b(?:" + String.join("|", EXCLUDED_WORDS) + ")\b");


    public static boolean isSuitableJobOffer(String overview, String description) {
        Matcher c2cMatcher = C2C_PATTERN.matcher(overview.toLowerCase());
        Matcher excludedWordsOverviewMatcher = EXCLUDED_WORDS_PATTERN.matcher(overview.toLowerCase());
        Matcher excludedWordsDescriptionMatcher = EXCLUDED_WORDS_PATTERN.matcher(description.toLowerCase());
        boolean containsExcludedWords = excludedWordsOverviewMatcher.find() && excludedWordsDescriptionMatcher.find();
        if ((c2cMatcher.find() && !containsExcludedWords)) {
            return true;
        }
        return false;
    }

}
