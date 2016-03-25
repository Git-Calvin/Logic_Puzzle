/**
 * Created by Calvin Wong & Jyoti Sharma
 *
 * =================
 * LogicPuzzleWindow
 * =================
 *
 * Program Description:
 *
 *  In this puzzle you are given a series of categories, and an equal number of options within each category. Each option is used once and only once.
 *  The goal is to figure out which options are linked together based on a series of given clues. The puzzle has only one unique solution, and can
 *  be solved using simple logical processes. A custom-labeled grid is provided for this puzzle. The grid allows you to cross-reference every possible option
 *  in every category. You can eliminate pairs you know aren't true with an X, and pencil in pairs you know are related with an O.
 *
 *
*/
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class LogicPuzzleWindow extends JFrame implements ActionListener {

    // Field declarations
    private JPanel panel0, panel1, panel2, panel3, panel4, panel5, panel6, panel7;
    private JButton clearErrorButton, hintButton, startOverButton, submitButton, undoButton;
    private JPanel mainPanel, buttonPanel;
    private JScrollPane jScrollPanel;
    private JTextArea jTextArea;
    private Font clueFont = new Font("SANS_SERIF", Font.PLAIN, 13);
    private Font labelFont = new Font("SANS_SERIF", Font.BOLD, 17);
    private final int WINDOW_WIDTH = 950;
    private final int WINDOW_HEIGHT = 750;

    // Creating 3 different panels, each having 16 buttons
    public PuzzleButton firstPanelButtons[] = new PuzzleButton[16];
    public PuzzleButton secondPanelButtons[] = new PuzzleButton[16];
    public PuzzleButton thirdPanelButtons[] = new PuzzleButton[16];
    public PuzzleButton undoButton1 = new PuzzleButton("");

    // Winning Combinations  (Winning combinations can be changed easily to accommodate new puzzles)
    public ArrayList<Integer> firstPanelWinningButtons = new ArrayList<Integer>(Arrays.asList(2, 5, 8, 15)); // {2, 5, 8, 15};
    public ArrayList<Integer> secondPanelWinningButtons = new ArrayList<Integer>(Arrays.asList(0, 6, 9, 15)); // {0, 6, 9, 15};
    public ArrayList<Integer> thirdPanelWinningButtons = new ArrayList<Integer>(Arrays.asList(2, 4, 9, 15)); // {2, 4, 9, 15};
    /*
        The order of buttons in a panel is like below:
        0    1     2    3
        4    5     6    7
        8    9    10   11
        12  13  14   15
        */

    //Finding all buttons in each panel which are errors
    public ArrayList<Integer> firstPanelErrorButtons = new ArrayList<Integer>();
    public ArrayList<Integer> secondPanelErrorButtons = new ArrayList<Integer>();
    public ArrayList<Integer> thirdPanelErrorButtons = new ArrayList<Integer>();

    public static boolean firstPanelClear = false;
    public static boolean secondPanelClear = false;
    public static boolean thirdPanelClear = false;

    public static boolean firstPanelHint = false;
    public static boolean secondPanelHint = false;
    public static boolean thirdPanelHint = false;

    public LogicPuzzleWindow() {

        initializeComponents();  //initializing components
        initializePanels();      //initializing panels
        initializeLabels();      //initializing labels
        initializeButtons();     //initializing buttons
        findErrorButtons();      //finding error combination buttons

        setTitle("Calvin & Jyoti's Logic Puzzle");

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(jScrollPanel,BorderLayout.EAST);

        setVisible(true);
    }

    private void initializeComponents() {

        mainPanel = new JPanel();   //holds panel Panel0-Panel7
        panel0 = new JPanel();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel();
        panel6 = new JPanel();
        panel7 = new JPanel();
        buttonPanel = new JPanel(); //Holds check answer, hint etc buttons
        undoButton = new JButton();
        hintButton = new JButton();
        clearErrorButton = new JButton();
        startOverButton = new JButton();
        submitButton = new JButton();
        jScrollPanel = new JScrollPane();
        jTextArea = new JTextArea();

        mainPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        mainPanel.setLayout(new GridLayout(3, 3));

        panel0.setLayout(new GridLayout(1, 0));
        mainPanel.add(panel0);

        panel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panel1.setLayout(new GridLayout(1, 0));
        mainPanel.add(panel1);

        panel2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panel2.setLayout(new GridLayout(1, 0));
        mainPanel.add(panel2);

        panel3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panel3.setLayout(new GridLayout(1, 0));
        mainPanel.add(panel3);

        panel4.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panel4.setLayout(new GridLayout(1, 0));
        mainPanel.add(panel4);

        panel5.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panel5.setLayout(new GridLayout(1, 0));
        mainPanel.add(panel5);

        panel6.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panel6.setLayout(new GridLayout(1, 0));
        mainPanel.add(panel6);

        panel7.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panel7.setLayout(new GridLayout(4, 4, 5, 5));
        mainPanel.add(panel7);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        buttonPanel.setLayout(new GridLayout());

        undoButton.setText("Undo"); // add undo button
        buttonPanel.add(undoButton);

        hintButton.setText("Hint"); // add hint button
        buttonPanel.add(hintButton);

        clearErrorButton.setText("Clear Errors"); // add clear error button
        buttonPanel.add(clearErrorButton);

        startOverButton.setText("Start Over"); // add start over button
        buttonPanel.add(startOverButton);

        submitButton.setText("Check Answer"); // add check answer button
        buttonPanel.add(submitButton);

        getContentPane().add(buttonPanel, BorderLayout.PAGE_END);

        jTextArea.setEditable(false);
        jTextArea.setColumns(20);
        jTextArea.setLineWrap(true);
        jTextArea.setRows(5);

        // This area stores the clues in a JTextArea, (Clues can be changed easily to accommodate new puzzles).
        jTextArea.setText("THE CLUES:\n\n\n1. The mushroom going to Wickman's is either the mushroom that sells for $25 per" +
                " pound or the Woodear mushroom.\n\n2. The mushroom going to Nature's Way sells for 5 dollars less per " +
                "pound than the pink bottom mushroom.\n\n3. The oyster mushroom sells for somewhat less per pound" +
                " than the woodear mushroom.\n\n4. The mushroom going to Nature's Way sells for 10 dollars less per" +
                " pound that the oyster mushroom. \n\n5. The mushroom going to Organimarket sells for somewhat more" +
                " per pound than the mushroom going to Pete's Produce.");

        jTextArea.setBackground(Color.WHITE); // black background
        jTextArea.setForeground(Color.BLACK); // white font
        jTextArea.setFont(clueFont);
        jTextArea.setWrapStyleWord(true);
        jScrollPanel.setViewportView(jTextArea);

        getContentPane().add(jScrollPanel, BorderLayout.LINE_START);

        pack();
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() instanceof PuzzleButton) {    // if any of the buttons are pressed, this function is invoked

            PuzzleButton puzzleButton = (PuzzleButton) e.getSource();    // reading which button got pressed
            //based on the current text in the button, alternates the new text
            if (puzzleButton.getText().equals("")) {  // if blank, changes to X
                puzzleButton.setForeground(Color.RED);
                puzzleButton.setValue("X");
            }
            else if (puzzleButton.getText().equals("X")) {   // if X, changes to O
                puzzleButton.setForeground(Color.GREEN);
                puzzleButton.setValue("O");
            }
            else if (puzzleButton.getText().equals("O")) {   // if O, changes to blank
                puzzleButton.setValue("");
            }
            undoButton1 = puzzleButton;  //undo button is used to store the last button clicked so as to perform undo operation
        }
        else if (e.getSource() == submitButton) {    //when submit button is clicked

            firstPanelClear = false;   //boolean set true if first panel is success
            secondPanelClear = false;   //boolean set true if second panel is success
            thirdPanelClear = false;   //boolean set true if third panel is success

            //if all combinations of successful button texts are matching, then boolean is set true
            if (firstPanelButtons[firstPanelWinningButtons.get(0)].getText().equals("O")
                    && firstPanelButtons[firstPanelWinningButtons.get(1)].getText().equals("O")
                    && firstPanelButtons[firstPanelWinningButtons.get(2)].getText().equals("O")
                    && firstPanelButtons[firstPanelWinningButtons.get(3)].getText().equals("O")) {
                firstPanelClear = true;
            }
            else {
                firstPanelClear = false;
            }
            //checking whether any of the error buttons are selected as 'O'
            for (int i = 0; i < firstPanelErrorButtons.size(); i++) {
                if (firstPanelButtons[firstPanelErrorButtons.get(i)].getText().equals("O")) {
                    firstPanelClear = false;
                    break;
                }
            }
            // Same conditions as first panel is repeated for second and third panels
            if (secondPanelButtons[secondPanelWinningButtons.get(0)].getText().equals("O")
                    && secondPanelButtons[secondPanelWinningButtons.get(1)].getText().equals("O")
                    && secondPanelButtons[secondPanelWinningButtons.get(2)].getText().equals("O")
                    && secondPanelButtons[secondPanelWinningButtons.get(3)].getText().equals("O")) {
                secondPanelClear = true;
            }
            else {
                secondPanelClear = false;
            }

            for (int i = 0; i < secondPanelErrorButtons.size(); i++) {
                if (secondPanelButtons[secondPanelErrorButtons.get(i)].getText().equals("O")) {
                    secondPanelClear = false;
                    break;
                }
            }
            if (thirdPanelButtons[thirdPanelWinningButtons.get(0)].getText().equals("O")
                    && thirdPanelButtons[thirdPanelWinningButtons.get(1)].getText().equals("O")
                    && thirdPanelButtons[thirdPanelWinningButtons.get(2)].getText().equals("O")
                    && thirdPanelButtons[thirdPanelWinningButtons.get(3)].getText().equals("O")) {
                thirdPanelClear = true;
            }
            else {
                thirdPanelClear = false;
            }

            for (int i = 0; i < thirdPanelErrorButtons.size(); i++) {
                if (thirdPanelButtons[thirdPanelErrorButtons.get(i)].getText().equals("O")) {
                    thirdPanelClear = false;
                    break;
                }
            }
            //if all booleans are true, the user wins
            //else error
            if ((firstPanelClear == true) && (secondPanelClear == true) && (thirdPanelClear == true)) {
                JOptionPane.showMessageDialog(this, "You Win!\n\nAnswers:\n$25   Shaggy Mane   Nature's Way\n" +
                        "$30   Pink Bottom     Pete's Produce\n$35   Oyster               Organimarket\n$40   Woodear          Wickman's\n", "Congratulations", JOptionPane.INFORMATION_MESSAGE );
            }
            else {
                JOptionPane.showMessageDialog(this, "Sorry...try again", "Uh Oh...", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if (e.getSource() == clearErrorButton) {   //clear button clicked

            clearErrors();  // error clearing method

        }
        else if (e.getSource() == startOverButton) {   // start over button pressed

            startOver(); // start over method

        }
        else if (e.getSource() == hintButton) {   // hint button pressed

            hint(); // hint method

        }
        else if (e.getSource() == undoButton) { // undo button pressed
            //if undo button1 is not null, then we will check the current text on it. if it is O, we will change it to X
            //if it is X, we will change it to "" etc
            if (undoButton1 != null) {
                if (undoButton1.getText().equals("")) {
                    undoButton1.setValue("O");
                    undoButton1.setForeground(Color.GREEN);
                }
                else if (undoButton1.getText().equals("X")) {
                    undoButton1.setValue("");
                }
                else if (undoButton1.getText().equals("O")) {
                    undoButton1.setForeground(Color.RED);
                    undoButton1.setValue("X");
                }
                undoButton1 = null;
            }
        }
    }

    private void initializePanels() {
        //setting panel layouts
        panel1.setLayout(new GridLayout(1, 4, 5, 5));
        panel1.setBackground(Color.WHITE);

        panel2.setLayout(new GridLayout(1, 4, 5, 5));
        panel2.setBackground(Color.WHITE);

        panel3.setLayout(new GridLayout(4, 1, 5, 5));
        panel3.setBackground(Color.WHITE);

        panel4.setLayout(new GridLayout(4, 4, 5, 5));
        panel4.setBackground(Color.WHITE);

        panel5.setLayout(new GridLayout(4, 4, 5, 5));
        panel5.setBackground(Color.WHITE);

        panel6.setLayout(new GridLayout(4, 1, 5, 5));
        panel6.setBackground(Color.WHITE);

        panel7.setLayout(new GridLayout(4, 4, 5, 5));
        panel7.setBackground(Color.WHITE);
    }

    private void initializeLabels() {
        // Label text are in html so as to show each character in a new line
        // This Area holds all the labels associated with the puzzle. The labels can be changed to accommodate new puzzles.

        //Panel 1
        //JLabel panel1Label1 = new JLabel("<html>O<br>Y<br>S<br>T<br>E<br>R</html>");
        //panel1Label1.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel panel1Label1 = new JLabel("Oyster");
        panel1Label1.setFont(labelFont);
        panel1Label1.setForeground(Color.BLACK);
        panel1Label1.setUI(new VerticalLabelUI(true));
        panel1Label1.setHorizontalAlignment(SwingConstants.RIGHT);

        //JLabel panel1Label2 = new JLabel("<html>P<br>I<br>N<br>K<br><br>B<br>O<br>T<br>T<br>O<br>M</html>");
        //panel1Label2.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel panel1Label2 = new JLabel("Pink Bottom");
        panel1Label2.setFont(labelFont);
        panel1Label2.setForeground(Color.BLACK);
        panel1Label2.setUI(new VerticalLabelUI(true));
        panel1Label2.setHorizontalAlignment(SwingConstants.RIGHT);

        //JLabel panel1Label3 = new JLabel("<html>S<br>H<br>A<br>G<br>G<br>Y<br> <br>M<br>A<br>N<br>E</html>");
        //panel1Label3.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel panel1Label3 = new JLabel("Shaggy Mane");
        panel1Label3.setFont(labelFont);
        panel1Label3.setForeground(Color.BLACK);
        panel1Label3.setUI(new VerticalLabelUI(true));
        panel1Label3.setHorizontalAlignment(SwingConstants.RIGHT);

        //JLabel panel1Label4 = new JLabel("<html>W<br>O<br>O<br>D<br>E<br>A<br>R<br></html>");
        //panel1Label4.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel panel1Label4 = new JLabel("Woodear");
        panel1Label4.setFont(labelFont);
        panel1Label4.setForeground(Color.BLACK);
        panel1Label4.setUI(new VerticalLabelUI(true));
        panel1Label4.setHorizontalAlignment(SwingConstants.RIGHT);

        panel1.add(panel1Label1);
        panel1.add(panel1Label2);
        panel1.add(panel1Label3);
        panel1.add(panel1Label4);

        // Panel 2
        //JLabel panel2Label1 = new JLabel("<html>N<br>A<br>T<br>U<br>R<br>E<br>S<br> <br>W<br>A<br>Y</html>");
        //panel2Label1.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel panel2Label1 = new JLabel("Nature's Way");
        panel2Label1.setFont(labelFont);
        panel2Label1.setForeground(Color.BLACK);
        panel2Label1.setUI(new VerticalLabelUI(true));
        panel2Label1.setHorizontalAlignment(SwingConstants.RIGHT);

        //JLabel panel2Label2 = new JLabel("<html>O<br>R<br>G<br>A<br>N<br>I<br>M<br>A<br>R<br>K<br>E<br>T</html>");
        //panel2Label2.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel panel2Label2 = new JLabel("Organimarket");
        panel2Label2.setFont(labelFont);
        panel2Label2.setForeground(Color.BLACK);
        panel2Label2.setUI(new VerticalLabelUI(true));
        panel2Label2.setHorizontalAlignment(SwingConstants.RIGHT);

        //JLabel panel2Label3 = new JLabel("<html>P<br>E<br>T<br>E<br>S<br> <br>P<br>R<br>O<br>D<br>U<br>C<br>E</html>");
        //panel2Label3.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel panel2Label3 = new JLabel("Pete's Produce");
        panel2Label3.setFont(labelFont);
        panel2Label3.setForeground(Color.BLACK);
        panel2Label3.setUI(new VerticalLabelUI(true));
        panel2Label3.setHorizontalAlignment(SwingConstants.RIGHT);

        //JLabel panel2Label4 = new JLabel("<html>W<br>I<br>C<br>K<br>M<br>A<br>N<br>S</html>");
        //panel2Label4.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel panel2Label4 = new JLabel("Wickman's");
        panel2Label4.setFont(labelFont);
        panel2Label4.setForeground(Color.BLACK);
        panel2Label4.setUI(new VerticalLabelUI(true));
        panel2Label4.setHorizontalAlignment(SwingConstants.RIGHT);

        panel2.add(panel2Label1);
        panel2.add(panel2Label2);
        panel2.add(panel2Label3);
        panel2.add(panel2Label4);

        // Panel 3
        JLabel panel3Label1 = new JLabel("$25");
        panel3Label1.setFont(labelFont);
        panel3Label1.setForeground(Color.BLACK);
        panel3Label1.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel panel3Label2 = new JLabel("$30");
        panel3Label2.setFont(labelFont);
        panel3Label2.setForeground(Color.BLACK);
        panel3Label2.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel panel3Label3 = new JLabel("$35");
        panel3Label3.setFont(labelFont);
        panel3Label3.setForeground(Color.BLACK);
        panel3Label3.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel panel3Label4 = new JLabel("$40");
        panel3Label4.setFont(labelFont);
        panel3Label4.setForeground(Color.BLACK);
        panel3Label4.setHorizontalAlignment(SwingConstants.RIGHT);

        panel3.add(panel3Label1);
        panel3.add(panel3Label2);
        panel3.add(panel3Label3);
        panel3.add(panel3Label4);

        // Panel 6
        JLabel panel6Label1 = new JLabel("Nature's Way");
        panel6Label1.setFont(labelFont);
        panel6Label1.setForeground(Color.BLACK);
        panel6Label1.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel panel6Label2 = new JLabel("Orgamimarket");
        panel6Label2.setFont(labelFont);
        panel6Label2.setForeground(Color.BLACK);
        panel6Label2.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel panel6Label3 = new JLabel("Pete's Produce");
        panel6Label3.setFont(labelFont);
        panel6Label3.setForeground(Color.BLACK);
        panel6Label3.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel panel6Label4 = new JLabel("Wickman's");
        panel6Label4.setFont(labelFont);
        panel6Label4.setForeground(Color.BLACK);
        panel6Label4.setHorizontalAlignment(SwingConstants.RIGHT);

        panel6.add(panel6Label1);
        panel6.add(panel6Label2);
        panel6.add(panel6Label3);
        panel6.add(panel6Label4);
    }

    private void initializeButtons() {
        //adding button action listeners
        submitButton.addActionListener(this);
        undoButton.addActionListener(this);
        clearErrorButton.addActionListener(this);
        hintButton.addActionListener(this);
        startOverButton.addActionListener(this);

        //adding each button created into each panels
        for (int i = 0; i < firstPanelButtons.length; i++) {
            firstPanelButtons[i] = new PuzzleButton("");
            firstPanelButtons[i].setBackground(Color.WHITE);
            panel4.add(firstPanelButtons[i]);
            firstPanelButtons[i].addActionListener(this);
        }

        for (int i = 0; i < secondPanelButtons.length; i++) {
            secondPanelButtons[i] = new PuzzleButton("");
            secondPanelButtons[i].setBackground(Color.WHITE);
            panel5.add(secondPanelButtons[i]);
            secondPanelButtons[i].addActionListener(this);
        }

        for (int i = 0; i < thirdPanelButtons.length; i++) {
            thirdPanelButtons[i] = new PuzzleButton("");
            thirdPanelButtons[i].setBackground(Color.WHITE);
            panel7.add(thirdPanelButtons[i]);
            thirdPanelButtons[i].addActionListener(this);
        }
    }

    private void findErrorButtons() {
        // If 0,1,2,3 is the winning combination, we will find 5,6,7...15 as the wrong combination
        // This action is performed for each set
        for (int i = 0; i < 16; i++) {
            if (!firstPanelWinningButtons.contains(i)) {
                firstPanelErrorButtons.add(i);
            }
        }
        for (int i = 0; i < 16; i++) {
            if (!secondPanelWinningButtons.contains(i)) {
                secondPanelErrorButtons.add(i);
            }
        }
        for (int i = 0; i < 16; i++) {
            if (!thirdPanelWinningButtons.contains(i)) {
                thirdPanelErrorButtons.add(i);
            }
        }
    }

    private void clearErrors() {

        // If the current text in a winning button is X, its changed to ""
        for (int i = 0; i < firstPanelWinningButtons.size(); i++) {
            if (firstPanelButtons[firstPanelWinningButtons.get(i)].getText().equals("X")) {
                firstPanelButtons[firstPanelWinningButtons.get(i)].setValue("");
            }
        }
        for (int i = 0; i < secondPanelWinningButtons.size(); i++) {
            if (secondPanelButtons[secondPanelWinningButtons.get(i)].getText().equals("X")) {
                secondPanelButtons[secondPanelWinningButtons.get(i)].setValue("");
            }
        }
        for (int i = 0; i < thirdPanelWinningButtons.size(); i++) {
            if (thirdPanelButtons[thirdPanelWinningButtons.get(i)].getText().equals("X")) {
                thirdPanelButtons[thirdPanelWinningButtons.get(i)].setValue("");
            }
        }
        //if the current text in a winning button is O, its changed to ""
        for (int i = 0; i < firstPanelErrorButtons.size(); i++) {
            if (firstPanelButtons[firstPanelErrorButtons.get(i)].getText().equals("O")) {
                firstPanelButtons[firstPanelErrorButtons.get(i)].setValue("");
            }
        }
        for (int i = 0; i < secondPanelErrorButtons.size(); i++) {
            if (secondPanelButtons[secondPanelErrorButtons.get(i)].getText().equals("O")) {
                secondPanelButtons[secondPanelErrorButtons.get(i)].setValue("");
            }
        }
        for (int i = 0; i < thirdPanelErrorButtons.size(); i++) {
            if (thirdPanelButtons[thirdPanelErrorButtons.get(i)].getText().equals("O")) {
                thirdPanelButtons[thirdPanelErrorButtons.get(i)].setValue("");
            }
        }
        undoButton1 = null;
    }

    private void startOver() {
        // Resetting string in each button
        for (int i = 0; i < 16; i++) {
            firstPanelButtons[i].setValue("");
            secondPanelButtons[i].setValue("");
            thirdPanelButtons[i].setValue("");
        }
        undoButton1 = null;
    }

    private void hint() {
        //if in first panel, any winning combination is not selected, then the first one as per the combination is set as O
        //if every combination is selected, we will pass on to 2nd panel and then third

        firstPanelHint = false;

        for (int i = 0; i < firstPanelWinningButtons.size(); i++) {
            if (firstPanelButtons[firstPanelWinningButtons.get(i)].getText().equals("") || firstPanelButtons[firstPanelWinningButtons.get(i)].getText().equals("X")) {
                firstPanelButtons[firstPanelWinningButtons.get(i)].setValue("O");
                firstPanelHint = true;
                break;
            }
        }
        if (!firstPanelHint) {
            secondPanelHint = false;
            for (int i = 0; i < secondPanelWinningButtons.size(); i++) {
                if (secondPanelButtons[secondPanelWinningButtons.get(i)].getText().equals("") || secondPanelButtons[secondPanelWinningButtons.get(i)].getText().equals("X")) {
                    secondPanelButtons[secondPanelWinningButtons.get(i)].setValue("O");
                    secondPanelHint = true;
                    break;
                }
            }
        }
        if (!firstPanelHint && !secondPanelHint) {
            thirdPanelHint = false;
            for (int i = 0; i < thirdPanelWinningButtons.size(); i++) {
                if (thirdPanelButtons[thirdPanelWinningButtons.get(i)].getText().equals("") || thirdPanelButtons[thirdPanelWinningButtons.get(i)].getText().equals("X")) {
                    thirdPanelButtons[thirdPanelWinningButtons.get(i)].setValue("O");
                    thirdPanelHint = true;
                    break;
                }
            }
        }
        if (!firstPanelHint && !secondPanelHint && !thirdPanelHint) {
            JOptionPane.showMessageDialog(this, "You ran out of hints!", "Uh Oh...", JOptionPane.INFORMATION_MESSAGE);
        }
        undoButton1 = null;

    }
    // Main method
    public static void main(String args[]) {
        new LogicPuzzleWindow(); // start logic puzzle
    }
} // end of class
