/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package QuarkusStartPrjs.app;

import QuarkusStartPrjs.list.LinkedList;

import static QuarkusStartPrjs.utilities.StringUtils.join;
import static QuarkusStartPrjs.utilities.StringUtils.split;
import static QuarkusStartPrjs.app.MessageUtils.getMessage;

import org.apache.commons.text.WordUtils;

public class App {
    public static void main(String[] args) {
        LinkedList tokens;
        tokens = split(getMessage());
        String result = join(tokens);
        System.out.println(WordUtils.capitalize(result));
    }
}
