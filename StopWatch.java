// Program Name:	StopWatch.java
// Developer:	Gary Simpson 
// Date:       	March 11, 2014
// Purpose:      	A Stop Watch App  
/*								. 
                  TO DO:
                  1.  Code  Time dispaly to pause timer/stopwatch. after timer/stopwatch starts.
                  2.  Code  button change and unlock timer menu when Timer  stops.
                  3. 

                  
                   CHANGE LOG:
                   1.3.1
                   - Seperated start/stop timer & stopwatch into seperate methods.
                   - Added setSelectedIndex to timer stop method
                   - Fixed error that set timer to "00:00:0.3"  after completion.
                   1.3.0
                   -Remove superflous code. from 758 lines to 520
                   			-individual timer buttons. actions, dis/enable buttons method.
                   			-instantiate buttons and default constructors for them. 
                   			-Associated button JPanels
                   -Lower App footprint to same as cpu use monitor.
                   			-Reset entire UI using GridLayout to shrink
                   -consider .setForeground(Color.RED); for stop mode.
                   -Toyed with multiple fonts. Ariel, Times,Baskerville, Monospaced etc  
                   				only to settle on Times as main and Ariel as a close second
                   -fix default comboBox selection of 15m by adding a 
                   		nonfunctioning item of 0m.
                   1.2.5
                   -lblTimer background changed
                   -adjust window size to reduce frame footprint
                   1.2.4
                   - Added to main the default frame position to bottom right of screen.
                   1.2.3
                   -Corrected  Start/Stop btn wrong color & text when time stops.
                   1.2.2
                   -Corrected StopWatch color after timer run reset to black.
                   - Added to main Stopwatch remaing above all windows.
                   
                   */

import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import java.util.NoSuchElementException; //pg 734 file read
import java.util.Scanner; // pg 734 file read




public class StopWatch extends JFrame implements ActionListener
{
// String arrary for button names in comboBox.
   private static final String[] timerSelection = 
   {"0m","7m","15m","30m","45m","1h","1.25h","1.5h","2h", "2.5h", "3h" };
   static String timer;
   private static boolean stopWatchRunning, timerRunning;
   private int i = 0; // int used for counter in TimerAction method
   private int x; // used for color for timer counter
   private int stopWatchTimer; // used as variable for timers
   private double y = 0; // int used for counter color changes
   private static int hours, minutes, seconds, miliseconds; // used for timer display
   private static final int START_DIGITS = 0; // used  to set initial digits for stopWatch in main.
   private final int DELAY = 100; // 100 millisecond delay = 1 second
   private final int TEXT_DELAY = 14; // millisecond delay for text animation
   private final int TOTAL_TIME = 10; // totals a second ... used for timer to count from.
   private Timer stopWatch, countDown; // Timer driver stopWatch timer
//Create main timer
   private JLabel lblTimer = new JLabel(timer); // USED FOR TESTING
   private JButton btnStartStop = new JButton("Start");
// Create ComboBox to streamline UI
   private JComboBox comboBox;

// Create layout and container.
   private BorderLayout layout;
   private Container con;
//////Container con = getContentPane();
// creat panels for border use
//    private JPanel northJPanel;
//    private JPanel eastJPanel;
//    private JPanel eastButtonsJPanel;
   private JPanel centerTimerJPanel;
   private JPanel centerButtonsJPanel;
   private JPanel centerStopWatchButtonsJPanel;
   private JPanel centerTimerButtonsJPanel;
   private JPanel centerJPanel;
//    private JPanel southJPanel;
//    private JPanel westJPanel;
//    private JPanel westButtonsJPanel;


///HOW IS THIS USEDs
   public StopWatch()
   {
      super("Stopwatch");
      layout = new BorderLayout(5, 5); // new border layout
      Container con = getContentPane();
      setLayout(layout); // set frame layout
      setResizable(false);
   
   // set button mnemonics and ActionCommands to send feedback type.
      btnStartStop.setMnemonic('S');
   // set ActionListener for buttons, comboBox.    
      btnStartStop.addActionListener(this);
      btnStartStop.setForeground(Color.decode("#008700"));
      btnStartStop.setEnabled(true);
   
   //set Timer menu drop down
      comboBox = new JComboBox(timerSelection);
      comboBox.setForeground(Color.decode("#0000af"));   
   
   
   //-----CODE COMBOBOX  LISTENER------//////
   //Set ActionCommands to update user name for later output.
      comboBox.addItemListener(
            new ItemListener()
            {
            //handle comboBox event
               public void itemStateChanged(ItemEvent event)
               {
               // determine which item selected. 
               
                  if( event.getStateChange() == ItemEvent.SELECTED) // was event.getStateChange() ==
                  {
                     String  timerChoice = (String)comboBox.getSelectedItem();
                     if(timerChoice ==  "0m")
                     {   } // if selection is 0m do nothing
                     else  // if any choice other than 0m made do this.
                     {
                        ResetTime();
                        startTimer();
                        lblTimer.setForeground(Color.BLACK);
                     //disableTimerButtons();
                        comboBox.setEnabled(false);
                     // 	set all buttons/textfields to false
                        btnStartStop.setText("Stop");
                        btnStartStop.setForeground(Color.RED);
                        timerRunning = true; 
                            
                        if(timerChoice ==  "7m")
                        {
                           stopWatchTimer = 7;
                           minutes = 7;
                        }//end if 15m
                        if(timerChoice ==  "15m")
                        {
                           stopWatchTimer = 15;
                           minutes = 15;
                        }//end if 15m
                        if(timerChoice ==  "30m")
                        {
                           stopWatchTimer = 30;
                           minutes = 30;
                        }//end if 30m
                        if(timerChoice ==  "45m")
                        {
                           stopWatchTimer = 45;
                           minutes  = 45;
                        }//end if 45m
                        if(timerChoice ==  "1h")
                        {
                           stopWatchTimer = 60;
                           hours = 1;
                        }//end if 1h
                        if(timerChoice ==  "1.25h")
                        {
                           stopWatchTimer = 75;
                           hours = 1;
                           minutes = 15;
                        }//end if 1.25h                     
                        if(timerChoice ==  "1.5h")
                        {
                           stopWatchTimer = 90;
                           hours = 1;
                           minutes = 30;
                        }//end if 1.5h
                        if(timerChoice ==  "2h")
                        {
                           stopWatchTimer = 120;
                           hours = 2;
                        }//end if 2h
                        if(timerChoice ==  "2.5h")
                        {
                           stopWatchTimer = 150;
                           hours = 2;
                           minutes = 30;
                        }//end if 2.5h
                        if(timerChoice ==  "3h")
                        {
                           stopWatchTimer = 180;
                           hours = 3;
                        }//end if 3 hrs
                     }//end if  != 0 selection                                    
                  }//end if getStateChange ==
               }  // end itemStateChanged
            }// endItemListener
         );
   
   
   // add components to continer.
      setLayout(layout);
   //       label.setFont(new Font("Arial", Font.ITALIC, 13));
      lblTimer.setFont(new Font("Times", Font.BOLD, 41));
      lblTimer.setOpaque(true); // Sets the label back ground Opaque so the color can be changed default = false
      lblTimer.setBackground(Color.decode("#DADADA"));//Color.decode("#dffff7")
      lblTimer.setForeground(Color.BLACK);
      lblTimer.setText( String.format("%02d",hours) + ":" + String.format("%02d", minutes)+
         ":" + String.format("%02d", seconds) + "." + miliseconds );
   
   
      centerJPanel = new JPanel();
      centerJPanel.setLayout(new GridLayout(2,1) ); // 2 rows 1 column
      centerTimerJPanel = new JPanel();
      centerTimerJPanel.setLayout(new GridLayout(1,1) );
   //centerTimerJPanel.add(lblTimerLabel);
      centerTimerJPanel.add(lblTimer);
      centerButtonsJPanel = new JPanel();
      centerButtonsJPanel.setLayout(new GridLayout(1,2) );
      centerStopWatchButtonsJPanel = new JPanel();
      centerStopWatchButtonsJPanel.setLayout(new GridLayout(1,1) );
      centerStopWatchButtonsJPanel.add(btnStartStop);
   //----- centerStopWatchButtonsJPanel.add(btnStop);
      centerStopWatchButtonsJPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "StopWatch"));
   
      centerTimerButtonsJPanel = new JPanel();
      centerTimerButtonsJPanel.setLayout(new GridLayout(1,1) );
      centerTimerButtonsJPanel.add(comboBox);
      centerTimerButtonsJPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Timers"));
      centerButtonsJPanel.add(centerStopWatchButtonsJPanel);
      centerButtonsJPanel.add(centerTimerButtonsJPanel);
   
      centerJPanel.add(centerTimerJPanel);
      centerJPanel.add(centerButtonsJPanel);
   
   
   //-----NOT YET USED....  add(northJPanel , BorderLayout.NORTH );
   //       add( westJPanel , BorderLayout.WEST );
      add(centerJPanel , BorderLayout.CENTER );
   //       add( eastJPanel , BorderLayout.EAST ); 
   //---disabled for space--- add( southJPanel , BorderLayout.SOUTH );
      validate();
   }    


//Actions based on choice go here.
   public void actionPerformed(ActionEvent event)
   {
      Object source = event.getSource();
      if(source == btnStartStop)
      {  
         if(stopWatchRunning==false && timerRunning==false)
         {
            startStopWatch();
         }// end if both Running
         else if(stopWatchRunning)
         {
            stopStopWatch();
         } //end if stopWatchRunning
         else if(timerRunning)
         {
            stopTimer();
         } //end if timerRunning
      
      } // end if begin Button
      invalidate();
      validate();
   } // end action performed


   public static void main(String[] args)
   { 
   // set default time digits for min hour sec and milisec.
      hours = START_DIGITS;
      minutes = START_DIGITS;
      seconds = START_DIGITS;
      miliseconds = START_DIGITS;
   // set default booleans to false
      stopWatchRunning = false;
      timerRunning = false;
   
   // Default frame size digits.... Set to nexus screen size
      final int FRAME_WIDTH = 185;//was 304 to 228 to 180, Mac = 185 Win = 206
      final int FRAME_HEIGHT =106;// was 322 to 144 to 104
   //declare frame for main form.
      StopWatch frame = new StopWatch();
      frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); // set frame exit on x clicked
      frame.setSize(FRAME_WIDTH, FRAME_HEIGHT); // set frame to sizes declared above. Set this before setting location.
      frame.setVisible(true);	// show frame
   //frame.pack(); 
   //--NOT USED-- frame.setLocationRelativeTo(null); // sets the form location centered.
      Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
      Point bottomRight = new Point(screenSize.width, screenSize.height);
      Point newLocation = new Point(bottomRight.x - (FRAME_WIDTH), bottomRight.y - (FRAME_HEIGHT) );
      frame.setLocation(newLocation); // Used to place window on bottom right corner
      frame.setAlwaysOnTop(true); // used to keep windows above all others
   //  frame.pack();// make window just large enough for GUI
   }	// end main


   public void ResetTime()
   {
      hours = START_DIGITS;
      minutes = START_DIGITS;
      seconds = START_DIGITS;
      miliseconds = START_DIGITS;
      i=0;
      lblTimer.setText( String.format("%02d",hours) + ":" + String.format("%02d", minutes)+ ":" + String.format("%02d", seconds) + "." + miliseconds );
   }// end ResetTime 


// start stopwatch
   public void startStopWatch()
   {
      lblTimer.setForeground(Color.BLACK);
   //disableTimerButtons();
      comboBox.setEnabled(false);
   // 	set all buttons/textfields to false
      btnStartStop.setText("Stop");
      btnStartStop.setForeground(Color.RED);
      stopWatchRunning = true;
   // create timer
   //lblTimer.setForeground(Color.decode("#00CC00")); // set label colot
      stopWatch = new Timer(DELAY , new StopWatchHandler() ); // .start used to avoid stopWatch.start(); to start timer
      stopWatch.start();
   } // end startTimer method

// stop stop watch
   public void stopStopWatch()
   {
      ResetTime();
      comboBox.setEnabled(true);
      stopWatchRunning = false;
      btnStartStop.setText("Start");
      btnStartStop.setForeground(Color.decode("#008700"));
      stopWatchRunning = false;
      stopWatch.stop();
   } // end stop watch method

// inner class to handle action events from Main Timer // see pg 977
   private class StopWatchHandler implements ActionListener
   {
   // respond to Timer's event
      public void actionPerformed( ActionEvent actionEvent )
      {
      //lblTimerLabel.setText("Timer Started...  "); // ---for testing
      //int x = Integer.parseInt( lblTimer.getText() );
         if(i != TOTAL_TIME)
         {
            i++ ;
            miliseconds++;
            lblTimer.setText( String.format("%02d",hours) + ":" + String.format("%02d", minutes)+
               ":" + String.format("%02d", seconds) + "." + miliseconds );
            if(miliseconds == 9)
            {
               miliseconds = 0;
            }
         // textArea.append(x + " "); // ----- tester for timer execution 
         } // end if != total_time
         if(i == TOTAL_TIME)
         {
            i = 0;
            seconds++;
            lblTimer.setText( String.format("%02d",hours) + ":" + String.format("%02d", minutes)+
               ":" + String.format("%02d", seconds) + "." + miliseconds );
         } // end if == Total_Time
         if(seconds == 60)
         {
            seconds  = 01;
            minutes++;
            lblTimer.setText( String.format("%02d",hours) + ":" + String.format("%02d", minutes)+
               ":" + String.format("%02d", seconds) + "." + miliseconds );
         } // end if 60 seconds
         if(minutes == 60)
         {
            minutes = 01;
            hours++;
            lblTimer.setText( String.format("%02d",hours) + ":" + String.format("%02d", minutes)+
               ":" + String.format("%02d", seconds) + "." + miliseconds );            
         } // end if 60 minutes
      }  //  end method actionPerformed
   }  // end class timer Handler


// start Timer
   public void startTimer()
   {   
      countDown = new Timer(DELAY , new TimerHandler() ); // .start used to avoid countDown.start(); to start timer
      countDown.start();
   } // end start timer method

// stop timer
   public void stopTimer()
   {
      ResetTime();
      stopWatchTimer = 180;
   //miliseconds = 0;
      btnStartStop.setText("Start");
      btnStartStop.setForeground(Color.decode("#008700"));
      comboBox.setEnabled(true);
      timerRunning = false;
      countDown.stop();
      comboBox.setSelectedIndex(0);
   } // end stop timer method


// inner class to handle action events from Main Timer // see pg 977
   private class TimerHandler implements ActionListener
   { 
   // respond to Timer's event
      public void actionPerformed( ActionEvent actionEvent )
      {   
         int totaltime = stopWatchTimer*60*10; //-----addd *10 for math
      //int x = Integer.parseInt( lblTimer.getText() );
         if(i != totaltime)
         {
            y =  totaltime - i  ;//((TOTAL_TIME-i)/(TOTAL_TIME*100));
            y =  y/totaltime;
            y =  y*100;
            x = (int)y; // convert double y into int x and use x for switch to change colors 
            switch ( x )
            {  // colors from http://www.rapidtables.com/web/color/RGB_Color.htm
               case 100:
                  lblTimer.setForeground(Color.decode("#009900"));
                  break;
               case 70:
                  lblTimer.setForeground(Color.decode("#4C9900"));
                  break;
               case 50:
                  lblTimer.setForeground(Color.decode("#999900"));
                  break;
               case 35:
                  lblTimer.setForeground(Color.decode("#CC6600"));
                  break;
               case 20:
                  lblTimer.setForeground(Color.decode("#CC0000"));
                  break;
            }//end switch for gradual color change on timer
            i++ ;
            lblTimer.setText( String.format("%02d",hours) + ":" + String.format("%02d", minutes)+
               ":" + String.format("%02d", seconds) + "." + miliseconds );            // ----- tester for timer execution textArea.append(x + " ");
         } // end if stopWatchTimer
         if(miliseconds == 0 &&seconds == 0)
         {
            if(seconds == 0 && minutes ==0)
            {
               if (hours != 0)
               {
                  hours--;
                  minutes = 59;
                  seconds = 59;
                  miliseconds = 9;
                  lblTimer.setText( String.format("%02d",hours) + ":" + String.format("%02d", minutes)+ ":" + String.format("%02d", seconds) + "." + miliseconds );
               }//end if != 0
               else if(hours == 0)
               {
                  stopTimer();
               }//end if hours ==
               lblTimer.setText( String.format("%02d",hours) + ":" + String.format("%02d", minutes)+ ":" + String.format("%02d", seconds) + "." + miliseconds );     
            } // end if seconds == minutes ==
            else if(seconds==0 && minutes!= 0)
            {
               minutes--;
               seconds=59;
               lblTimer.setText( String.format("%02d",hours) + ":" + String.format("%02d", minutes)+ ":" + String.format("%02d", seconds) + "." + miliseconds );
            }//end else if seconds == minutes !=
         }//end if miliseconds== seconds==      
         else if(miliseconds == 0 && seconds != 0)
         {
            seconds--;
            miliseconds=9;
            lblTimer.setText( String.format("%02d",hours) + ":" + String.format("%02d", minutes)+ ":" + String.format("%02d", seconds) + "." + miliseconds );         
         /// ----- PLAY AUDIO ALERT CODE HERE -----
         }  // end else if miliseconds
         else if(miliseconds != 0)
         {
            miliseconds--;
            lblTimer.setText( String.format("%02d",hours) + ":" + String.format("%02d", minutes)+ ":" + String.format("%02d", seconds) + "." + miliseconds ); 
         } // end milisecond != 0
      }  //  end method actionPerformed
   }  // end class timer Handler

}  ///----- END PROGRAM
