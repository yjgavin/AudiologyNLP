package main.java.com.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Report {
    public List<String> info;
    public List<List<String>> history;
    public List<List<String>> test;
    public List<List<String>> report;
    public List<String> recommendation;

    private String[] headings = new String[]{"General patient info", "Case history",
            "Test", "Result", "Recommendation/plan"};
    private List<String> infoHeadings = Arrays.asList("Name", "DOB", "Date of Visit", "Location", "Organization", "Sex");
    private List<String> historyHeadings = Arrays.asList("Hearing screen", "Known hearing loss",
            "Early intervention", "Risk factors", "Hearing aid");
    private List<String> testHeadings = Arrays.asList("Cochlear function", "Evoked potential",
            "Middle ear function", "Behavourial audiometry");
    private List<String> reportHeadings = Arrays.asList("General Result", "Degree", "Which ear", "Type");

    public Report() {
        info = new ArrayList<>();
        history = new ArrayList<>();
        test = new ArrayList<>();
        report = new ArrayList<>();
        recommendation = new ArrayList<>();
    }

    @Override
    public String toString(){
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("-General patient info:\n");
//        Iterator infoHeadingIt = infoHeadings.iterator();
//        Iterator infoIt = info.iterator();
//        while (infoIt.hasNext()) {
//            sb.append(infoHeadingIt.next() + ": ");
//            sb.append(infoIt.next() + "\n");
//        }
//
//        sb.append("-Case history:\n");
//        Iterator histHeadingIt = historyHeadings.iterator();
//        Iterator histIt = history.iterator();
//        while (histIt.hasNext()) {
//            sb.append(histHeadingIt.next() + ": ");
//            List<String> ls = (List<String>)histIt.next();
//            for (String s : ls) {
//                sb.append(s + ",");
//            }
//            sb.deleteCharAt(sb.length() - 1);
//            sb.append("\n");
//        }
//
//        sb.append("-Test:\n");
//        Iterator testHeadingIt = testHeadings.iterator();
//        Iterator testIt = test.iterator();
//        while (testIt.hasNext()) {
//            sb.append(testHeadingIt.next() + ": ");
//            List<String> ls = (List<String>)testIt.next();
//            for (String s : ls) {
//                sb.append(s + ",");
//            }
//            sb.deleteCharAt(sb.length() - 1);
//            sb.append("\n");
//        }
//
//        sb.append("-Result:\n");
//        Iterator reportHeadingIt = reportHeadings.iterator();
//        Iterator reportIt = report.iterator();
//        while (reportIt.hasNext()) {
//            sb.append(reportHeadingIt.next() + ": ");
//            List<String> ls = (List<String>)reportIt.next();
//            for (String s : ls) {
//                sb.append(s + ",");
//            }
//            sb.deleteCharAt(sb.length() - 1);
//            sb.append("\n");
//        }
//
//        sb.append("-Recommendation:\n");
//        for (String r : recommendation) {
//            sb.append(r + ",");
//        }
//        sb.deleteCharAt(sb.length() - 1);
//        sb.append("\n");

        StringBuilder sb = new StringBuilder();
        for (String cat: infoHeadings) {
            sb.append(cat);
            sb.append(",");
        }
        for (String cat: historyHeadings) {
            sb.append(cat);
            sb.append(",");
        }
        for (String cat: testHeadings) {
            sb.append(cat);
            sb.append(",");
        }
        for (String cat: reportHeadings) {
            sb.append(cat);
            sb.append(",");
        }
        sb.append("Recommendation");

        sb.append("\n");
        for (String cat : info) {
            sb.append(cat);
            sb.append(",");
        }
        for (List<String> ls : history) {
            for (String cat : ls) {
                sb.append(cat);
                sb.append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(",");
        }
        for (List<String> ls : test) {
            for (String cat : ls) {
                sb.append(cat);
                sb.append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(",");
        }
        for (List<String> ls : report) {
            for (String cat : ls) {
                sb.append(cat);
                sb.append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(",");
        }
        for (String cat : recommendation) {
            sb.append(cat);
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

}
