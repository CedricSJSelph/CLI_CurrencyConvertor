import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;

//https://github.com/CedricSJSelph/CLI_CurrencyConvertor.git
/*
Welcome to the CLI Currency Convertor
=====================================
What is the starting currency?: USD
What is the ending currency?: EUR
What is the amount?: $150
150 USD => 153.98 EUR
=====================================
 */

public class Main {
    public static void main(String[] args) throws IOException {
       System.out.println("Welcome to the CLI Currency Convertor");
        boolean userCont;
        do{
            Scanner scan = new Scanner(System.in);
            System.out.print("What is the starting currency?: ");
            String startingCurrency = scan.next();
            scan.nextLine();
            System.out.println("\n");
            System.out.print("What is the starting currency?: ");
            String endingCurrency = scan.next();
            scan.nextLine();
            System.out.println("\n");
            System.out.print("What is the amount $?: ");
            double amount = scan.nextInt();
            scan.nextLine();
            System.out.println();

            double rate = getCovnersion(startingCurrency, endingCurrency, amount);
            System.out.printf("%,.2f" + " in " + startingCurrency + " is ", amount);
            System.out.printf("%,.2f in " + endingCurrency + "\n",(amount*rate));
            System.out.println();
            System.out.print("Would you like to convert again? (Y/N): ");

            String tempstring = scan.nextLine();
            if(tempstring.charAt(0) == 'y' || tempstring.charAt(0) == 'Y'){
                userCont = true;
            }else{
                userCont = false;
                break;
            }
        }while(userCont);
        System.out.println("You have chosen to exit.... have a good day");
    }
    public static double getCovnersion(String startingCurrency, String endingCurrency, double amount) throws IOException {
        String url_str = "https://api.exchangerate.host/convert?from="+startingCurrency+"&to="+endingCurrency;
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod("GET");

        request.setConnectTimeout(5000);
        request.setReadTimeout(5000);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(request.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);//.append(",").append("\n");
        }
        in.close();
        return parseWebData(content.toString());
    }
    public static double parseWebData(String info) {
        String finalRate = "";
        String[] temp = info.split(",");
        for(int i = 0; i <temp.length; i++){
            if(temp[i].substring(1,7).equals("result")){
                finalRate = temp[i].substring(9,temp[i].length()-1);
            }
        }
        return Float.parseFloat(finalRate);
    }
}