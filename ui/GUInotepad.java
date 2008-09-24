




/*QUESTA VERSIONE FUNZIONA DA SE*/           /*AGGIORNATO AL 18-08-08*/


package edumips64.ui;
import java.util.*;
import java.io.*;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.io.File.*;
//public class GUInotepad extends Component {
public class GUInotepad extends GUIComponent {
 // Per fare contento il compilatore, visto che dobbiamo implementare
 // GUIComponent
 public void draw() {
 }
 public void update() {
 }
	
public class pad extends JFrame implements ActionListener 
	



{
   
 JFileChooser fileChooser= new JFileChooser();
 int i =0;
 int p=0;	
 int response =0;
 int indice1 =0;
 int indice2 =0;
 int dim_carattere = 12;
 JTextArea TAarea_blocco;
 JOptionPane opt_p;
 Choice Cdim_carattere;
 Choice Cstile_carattere;
 File f= new File("prova.txt");
 File g= new File("prova.txt");
 Writer out;
 String s;
 String h;
 String t;
 Reader in;
 Choice colore;
 Choice coloresf;
 

 public  void main (String argv[]) 
   
 
 
  {
    pad ist = new pad();
    System.out.println ("");
    System.out.println ("notepad per edumips PIE");
   
  }
    
    pad() 
      {
        setLayout(new BorderLayout());
        setVisible(true);
        setBackground (Color.gray);
	    
        TAarea_blocco = new JTextArea();
	TAarea_blocco.setVisible(true);   
	JScrollPane scrollPane = new JScrollPane(TAarea_blocco);
        add(scrollPane, BorderLayout.CENTER);	    
        JMenuBar barraMenu = new JMenuBar();
        setJMenuBar (barraMenu);
	s=TAarea_blocco.getText();
	t=f.getName();   
        JMenu file = new JMenu("File");
        file.add (new JMenuItem("Nuovo")).addActionListener(this);
	file.add (new JMenuItem("salvaas")).addActionListener(this);
	file.add (new JMenuItem("apri")).addActionListener(this);
	file.add (new JMenuItem("salva")).addActionListener(this);
        file.add (new JMenuItem("Esci")).addActionListener(this);
        JMenu strum = new JMenu("Strumenti");
        strum.add (new JMenuItem("Cancella tutto")).addActionListener(this);
        barraMenu.add (file);
        barraMenu.add (strum); 
        JToolBar toolbar=new JToolBar();
	barraMenu.add(toolbar);
	String d;
        if (MouseEvent.BUTTON2==MouseEvent.MOUSE_CLICKED ) d = TAarea_blocco.getSelectedText();     
	if (MouseEvent.BUTTON3==MouseEvent.MOUSE_CLICKED )TAarea_blocco.replaceSelection(d);
       
        Cdim_carattere = new Choice();
        Cdim_carattere.setBounds (40,200,50,20);
        Cdim_carattere.add ("8");
        Cdim_carattere.add ("10");
        Cdim_carattere.add ("12");
        Cdim_carattere.add ("14");
        Cdim_carattere.add ("18");
        Cdim_carattere.addItemListener(new ItemListener() 
	  {
            public void itemStateChanged(ItemEvent e)
	      {
                int indice_lista = Cdim_carattere.getSelectedIndex();
                
                if (indice_lista == 0) dim_carattere = 12;
                if (indice_lista == 1) dim_carattere = 14;
                if (indice_lista == 2) dim_carattere = 16;
                if (indice_lista == 3) dim_carattere = 18;
                if (indice_lista == 4) dim_carattere = 38;
                if (indice2==0)TAarea_blocco.setFont (new Font ("Monospaced",Font.PLAIN,dim_carattere));
                if (indice2 == 1) TAarea_blocco.setFont (new Font("Monospaced",Font.BOLD,dim_carattere));
                if (indice2 == 2) TAarea_blocco.setFont (new Font("Monospaced",Font.ITALIC,dim_carattere));
            
	     }
        });
       
	    toolbar.add(Cdim_carattere);
            Cstile_carattere = new Choice();
	    Cstile_carattere.setBounds (40,230,80,20);
            Cstile_carattere.add ("Normale");
            Cstile_carattere.add ("Grassetto");
            Cstile_carattere.add ("Corsivo");
            Cstile_carattere.addItemListener(new ItemListener() 
	     {
              public void itemStateChanged(ItemEvent e) 
	       {
	         int indice_lista2 = Cstile_carattere.getSelectedIndex();
                
	         if (indice_lista2 == 0) TAarea_blocco.setFont (new Font("Monospaced",Font.PLAIN,dim_carattere));
	         if (indice_lista2 == 1) TAarea_blocco.setFont (new Font("Monospaced",Font.BOLD,dim_carattere));
	         if (indice_lista2 == 2) TAarea_blocco.setFont (new Font("Monospaced",Font.ITALIC,dim_carattere));
	         if (indice_lista2 == 0) indice2=0 ;
                 if (indice_lista2 == 1) indice2=1;
	         if (indice_lista2 == 2) indice2=2;
	       }
             });
         
	  toolbar.add (Cstile_carattere);
	  colore = new Choice();
          colore.setBounds (40,200,50,20);
          colore.add ("nero");
          colore.add ("rosso");
          colore.add ("blu");
          colore.addItemListener(new ItemListener() 
	   {
             public void itemStateChanged(ItemEvent e) 
	      {
                int indice_lista3 = colore.getSelectedIndex();
                if (indice_lista3 == 0) TAarea_blocco.setForeground (Color.black);
                if (indice_lista3 == 1) TAarea_blocco.setForeground (Color.red);
                if (indice_lista3 == 2) TAarea_blocco.setForeground (Color.blue);
                
              }
           });
	  toolbar.add (colore);
	  coloresf = new Choice();
	  coloresf.setBounds (40,200,50,20);
          coloresf.add ("bianco");
	  coloresf.add ("giallo");
          coloresf.add ("rosso");
          coloresf.add ("blu");
          coloresf.addItemListener(new ItemListener()
 	   {
             public void itemStateChanged(ItemEvent e) 
	      {
                int indice_lista4 = coloresf.getSelectedIndex();
                if (indice_lista4 == 0) TAarea_blocco.setBackground (Color.white);
                if (indice_lista4 == 1) TAarea_blocco.setBackground (Color.yellow);
                if (indice_lista4 == 2) TAarea_blocco.setBackground (Color.red);
                if (indice_lista4 == 3) TAarea_blocco.setBackground (Color.blue);
              }
           });
	  toolbar.add (coloresf);
      
	}
    
    
 
    
    
 public void actionPerformed (ActionEvent e) 
	 
 
 
 
	 {
	   if (e.getActionCommand().equals("Nuovo"))
	   {
	    newDocument();
	   if(p==0)	   
	   {TAarea_blocco.setText("");
	    f= new File("prova.txt");
	    t =f.getName();}
	   }
           if (e.getActionCommand().equals("Esci"))
	    closeDocument();
	   if (e.getActionCommand().equals("salvaas"))
	    {
	      int  response = fileChooser.showSaveDialog(this);
              if(response==JFileChooser.APPROVE_OPTION)
	        { f =  fileChooser.getSelectedFile();
	           t = f.getAbsolutePath()/*.getName()*/;
		   save();
	        }
	    }
	  if (e.getActionCommand().equals("salva"))
	   {System.out.println (t);
	     System.out.println (g.getName());
	    if(g.getName()==t)
            {
	      int  response = fileChooser.showSaveDialog(this);
              if(response==JFileChooser.APPROVE_OPTION)
	        {
		 f =  fileChooser.getSelectedFile();
	         t =	f.getAbsolutePath()/*.getName()*/;	
		 save();
	        }
	     }
            
	 else 
	      {
		System.out.println (TAarea_blocco.getText());
		save();
	       }
            }	       
	 if (e.getActionCommand().equals("apri"))
		{
	      int  response = fileChooser.showOpenDialog(this);
              if(response==JFileChooser.APPROVE_OPTION)
	        { f =  fileChooser.getSelectedFile();
	           t =	f.getAbsolutePath()/*.getName()*/;
		   open();
	        }
	    }
	 if (e.getActionCommand().equals("Cancella tutto"))	
           TAarea_blocco.setText("");
	 
         		 
	
    }

public void save() 



{
    
  try
       {
        File n=new File(t); 
	Writer out = new PrintWriter(n);
        TAarea_blocco.write(out);
	System.out.println (TAarea_blocco.getText());
	s =TAarea_blocco.getText();      
	System.out.println (t);
       }

catch (IOException u) {}
}

 

public void save2() 



{
    
 if(g.getName()==t)
            {
	      int  response = fileChooser.showSaveDialog(this);
              if(response==JFileChooser.APPROVE_OPTION)
	        {
		 f =  fileChooser.getSelectedFile();
	         t =	f.getAbsolutePath()/*.getName()*/;	
		 save();
	        }
		else p=3;
	     }
}

 
public void open() 




  
   {
     try
	{
	  File n=new File(t);
	  Reader in = new FileReader(n); 
	  Object desc = new Object(); 
	  TAarea_blocco.read(in,desc);
	  s =TAarea_blocco.getText();
	  System.out.println (n.getName());
	  
	}
    catch (IOException u) {}
   }




public void closeDocument() 
	



  {
    String warning; 
   /* System.out.println (s);
    h=TAarea_blocco.getText();
    if(s==h) 
    System.exit(0);
  else*/

  {
      warning = "Save Changes on  before ?";
      int opt = opt_p.showConfirmDialog(TAarea_blocco,warning ,"pad",opt_p.YES_NO_CANCEL_OPTION);
  
      if(opt==opt_p.CANCEL_OPTION)
      opt_p.setVisible(false);    
       else if(opt==opt_p.NO_OPTION)
                 System.exit(0);    
		 else if(opt==opt_p.YES_OPTION)
                          {
                            save2();  // anche questa chiamata puo' lanciare una AbortException
                            System.exit(0);
                          }
     }
}



public void newDocument() 
	



  {
    String warning; 
   /* System.out.println (s);
    h=TAarea_blocco.getText();
    if(s==h) 
    System.exit(0);
  else*/

  {
      warning = "Save Changes on  before ?";
      int opt = opt_p.showConfirmDialog(TAarea_blocco,warning ,"pad",opt_p.YES_NO_CANCEL_OPTION);
  
      if(opt==opt_p.CANCEL_OPTION)
      opt_p.setVisible(false);    
       else if(opt==opt_p.NO_OPTION)
       {p=0;          
       return;}    
		 else if(opt==opt_p.YES_OPTION)
                          {
			    p=0;
                            save2();  // anche questa chiamata puo' lanciare una AbortException
                            return;
                          }
     }
}



}
     
}




    
