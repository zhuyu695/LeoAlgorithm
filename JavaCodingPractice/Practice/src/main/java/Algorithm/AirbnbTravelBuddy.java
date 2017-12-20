package Algorithm;

import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */


// Travel Buddies
// A is TB of B if A shared 50% or more of WL with B
// score is number of cities in common

// John,Amsterdam,Barcelona,London,Prague
// Yu,Shanghai,Hong Kong,Moscow,Sydney,Melbourne
// Liz,London,Boston,Amsterdam,Madrid
// Stan,Barcelona,Prague,London,Sydney,Moscow
// Me,Amsterdam,Barcelona,London,Prague
  
// getRecommendations(int numRecommendations) -> recommendations are cities that are not in my WL but in TB WL  
  
// Hey Yu! Can you see this?

class AirbnbTravelBuddy {
  class Buddy {
    String name;
    int score;
    public Buddy(String n, int s) {
      name = n;
      score =s;
    }
  }
  
  public List<String> findTravelBuddies(String myList, String[] others) {
    String[] myDest = myList.split(",");
    Set<String> interest = new HashSet<String>();
    List<Buddy> res = new ArrayList<Buddy>();
    
    for (int i=1; i<myDest.length; ++i) {
      interest.add(myDest[i]);
    }
    
    for (int i=0; i<others.length; ++i) {
      String[] other = others[i].split(",");
      String name = other[0];
      double percentage = 0.0;
      double score = 0.0;
      for (int j=1; j<other.length; ++j) {
        if (interest.contains(other[j])) {
          ++score;
        }
      }
      percentage = score / (other.length - 1);
      
      if (percentage < 0.5) {
        continue;
      } else {
        res.add(new Buddy(name, (int)score));
      }
    }
    
    Collections.sort(res, new Comparator<Buddy>() {
      public int compare(Buddy a, Buddy b) {
        return b.score - a.score;
      }
    });
    
    List<String> buddies = new ArrayList<String>();
    for (Buddy b : res) {
      buddies.add(b.name);
    }
    return buddies;
  }
  
  public static void main(String[] args) {
    //   John, Stan, Liz

    String myList = "Me,Amsterdam,Barcelona,London,Prague";
    String[] others = {
      "Katy,Barcelona,Prague",
      "John,Amsterdam,Barcelona,London,Prague",
      "Yu,Shanghai,HongKong,Moscow,Sydney,Melbourne",
      "Liz,London,Boston,Amsterdam,Madrid",
      "Stan,Barcelona,Prague,London,Sydney,Moscow"};
    
    
    AirbnbTravelBuddy s = new AirbnbTravelBuddy();
    System.out.println(s.findTravelBuddies(myList, others).toString());
  }
}