import kong.unirest.Unirest;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Pattern;

import static input.InputUtils.stringInput;
import static input.InputUtils.yesNoInput;

public class ExchangeRates {
    public static void main(String[] args) {
        String AccessKey = "6f7fa2ccf5e122f7350dff6432de3a01";
        String url = "http://api.exchangeratesapi.io/v1/latest";

        Map<String, Object> params = new HashMap<>();
        params.put("access_key", AccessKey);

        //String keyURLaddendum = "?access_key=" + AccessKey;

        List<String> currencies = new ArrayList<>();

        // get a list of the current currencies from the api
        CCode ccodes = Unirest.get(url).queryString(params).asObject(CCode.class).getBody();

        if (!ccodes.success) {
            System.out.println("Error getting currency codes from API");
        } else {
            // splits the string returned by the API by commas
            // places pieces into an array
            String[] codes = (ccodes.symbols).split(",");

            // put the returned array in the currencies list
            // can avoid an iteration loop using a Collections method
            currencies.addAll(Arrays.asList(codes));
        }

        System.out.println("Welcome to the exchange rate program!" +
                "\nHere is a list of all current currencies and their 3 letter codes");
        printCurrencies(currencies);

        String base = userBase();
        // adds base to the parameter if not empty
        if (base != "") {
            params.put("base", base); }

        String outputCodes = userLimit();
        // puts symbols into the parameter, as long as it isn't empty
        if (outputCodes != ""){
        params.put("symbols", outputCodes); }



        // String parameterURLaddendum = "&symbols=" + ;

        // get the response packaged in a class
        Response response = Unirest.get( url
                            )
                .asObject(Response.class).getBody();
        boolean success = response.success;
        if (!success) {
            System.out.println("Currency retrieval from API was unsuccessful");
            boolean again = yesNoInput("Would you like to try to enter the currency again?");
            if (again) {

            }
        }
        // display the response
        Display(response);
    }

    private static String userBase() {
        String base = stringInput("Please enter a base currency using the 3 letter code." +
                "\nIf nothing is entered the base will be EUR.");

        return base;
    }

    private static void printCurrencies(List<String> currencies) {
        for ( String x : currencies) {
            System.out.println(x);
        }
    }

    private static String userLimit() {

        String picks = stringInput("Please enter all desired 3 letter currency codes separated by commas, or press enter for rates in every currency");
        Pattern pattern = Pattern.compile("[A-Za-z]{3}(,[A-Za-z] ?)*");
        if (picks == "") {
            return "";
        }
        if (!picks.matches("[A-Za-z]{3}(,[A-Za-z] ?)*")) {
            System.out.println("Incorrect format entered, example: ABC, DEF, GHI");
            String enter = stringInput("Please try again");

        }
        // if it's in the correct format, place in an uppercase string and strip of whitespace
        picks = picks.strip().toUpperCase();
        return picks;
    }

    public static void Display(Response res){
        

    }
}

class Response {
    public boolean success;
    public long timestamp;
    public String base;
    public Date date;
    public List<Rate> rates;
}

class Rate {
    public String base;
    public float rate;
}

class CCode {
    public boolean success;
    public String symbols;
}