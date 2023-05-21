package clivet268.Enforcry.SecureLine;


import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

public class ClientTextBox{
    public static void main( String [] args ) throws InterruptedException  {
        JFrame frame = new JFrame();
        frame.add( new JLabel(" Outout" ), BorderLayout.NORTH );

        JTextArea ta = new JTextArea();
        TextAreaOutputStream taos = new TextAreaOutputStream( ta, 60 );
        PrintStream ps = new PrintStream( taos );
        System.setOut( ps );
        System.setErr( ps );


        frame.add( new JScrollPane( ta )  );

        frame.pack();
        frame.setVisible( true );
        frame.setSize(800,600);

        for( int i = 0 ; i < 100 ; i++ ) {
            /*System.out.println( i );
            Thread.sleep( 500 );

             */
        }
    }
}
/*
public class ClientTextBox {
    JFrame inputFrame = new JFrame("Client");
    JTextField txtInput = new JTextField("");
    public ClientTextBox(){
        inputFrame.getContentPane().add(txtInput);
        inputFrame.addInputMethodListener(new txtInputListener());
    }

    public class txtInputListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            input = txtInput.getText();   //receive input from text field

        }
    }

}

 */
