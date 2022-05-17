import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class project2{

  //state
  static ArrayList<ArrayList<String>> list = new ArrayList<>();
  //zip
  static ArrayList<ArrayList<String>> list2 = new ArrayList<>();
  //output
  static ArrayList<ArrayList<String>> list3 = new ArrayList<>();
  //tmp array for swaps
  static ArrayList<String> tmp;

  public static void main(String []args){
    //State from both files but state og from state
    //Capital from state file
    //number of zipcodes from zip files
    //estimated pop from zip file
    //avg of avg wages from zip file
    String ht = "";
    //path to files
    String path2 = "upload/"+args[0];
    String path1 = "upload/"+args[1];
    String line = "";
    //header for table
    String []header = {"State","Capital","Number_of_zipcodes","Total_Estimated_Population","Average_AvgWages"};
    //date for timestamp
    Date date = new Date();
    SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");

    try{// parse state list save to arraylist of lists
      BufferedReader br = new BufferedReader(new FileReader(path1));
      while((line = br.readLine())!=null){
        String[] row = line.split(",");
        tmp =  new ArrayList<String>();
        Collections.addAll(tmp, row);
        list.add(tmp);
      }
    }catch (FileNotFoundException e){
      e.printStackTrace();
    }catch (IOException e){
      e.printStackTrace();
    }

    try{// parse zip list save to arraylist of lists
      BufferedReader br2 = new BufferedReader(new FileReader(path2));
      while((line = br2.readLine())!=null){
        String[] row = line.split(",");
        tmp =  new ArrayList<String>();
        Collections.addAll(tmp, row);
        list2.add(tmp);
      }
    }catch (FileNotFoundException e){
      e.printStackTrace();
    }catch (IOException e){
      e.printStackTrace();
    }

    
        
    try{  
    tmp =  new ArrayList<String>();
    Collections.addAll(tmp, header);
    list3.add(tmp);

    //make the table
    for(int i=1;i<list.size();i++){
      //state from state, capital from state, zipcodes from state to zip,
      String row[] = {list.get(i).get(1), list.get(i).get(3), getZip(list.get(i).get(1)), getPop(list.get(i).get(1)), getAvg(list.get(i).get(1))};
      tmp = new ArrayList<String>();
      Collections.addAll(tmp,row);
      list3.add(tmp);
    }
    sortArr();
    //output table
    System.out.print("<table border='1'>");
    ht+="<table border='1'>";
    System.out.print("<tr><td>State</td><td>Capital</td><td>Number_of_zipcodes</td><td>Total_Estimated_Population</td><td>Average_AvgWages</td><tr>");
    ht+="<tr><td>State</td><td>Capital</td><td>Number_of_zipcodes</td><td>Total_Estimated_Population</td><td>Average_AvgWages</td><tr>";
    //loop through output list while converting to table html format
    for(int i=1;i<list3.size();i++){
      if(!list3.get(i).get(3).equals("0"))
      System.out.print("</tr>");
      ht+="</tr>";
      for(int j=0;j<1;j++){
        //used to format the population and wages
        Formatter frm = new Formatter();
        Formatter frm2 = new Formatter();
        if(!list3.get(i).get(3).equals("0")){
          //prints in order, State, Capital, Number of Zipcodes, Total estimated population, Average of avg wages
          System.out.print("<td>"+list3.get(i).get(0)+"</td>"
            +"<td>"+list3.get(i).get(1)+"</td>"
            +"<td align='right'>"+list3.get(i).get(2)+"</td>"
            +"<td align='right'>"+frm.format("%,d", Integer.parseInt(list3.get(i).get(3))).toString()+"</td>"
            +"<td align='right'>"+"$"+list3.get(i).get(4)+"</td><tr>");
          //savesin order, State, Capital, Number of Zipcodes, Total estimated population, Average of avg wages for html file
          ht+="<td>"+list3.get(i).get(0)+"</td>"
          +"<td>"+list3.get(i).get(1)+"</td>"
          +"<td align='right'>"+list3.get(i).get(2)+"</td>"
          +"<td align='right'>"+frm2.format("%,d", Integer.parseInt(list3.get(i).get(3))).toString()+"</td>"
          +"<td align='right'>"+"$"+list3.get(i).get(4)+"</td><tr>";
        }
      }  
    }

    //print end of the table at the same time saving to html
    System.out.print("</table><br><br>");
    ht+="</table><br><br>";
    //Print out max avg wage
    System.out.print(getMaxAvg());
    ht+=getMaxAvg();
    //print minimum population
    System.out.print("<br>"+ getMinPop());
    ht+="<br>"+ getMinPop();

    //Output Date;
    System.out.print("<br><br>This java program was run at "+time.format(date));
    ht+="<br><br>This java program was run at "+time.format(date);
    //Make, Write, Save HTML Filez
    try{
      File html = new File("../CPS3525/Project2_results.html");
      html.createNewFile();
    }catch (IOException e) {
      e.printStackTrace();
    }

    try {
      FileWriter myWriter = new FileWriter("../CPS3525/Project2_results.html");
      myWriter.write(ht);
      myWriter.close();
    }catch (IOException e) {
      e.printStackTrace();
    }

    //Catch index out of bounds exception which is due to incorrect columns in csv file.
    }catch (IndexOutOfBoundsException e){
    if(list2.get(0).size()!=21)
      System.out.print("<br>Your zipcode.csv file does have the correct columns (21)");
      ht+= "<br>Your zipcode.csv file does not have the correct amount of columns (21)";
      
    if(list.get(0).size()!=8)
      System.out.print("<br>Your state.csv file does not have the correct columns (8)");
      ht+="<br>Your state.csv file does not have the correct amount of columns (8)";
    }
  }


  //Get zipcode method from zip file and count
  public static String getZip(String s){
    int count =0;
    for(int i=0;i<list2.size();i++){
      if(list2.get(i).get(4).equals(s))
      count++;
    }
    return ""+count;
  }
  //get population from zipcode file and add total population, by comparing state 
  public static String getPop(String s){
    int count =0, i=1;
    for(i=1;i<list2.size();i++){
      if(list2.get(i).get(4).equals(s)){
        try{
          count+=Integer.parseInt(list2.get(i).get(18).trim());
        }catch (NumberFormatException e){
          count+=0;
        }
      }
    }
    return ""+count;
  }

  //get the avg of each state avg, by comparing state from state to zip
  public static String getAvg(String s){
    Formatter form = new Formatter();
    double wage=0;
    int count=0;
    for(int i=0;i<list2.size();i++){
      if(list2.get(i).get(4).equals(s)){
        if(list2.get(i).size()>19){
          try{
            wage+=Double.parseDouble(list2.get(i).get(20));
          }catch (NumberFormatException e){
            count=count-1;
          }
          count=count+1;
        }
      }
    }
    wage=wage/count;
    return form.format("%,.1f", wage).toString();
  }

  //get min pop by traversing through zip file
  public static String getMinPop(){
    Formatter form = new Formatter();
    Formatter form2 = new Formatter();
    String s = "";
    int index=0;
    double min=Double.MAX_VALUE,tmp=0;
    for(int i=1;i<list2.size();i++){
      if(list2.get(i).size()>19){
        try{
          tmp =Double.parseDouble(list2.get(i).get(18));
        }catch(NumberFormatException e){
          tmp = Double.MAX_VALUE;
        }
        if(tmp<min){
          min = Double.parseDouble(list2.get(i).get(18));
          index = i;
        }
      }  
    }
    s = "Zipcode "+list2.get(index).get(1)+ 
    " at "+ list2.get(index).get(3)+ 
    ", "+list2.get(index).get(4)+ 
    " has the minimum population " + form.format("%,d",Integer.parseInt(list2.get(index).get(18))).toString()+
    " with AvgWages $"+ form2.format("%,.1f", Double.parseDouble(list2.get(index).get(20)))+".";
    return s;
  }

  //get max avg by traversing through zip file
  public static String getMaxAvg(){
    Formatter form = new Formatter();
    Formatter form2 = new Formatter();
    String s = "";
    int index=0;
    double max=0,tmp=0;
    for(int i=1;i<list2.size();i++){
      if(list2.get(i).size()>19){
        try{
          tmp =Double.parseDouble(list2.get(i).get(20));
        }catch(NumberFormatException e){
          tmp =0;
        }
        if(tmp>max){
          max = Double.parseDouble(list2.get(i).get(20));
          index = i;
        }
      }  
    }
    s = "Zipcode "+list2.get(index).get(1)+ 
    " at "+ list2.get(index).get(3)+ 
    ", "+list2.get(index).get(4)+ 
    " has the maximum AvgWages $"+ form.format("%,.1f", Double.parseDouble(list2.get(index).get(20)))+ 
    " with population " + form2.format("%,d",Integer.parseInt(list2.get(index).get(18))).toString()+".";
    return s;
  }

  //simple selection sort based on avgwage
  public static void sortArr(){
    for(int i=1;i<list3.size()-1;i++){
      int min=i;
      for(int j=i+1;j<list3.size();j++){
        double a = Double.parseDouble(list3.get(j).get(4).replace(",",""));
        double b = Double.parseDouble(list3.get(min).get(4).replace(",",""));
        if(a>b)
          min = j;
      }
      tmp = list3.get(min);
      list3.set(min, list3.get(i));
      list3.set(i,tmp);
    }
  }

}
