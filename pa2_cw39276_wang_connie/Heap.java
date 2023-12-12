/*
 * Name: <Connie Wang>
 * EID: <cw39276>
 */

// Implement your heap here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;

public class Heap {
    private ArrayList<City> minHeap;

    public Heap() {
        minHeap = new ArrayList<City>();
    }

    /**
     * buildHeap(ArrayList<City> cities)
     * Given an ArrayList of Cities, build a min-heap keyed on each City's minDist
     * Time Complexity - O(nlog(n)) or O(n)
     *
     * @param cities
     */
    public void buildHeap(ArrayList<City> cities) {
        int numCities = cities.size(); //get size of arraylist
        int index = (numCities/2)-1;  //find middle of arraylist to start from

        for(int i = index; i>=0; i--){
            heapify(cities, numCities, i);
        }
        /*System.out.println("gjh");
  for(int i =0; i<minHeap.size();i++){
            System.out.println(minHeap.get(i).getName() +" " +minHeap.get(i).getMinDist());
    
        }*/
    
    //citation: based off class
    }

    public void heapify(ArrayList<City> cities, int size, int index){
        int min = index;
        int left = 2*index+1; //left node
        int right = 2*index+2; //right node
       /* System.out.println("hgjgjhg");
    for(int i =0; i<cities.size();i++){
            System.out.println(cities.get(i).getName() +" " +cities.get(i).getMinDist());
        }
        */

        //left<root
        if(left < size && cities.get(left).getMinDist()<cities.get(index).getMinDist()){
            min = left;
        }
        //right<root
        if(right < size && cities.get(right).getMinDist()<cities.get(index).getMinDist()){
            min = right;
        }
        //root is not min
        if (min != index) { 
            //System.out.println("here");
            //swap without using collections.swap which would have been easier
            City temp = cities.get(index);
            cities.set(index, cities.get(min));
            cities.set(min, temp);

		    // Recursive heapify 
		    heapify(cities, size, min); 
        } 

        minHeap = cities;

    }

    /**
     * insertNode(City in)
     * Insert a City into the heap.
     * Time Complexity - O(log(n))
     *
     * @param in - the City to insert.
     */
    public void insertNode(City in) {
        // TODO: implement this method
        minHeap.add(in);
        int index = minHeap.size()-1;
        //swap with parent until in right place
        while(index>0 && minHeap.get(index).getMinDist() < minHeap.get((index-1)/2).getMinDist()){
            //swap
            City temp = minHeap.get(index);
            minHeap.set(index, minHeap.get((index-1)/2));
            minHeap.set((index-1)/2, temp);
           //update index
            index = (index-1)/2;
        }
    }

    /**
     * findMin()
     * Time Complexity - O(1)
     *
     * @return the minimum element of the heap.
     */
    public City findMin() {
        // TODO: implement this method
        return minHeap.get(0);
    }

    /**
     * extractMin()
     * Time Complexity - O(log(n))
     *
     * @return the minimum element of the heap, AND removes the element from said heap.
     */
    public City extractMin() {
        // TODO: implement this method
        City min = findMin();
        //move another city to index 0 and rebuild heap
        minHeap.set(0, minHeap.get(minHeap.size()-1));
        minHeap.remove(minHeap.size()-1);
        heapify(minHeap, minHeap.size(),0);
        return min;
    }

    /**
     * delete(int index)
     * Deletes an element in the min-heap given an index to delete at.
     * Time Complexity - O(log(n))
     *
     * @param index - the index of the item to be deleted in the min-heap.
     */
    public void delete(int index) {
        // TODO: implement this method
        //remove
        minHeap.remove(index);
        //rebuild heap
        index = (index-1)/2;
        if(index >=0){
            heapify(minHeap, minHeap.size(),index );
        }
    }

    /**
     * changeKey(City r, int newDist)
     * Changes minDist of City s to newDist and updates the heap.
     * Time Complexity - O(log(n))
     *
     * @param r       - the City in the heap that needs to be updated.
     * @param newDist - the new cost of City r in the heap (note that the heap is keyed on the values of minDist)
     */
    public void changeKey(City r, int newDist) {
        // TODO: implement this method
        minHeap.remove(r);
        r.setMinDist(newDist);
        insertNode(r);
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < minHeap.size(); i++) {
            output += minHeap.get(i).getName() + " ";
        }
        return output;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public ArrayList<City> toArrayList() {
        return minHeap;
    }
}
