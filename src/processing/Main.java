/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package processing;

/**
 *
 * @author ${user}
 */
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import processing.core.PApplet;

@SuppressWarnings("serial")
public class Main extends PApplet {

    @Override
    public void setup() {

        Integer WIDTH = 1000;
        Integer HEIGTH = 1000;
        Integer TEXTSIZE = 20;

        size(WIDTH, HEIGTH, P3D);

        TreeMap map = new TreeMap();
        map.putAll(cowo.Main.mapYearsToStrings);
        Iterator<Entry<Integer, String>> mapIterator = map.entrySet().iterator();
        String currString;
        Float stringWidth;
        textSize(TEXTSIZE);
        Float cursorH = (float) 0;
        Integer cursorV = 0;
        String[] words;
        Integer textSizeFirstWord = TEXTSIZE + 3;

        while (mapIterator.hasNext()) {
            Entry<Integer, String> currEntry = mapIterator.next();
            Integer currYear = currEntry.getKey();
            currString = currEntry.getValue();
            words = currString.split(",");
            for (String word : words) {
                stringWidth = textWidth(word);

                if (words[0].equals(word)) {
                    text(currYear + "->", cursorH, cursorV);
                    cursorH = cursorH + textWidth(currYear + "=>");
                    textSize(textSizeFirstWord);
                    text(word, cursorH, cursorV);
                    cursorH = cursorH + stringWidth;
                    continue;
                }


                if (cursorH + stringWidth > WIDTH) {
                    cursorH = (float) 0;
                    cursorV = cursorV + TEXTSIZE;

                }

                text(word, cursorH, cursorV);


                cursorH = cursorH + stringWidth;
                textSize(TEXTSIZE);
            }

        }

    }

    @Override
    public void draw() {


    }

    /**
     * @param args the command line arguments
     */
    public static void main() {
        PApplet.main(new String[]{processing.Main.class.getName()});
    }
}
