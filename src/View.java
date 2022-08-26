package Output;
import Tube.AdjStation;
import Output.ShortestPath;
import Output.Tube;

import java.util.ArrayList;
import java.util.Scanner;

public class View {
    Tube myTube;

    public View() throws Exception{
        myTube = new Tube();
        homeScreen();
    }

    public void homeScreen(){
        System.out.println("Welcome to the Output.Tube Map Planner. Please select an option below");
        System.out.println("(a) Plan Journey");
        System.out.println("(b) Settings");
        System.out.print("Selected Option: ");
        Scanner sc = new Scanner(System.in);
        String chosenOption = sc.next();
        if (chosenOption.charAt(0) == 'a'){
            planJourney();
        }
        else if (chosenOption.charAt(0) == 'b'){
            settings();
        }
    }

    public void planJourney(){
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter ID of Starting Output.Tube.Station: ");
        int startingStation = sc.nextInt();
        System.out.print("Enter ID of Destination Output.Tube.Station: ");
        int destinationStation = sc.nextInt();
        printShortestPath(startingStation,destinationStation);

    }

    public void printShortestPath(int startingId, int destinationId){
        ShortestPath path = new ShortestPath(myTube,startingId,destinationId);
        path.findShortestPath();
        System.out.println("\nSUMMARY OF JOURNEY");
        if (path.getShortestPath().size() != 0){
            System.out.println("Your Output.Tube.Route Preference: " + ShortestPath.getPriority().getRoutePreference());
            System.out.println("Journey Time (Minutes): " + path.getTotalTime());
            System.out.println("Total Stops: " + path.getNrStops());
            System.out.println("Total Changes: " + path.getNrChanges());
            printJourney(path.getShortestPath(),startingId);
        }
        else{
            System.out.println("No route available");
        }
        System.out.println("");
        homeScreen();

    }

    public void printJourney(ArrayList<AdjStation> shortestPath, int startingId){
        System.out.println("\nStart at " + myTube.getStation(startingId).getName());
        int currentId = shortestPath.get(shortestPath.size() - 1).getAdjacentId();
        int currentLineId = shortestPath.get(shortestPath.size() - 1).getLineId();
        int currentRouteId = shortestPath.get(shortestPath.size() - 1).getRouteId();
        for (int i = shortestPath.size() - 1; i >= 0; i--){
            int stationId = shortestPath.get(i).getAdjacentId();
            int lineId = shortestPath.get(i).getLineId();
            int routeId = shortestPath.get(i).getRouteId();
            if (lineId != currentLineId){
                changeLineOrRoute(currentId,currentLineId);
                System.out.println("Change Output.Tube.Line");
            }
            else if (routeId != currentRouteId){
                changeLineOrRoute(currentId,currentLineId);
                System.out.println("Change Output.Tube.Route");
            }

            if (i == 0){
                changeLineOrRoute(stationId,lineId);
            }
            currentId = stationId;
            currentLineId = lineId;
            currentRouteId = routeId;

        }

    }

    public void changeLineOrRoute(int currentId, int currentLineId){
        String lineName = myTube.getLine(currentLineId).getName();
        String stationName = myTube.getStation(currentId).getName();
        System.out.println("Take " + lineName + " Output.Tube.Line " + "to " + stationName);
    }

    public void settings(){
        System.out.println("\nPlease select an option below");
        System.out.println("(a) Change Output.Tube.Route Preference");
        System.out.println("(b) Close off a Output.Tube.Station");
        System.out.print("Selected Option: ");
        Scanner sc = new Scanner(System.in);
        String chosenOption = sc.next();
        if (chosenOption.charAt(0) == 'a'){
            changeRoutePreference();
        }
        else if(chosenOption.charAt(0) == 'b'){
            closeLine();
        }
    }

    public void changeRoutePreference(){
        System.out.println("\nYour current route preference is: " + ShortestPath.getPriority().getRoutePreference());
        System.out.println("Please select an option below");
        System.out.println("(1) Fastest Output.Tube.Route");
        System.out.println("(2) Fewest Stops");
        System.out.println("(3) Fewest Changes");
        System.out.print("Selected Option: ");
        Scanner sc = new Scanner(System.in);
        int chosenOption = sc.nextInt();
        ShortestPath.setPriority(chosenOption);
        System.out.println("");
        homeScreen();
    }

    public void closeLine(){
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter ID of Output.Tube.Station: ");
        int chosenStation = sc.nextInt();
        System.out.print("Enter ID of Output.Tube.Line: ");
        int chosenLine = sc.nextInt();
        myTube.removeStation(chosenStation,chosenLine);
        System.out.println("");
        homeScreen();
    }


}
