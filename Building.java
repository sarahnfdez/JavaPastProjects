package assignment3;

public class Building {

 OneBuilding data;
 Building older;
 Building same;
 Building younger;
 
 public Building(OneBuilding data){
  this.data = data;
  this.older = null;
  this.same = null;
  this.younger = null;
 }
 
 public String toString(){
  String result = this.data.toString() + "\n";
  if (this.older != null){
   result += "older than " + this.data.toString() + " :\n";
   result += this.older.toString();
  }
  if (this.same != null){
   result += "same age as " + this.data.toString() + " :\n";
   result += this.same.toString();
  }
  if (this.younger != null){
   result += "younger than " + this.data.toString() + " :\n";
   result += this.younger.toString();
  }
  return result;
 }
 
 public Building addBuilding (OneBuilding obToAdd){
  // ADD YOUR CODE HERE
   int buildingAge = this.data.yearOfConstruction;
   int buildingHeight = this.data.height;
   Building a = new Building(obToAdd);
   int newBuildAge = obToAdd.yearOfConstruction;
   int newBuildHeight = obToAdd.height;
   
   if(newBuildAge < buildingAge){
     if(this.older == null){
       this.older = a;
     } else {
       Building b = this.older.addBuilding(obToAdd);
       this.older = b;
     }
   } else if (newBuildAge > buildingAge){
     if(this.younger == null){
       this.younger = a;
     } else {
       Building b = this.younger.addBuilding(obToAdd);
       this.younger = b;
     }
   } else {
     if(newBuildHeight <= buildingHeight){
       if(this.same == null){
         this.same = a;
       } else if (newBuildHeight > this.same.data.height){
         Building b = new Building(obToAdd);
         b.same = this.same;
         this.same = b;
         return this;
       } else {
         Building b = this.same.addBuilding(obToAdd);
       }
     } else {
       Building temp = new Building(this.data);
       a.older = this.older;
       a.younger = this.younger;
       a.same = this;
       a.same.older = null;
       a.same.younger = null;
     }
   }
   if(this.data.yearOfConstruction == a.data.yearOfConstruction && this.data.height < a.data.height){
     return a;
   } else {
     return this; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
   }
 }
 
 public Building addBuildings (Building b){
  // ADD YOUR CODE HERE
   OneBuilding c = b.data;
   Building h = this;
   
     Building co = b.older;
     Building cy = b.younger;
     Building cs = b.same;
     h = this.addBuilding(c);
     if(co != null){
         h = h.addBuildings(co);
     }
     if(cs != null){
        h = h.addBuildings(cs);
     }
     if(cy != null){
         h = h.addBuildings(cy);
     }

  return h; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
 }
 
 public Building removeBuilding (OneBuilding b){
  // ADD YOUR CODE HERE
   Building d = this;
   if(d.data.name == b.name && d.data.yearOfConstruction == b.yearOfConstruction && d.data.height == b.height && d.data.yearForRepair == b.yearForRepair && d.data.costForRepair == b.costForRepair){
     if(d.same != null){
       this.data = d.same.data;
       if(d.same.younger != null){
         this.addBuildings(d.younger);
       }
       if(d.same.older != null){
         this.addBuildings(d.older);
       }
       this.same = d.same.same;
       return this;
     } else if (d.same == null && d.older != null){
       this.same = d.older.same;
       Building temp1 = this.younger;
       this.data = d.older.data;
       this.younger = d.older.younger;
       if(temp1 != null){
         this.addBuildings(temp1);
       }
       Building temp2 = this.older;
       this.older = d.older.older;
       return this;
     } else if (d.same == null && d.older == null && d.younger != null){
       this.same = d.younger.same;
       this.data = d.younger.data;
       Building temp1 = this.older;
       this.older = d.younger.older;
       if(temp1 != null){
         this.addBuildings(temp1);
       }
       this.younger = d.younger.younger;
       return this;
     } else {
       this.data = null;
       this.younger = null;
       this.older = null;
     }
  }
   if(d.older != null){
     d.older.removeBuilding(b);
   }
   if(d.younger != null){
     d.younger.removeBuilding(b);
   }
   if(d.same != null){
     Building temp = d.same;
     if(d.same.data == b && d.same.older == null && d.same.younger == null && d.same.same == null){
       d.same = null;
     }
     temp.removeBuilding(b);
   }
  return this; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
 }
 
 public int oldest(){
  // ADD YOUR CODE HERE
   int o = this.data.yearOfConstruction;
   if(this.older == null){
     return o;
   } else {
     o = this.older.oldest();
   }
   return o; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
 }
 
 public int highest(){
  // ADD YOUR CODE HERE
   int h = this.data.height;
   int ho = 0;
   int hs = 0;
   int hy = 0;
   if(this.older == null && this.younger == null && this.same == null){
     return h;
   } else {
     if(this.older != null){
       ho = this.older.highest();
     }
     if(this.younger != null){
       hy = this.younger.highest();
     }
     if(this.same != null){
       hs = this.same.highest();
     }
     if(ho > h){
       h = ho;
     }
     if(hy > h){
       h = hy;
     }
     if (hs > h){
       h = hs;
     }
   }
  return h; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
 }
 
 public OneBuilding highestFromYear (int year){
  // ADD YOUR CODE HERE
   Building c = this;
   int h = 0;
   OneBuilding r = null;
   if(year < c.data.yearOfConstruction){
     if(c.older != null){
       c = c.older;
       r = c.highestFromYear(year);
     } else {
       return null;
     }
   }
   if(year > c.data.yearOfConstruction){
     if(c.younger != null){
       c = c.younger;
       r = c.highestFromYear(year);
     } else {
       return null;
     }
   }
   if(year == this.data.yearOfConstruction){
     h = this.data.height;
     r = this.data;
     if(c.same == null){
       return this.data;
     } else {
       while(c != null){
         if(c.data.height > h){
           r = c.data;
         }
         c = c.same;
       }
     }
   }
  return r; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
 }
 
 public int numberFromYears (int yearMin, int yearMax){
  // ADD YOUR CODE HERE
  int b = 0;
   if(yearMin > yearMax){
     return 0;
   } else {
     if(this.data.yearOfConstruction > yearMax && this.older != null){
       b = this.older.numberFromYears(yearMin, yearMax);
     }
     if(this.data.yearOfConstruction < yearMin && this.younger != null){
       b = this.younger.numberFromYears(yearMin, yearMax);
     }
     if(this.data.yearOfConstruction >= yearMin && this.data.yearOfConstruction <= yearMax){
       b = b+1;
       if(this.same != null){
         b = b+this.same.numberFromYears(yearMin, yearMax);
       }
       if(this.older != null){
         b = b+this.older.numberFromYears(yearMin, yearMax);
       }
       if(this.younger != null){
         b = b+this.younger.numberFromYears(yearMin, yearMax);
       }
     }
   }
  return b; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
 }
 
 public int[] costPlanning (int nbYears){
  // ADD YOUR CODE HERE
   int costs[] = new int[nbYears];
   if(this.data.yearForRepair >= 2018 && this.data.yearForRepair < 2018+nbYears){
     int place = this.data.yearForRepair - 2018;
     costs[place] = costs[place]+this.data.costForRepair;
     if(this.same != null){
       int same[] = this.same.costPlanning(nbYears);
       for(int k = 0; k < nbYears; k++){
         costs[k] = costs[k] + same[k];
       }
     }
     if(this.older != null){
       int older[] = this.older.costPlanning(nbYears);
       for(int o = 0;o< nbYears; o++){
         costs[o] = costs[o]+older[o];
       }
     }
     if(this.younger != null){
       int younger[] = this.younger.costPlanning(nbYears);
       for(int y =0;y<nbYears;y++){
         costs[y]=costs[y]+younger[y];
       }
     }
   } if (!(this.data.yearForRepair >= 2018 && this.data.yearForRepair < 2018+nbYears) && this.younger != null){
     int oy[] = this.younger.costPlanning(nbYears);
     for(int q = 0; q < nbYears;q++){
       costs[q] = costs[q]+oy[q];
     }
   } if (!(this.data.yearForRepair >= 2018 && this.data.yearForRepair < 2018+nbYears) && this.older != null){
     int oo[] = this.older.costPlanning(nbYears);
     for(int o = 0; o < nbYears;o++){
       costs[o] = costs[o] + oo[o];
     }
   } if(!(this.data.yearForRepair >= 2018 && this.data.yearForRepair < 2018+nbYears) && this.same != null){
     int os[] = this.same.costPlanning(nbYears);
     for(int s = 0; s < nbYears;s++){
       costs[s] = costs[s]+os[s];
     }
   }
  return costs; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
 }
}
