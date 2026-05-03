import java.io.*;
import java.util.*;

public class DBMSproject {
////////////////////////////////////MAIN METHOD///////////////////////////////////////////    
    public static void main(String[] args) {
        System.out.println(">> Welcome to Database Management System.\n   Enter \"HELP\" for the syntax details \n");
        try (Scanner sc1 = new Scanner(System.in)) {
            while (true) {
                
                System.out.print(">> ");
                String input2 = (sc1.nextLine().trim());
                String input = "";

                //--------to remove extra spaces--------\\
                for(int i = 0; i < input2.length(); i++){
                    char c = input2.charAt(i);
                    char cf = 0;
                    if(i != input2.length()-1)
                        cf = input2.charAt(i+1);
                    if (c == ' '){
                        if(cf == ' '){
                            continue;
                        }
                    }
                    input += c;
                }

                if ((input.toUpperCase()).startsWith("CREATE TABLE")) {
                    Create(input);
                }
                else if ((input.toUpperCase()).startsWith("DROP") && (input.toUpperCase()).endsWith("TABLE") ) {
                    String tableName = input.substring(input.indexOf(' '), input.lastIndexOf(' ')).trim();
                    if (isValid(tableName)) {
                        DROP(tableName);
                    }
                }
                else if ((input.toUpperCase().trim()).equals("SHOW ALL")) {
                    show();
                }
                else if ((input.toUpperCase()).startsWith("INSERT IN TABLE") && (input.toUpperCase()).contains("VALUES")) {
                    INSERT(input);
                }
                else if ((input.toUpperCase()).startsWith("SELECT FROM TABLE")) {
                    SELECT(input);
                }
                else if ((input.toUpperCase()).startsWith("UPDATE") && (input.toUpperCase()).contains("SET") && (input.toUpperCase()).contains("WHERE")) {
                    UPDATE(input);
                }
                else if ((input.toUpperCase()).startsWith("DELETE FROM TABLE") && (input.toUpperCase()).contains("HAVING")) {
                    DELETE(input);
                }
                else if ((input.toUpperCase()).equals("HELP")) {
                    HELP();
                }
                else if ((input.toUpperCase()).equals("EXIT")) {
                    System.out.println("exiting ..");
                    break;
                }
                else System.out.println("Invalid Prompt");
            }       }
}


//////////////CREATE/////////////////////////////////////////////////////////////////////////////

public static void Create(String input) {
    String Prefix = "CREATE TABLE";
    if (input.indexOf("(") > input.indexOf(")")) {
        System.out.println("Invalid CREATE syntax. Missing columns definition.");
        return;
    }

    String tb_name = input.substring(Prefix.length(), input.indexOf('(')).trim();
    String tb_header = input.substring(input.indexOf('(') + 1, input.indexOf(')')).trim();


    if (tb_name.isEmpty()) {
        System.out.println("Table name cannot be empty");
        return;
    }


    if (tb_header.isEmpty()) {
        System.out.println("Column list cannot be empty");
        return;
    }


    if (tb_header.contains(",,")) {
        System.out.println("Invalid column definition: consecutive commas found");
        return;
    }


    if (tb_header.startsWith(",") || tb_header.endsWith(",")) {
        System.out.println("Invalid column definition: leading/trailing comma found");
        return;
    }

    String[] hed = tb_header.split(",");
    if (hed.length == 0) {
        System.out.println("At least one column required");
        return;
    }
    if (isValid(tb_name)) {
            try{
            File myfile = new File(tb_name+".csv");
            
            if (myfile.createNewFile()){
                PrintWriter pw = new PrintWriter(myfile);
                for (int i = 0; i < hed.length; i++) {
                    pw.print(hed[i].trim());
                    if(i != hed.length-1){
                        pw.print(',');
                    }
                }
                pw.println();
                
                pw.close();
                
                System.out.println("Table created successfully ...");
            }else {
                System.out.println("File already existed");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
        else System.out.println("invalid table name");
} 

//////-----------------------------------------------------------------------------------//////



/////////////////// DROP //////////////////////////////////////////////////////////////////////
public static void DROP(String tableName){
    Scanner sc = new Scanner(System.in);
    System.out.println("going to delete \"" + tableName + "\" table.");
        
        System.out.print("are you sure to want to continue (Y/N)");
        String input = sc.next();
        if (input.toUpperCase() .equals("Y")) {
            try{
                File f = new File(tableName + ".csv");
                
                if(!f.createNewFile()){
                    boolean isDeleted = f.delete();
                    if(isDeleted)
                    System.out.println("table deleted successfully..");
                    else
                    System.out.println("Error! Failed to delete " + tableName + " file..");
                }
                else{
                    f.delete();
                    System.out.println("File not found!");
                }
                
            }catch(IOException ed){
                System.out.println(ed.getMessage());
            }
            
        } 
        else if (input.toUpperCase() .equals("N")) {
            System.out.println("not deleted table .. ");
            
        }
        else System.out.println("invalid input. Try again");

    
}
///////-----------------------------------------------------------------------------------///////




/////////////////////SHOW//////////////////////////////////////////////////////////////////////
public static void show(){

    try{
        File folder = new File("./");
        String[] allFiles = folder.list();
        for (int i = 0; i < allFiles.length; i++)
            if (allFiles[i].contains(".csv"))
                System.out.println(allFiles[i].replaceAll(".csv",""));
    }catch(Exception ea){
        System.out.println(ea.getMessage());
    }
    
}
/////-----------------------------------------------------------------------------------///////




/////////////////////INSERT/////////////////////////////////////////////////////////////////////
public static void INSERT(String input){
    String Prefix = "INSERT IN TABLE";
    String Contain = "VALUES";
    String tb_name = input.substring(Prefix.length(),(input.toUpperCase().indexOf(Contain))).trim();
    System.out.println(tb_name);
    String tb_row = input.substring(input.indexOf('(') + 1, input.indexOf(')')).trim();

    boolean hasQuotes = tb_row.charAt(0) == '\"'; 

    String row = "";
    for (int i = 0; i < tb_row.length(); i++) {
        char c = tb_row.charAt(i);
        if (c == ' ') {
            continue;
        } else if (!hasQuotes && (c == ',' || i == 0 || i == tb_row.length() )) { 
            row += "\""; // Add opening quote
        }
        row += c; 
        if (!hasQuotes && (c == ',' || i == tb_row.length()-1)) { 
            row += "\""; // Add closing quote
        }
    }


    try{
        File myfile = new File(tb_name+".csv");

        if (!myfile.createNewFile()){
            FileWriter fw = new FileWriter(myfile, true);
            fw.write(row + "\n");
                    
        fw.close();

        System.out.println("Values inserted successfully ...");
        }else {
             myfile.delete();
             System.out.println("ERROR! File not found..");
        }
    }catch(Exception e){
            System.out.println(e.getMessage());
    }

}
/////-----------------------------------------------------------------------------------///////




/////////////////////SELECT/////////////////////////////////////////////////////////////////////
public static void SELECT(String input){
    String Prefix = "SELECT FROM TABLE";
    String Contain = "HAVING";
    String Contain2 = "SORT BY";

    String tb_name;
    String column = "";
    String value = "";
    String columnS = "";

    if(input.toUpperCase().indexOf(Contain) > input.toUpperCase().indexOf(Contain2) && input.toUpperCase().indexOf(Contain2) != -1){
        System.out.println("Invalid Syntax");
        return;
    }

    boolean sort = false;
    if(input.toUpperCase().contains(Contain)){
        tb_name = input.substring(Prefix.length(),(input.toUpperCase().indexOf(Contain))).trim();
        column = input.substring((input.toUpperCase().indexOf(Contain) + Contain.length()),(input.indexOf('='))).trim();

        if (input.contains(Contain2)){
            value = input.substring((input.indexOf('=')+1),(input.toUpperCase().indexOf(Contain2) )).trim();
            columnS = input.substring((input.toUpperCase().indexOf(Contain2) + Contain2.length())).trim();
            sort = true;
        }
        else{
            value = input.substring((input.indexOf('=')+1)).trim();
        }
    }
    else if (input.toUpperCase().contains(Contain2)){
        tb_name = input.substring(Prefix.length(),(input.toUpperCase().indexOf(Contain2))).trim();
        System.out.println(tb_name);
        columnS = input.substring((input.toUpperCase().indexOf(Contain2) + Contain2.length())).trim();
        sort = true;
    }
    else {
        tb_name = input.substring(Prefix.length()).trim();
    }

    try{
        File check = new File(tb_name+".csv");
        if(check.createNewFile()){
            System.out.println("Error! file not found");
            check.delete();
            return;
    }
    }catch(Exception e){
        System.out.println(e.getMessage());
    }
    

    String tempArray[][] = fileToArray(tb_name);

    int selCol = -1;
    if(input.contains(Contain)){        
            for(int i = 0; i < tempArray[0].length; i++){
                if(tempArray[0][i].equalsIgnoreCase(column)){
                    selCol = i;
                    break;
                }
            }
            if(selCol == -1){
                System.out.println("Provided Column Value : "+ column +" does not exist");
                return;
            }
    }

    if (sort){
        System.out.println("Start sorting");
        int sortCol = -1;
        for(int i = 0; i < tempArray[0].length; i++){
            if(tempArray[0][i].equalsIgnoreCase(columnS)){
                sortCol = i;
                break;
            }
        }if(sortCol==-1){
            System.out.println("Provided Column Value : "+ columnS +" does not exist");
            return;
        }
       
        for(int i = 1; i < tempArray.length-1; i++){
            for(int j = i+1; j < tempArray.length; j++){
                if((tempArray[i][sortCol]).compareToIgnoreCase(tempArray[j][sortCol]) > 0){
                    String[] tempRow = new String[tempArray[0].length];
                    
                    for(int id = 0; id < tempArray[j].length; id++){
                        tempRow[id] = tempArray[j][id];
                        tempArray[j][id] = tempArray[i][id] ;
                        tempArray[i][id] = tempRow[id];
                    }}}}
        }

    // Printing

    if(input.contains(Contain)){
        System.out.println("Selected Table: " + tb_name);

        printTableHeader(tempArray[0]);
        for (int i = 0; i < tempArray[0].length; i++) {
            System.out.printf("%-15s", "-------");
        }
        
        System.out.println();
        for (int i = 0; i < tempArray.length; i++) {
            if(tempArray[i][selCol].equalsIgnoreCase(value)){
                for (int j = 0; j < tempArray[i].length; j++) {
                    System.out.printf("%-15s",tempArray[i][j].replace("\"", ""));
                }
                System.out.println(); 
            }                      
        }
        System.out.println("sorting finished .. "); 
    }else{
        System.out.println("Whole Table:");
        printTableHeader(tempArray[0]);
        for (int i = 0; i < tempArray[0].length; i++) {
            System.out.printf("%-15s", "-------");
        }
        System.out.println();
        for (int i = 1; i < tempArray.length; i++) {
            for (int j = 0; j < tempArray[i].length; j++) {
                System.out.printf("%-15s",tempArray[i][j].replace("\"", ""));
            }
            System.out.println();   
        }
    }

}
///////-----------------------------------------------------------------------------------///////




////////////////////////UPDATE///////////////////////////////////////////////////////////////////////
public static void UPDATE(String input){
    String Prefix = "UPDATE";
    String Contain = "SET";
    String Contain2 = "WHERE";

    if(input.toUpperCase().indexOf(Contain) > input.toUpperCase().indexOf(Contain2)){
        System.out.println("Error! Invalid Syntax");
        return;
    }

    String tb_name = input.substring(Prefix.length(),(input.toUpperCase().indexOf(Contain))).trim();
    String column = input.substring((input.toUpperCase().indexOf(Contain) + Contain.length()),(input.indexOf('='))).trim();
    String value1 = input.substring((input.indexOf('=')+1),(input.toUpperCase().indexOf(Contain2) )).trim();
    String keyword = input.substring((input.toUpperCase().indexOf(Contain2) + Contain2.length()),(input.lastIndexOf('='))).trim();
    String value2 = input.substring(input.lastIndexOf('=')+1).trim();

    String tempArray[][] = fileToArray(tb_name);
    if(tempArray == null)
        return;

        int rows = tempArray.length;
        System.out.println(rows);
        int col = tempArray[0].length;
        System.out.println(col);

            int idx1 = -1;
            int idx2 = -1;
            for(int i = 0; i < col ; i++){
                if(tempArray[0][i].equals(keyword)){
                    idx1 = i;
                    System.out.println(idx1);
                }
                if(tempArray[0][i].equals(column)){
                    idx2 = i;
                    System.out.println(idx2);
                }
            }
            if(idx1 == -1){
                System.out.println("Invalid value : " + keyword);
                return;
            }
            if(idx2 == -1){
                System.out.println("Invalid value : " + column);
                return;
            }

            for (int i = 0; i < rows; i ++){
                if(tempArray[i][idx1].equals(value2)){
                    tempArray[i][idx2] = value1;
                }
            }
            //check
            for(int i = 0; i < rows; i++){
                for (int j = 0; j < col; j++){
                    System.out.print(tempArray[i][j] + "   ");
                }
                System.out.println();
            }

            try{
                File file = new File(tb_name + ".csv");
                File tempF = new File("temp.csv"); 
                FileWriter temp = new FileWriter(tempF, true);
                for(int i = 0; i < rows; i++){
                    for(int j = 0; j < col; j++){
                        temp.write(tempArray[i][j]);
                        if( j != col-1){
                            temp.write(',');
                        }
                    }
                    temp.write('\n');
                }
                temp.close();

                if(file.delete()){
                    tempF.renameTo(file);
                    System.out.println("File Updated Successfully");
                }
                else System.out.println("Unable to update file");

            }catch(Exception e1){
                System.out.println(e1.getMessage());
                
            }

}
//////-----------------------------------------------------------------------------------///////




//////////////////////////DELETE////////////////////////////////////////////////////////////////////
public static void DELETE(String input){
    String Prefix = "DELETE FROM TABLE";
    String Contain = "HAVING";

    String tb_name = input.substring(Prefix.length(),(input.toUpperCase().indexOf(Contain))).trim();
    String column = input.substring((input.toUpperCase().indexOf(Contain) + Contain.length()),(input.indexOf('='))).trim();
    String value = input.substring(input.indexOf('=')+1).trim();

    String tempArray[][] = fileToArray(tb_name);
    if(tempArray == null)
        return;

        int rows = tempArray.length;
        System.out.println(rows);
        int col = tempArray[0].length;
        System.out.println(col);

    int idx = -1;

    for(int i = 0; i < col ; i++){
        if(tempArray[0][i].equals(column)){
            idx = i;
            System.out.println(idx);
        } 
    }
    if(idx == -1){
        System.out.println("Invalid value : " + column);
        return;
    }

    try{
        File file = new File(tb_name + ".csv");
        File tempF = new File("temp.csv"); 
        FileWriter temp = new FileWriter(tempF, true);
        for(int i = 0; i < rows; i++){
            if(tempArray[i][idx].equals(value)){
                continue;
            }
            else{
                for(int j = 0; j < col; j++){
                    temp.write(tempArray[i][j]);
                    if( j != col-1){
                        temp.write(',');
                    }
                }
                temp.write('\n');
            }
            
        }
        temp.close();

        if(file.delete()){
            tempF.renameTo(file);
            System.out.println("File Updated Successfully");
        }
        else System.out.println("Unable to update file");

    }catch(IOException e1){
        System.out.println(e1.getMessage());
        
    }

}
/////-----------------------------------------------------------------------------------///////



/////////////////////////////////// HELP //////////////////////////////////////////////////////////////////

public static void HELP (){
    System.out.println("\n------------------CREATE Statement:------------------");
    System.out.println("\nFUNCTIONALITY: Used to create a new table with table name provided by the user. \nThe syntax below shows how to add the table name and columns in the new table");
    System.out.println("SYNTAX : CREATE TABLE table_name (column1, column2, column3,…)");
    System.out.println("EXAMPLE: CREATE TABLE student (name,regno,address) ");

    System.out.println("\n------------------DROP Statement:------------------");
    System.out.println("\nFUNCTIONALITY: Used to delete a pre-existing table. \nThe syntax below shows how to drop the table");
    System.out.println("SYNTAX : DROP table_name TABLE");
    System.out.println("EXAMPLE: DROP student TABLE ");

    System.out.println("\n------------------SHOW Statement:------------------");
    System.out.println("\nFUNCTIONALITY: Used to show all pre-existing table names in the DBMS. \nThe syntax below shows how to show all table names");
    System.out.println("SYNTAX : Show ALL");
    System.out.println("EXAMPLE: Show ALL ");

    System.out.println("\n------------------INSERT Statement:------------------");
    System.out.println("\nFUNCTIONALITY: Used to insert information in a pre-existing table.");
    System.out.println("SYNTAX : INSERT IN TABLE table_name VALUES (value1, value2, value3, ...)");
    System.out.println("EXAMPLE: INSERT IN TABLE student VALUES (\"Haq\",\"FA16-BCT-099\",\"Islamabad\")\r ");

    System.out.println("\n------------------SELECT Statement:------------------");
    System.out.println("\nFUNCTIONALITY: This statement selects any provided entry from a column \nand sorts all the rows containing that entry alphabetically.");
    System.out.println("SYNTAX : SELECT FROM TABLE table_name HAVING column = value SORT BY column");
    System.out.println("EXAMPLE: SELECT FROM TABLE student HAVING address = \"Islamabad\" SORT BY name ");
    System.out.println("\nNOTE: In SELECT statement, WHERE and ORDER BY clauses are optional. \nIf WHERE clause is skipped, all the data should be displayed. \nIf ORDER BY clause is missing, the order will be the same as listed in the file.");

    System.out.println("\n------------------UPDATE Statement:------------------");
    System.out.println("\nFUNCTIONALITY: Used to update the old data from the pre existing table \nwith table name provided by the user. ");
    System.out.println("SYNTAX : UPDATE table_name SET column = value WHERE column = value");
    System.out.println("EXAMPLE: UPDATE student SET address = \"Lahore\" WHERE name = \"Haq\" ");

    System.out.println("\n------------------DELETE Statement:------------------");
    System.out.println("\nFUNCTIONALITY: Used to delete a row entry from a pre-existing tanle. \nThe syntax below shows how to delete a table row by using the header entry");
    System.out.println("SYNTAX : DELETE FROM TABLE table_name HAVING column = value");
    System.out.println("EXAMPLE: DELETE FROM TABLE student HAVING name = \"Haq\" \n");

}

/// /////////////////////////////////////////////////////////////////////////////////////////// 


///////////////File to array //////////////////////////////////////////////////////////////////
public static String[][] fileToArray (String tb_name){
    String[][] tempdata = null;
    try{        
        File file = new File(tb_name + ".csv");
        if(!file.exists()){
            System.out.println("ERROR! File not found");
            return tempdata;
        }
        Scanner cReader = new Scanner(file);
        Scanner reader = new Scanner(file);
        String header = cReader.nextLine();

        int col = 1;
        for (int i = 0; i < header.length(); i++) {
            if ((header.charAt(i) == (char)',')) {
                col++;     
            }
        }

        int rows = 1;
        while(cReader.hasNextLine()){
            rows++;
            String line = cReader.nextLine();
        }

            tempdata = new String[rows][col];

            
            int ctr = 0;
            while(reader.hasNextLine()){         
                String row = reader.nextLine();
                String[] temprow = null;
                if (ctr == 0){
                    temprow = row.split(",");
                    for(int i = 0; i < col; i++)
                    {
                        tempdata[ctr][i] = temprow[i].trim();
                    }
                }else{
                    temprow = row.split("\",");
                    for(int i = 0; i < col; i++){
                        if(i < temprow.length){
                            if(i == col-1)
                            {
                                tempdata[ctr][i] = temprow[i].trim();
                            }else{
                                tempdata[ctr][i] = temprow[i].trim() + "\"";
                            }
                        }
                        else{
                            tempdata[ctr][i] = "";
                        }
                        
                    }
                }
                ctr++;
            }
            cReader.close();
            reader.close();
    }catch(IOException e){
        System.out.println(e.getMessage());
    }

    return tempdata;
}
///////////////////////////////////////////////////////////////////////////////////////////////

public static boolean isValid(String tableName) {
    if (tableName == null || tableName.isEmpty() || !Character.isJavaIdentifierStart(tableName.charAt(0))) {
        return false;
    }

    for (int i = 1; i < tableName.length(); i++) {
        if (!Character.isJavaIdentifierPart(tableName.charAt(i))) {
            return false;
        }
    }

    return true;
}
public static void printTableHeader(String[] headers) {
    for (String header : headers) {
        System.out.printf("%-15s", header);
    }
    System.out.println();
}
}
