package moduls;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) throws IOException{

		Scanner sc = new Scanner(System.in);
		int status;
		boolean exists = false;
		
		//Getting info from the user
		System.out.println("Enter your name: ");
		String name = sc.next();
		System.out.println("Enter your passport number: ");
		String passportNo = sc.next();
		
		String details = name+ "\t" + passportNo;

		//Creating a file to store names and info
		File nameInfo = new File("nameInfo.txt");
		FileWriter nameInfoW = new FileWriter(nameInfo, true);
		FileReader nameInfoFR = new FileReader(nameInfo);
		BufferedReader nameInfoBR = new BufferedReader(nameInfoFR);

		String line = "";
		
		//Checking for duplicates
		while((line = nameInfoBR.readLine()) != null) {
			if(line.contains(details)) {
				exists=true;
				break;
			}
		}
		if(exists) {
			System.out.println("This user already exists");
		}
		else {		
			//Getting card details from the user
			System.out.println("Enter your card number:");
			long cardNo = sc.nextLong();
			System.out.println("Enter CVV:");
			int cvv = sc.nextInt();
			
			//Creating a file to save card details
			File cardInfo = new File("cardInfo.txt");
			FileWriter cardInfoW = new FileWriter(cardInfo, true);
			
			
			//Asking the bank for payment status
			System.out.println("=====Bank=====");
			System.out.println("Is this payment status okay? (Y=1/N=0)");
			status = sc.nextInt();
			
			Random rand = new Random();
			int visaNumber = 10000000 + rand.nextInt(1000000000);
			int visaDuration = rand.nextInt(10);
			
			File visaInfo = new File("visaInfo.txt");
			FileWriter visaInfoW = new FileWriter(visaInfo, true);
			//Checking for payment status
			if (status == 1) {
				nameInfoW.write("\n" + name + "\t" + passportNo);
				cardInfoW.write("\n" + cardNo + "\t" + cvv);
				visaInfoW.write("\n" + visaNumber + "\t" + visaDuration);
				cardInfoW.close();
				visaInfoW.close();
				System.out.println("Your visa is created and its number is: " + visaNumber
						+ " and the duration is: " + visaDuration + " Years.");
			}
			else {
				System.out.println("Your visa is rejected");
			}
		}

		nameInfoW.close();
		nameInfoBR.close();
		sc.close();
	}
}
