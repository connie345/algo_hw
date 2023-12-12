/*
 * Name: <Connie Wang>
 * EID: <cw39276>
 */

// Implement your algorithms here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;

public class Program2 {
    private ArrayList<City> cities;  // this is a list of all Cities, populated by Driver class
    private Heap minHeap;

    // additional constructor fields may be added, but don't delete or modify anything already here
    public Program2(int numCities) {
        minHeap = new Heap();
        cities = new ArrayList<City>();
    }

    /**
     * findMinimumRouteDistance(City start, City dest)
     *
     * @param start - the starting City.
     * @param dest  - the end (destination) City.
     * @return the minimum distance possible to get from start to dest.
     * Assume the given graph is always connected.
     */
    public int findMinimumRouteDistance(City start, City dest) {
        // TODO: implement this function
        /*
        start = cities.get(0);
        dest = cities.get(8);
         System.out.println(start.getName() + " to " + dest.getName());
         */
        //set start dist to 0
        start.setMinDist(0) ;
        //SPT to track what is and isn't in minheap
        ArrayList<City> SPT = new ArrayList<City>();
        ArrayList<City> city = new ArrayList<City>();
        for(int i = 0; i< cities.size(); i++){
            city.add(cities.get(i));
        }
        SPT.add(start);
        //build heap
        minHeap.buildHeap(city);
        //followed Dijkstra algorithm
        while(!(minHeap.toArrayList().size()==0)){
            //get city
            City ext = minHeap.extractMin();
            ArrayList <City> neighbor = ext.getNeighbors();
            ArrayList <Integer> weight = ext.getWeights();
            SPT.add(ext);
            //for each adjacent city
            for( int i = 0; i< neighbor.size(); i++){
                City next = neighbor.get(i);
                //update distance
                if(!SPT.contains(next)){
                    int newDist = ext.getMinDist()+ weight.get(i);
                    int currDist = next.getMinDist();
                    if (newDist < currDist){
                        minHeap.changeKey(next, newDist);
                    }
                }
            }
        }
        return dest.getMinDist();
    }

    /**
     * findMinimumLength()
     *
     * @return The minimum total optical line length required to connect (span) each city on the given graph.
     * Assume the given graph is always connected.
     */
    public int findMinimumLength() {
        // TODO: implement this function
        int[] rets = new int [cities.size()];
        int[] keys = new int [cities.size()];
        cities.get(0).setMinDist(0) ;
        //tracking array 
        ArrayList<City> checked = new ArrayList<City>();
        ArrayList<City> cityStat = new ArrayList<City>();
        ArrayList<City> city = new ArrayList<City>();
       // ArrayList<Integer> edge = new ArrayList<Integer>();
        for(int i = 0; i< cities.size(); i++){
            cityStat.add(cities.get(i));
            city.add(cities.get(i));
            keys[i] = Integer.MAX_VALUE;
        }
        city.get(0).setMinDist(0) ;
        checked.add(city.get(0));
        
        //build heap
        minHeap.buildHeap(city);
        //while heap not empty
        while(!(minHeap.toArrayList().size()==0)){
            City ext = minHeap.extractMin();
           // System.out.println("city : "+ext.getName());
            ArrayList <City> neighbor = ext.getNeighbors();
            ArrayList <Integer> weight = ext.getWeights();
            checked.add(ext);
            for( int i = 0; i< neighbor.size(); i++){
                City next = neighbor.get(i);
                //System.out.println("checking neighbor : "+next.getName());
                if(!checked.contains(next)){
                 //   edge.add(weight.get(i));
                    int newDist = weight.get(i);
                    int currDist = next.getMinDist();
                    if(keys[cityStat.indexOf(next)]>weight.get(i)){
                        minHeap.changeKey(next, newDist);
                        rets[cityStat.indexOf(next)] = weight.get(i);
                        keys[cityStat.indexOf(next)] = weight.get(i);
                    }
                    /*
                    for(int k = 0; k< rets.length; k++){
                        System.out.print(rets[k]+" ");
                    }
                    System.out.println();
                    */
                }
               
            }
        }
        int r = 0;
        
        for(int i = 0; i< rets.length; i++){
            //System.out.println(rets[i]);
            r += rets[i];
        }
        
        return r;
    }

    //returns edges and weights in a string.
    public String toString() {
        String o = "";
        for (City v : cities) {
            boolean first = true;
            o += "City ";
            o += v.getName();
            o += " has neighbors ";
            ArrayList<City> ngbr = v.getNeighbors();
            for (City n : ngbr) {
                o += first ? n.getName() : ", " + n.getName();
                first = false;
            }
            first = true;
            o += " with distances ";
            ArrayList<Integer> wght = v.getWeights();
            for (Integer i : wght) {
                o += first ? i : ", " + i;
                first = false;
            }
            o += System.getProperty("line.separator");

        }

        return o;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public Heap getHeap() {
        return minHeap;
    }

    public ArrayList<City> getAllCities() {
        return cities;
    }

    // used by Driver class to populate each City with correct neighbors and corresponding weights
    public void setEdge(City curr, City neighbor, Integer weight) {
        curr.setNeighborAndWeight(neighbor, weight);
    }

    // used by Driver.java and sets cities to reference an ArrayList of all RestStops
    public void setAllNodesArray(ArrayList<City> x) {
        cities = x;
    }
}
