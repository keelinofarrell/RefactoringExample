
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

public class BankApplication {
	
	ArrayList<BankAccount> accountList = new ArrayList<BankAccount>();
	static HashMap<Integer, BankAccount> table = new HashMap<Integer, BankAccount>();
	static JFileChooser fc;
	JTable jTable;
	double interestRate;
	DealWithJFrames create;
	
	int currentItem = 0;
	
	
	boolean openValues;
	
	public BankApplication() {
		create = new DealWithJFrames();
		create.setVisible(true);
	}

	
	
	public static void main(String[] args) {
		BankApplication ba = new BankApplication();
		
	}
	
	
}

