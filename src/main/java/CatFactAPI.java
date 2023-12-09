import kong.unirest.Unirest;

public class CatFactAPI {
    public static void main(String[] args) {

        String url = "https://catfact.ninja/fact";
        // gets the JSON response from the site and turns it into an object, specified by a class
        CatFact catFact = Unirest.get(url).asObject(CatFact.class).getBody();

        String fact = catFact.fact;
        int length = catFact.length;

        System.out.println(fact);
        System.out.println("This Cat Fact is " + length + " characters long.");
    }
}
// this class processes the response into its Fact and Length pieces
class CatFact {
    public String fact;
    public int length;
}
