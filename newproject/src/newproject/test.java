package newproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

public class test extends JFrame{

	public static void main(String [] args) {
		String a = new IdFunction().passWord("all");
		System.out.println(a);
	}
}