//package com.ebi.snap_food.utils;
//
//import net.ricecode.similarity.JaroWinklerStrategy;
//import net.ricecode.similarity.StringSimilarityService;
//import net.ricecode.similarity.StringSimilarityServiceImpl;
//
//public class StringSimilarityUtils {
//
//    private static final StringSimilarityService service = new StringSimilarityServiceImpl(new JaroWinklerStrategy());
//
//    public static int score(String source, String target) {
//
//        double score = service.score(source, target);
//        return (int) (score * 100);
//    }
//}
