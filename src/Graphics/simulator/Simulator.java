package Graphics.simulator;

import Graphics.controller.GameManager;
import Graphics.Main;
import Graphics.controller.controllers.RobotController;
import Graphics.controller.controllers.SettlerController;
import Graphics.controller.controllers.SolarSystem;
import Graphics.controller.controllers.UfoController;
import Graphics.entity.entities.Robot;
import Graphics.entity.entities.Settler;
import Graphics.entity.entities.Ufo;
import Graphics.material.Material;
import Graphics.material.MaterialCompare;
import Graphics.material.materials.*;
import Graphics.thing.Thing;
import Graphics.thing.things.Asteroid;
import Graphics.thing.things.MainAsteroid;
import Graphics.thing.things.TeleportGate;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Store all the simulations and it can run them.
 */
public class Simulator {
	/**
	 * List of the messages.
	 */
	private static LinkedList<String> messages;

	/**
	 * Add message to the messages list.
	 * @param msg The message.
	 */
	public static void addMessage(String msg) {
		if (messages != null)
			messages.add(msg);
	}

	/**
	 * List of the commands.
	 */
	ArrayList<String> commands;
	/**
	 * List of the materials.
	 */
	LinkedList<Material> materials;
	/**
	 * List of the gates.
	 */
	LinkedList<TeleportGate> gates;
	/**
	 * It turns the random to false.
	 */
	boolean random = false;

	/**
	 * The constructor of the class
	 */
	public Simulator(){
		commands = new ArrayList<>();
		materials = new LinkedList<>();
		gates = new LinkedList<>();
		messages = new LinkedList<>();
	}

	/**
	 * Returns the material by his given name
	 * @param name Name of the material
	 * @return The material
	 */
	private Material getMaterialByName(String name) {
		for (Material m : materials)
			if (m.getName().equals(name))
				return m;

		return null;
	}

	/**
	 * Returns the teleport gate by his given name
	 * @param name Name of the teleport gate
	 * @return The teleport gate
	 */
	private TeleportGate getGateByName(String name) {
		for (TeleportGate m : gates)
			if (m.getName().equals(name))
				return m;

		return null;
	}

	/**
	 * Read the files.
	 * @param in X_input.text file
	 * @param out X_out.txt file
	 * @param exp X_output.txt file
	 * @return false
	 */
	public boolean Read(File in, File out, File exp){
		try {
			Scanner sc = null;
			sc = new Scanner(in);
			commands = new ArrayList<>();
			while (sc.hasNext()){
				String stringTmp = sc.nextLine();
				commands.add(stringTmp);
			}
			Execute(out);
			return compareTest(exp, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(commands);
		return false;
	}

	/**
	 * Run the simulator
	 */
	public void run() {
		boolean quit = false;
		while (!quit) {
			String line = Main.scanner.nextLine();
			String[] args = line.split(" ");
			File input = null;
			File output = null;
			File expected = null;
			if (args[0].equals("load"))
				for (int i = 0; i < args.length; ++i) {
					if (args[i].equals("-i") && i + 1 < args.length)
						input = new File(args[++i]);

					if (args[i].equals("-o") && i + 1 < args.length)
						output = new File(args[++i]);
					if (args[i].equals("-e") && i + 1 < args.length)
						expected = new File(args[++i]);
				}

			if (input != null && output != null && expected != null)
				Read(input, output, expected);

			if (args[0].equals("quit"))
				quit = true;
		}
	}

	/**
	 * Executes the commands from the input
	 * @param out the outcome of the commands
	 */
	public void Execute(File out) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(out.getAbsoluteFile()));

			boolean setup = false;
			boolean progress = false;
			int readboard = 0;
			int readnei = 0;

			GameManager.init();
			SolarSystem.init(GameManager.getInstance());
			SettlerController.init(GameManager.getInstance());
			RobotController.init(GameManager.getInstance());
			UfoController.init(GameManager.getInstance());
			MaterialCompare.init();

			for (String curent : commands) {
				String splittedCommand[] = curent.split(" ");
				if (readboard > 0) {
					String tmp[] = curent.split(";");
					if (tmp[tmp.length - 1].equals("1")) {
						MainAsteroid main = null;
						if (tmp[2].equals("-e")) {
							main = new MainAsteroid(tmp[0], Integer.parseInt(tmp[1]), null, tmp[2].equals("1"));
						} else {
							Material core = null;
							switch(tmp[2]){
								case "-c":
									core = new Coal(tmp[3]);
									break;
								case "-i":
									core = new Iron(tmp[3]);
									break;
								case "-s":
									core = new Silicon(tmp[3]);
									break;
								case "-u":
									core = new Uran(tmp[3]);
									break;
								case "-w":
									core = new WaterIce(tmp[3]);
									break;
								default:
									core = null;
							}
							main = new MainAsteroid(tmp[0], Integer.parseInt(tmp[1]), core, tmp[3].equals("1"));
						}
						SolarSystem.getInstance().addThing(main);
					} else {
						Asteroid asteroid = null;
						if (tmp[2].equals("-e")) {
							asteroid = new Asteroid(tmp[0], Integer.parseInt(tmp[1]), null, tmp[3].equals("1"));
						} else {
							Material core = null;
							switch(tmp[2]){
								case "-c":
									core = new Coal(tmp[3]);
									break;
								case "-i":
									core = new Iron(tmp[3]);
									break;
								case "-s":
									core = new Silicon(tmp[3]);
									break;
								case "-u":
									core = new Uran(tmp[3]);
									break;
								case "-w":
									core = new WaterIce(tmp[3]);
									break;
								default:
									core = null;
							}
							asteroid = new Asteroid(tmp[0], Integer.parseInt(tmp[1]), core, tmp[4].equals("1"));
						}
						SolarSystem.getInstance().addThing(asteroid);
					}
//					System.out.println(tmp[0]);
					readboard--;
					continue;
				}

				if (readnei > 0){
					String tmp[] = curent.split(";");
					Thing t1 = null;
					Thing t2 = null;

					t1 = getGateByName(tmp[0]);
					t2 = getGateByName(tmp[1]);

					if (t1 == null)
						t1 = SolarSystem.getInstance().getThingByName(tmp[0]);
					else {
						TeleportGate tg = (TeleportGate) t1;
						tg.activate();
						SolarSystem.getInstance().addThing(tg);
					}

					if (t2 == null)
						t2 = SolarSystem.getInstance().getThingByName(tmp[1]);
					else {
						TeleportGate tg = (TeleportGate) t2;
						tg.activate();
						SolarSystem.getInstance().addThing(tg);
					}

					if (t1 != null && t2 != null) {
						t1.addNeighbour(t2);
						t2.addNeighbour(t1);
					}

//					System.out.println(tmp[0]);
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
							//System.out.println("Setboard -> " + readboard);
						}
						break;
					case "Makegate":
						if (setup){
							//System.out.println("kapu1: " + splittedCommand[1] + " kapu2: " + splittedCommand[2]);
							TeleportGate tg1 = new TeleportGate(splittedCommand[1]);
							TeleportGate tg2 = new TeleportGate(splittedCommand[2]);
							tg1.setPair(tg2);
							tg2.setPair(tg1);

							gates.add(tg1);
							gates.add(tg2);
						}
						break;
					case "Setnei":
						if (setup){
							readnei = Integer.parseInt(splittedCommand[1]);
							//System.out.println("Setnei -> " + readboard);
						}
						break;
					case "Settler":
						if (setup){
//							System.out.println("nev: " + splittedCommand[1] + " aszteroid: " + splittedCommand[2]);
							Thing loc = SolarSystem.getInstance().getThingByName(splittedCommand[2]);
							Settler s = new Settler(loc, splittedCommand[1]);
							SettlerController.getInstance().addSettler(s);
							loc.addEntity(s);
						}
						break;
					case "Makematerial":
						if (setup) {
							if (splittedCommand[1].equals("-u")) {
								//System.out.println("nev: " + splittedCommand[2] + " napkoz: " + splittedCommand[3]);
								try {
									materials.add(new Uran(splittedCommand[2], Integer.parseInt(splittedCommand[3])));
									break;
								} catch (ArrayIndexOutOfBoundsException e){
									materials.add(new Uran(splittedCommand[2], 0));
									break;
								}

							}
							switch(splittedCommand[1]){
								case "-c":
									materials.add(new Coal(splittedCommand[2]));
									break;
								case "-i":
									materials.add(new Iron(splittedCommand[2]));
									break;
								case "-s":
									materials.add(new Silicon(splittedCommand[2]));
									break;
								case "-w":
									materials.add(new WaterIce(splittedCommand[2]));
									break;
								default:
							}
							//System.out.println("nev: " + splittedCommand[2]);
						}
						break;
					case "Setinventory":
						if (setup){
//							System.out.println("des: " + splittedCommand[1] + " sour: " + splittedCommand[2]);

							Thing thing = SolarSystem.getInstance().getThingByName(splittedCommand[1]);
							Settler settler = SettlerController.getInstance().getSettlerByName(splittedCommand[1]);

							if (thing != null){
								Asteroid tmp = (Asteroid) thing;
								tmp.setCore(getMaterialByName(splittedCommand[2]));

							} else if (settler != null){
								Material mat = getMaterialByName(splittedCommand[2]);
								TeleportGate tpGate = getGateByName(splittedCommand[2]);

								if (mat != null && tpGate == null){
									settler.getMyInventory().addMaterial(mat);

								} if (tpGate != null && mat == null)
									settler.addGate(tpGate);
								}
						}
						break;
					case "Robot":
						if (setup){
//							System.out.println("nev: " + splittedCommand[1] + " aszteroid: " + splittedCommand[2]);
							Thing loc = SolarSystem.getInstance().getThingByName(splittedCommand[2]);
							Robot r = new Robot(splittedCommand[1], loc);
							RobotController.getInstance().addRobot(r);
							loc.addEntity(r);
						}
						break;
					case "UFO":
						if (setup){
//							System.out.println("nev: " + splittedCommand[1] + " aszteroid: " + splittedCommand[2]);
							Thing loc = SolarSystem.getInstance().getThingByName(splittedCommand[2]);
							Ufo u = new Ufo(loc, splittedCommand[1]);
							UfoController.getInstance().addUfo(u);
							loc.addEntity(u);
						}
						break;
					case "Setrandom":
						if (setup){
//							System.out.println("ertek: " + splittedCommand[1]);
							random = splittedCommand[1].equals("1");
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
//							System.out.println("nev: " + splittedCommand[1] + " des: " + splittedCommand[2]);

							Settler s = SettlerController.getInstance().getSettlerByName(splittedCommand[1]);
							if (s != null) {
								SettlerController.getInstance().handleCommand("Move " + splittedCommand[1] + " " + splittedCommand[2]);
								break;
							}

							Robot r = RobotController.getInstance().getRobotByName(splittedCommand[1]);
							if (r != null) {
								RobotController.getInstance().handleCommand("Move " + splittedCommand[1] + " " + splittedCommand[2]);
								break;
							}

							Ufo u = UfoController.getInstance().getUfoByName(splittedCommand[1]);
							if (u != null) {
								UfoController.getInstance().handleCommand("Move " + splittedCommand[1] + " " + splittedCommand[2]);
								break;
							}

						}
						break;
					case "Drill":
						if (progress){
							//System.out.println("nev: " + splittedCommand[1]);

							Settler s = SettlerController.getInstance().getSettlerByName(splittedCommand[1]);
							if (s != null) {
								SettlerController.getInstance().handleCommand("Drill " + splittedCommand[1]);
								break;
							}

							Robot r = RobotController.getInstance().getRobotByName(splittedCommand[1]);
							if (r != null) {
								RobotController.getInstance().handleCommand("Drill " + splittedCommand[1]);
								break;
							}

							Ufo u = UfoController.getInstance().getUfoByName(splittedCommand[1]);
							if (u != null) {
								UfoController.getInstance().handleCommand("Drill " + splittedCommand[1]);
								break;
							}
						}
						break;
					case "Mine":
						if (progress){
						//	System.out.println("nev: " + splittedCommand[1]);

							Settler s = SettlerController.getInstance().getSettlerByName(splittedCommand[1]);
							if (s != null) {
								SettlerController.getInstance().handleCommand("Mine " + splittedCommand[1]);
								break;
							}

							Robot r = RobotController.getInstance().getRobotByName(splittedCommand[1]);
							if (r != null) {
								RobotController.getInstance().handleCommand("Mine " + splittedCommand[1]);
								break;
							}

							Ufo u = UfoController.getInstance().getUfoByName(splittedCommand[1]);
							if (u != null) {
								UfoController.getInstance().handleCommand("Mine " + splittedCommand[1]);
								break;
							}
						}
						break;
					case "Buildrobot":
						if (progress){
						//	System.out.println("robot: " + splittedCommand[1] + " telepes: " + splittedCommand[2]);

							Settler s = SettlerController.getInstance().getSettlerByName(splittedCommand[2]);
							if (s != null) {
								SettlerController.getInstance().handleCommand("Buildrobot " + splittedCommand[2] + " " + splittedCommand[1]);
								break;
							}
						}
						break;
					case "Buildgate":
						if (progress){
//							System.out.println("kapu: " + splittedCommand[1] + " telepes: " + splittedCommand[2]);

							Settler s = SettlerController.getInstance().getSettlerByName(splittedCommand[2]);
							if (s != null){
								SettlerController.getInstance().handleCommand("Buildgate " + splittedCommand[2] + " " + splittedCommand[1]);
							}
						}
						break;
					case "Buildbase":
						if (progress){
							if (progress){
							//	System.out.println("telepes: " + splittedCommand[1]);

								Settler s = SettlerController.getInstance().getSettlerByName(splittedCommand[1]);
								if (s != null){
									SettlerController.getInstance().handleCommand("Buildbase " + splittedCommand[1]);
								}
							}
						}
						break;
					case "Putdown":
						if (progress){
						//	System.out.println("obj nev: " + splittedCommand[1] + " telepes: " + splittedCommand[2]);

							Settler s = SettlerController.getInstance().getSettlerByName(splittedCommand[2]);
							if (s != null){
								SettlerController.getInstance().handleCommand("Putdown " + splittedCommand[2] + " " + splittedCommand[1]);
							}
						}
						break;
					case "Step":
						if (progress){
						//	System.out.println("step");
							if (random) {
								SolarSystem.getInstance().makeTurn();
								RobotController.getInstance().makeTurn();
								UfoController.getInstance().makeTurn();
							}
						}
						break;
					case "List":
						if (progress){
						//	System.out.println("list");
							SolarSystem.getInstance().getThings().forEach(t -> {
								try{
									if (t.nullLayer)
										writer.write("There is no layer\n");
								} catch (IOException e) {
									e.printStackTrace();
								}

							});

							SettlerController.getInstance().getSettlers().forEach(t -> {
								try{
									if (t.emptyCore)
										writer.write("Core is empty\n");
									if (t.fullInventory)
										writer.write("Inventory is full\n");
								} catch (IOException e) {
									e.printStackTrace();
								}
							});


							SolarSystem.getInstance().getThings().forEach(t -> {
								try {
									writer.write(t.List());
								} catch (IOException e) {
									e.printStackTrace();
								}
							});

							SettlerController.getInstance().getSettlers().forEach(s -> {
								s.getGates().forEach(g -> {
									try {
										writer.write(g.List());
									} catch (IOException e) {
										e.printStackTrace();
									}
								});
							});

							SettlerController.getInstance().getSettlers().forEach(s -> {

								try {
									writer.write(s.List());
								} catch (IOException e) {
									e.printStackTrace();
								}
							});
							RobotController.getInstance().getRobots().forEach(r -> {
								try {
									writer.write(r.List());
								} catch (IOException e) {
									e.printStackTrace();
								}
							});
							UfoController.getInstance().getUfos().forEach(u -> {
								try {
									writer.write(u.List());
								} catch (IOException e) {
									e.printStackTrace();
								}
							});
							if (messages.size() > 0)
								writer.write("+------------------+\n");
							messages.forEach(msg -> {
								try {
									writer.write(msg);
								} catch (IOException e) {
									e.printStackTrace();
								}
							});
						}
						break;
					case "Makeeruption":
						if (progress){
						//	System.out.println("aszteroida: " + splittedCommand[1] + " sugar: " + splittedCommand[2]);
							Thing th = SolarSystem.getInstance().getThingByName(splittedCommand[1]);
							SolarSystem.getInstance().makeSolarEruption(th, Integer.parseInt(splittedCommand[2]));
						}
						break;
					case "Abort":


						return;
					default:
						if (readboard == 0 && readnei == 0)
						//	System.out.println("Ismeretlen parancs");
						break;
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Compares the test result with the expected result.
	 * @param test1 The expected result
	 * @param test2 The result
	 * @return true if the result and the expected result is equal
	 * @throws IOException
	 */
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
			System.out.println("Test passed.");
			reader1.close();
			reader2.close();
			return true;
		}
		else
		{
			System.out.println("Test failed at line "+lineNum);

			System.out.println("Expected: "+line1+" and Gotten: "+line2+" at line "+lineNum);
			reader1.close();
			reader2.close();
			return false;
		}

	}

}
