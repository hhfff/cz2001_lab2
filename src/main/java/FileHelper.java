import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class FileHelper {
    public static LinkedList<Integer> readHospitalFile(File file){
        int h=0;
        int inputVertex=0;

        try
        {
            LinkedList<Integer> scrList=new LinkedList<>();
            Scanner scStream = new Scanner(file);
            //System.out.println( "The file contains:" );
            scStream.next();
            h= scStream.nextInt();
            while ( scStream.hasNext() )
            {
                inputVertex = scStream.nextInt();
                //System.out.println( inputVertex );
                scrList.add(inputVertex);
            }
            scStream.close();
            System.out.println(h);
            System.out.println(scrList);
            return scrList;
        }
        catch ( FileNotFoundException e ) {
            System.out.println( "File Error!" + e.getMessage() );
            //System.exit( 0 );
        }

        return null;
    }
    public static void writeHospitalFile(String fileName) {
        Scanner sc = new Scanner(System.in);
        int h;
        System.out.println("input h");
        h=sc.nextInt();
        try {
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("hpspital File created: " + myObj.getName());
            } else {
                System.out.println("hospital File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter fwstream = new FileWriter(fileName);
            BufferedWriter bwStream = new BufferedWriter( fwstream );
            PrintWriter    myWriter = new PrintWriter(    bwStream  );

            myWriter.println("# "+h);
            System.out.println("input hospital's index");
            for(int i=0; i<h; i++) {
                myWriter.println(sc.nextInt());
            }
            myWriter.close();
            System.out.println("Successfully wrote to the fileName.");
        } catch (IOException e) {
            System.out.println("An error occurred in creating hospital input fileName.");
            e.printStackTrace();
        }
    }
    public static ArrayList<ArrayList<Integer>> readRealNetworkData(File file,int n) {
        ArrayList<ArrayList<Integer>> adjacencyList =new ArrayList<>();
        int u=0;
        try
        {
            Scanner scanner = new Scanner(file);
            //System.out.println( "The file contains:" );
            scanner.nextLine();
            scanner.nextLine();
            scanner.nextLine();
            scanner.nextLine();


            for (int i = 0; i < n; i++) {
                adjacencyList.add(new ArrayList<Integer>());
            }
            while ( scanner.hasNext() ) {
                u = scanner.nextInt();
                adjacencyList.get(u).add(scanner.nextInt());

            }
            scanner.close();
            return adjacencyList;
        }
        catch ( FileNotFoundException e )
        {
            System.out.println( "File Error!" + e.getMessage() );
            System.exit( 0 );
        }

        return null;
    }
    public static void writeToFile(int dist[], int pred[], int n, String fileName) {

        try {
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("output File created: " + myObj.getName());
            } else {
                System.out.println("output File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter fwstream = new FileWriter(fileName);
            BufferedWriter bwStream = new BufferedWriter( fwstream );
            PrintWriter myWriter = new PrintWriter(bwStream);

            for(int i=0; i<n; i++) {
                if(dist[i] != Integer.MAX_VALUE) {

                    int j=i;
                    while(pred[j]>=0) {
                        j = pred[j];

                    }
                    myWriter.println(i +"-> hospital " + j+ "; Distance = "+ dist[i]);
                    myWriter.println("path: ");
                    j=i;
                    while(pred[j]>=0) {
                        myWriter.print(i);
                        j = pred[j];
                        myWriter.print(" --> "+j);
                    }
                    myWriter.println();
                    myWriter.println();
                }

            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred in creating hospital input file.");
            e.printStackTrace();
        }
    }



}
