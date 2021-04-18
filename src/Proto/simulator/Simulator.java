import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Store all the simulations and it can run them.
 */
public class Simulator {

	Scanner sc;
	ArrayList<String> commands;
	boolean random = false;

	public Simulator(Scanner sc){
		this.sc = sc;
		commands = new ArrayList<>();
	}

	public void Read(){

		while (sc.hasNext()){
			String stringTmp = sc.nextLine();
			commands.add(stringTmp);
		}
		//System.out.println(commands);
	}

	public void Execute(){
		boolean setup = false;
		boolean progress = false;
		int readboard = 0;
		int readnei = 0;
		for (String curent : commands) {
			String splittedCommand[] = curent.split(" ");
			if (readboard > 0){
				String tmp[] = curent.split(";");
				System.out.println(tmp[0]);
				readboard--;
				continue;
			}

			if (readnei > 0){
				String tmp[] = curent.split(";");
				System.out.println(tmp[0]);
				readnei--;
				continue;
			}

			switch (splittedCommand[0]){
				case "<SETUP>":
					if (!progress)
						setup = true;
					break;
				case "</SETUP>":
					if (setup)
						setup = false;
					break;
				case "Setboard":
					if (setup){
						readboard = Integer.parseInt(splittedCommand[1]);
						System.out.println("Setboard -> " + readboard);
					}
					break;
				case "Makegate":
					if (setup){
						System.out.println("kapu1: " + splittedCommand[1] + " kapu2: " + splittedCommand[2]);
					}
					break;
				case "Setnei":
					if (setup){
						readnei = Integer.parseInt(splittedCommand[1]);
						System.out.println("Setnei -> " + readboard);
					}
					break;
				case "Settler":
					if (setup){
						System.out.println("nev: " + splittedCommand[1] + " aszteroid: " + splittedCommand[2]);
					}
					break;
				case "Makematerial":
					if (setup) {
						if (splittedCommand[1].equals("-u")) {
							System.out.println("nev: " + splittedCommand[2] + " napkoz: " + splittedCommand[3]);
							break;
						}
						System.out.println("nev: " + splittedCommand[2]);
					}
					break;
				case "Setinventory":
					if (setup){
						System.out.println("des: " + splittedCommand[1] + " sour: " + splittedCommand[2]);
					}
					break;
				case "Robot":
					if (setup){
						System.out.println("nev: " + splittedCommand[1] + " aszteroid: " + splittedCommand[2]);
					}
					break;
				case "UFO":
					if (setup){
						System.out.println("nev: " + splittedCommand[1] + " aszteroid: " + splittedCommand[2]);
					}
					break;
				case "Setrandom":
					if (setup){
						System.out.println("ertek: " + splittedCommand[1]);
					}
					break;
				case "<PROGRESS>":
					if (!setup)
						progress = true;
					break;
				case "</PROGRESS>":
					if (progress)
						progress = false;
					break;
				case "Move":
					if (progress){
						System.out.println("nev: " + splittedCommand[1] + " des: " + splittedCommand[2]);
					}
					break;
				case "Drill":
					if (progress){
						System.out.println("nev: " + splittedCommand[1]);
					}
					break;
				case "Mine":
					if (progress){
						System.out.println("nev: " + splittedCommand[1]);
					}
					break;
				case "Buildrobot":
					if (progress){
						System.out.println("robot: " + splittedCommand[1] + " telepes: " + splittedCommand[2]);
					}
					break;
				case "Buildgate":
					if (progress){
						System.out.println("kapu: " + splittedCommand[1] + " telepes: " + splittedCommand[2]);
					}
					break;
				case "Buildbase":
					if (progress){
						if (progress){
							System.out.println("telepes: " + splittedCommand[1]);
						}
					}
					break;
				case "Putdown":
					if (progress){
						System.out.println("obj nev: " + splittedCommand[1] + " telepes: " + splittedCommand[2]);
					}
					break;
				case "Step":
					if (progress){
						System.out.println("step");
					}
					break;
				case "List":
					if (progress){
						System.out.println("list");
					}
					break;
				case "Makeeruption":
					if (progress){
						System.out.println("aszteroida: " + splittedCommand[1] + " sugar: " + splittedCommand[2]);
					}
					break;
				case "Abort":
					return;
				case "Load":
					System.out.println("load: " + splittedCommand[1]);
					break;
				default:
					if (readboard == 0 && readnei == 0)
						System.out.println("Ismeretlen parancs");
					break;
			}
		}
	}

	public boolean compareTest(File test1, File test2) throws IOException {
		BufferedReader reader1 = new BufferedReader(new FileReader(test1));

		BufferedReader reader2 = new BufferedReader(new FileReader(test2));

		String line1 = reader1.readLine();

		String line2 = reader2.readLine();

		boolean areEqual = true;

		int lineNum = 1;

		while (line1 != null || line2 != null)
		{
			if(line1 == null || line2 == null)
			{
				areEqual = false;

				break;
			}
			else if(! line1.equalsIgnoreCase(line2))
			{
				areEqual = false;

				break;
			}

			line1 = reader1.readLine();

			line2 = reader2.readLine();

			lineNum++;
		}

		if(areEqual)
		{
			System.out.println("Two files have same content.");
			reader1.close();
			reader2.close();
			return true;
		}
		else
		{
			System.out.println("Two files have different content. They differ at line "+lineNum);

			System.out.println("File1 has "+line1+" and File2 has "+line2+" at line "+lineNum);
			reader1.close();
			reader2.close();
			return false;
		}

	}

}
