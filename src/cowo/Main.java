/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cowo;

import com.csvreader.CsvReader;
import com.google.common.collect.*;
import com.google.common.collect.Multiset.Entry;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author C. Levallois
 *
 * FUNCTION OF THIS PROGRAM: Take a text as input, returns semantic networks as
 * an output.
 *
 * DETAIL OF OPERATIONS: 1. loads several stopwords files in memory 2. reads the
 * text file and does some housekeeping on it 3. lemmatization of the text 4.
 * extracts n-grams 5. housekeeping on n-grams, removal of least frequent terms
 * 6. removal of stopwords, removal of least frequent terms 7. removal of
 * redudant n-grams (as in: removal of "united states of" if "united stated of
 * America" exists frequently enough 8. determines all word co-occurrences for
 * each line of the text 9. prints vosViewer output 10. prints GML file 11.
 * print a short report of all these operations
 */
public class Main {

    public static String currLine = "";
    public static HashMultiset<String> freqSet = HashMultiset.create();
    //public static Multiset<Pair<String, String>> wordsPerLine = ConcurrentHashMultiset.create();
    public static LinkedHashMultimap<Integer, String> wordsPerLine = LinkedHashMultimap.create();
    public static LinkedHashMultimap<Integer, String> wordsPerLineFiltered = LinkedHashMultimap.create();
    public static HashSet<String> setOfWords = new HashSet();
    public static HashMultiset<String> setNGrams = HashMultiset.create();
    public static Multiset<String> multisetOfWords = ConcurrentHashMultiset.create();
    public static Multiset<String> multisetOcc = ConcurrentHashMultiset.create();
    public static Multiset<String> filteredFreqSet = ConcurrentHashMultiset.create();
    public static Multiset<String> ngramsInLine = ConcurrentHashMultiset.create();
    public static HashMap<String, Integer> ngramsCountinCorpus = new HashMap();
    public static Multiset<String> setCombinations = ConcurrentHashMultiset.create();
    public static Multiset<String> future = ConcurrentHashMultiset.create();
    public static List<Entry<String>> freqList;
    public static List<Entry<String>> freqListFiltered = new ArrayList();
    public static String[] stopwords;
    public static int occurrenceThreshold = 3;
    private static FileReader fr;
    public static int maxgram = 4;
    private final static int nbStopWords = 5000;
    private final static int nbStopWordsShort = 300;
    public final static int maxAcceptedGarbage = 3;
    //static int numberOfThreads = Runtime.getRuntime().availableProcessors();
    static int numberOfThreads = 7;
    private static Integer counterLines = 0;
    // logic of freqThreshold: the higher the number of stopwords filtered out, the lower the number of significant words which should be expected
    private static int freqThreshold = 400;
    public static String wordSeparator;
    static private String wk;
    static public String wkOutput;
    static private String textFileName;
    static private String textFile;
    static String cleanWord;
    public static int counter = 0;
    private static int numberOfDocs;
    private static BufferedReader fileStopWords;
    private static BufferedReader fileKeepWords;
    private static BufferedReader fileStopWords2;
    private static BufferedReader fileStopWordsFrench;
    private static String[] stopwordsOwn;
    private static BufferedReader fileStopWords4;
    public static String[] stopwordsShort;
    public static String[] stopwordsFrench;
    private static String[] stopwordsScientific;
    public static Set<String> setStopWords = new HashSet();
    public static Set<String> setStopWordsScientific = new HashSet();
    public static Set<String> setStopWordsScientificOrShort = new HashSet();
    public static Set<String> setNoLemma = new HashSet();
    public static int minWordLength = 3;
    public static HashMap<Integer, String> mapofLines = new HashMap();
    public static HashMultiset<String> setFreqWords = HashMultiset.create();
    private static String fileMapName;
    private static BufferedWriter fileMapFile;
    private static String fileNetworkName;
    private static BufferedWriter fileNetworkFile;
    private static String fileParametersName;
    private static BufferedWriter fileParametersFile;
    public static Set<String> setStopWordsShort = new HashSet();
    public static Set<String> setStopWordsOwn = new HashSet();
    public static Set<String> setStopWordsFrench = new HashSet();
    public static Set<String> setKeepWords = new HashSet();
    private static BufferedReader fileNoLemma;
    private static String[] noLemmaArray;
    public static String[] keepWordsArray;
    static InputStream in10000 = Main.class.getResourceAsStream("stopwords_10000_most_frequent_filtered.txt");
    static InputStream inFrench = Main.class.getResourceAsStream("stopwords_french.txt");
    static InputStream inscientific = Main.class.getResourceAsStream("scientificstopwords.txt");
    static InputStream inOwn;
    static InputStream inkeep = Main.class.getResourceAsStream("stopwords_tokeep.txt");
    static InputStream innolemma = Main.class.getResourceAsStream("nolemmatization.txt");
    public static String ownStopWords = "";
    public static String fileGMLName;
    private static BufferedWriter fileGMLFile;
    public static boolean binary = true;
    public static boolean filterDifficultChars = true;
    static private boolean useScientificStopWords = false;
    private static boolean useTDIDF = false;
    public static TreeMultimap<Integer, String> mapYearsToAbstracts = TreeMultimap.create();
    public static TreeMap<Integer, String> mapYearsToStrings;
    //
    //
    // Alchemy API variables
    //
    //
    public boolean useAAPI_Entity = false;
    public static String AlchemyAPIKey = "35876638f85ebcba7e31184b52fefe52e339e18e";
    public static HashMultimap<String, String> currMapTypeToText = HashMultimap.create();
    public static HashMultimap<String, String> overallMapTypeToText = HashMultimap.create();
    public static HashMap<String, String> overallMapTextToType = new HashMap();
    public static ExecutorService executor;
    public static HashMap<Integer, Future<String>> listFutures;
//    public static HashMultiset<String> multisetTypesOfEntities = HashMultiset.create();
    public static HashSet<String> setFilteredFields = new HashSet();
    public static HashMultiset<String> multisetFreqWordsViaAlchemy = HashMultiset.create();
    public static String msgAlchemy;
    StringBuilder AlchemyAPIfieldsAndNumbers;
    //CSV reader
    public static String[] headers;
    public static boolean readHeaders;
    public static CsvReader transactionsCsv;
    public static String textDelimiter = "\"";
    public static String fieldDelimiter = ",";

    public static void main(String args[]) throws IOException {

        textFile = "c:/data/hbr.csv";
        wk = "c:/data";
        textFileName = "hbr.csv";
        Main.freqThreshold = 2000;
        Main.minWordLength = 3;
        Main.maxgram = 4;
        Main.occurrenceThreshold = 3;
        Main.filterDifficultChars = true;
        Main.useScientificStopWords = true;
        System.out.println("use scientific stopwords? " + Main.useScientificStopWords);
        Main.wordSeparator = " ";
        System.out.println("word separator is: \"" + wordSeparator + "\"");
        Main.useTDIDF = false;



        run();

    }

    static public void run() throws IOException {

        wkOutput = wk.concat("\\");
        StringBuilder freqTerms = new StringBuilder();

        BufferedWriter br = new BufferedWriter(new FileWriter("c:/data/output.csv"));

        for (Integer k = 1922; k < 2013; k++) {

            br.write(k.toString());
            br.write(",");
        }
        br.newLine();

        System.out.println("---------------------------------");
        System.out.println();

        // #### 1. LOADING FILES CONTAINING STOPWORDS
        // Several sources of stopfiles are used.
        // Once transformed in array, they will be invoked by the StopWordsRemoverRT class

        Clock loadingStopWordsTime = new Clock("Loading the list of stopwords");
        inOwn = Main.class.getResourceAsStream("stopwords_hbr.txt");
        fileStopWords2 = new BufferedReader(new InputStreamReader(inOwn));
        stopwordsOwn = fileStopWords2.readLine().split(",");
        setStopWordsOwn.addAll(Arrays.asList(stopwordsOwn));
        fileStopWords2.close();


        if (Main.useScientificStopWords) {

            fileStopWords4 = new BufferedReader(new InputStreamReader(inscientific));
            stopwordsScientific = fileStopWords4.readLine().split(",");
            setStopWordsScientific.addAll(Arrays.asList(stopwordsScientific));
            fileStopWords4.close();
        }

//            fileKeepWords = new BufferedReader(new InputStreamReader(inkeep));
//            keepWordsArray = fileKeepWords.readLine().split(",");
//            setKeepWords.addAll(Arrays.asList(keepWordsArray));
        //fileKeepWords.close();

        fileNoLemma = new BufferedReader(new InputStreamReader(innolemma));
        noLemmaArray = fileNoLemma.readLine().split(",");
        setNoLemma.addAll(Arrays.asList(noLemmaArray));
        fileNoLemma.close();

        fileStopWords = new BufferedReader(new InputStreamReader(in10000));
        stopwords = fileStopWords.readLine().split(",");
        stopwords = Arrays.copyOf(stopwords, nbStopWords);
        fileStopWords.close();

        stopwords = ArrayUtils.addAll(stopwords, stopwordsOwn);

        if (Main.useScientificStopWords) {
            stopwords = ArrayUtils.addAll(stopwords, stopwordsScientific);
        }

        setStopWords.addAll(Arrays.asList(stopwords));


        stopwordsShort = Arrays.copyOf(stopwords, nbStopWordsShort);

        setStopWordsShort.addAll(Arrays.asList(stopwordsShort));
        if (Main.useScientificStopWords) {
            setStopWordsScientificOrShort.addAll(setStopWordsScientific);
        }

        setStopWordsScientificOrShort.addAll(setStopWordsShort);


        loadingStopWordsTime.closeAndPrintClock();
        //-------------------------------------------------------------------------------------------------------------


        // ### 2. LOADING FILE IN MEMORY AND CLEANING  ...





        char textdelimiter = textDelimiter.charAt(0);
        char fielddelimiter = fieldDelimiter.charAt(0);
        msgAlchemy = "";

        transactionsCsv = new CsvReader(new BufferedReader(new FileReader(textFile)), fielddelimiter);
        transactionsCsv.setTextQualifier(textdelimiter);
        transactionsCsv.setUseTextQualifier(true);

        readHeaders = transactionsCsv.readHeaders();
        headers = transactionsCsv.getHeaders();

        mapYearsToStrings = new TreeMap();

        while (transactionsCsv.readRecord()) {
            new Transaction(transactionsCsv.getValues());
        }


        Iterator<Integer> mapYearsToAbstractsIterator = mapYearsToAbstracts.keySet().iterator();
        Integer countLinesInFile = 0;

        Set<String> currYearAbstracts = new HashSet();

//        for (int j = 1922; j < 2013; j++) {

        while (mapYearsToAbstractsIterator.hasNext()) {
            mapofLines = new HashMap();
            freqSet.clear();
            filteredFreqSet.clear();
            freqListFiltered.clear();
            setNGrams.clear();

            System.out.println("freqSet size is:" + freqSet.size());
            System.out.println("filteredFreqSet size is:" + filteredFreqSet.size());
            System.out.println("freqListFiltered size is:" + freqListFiltered.size());

            Integer currYearInMap = mapYearsToAbstractsIterator.next();
            System.out.println("doing year " + currYearInMap);
//                if (!currYearInMap.equals(j)) {
//                    continue;
//                }
//            finalString.append(currYearInMap).append(" ->");

            if (currYearInMap < 2001 && ((currYearInMap - 1921) % 5) != 0) {
                currYearAbstracts.addAll(mapYearsToAbstracts.get(currYearInMap));
                continue;
            }
            currYearAbstracts.addAll(mapYearsToAbstracts.get(currYearInMap));

            Iterator<String> currYearAbstractsIterator = currYearAbstracts.iterator();

            while (currYearAbstractsIterator.hasNext()) {

                currLine = currYearAbstractsIterator.next();
                countLinesInFile++;
                if (!currLine.matches(".*\\w.*")) {
                    continue;
                }

                if (currLine.contains("fictional") || currLine.contains("workshop event")) {
                    continue;
                }
                counterLines++;




                currLine = TextCleaner.doBasicCleaning(currLine);
//                System.out.println("currLine is "+currLine);
                mapofLines.put(counterLines, currLine);
                //System.out.println(currLine);


            }


            // end looping through all lines of the original text file
            counterLines = 0;

            numberOfDocs = mapofLines.keySet().size();

            System.out.println(
                    "nb of docs treated: " + numberOfDocs);





            // ### EXTRACTING set of NGrams and LEMMATIZING


            NGramFinder.runIt(mapofLines);
            NGramCleaner.cleanIt();

//                Clock LemmatizerClock = new Clock("Lemmatizing");

            freqSet = Lemmatizer.doLemmatizationReturnMultiSet(freqSet);

//                LemmatizerClock.closeAndPrintClock();

//                System.out.println(
//                        "size of freqSet (unique Words):" + freqSet.elementSet().size());

            //-------------------------------------------------------------------------------------------------------------        
            // #### 6. REMOVING STOPWORDS
//                    Clock stopwordsRemovalTime = new Clock("Removing stopwords");
            Iterator<Entry<String>> it = freqSet.entrySet().iterator();


            while (it.hasNext()) {
                Entry<String> entry = it.next();
//                    if (!entry.getElement().contains(" ")) {
//                        continue;
//                    }

//                    Main.filteredFreqSet.add(entry.getElement(), entry.getCount());
                new StopWordsRemover(entry.getElement().trim(), entry.getCount());
            }
            counterLines = 0;
//                    stopwordsRemovalTime.closeAndPrintClock();


            // #### SORTS TERMS BY FREQUENCY, LEAVING OUT THE LESS FREQUENT ONE
            freqList = MultiSetSorter.sortMultisetPerEntryCount(filteredFreqSet);

//                System.out.println(
//                        "size of freqList: " + freqList.size());
            ListIterator<Entry<String>> li = freqList.listIterator(Math.min(freqThreshold, freqList.size()));

            while (li.hasNext()) {
                li.next();
                li.remove();
            }

//                System.out.println(
//                        "size of the set of words after terms less frequent than " + freqThreshold + " are removed: " + freqList.size());



            //-------------------------------------------------------------------------------------------------------------   
            // #### DELETES bi-grams trigrams and above, IF they are already contained in n+1 grams
            NGramDuplicatesCleaner.removeDuplicates();

            //---------------------------------------------------------------------------------------------------------------
            //Deletes terms below the frequency threshold and in the case of a person, deletes it if there is no space in it.

//                System.out.println(
//                        "size of setFreqWords:" + setFreqWords.elementSet().size());


            //-------------------------------------------------------------------------------------------------------------           
            // #### PRINTING MOST FREQUENT TERMS         
            String currFrequentTerm;
            Integer limitTerms;
            if (currYearInMap <= 2001) {
                limitTerms = 50;
            } else {
                limitTerms = 10;
            }
            for (int i = 0;
                    i < freqListFiltered.size()
                    && i < limitTerms; i++) {
                currFrequentTerm = freqListFiltered.get(i).getElement().toString();
                freqTerms.append(currFrequentTerm).append("|");
//                    System.out.println("most frequent words: " + currFrequentTerm);
//                    mostFrequentTerms.append("most frequent words: ").append(currFrequentTerm).append("\n");
            }

            mapYearsToStrings.put(currYearInMap, freqTerms.toString());
//            br = new BufferedWriter(new FileWriter("c:/data/output.csv"));
            br.write(freqTerms.toString() + ",");
            System.out.println("result for year " + currYearInMap + " : " + freqTerms.toString());
            freqTerms = new StringBuilder();
            currYearAbstracts.clear();



        }
        br.close();
//        processing.Main.main();


//        }

    }
}
