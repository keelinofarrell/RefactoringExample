import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class FileService {
	
	ArrayList<BankAccount> accountList = new ArrayList<BankAccount>();
	static HashMap<Integer, BankAccount> table = new HashMap<Integer, BankAccount>();
	private final static int TABLE_SIZE = 29;
	static JFileChooser fc;
	JTable jTable;
	double interestRate;

	
	private static RandomAccessFile input;
	private static RandomAccessFile output;
	private static final int NUMBER_RECORDS = 100;

	
	public static void openFileRead()
	   {
		
		table.clear();
		fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
		 
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

        } 

		      try // open file
		      {
		    	  if(fc.getSelectedFile()!=null)
		    		  input = new RandomAccessFile( fc.getSelectedFile(), "r" );
		      } // end try
		      catch ( IOException ioException )
		      {
		    	  JOptionPane.showMessageDialog(null, "File Does Not Exist.");
		      } // end catch
			
	   } // end method openFile
	
	static String fileToSaveAs = "";
	
	public static void openFileWrite()
	   {
		if(fileToSaveAs!=""){
	      try // open file
	      {
	         output = new RandomAccessFile( fileToSaveAs, "rw" );
	         JOptionPane.showMessageDialog(null, "Accounts saved to " + fileToSaveAs);
	      } // end try
	      catch ( IOException ioException )
	      {
	    	  JOptionPane.showMessageDialog(null, "File does not exist.");
	      } // end catch
		}
		else
			saveToFileAs();
	   }
	
	public static void saveToFileAs()
	   {
		
		fc = new JFileChooser();
		
		 int returnVal = fc.showSaveDialog(null);
         if (returnVal == JFileChooser.APPROVE_OPTION) {
             File file = fc.getSelectedFile();
           
             fileToSaveAs = file.getName();
             JOptionPane.showMessageDialog(null, "Accounts saved to " + file.getName());
         } else {
             JOptionPane.showMessageDialog(null, "Save cancelled by user");
         }
        
     	    
	         try {
	        	 if(fc.getSelectedFile()==null){
	        		 JOptionPane.showMessageDialog(null, "Cancelled");
	        	 }
	        	 else
	        		 output = new RandomAccessFile(fc.getSelectedFile(), "rw" );
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      
	      
	     
	   }
	
	public static void closeFile() 
	   {
	      try // close file and exit
	      {
	         if ( input != null )
	            input.close();
	      } // end try
	      catch ( IOException ioException )
	      {
	         
	    	  JOptionPane.showMessageDialog(null, "Error closing file.");//System.exit( 1 );
	      } // end catch
	   } // end method closeFile
	
	public static void readRecords()
	   {
	      RandomAccessBankAccount record = new RandomAccessBankAccount();
	      try // read a record and display
	      {
	         while ( true )
	         {
	            do
	            {
	            	if(input!=null)
	            		record.read( input );
	            } while ( record.getAccountID() == 0 );
	            
	            BankAccount ba = new BankAccount(record.getAccountID(), record.getAccountNumber(), record.getFirstName(),
	                    record.getSurname(), record.getAccountType(), record.getBalance(), record.getOverdraft());
	            
	            
	            Integer key = Integer.valueOf(ba.getAccountNumber().trim());
			
				int hash = (key%TABLE_SIZE);
		
				
				while(table.containsKey(hash)){
			
					hash = hash+1;
				}
				
	            table.put(hash, ba);
		

	         } // end while
	      } // end try
	      catch ( EOFException eofException ) // close file
	      {
	         return; // end of file was reached
	      } // end catch
	      catch ( IOException ioException )
	      {
	    	  JOptionPane.showMessageDialog(null, "Error reading file.");
	         System.exit( 1 );
	      } // end catch
	   }
	
public static void saveToFile(){
		
	
		RandomAccessBankAccount record = new RandomAccessBankAccount();
	
	      Scanner input = new Scanner( System.in );

	      
	      for (Map.Entry<Integer, BankAccount> entry : table.entrySet()) {
			   record.setAccountID(entry.getValue().getAccountID());
			   record.setAccountNumber(entry.getValue().getAccountNumber());
			   record.setFirstName(entry.getValue().getFirstName());
			   record.setSurname(entry.getValue().getSurname());
			   record.setAccountType(entry.getValue().getAccountType());
			   record.setBalance(entry.getValue().getBalance());
			   record.setOverdraft(entry.getValue().getOverdraft());
			   
			   if(output!=null){
			   
			      try {
						record.write( output );
					} catch (IOException u) {
						u.printStackTrace();
					}
			   }
			   
			}
    	  
	      
	}

	public static void writeFile(){
		openFileWrite();
		saveToFile();
		//addRecords();
		closeFile();
	}
	
	public static void saveFileAs(){
		saveToFileAs();
		saveToFile();	
		closeFile();
	}
	
	public static void readFile(){
	    openFileRead();
	    readRecords();
	    closeFile();		
	}
	
	public void put(int key, BankAccount value){
		int hash = (key%TABLE_SIZE);
	
		while(table.containsKey(key)){
			hash = hash+1;
		
		}
		table.put(hash, value);

	}
}
