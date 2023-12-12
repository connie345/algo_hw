/*
 * Name: Connie Wang
 * EID: cw39276
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


/**
 * Your solution goes in this class.
 *
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 *
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution. However, do not add extra import statements to this file.
 */
public class Program1 extends AbstractProgram1 {

    /**
     * Determines whether a candidate Matching represents a solution to the stable matching problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
    @Override
    public boolean isStableMatching(Matching problem) {
        ArrayList <ArrayList<Integer>> companyPrefs = (ArrayList <ArrayList<Integer>> )problem.getCompanyPreference();
        ArrayList <ArrayList<Integer>> internPrefs = (ArrayList <ArrayList<Integer>>)problem.getInternPreference().clone();
         //ArrayList<Integer> match = (ArrayList <Integer> )problem.getInternMatching().clone();
         ArrayList<Integer> match = problem.getInternMatching();
       //  System.out.println(match);
         // First type of instability
        for(int i = 0; i< match.size(); i++){
            int test = match.get(i);
            if(test == -1){
           //     System.out.println("unmatched intern: " + i);
                 for(int j = 0; j< match.size(); j++){
                     int comp = match.get(j);             
                    if(comp != -1){
                          if(companyPrefs.get(comp).indexOf(i)
                        < companyPrefs.get(comp).indexOf(j)){
                            System.out.println(comp + " prefers unmatched " + i + " over " + j);
                            return false;
                        }
                    }
                }
            }  
           
        }
        //Second type of instability
         for(Integer test: match){
                for(Integer comp: match){
                    if(test != -1 && comp != -1){
                        //company 1 = test
                        //company 1 current match = index of test
                        //company 2 = comp
                        //company 2 cuurent match = index of comp
                        if(companyPrefs.get(comp).indexOf(match.indexOf(test))
                        < companyPrefs.get(comp).indexOf(match.indexOf(comp)) && 
                        internPrefs.get(match.indexOf(test)).indexOf(comp)< 
                        internPrefs.get(match.indexOf(test)).indexOf(test)){
                            System.out.println("unstabe");
                            return false;
                        }
                    }
                }
        }
        return true;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_companyoptimal(Matching problem) {
        int m = problem.getCompanyCount();
        int n = problem.getInternCount();
        ArrayList <ArrayList<Integer>> companyPrefs = new ArrayList<ArrayList<Integer>>();
        ArrayList <ArrayList<Integer>> internPrefs = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> companyPos = new ArrayList<Integer>();
        ArrayList <Integer> freeComp = new ArrayList<Integer> (m);
        ArrayList <Integer> matches = new ArrayList<Integer> (n);

        for(int i = 0; i < n; i++){
            matches.add(-1);
            internPrefs.add((ArrayList<Integer>)problem.getInternPreference().get(i).clone());
        }
        for(int i = 0; i < m; i++){
            freeComp.add(i);
            companyPos.add(problem.getCompanyPositions().get(i));
            companyPrefs.add((ArrayList<Integer>)problem.getCompanyPreference().get(i).clone());
        }
    
        while(!freeComp.isEmpty()){
            ArrayList <Integer> holdR = new ArrayList<Integer> ();
            ArrayList <Integer> holdA = new ArrayList<Integer> ();
            for(Integer company: freeComp){
                int BestMatch= companyPrefs.get(company).get(0);
                int posAva = companyPos.get(company);
                
              //      int BestMatch= companyPrefs.get(company).get(0);
               // System.out.println("company: "+company);
                 //   System.out.println("positions left: " + posAva);
                    for(int i = 0; i< posAva ;i++){
                        if(!companyPrefs.get(company).isEmpty()){
                         BestMatch= companyPrefs.get(company).get(0);
                         companyPrefs.get(company).remove(0);
                   // System.out.println(BestMatch);
                            if(matches.get(BestMatch) == -1){
                            // System.out.println("free");
                                matches.set(BestMatch, company);
                            //  System.out.println(companyPos.get(company)-1);
                                companyPos.set(company,companyPos.get(company) -1);
                           //     System.out.println("posAva" +companyPos.get(company);
                                if(companyPos.get(company) <= 0){
                            //    System.out.println("out");
                                  holdR.add(company);
                                }
                            }
                            else{
                    if(internPrefs.get(BestMatch).indexOf(company) < 
                    internPrefs.get(BestMatch).indexOf(matches.get(BestMatch))){
                       //  System.out.println("company " + company + " swapped intern " +  matches.get(BestMatch)+ " for " + BestMatch);
                    
                            if(companyPos.get(matches.get(BestMatch))== 0){
                                holdA.add(matches.get(BestMatch));
                            }
                            companyPos.set(matches.get(BestMatch),companyPos.get(matches.get(BestMatch))+1); 
                            matches.set(BestMatch, company);
                            companyPos.set(company, companyPos.get(company)-1);
                            if(companyPos.get(company) == 0){
                                 holdR.add(company);
                             }  
                             
                    }
                    
                }
                        }
                    }
                
                
                    //     System.out.println(problem.getCompanyPreference());
                      
                
                
            }
//System.out.println("h" + holdR);
//System.out.println("a" + holdA);
            freeComp.removeAll(holdR);
            freeComp.addAll(holdA);
      // System.out.println(" f"  +freeComp);
       //System.out.println(!freeComp.isEmpty());
                  
        }
      // System.out.println("wtf" + companyPos);
      //ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(2,0,1));
        problem.setInternMatching(matches);
     //   System.out.println(problem.getCompanyPreference());
        return problem;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_internoptimal(Matching problem) {
        /* TODO implement this function */
                /* TODO implement this function */
        int m = problem.getCompanyCount();
        int n = problem.getInternCount();
        
        ArrayList <ArrayList<Integer>> companyPrefs = new ArrayList<ArrayList<Integer>>();
        ArrayList <ArrayList<Integer>> internPrefs = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> companyPos = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> pos = new ArrayList<ArrayList<Integer>>();
        ArrayList <Integer> freeIntern = new ArrayList<Integer> (n);
        ArrayList <Integer> freeComp = new ArrayList<Integer>();
        ArrayList <Integer> matches = new ArrayList<Integer> (n);

        for(int i = 0; i < n; i++){
            matches.add(-1);
            freeIntern.add(i);
            internPrefs.add((ArrayList<Integer>)problem.getInternPreference().get(i).clone());
        }
        for(int i = 0; i < m; i++){
           ArrayList <Integer> x = new ArrayList <Integer>();
           companyPrefs.add((ArrayList<Integer>)problem.getCompanyPreference().get(i).clone());
            companyPos.add(problem.getCompanyPositions().get(i));
            freeComp.add(problem.getCompanyPositions().get(i));
            pos.add(i,x );
        }
      int count = 0;
        //System.out.println("wtf"+companyPos);
        while( count <= n){
          /*   System.out.println(companyPos);
            System.out.println(freeComp);
            System.out.println("n: "+n);
            System.out.println("m: "+m);
           for (ArrayList <Integer> name : pos.values()) {
             System.out.print(name +"|");
            }*/
            //    System.out.println("\nrun " + count);
            count++;
            for(Integer intern: freeIntern)
            {
                if(matches.get(intern)==-1){
                   //    System.out.println(intern);
                    if(!internPrefs.get(intern).isEmpty()){
                        int BestMatch= internPrefs.get(intern).get(0);
                /*    System.out.println("BM: "+ BestMatch);
                    System.out.println("freecomp: "+ freeComp);
                    System.out.println("pos availabe: "+freeComp.get(BestMatch));*/
                        if(freeComp.get(BestMatch) != 0){
                                // System.out.println("free");
                            matches.set(intern, BestMatch);
                                //  System.out.println(companyPos.get(company)-1);
                            freeComp.set(BestMatch, freeComp.get(BestMatch)-1);
                            pos.get(BestMatch).add(intern);
                            
                                //    System.out.println("company " + BestMatch + " accepted intern " + intern);
                        }
                        else{
                            int min =99999;
                            for(int i = 0; i< companyPos.get(BestMatch);i++){
                                int temp = pos.get(BestMatch).get(i);
                                //          System.out.print(temp +" ");
                                if(companyPrefs.get(BestMatch).indexOf(temp) > companyPrefs.get(BestMatch).indexOf(min)){
                                    min = temp;
                                }
                            }
                            //   System.out.println(pos);
                            //     System.out.println(min);
                            int index = pos.get(BestMatch).indexOf(min);
                            //      System.out.println("ph: "+index);
                            if(companyPrefs.get(BestMatch).indexOf(intern) < 
                                companyPrefs.get(BestMatch).indexOf(min)){
                               //            System.out.println("company " + BestMatch + " swapped intern " + min + " for " + intern);
                    
                                  //  System.out.println("taken");
                                   freeIntern.set(min,min);
                                   matches.set(min, -1);
                                    matches.set(intern, BestMatch);
                                    pos.get(BestMatch).set(index, intern);
                                  
                            }
                        }
                        internPrefs.get(intern).remove(0);
                    }   
                }
            }         
        }
        //System.out.println(matches);
        problem.setInternMatching(matches);
        return problem;
    }
}