import java.awt.Color;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.ListSelectionModel;

/**
 * The UI for the project.
 * 
 * @author Daniel Townsend
 *
 */
public class ShortestPathUI extends JFrame {

	private JPanel contentPane;
	private JRadioButton distCostButton;
	private JRadioButton timeCostButton;
	public Graph graph;
	private JButton findPathButton;
	private JTextField fileNameTextBox;
	private JTextField txtInvalidFileName;
	private JList toVertexList;
	private JList fromVertexList;
	private JTextField fromLabel;
	private JTextField toLabel;
	private JTextField txtPathOptions;
	private JCheckBox addressFormatOption;
	private JTextArea outputTextArea;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JButton btnShowCredits;
	private JLabel creditsLabel;
	private JRadioButton restStopButton;
	private JRadioButton leftTurnButton;
	private JRadioButton chargingStationsButton;
	private JTextField arrowText;
	private JToggleButton darkModeButton;
	private JButton loadButton;
	private JTextField txtEnterFileName;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShortestPathUI frame = new ShortestPathUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}

	/**
	 * Create the frame.
	 */
	public ShortestPathUI() {
		setResizable(false);
		setTitle("Shortest Path");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 875, 619);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnNewMenu = new JMenu("Info");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("What is Dijkstra's algorithm?");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Action.BROWSE)) {
					try {
						Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/Dijkstra's_algorithm"));
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		distCostButton = new JRadioButton("Use distance cost");
		distCostButton.setSelected(true);
		distCostButton.setForeground(Color.BLACK);
		distCostButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		distCostButton.setBackground(Color.WHITE);
		timeCostButton = new JRadioButton("Use time cost");
		timeCostButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		timeCostButton.setBackground(Color.WHITE);
		timeCostButton.setForeground(Color.BLACK);
		
		timeCostButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(timeCostButton.isSelected()) {
					Graph.useDistCost = false;
					distCostButton.setSelected(false);
					Graph.useTimeCost = true;
					timeCostButton.setSelected(true);
					Graph.useLeftTurns = false;
					leftTurnButton.setSelected(false);
					Graph.useRestStops = false;
					restStopButton.setSelected(false);
					chargingStationsButton.setSelected(false);
				}
			}
		});
		
		distCostButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(distCostButton.isSelected()) {
					Graph.useDistCost = true;
					distCostButton.setSelected(true);
					Graph.useTimeCost = false;
					timeCostButton.setSelected(false);
					Graph.useLeftTurns = false;
					leftTurnButton.setSelected(false);
					Graph.useRestStops = false;
					restStopButton.setSelected(false);
					chargingStationsButton.setSelected(false);
				}
			}
		});
		
		distCostButton.setToolTipText("");
		distCostButton.setBounds(47, 128, 154, 23);
		contentPane.add(distCostButton);
		
		
		timeCostButton.setBounds(203, 128, 121, 23);
		contentPane.add(timeCostButton);
		
		findPathButton = new JButton("Find optimal path");
		findPathButton.setEnabled(false);
		findPathButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Path shortestPath = Dijkstra.shortestPath(graph, (Vertex) fromVertexList.getSelectedValue(), (Vertex) toVertexList.getSelectedValue());
				if(((Vertex)fromVertexList.getSelectedValue()).equals(((Vertex)toVertexList.getSelectedValue()))) {
					outputTextArea.setText("Starting point and destination are the same!");
				} else {
					if(shortestPath == null) {
					outputTextArea.setText("No such path exists!");
					} else {
						outputTextArea.setText(shortestPath.toString());
					}
				}
				
				
			}
		});
		
		findPathButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		findPathButton.setBounds(652, 255, 177, 106);
		contentPane.add(findPathButton);
		
		darkModeButton = new JToggleButton("Dark Mode");
		darkModeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(darkModeButton.isSelected()) {
					// background
					contentPane.setBackground(Color.DARK_GRAY);
					
					// load button
					loadButton.setForeground(Color.WHITE);
					loadButton.setBackground(Color.GRAY);
					
					// dark mode button
					darkModeButton.setForeground(Color.WHITE);
					darkModeButton.setBackground(Color.GRAY);
					
					// show credits button
					btnShowCredits.setForeground(Color.WHITE);
					btnShowCredits.setBackground(Color.GRAY);
					
					// find path button
					findPathButton.setForeground(Color.WHITE);
					findPathButton.setBackground(Color.GRAY);
					
					// credits label
					creditsLabel.setForeground(Color.WHITE);
					
					// path options label
					txtPathOptions.setForeground(Color.WHITE);
					txtPathOptions.setBackground(Color.DARK_GRAY);
					
					// to label
					toLabel.setForeground(Color.WHITE);
					toLabel.setBackground(Color.DARK_GRAY);
					
					// from label
					fromLabel.setForeground(Color.WHITE);
					fromLabel.setBackground(Color.DARK_GRAY);
					
					// arrow text
					arrowText.setForeground(Color.WHITE);
					arrowText.setBackground(Color.DARK_GRAY);
					
					// enter file name text
					txtEnterFileName.setForeground(Color.WHITE);
					txtEnterFileName.setBackground(Color.DARK_GRAY);
					
					// invalid file name text
					txtInvalidFileName.setBackground(Color.DARK_GRAY);
					
					// distance cost button
					distCostButton.setForeground(Color.WHITE);
					distCostButton.setBackground(Color.DARK_GRAY);
					
					// time cost button
					timeCostButton.setForeground(Color.WHITE);
					timeCostButton.setBackground(Color.DARK_GRAY);
					
					// rest stop button
					restStopButton.setForeground(Color.WHITE);
					restStopButton.setBackground(Color.DARK_GRAY);
					
					// left turns button
					leftTurnButton.setForeground(Color.WHITE);
					leftTurnButton.setBackground(Color.DARK_GRAY);
					
					// charging stations button
					chargingStationsButton.setForeground(Color.WHITE);
					chargingStationsButton.setBackground(Color.DARK_GRAY);
					
					// address format checkbox
					addressFormatOption.setForeground(Color.WHITE);
					addressFormatOption.setBackground(Color.DARK_GRAY);
					
					// file name text box
					fileNameTextBox.setForeground(Color.WHITE);
					fileNameTextBox.setBackground(Color.BLACK);
					
					// from vertex list
					fromVertexList.setForeground(Color.WHITE);
					fromVertexList.setBackground(Color.GRAY);
					
					// to vertex list
					toVertexList.setForeground(Color.WHITE);
					toVertexList.setBackground(Color.GRAY);
					
					// output text area
					outputTextArea.setForeground(Color.WHITE);
					outputTextArea.setBackground(Color.GRAY);				
					
				} else {
					// background
					contentPane.setBackground(Color.WHITE);
					
					// load button
					loadButton.setForeground(Color.BLACK);
					loadButton.setBackground(Color.WHITE);
					
					// dark mode button
					darkModeButton.setForeground(Color.BLACK);
					darkModeButton.setBackground(Color.WHITE);
					
					// show credits button
					btnShowCredits.setForeground(Color.BLACK);
					btnShowCredits.setBackground(Color.WHITE);
					
					// find path button
					findPathButton.setForeground(Color.BLACK);
					findPathButton.setBackground(Color.WHITE);
					
					// credits label
					creditsLabel.setForeground(Color.BLACK);
					
					// path options label
					txtPathOptions.setForeground(Color.BLACK);
					txtPathOptions.setBackground(Color.WHITE);
					
					// to label
					toLabel.setForeground(Color.BLACK);
					toLabel.setBackground(Color.WHITE);
					
					// from label
					fromLabel.setForeground(Color.BLACK);
					fromLabel.setBackground(Color.WHITE);
					
					// enter text file label
					txtEnterFileName.setForeground(Color.BLACK);
					txtEnterFileName.setBackground(Color.WHITE);
					
					// arrow text
					arrowText.setForeground(Color.BLACK);
					arrowText.setBackground(Color.WHITE);
					
					// invalid file name text
					txtInvalidFileName.setBackground(Color.WHITE);
					
					// distance cost button
					distCostButton.setForeground(Color.BLACK);
					distCostButton.setBackground(Color.WHITE);
					
					// time cost button
					timeCostButton.setForeground(Color.BLACK);
					timeCostButton.setBackground(Color.WHITE);
					
					// rest stop button
					restStopButton.setForeground(Color.BLACK);
					restStopButton.setBackground(Color.WHITE);
					
					// left turns button
					leftTurnButton.setForeground(Color.BLACK);
					leftTurnButton.setBackground(Color.WHITE);
					
					// charging stations button
					chargingStationsButton.setForeground(Color.BLACK);
					chargingStationsButton.setBackground(Color.WHITE);
					
					// address format checkbox
					addressFormatOption.setForeground(Color.BLACK);
					addressFormatOption.setBackground(Color.WHITE);
					
					// file name text box
					fileNameTextBox.setForeground(Color.BLACK);
					fileNameTextBox.setBackground(Color.WHITE);
					
					// from vertex list
					fromVertexList.setForeground(Color.BLACK);
					fromVertexList.setBackground(Color.LIGHT_GRAY);
					
					// to vertex list
					toVertexList.setForeground(Color.BLACK);
					toVertexList.setBackground(Color.LIGHT_GRAY);
					
					// output text area
					outputTextArea.setForeground(Color.BLACK);
					outputTextArea.setBackground(Color.LIGHT_GRAY);
				}
			}
		});
		
		darkModeButton.setBounds(722, 11, 121, 23);
		contentPane.add(darkModeButton);
		
		DefaultListModel<Vertex> DLM = new DefaultListModel<Vertex>();
		
		loadButton = new JButton("Load data");
		loadButton.setForeground(Color.BLACK);
		loadButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName = fileNameTextBox.getText();		
				try {
					Scanner tmpFileReader = new Scanner(new File(fileName));
					tmpFileReader.close();
					graph = new Graph(fileName);
					graph.useAlternateString = true;
					for(Vertex vertex : graph.vertexList) {	
						DLM.addElement(vertex);
					}
					toVertexList.setSelectedIndex(0);
					fromVertexList.setSelectedIndex(0);
					txtInvalidFileName.setVisible(false);
					findPathButton.setEnabled(true);
				} catch (Exception e1) {
					txtInvalidFileName.setVisible(true);
					findPathButton.setEnabled(false);
				}			
			}
		});
		
		loadButton.setBounds(378, 11, 115, 62);
		contentPane.add(loadButton);
		
		fileNameTextBox = new JTextField();
		fileNameTextBox.setText("MapInformation.txt");
		fileNameTextBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		fileNameTextBox.setBounds(133, 11, 235, 62);
		contentPane.add(fileNameTextBox);
		fileNameTextBox.setColumns(10);
		
		txtInvalidFileName = new JTextField();
		txtInvalidFileName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtInvalidFileName.setBorder(BorderFactory.createEmptyBorder());
		txtInvalidFileName.setEnabled(true);
		txtInvalidFileName.setVisible(false);
		txtInvalidFileName.setEditable(false);
		txtInvalidFileName.setHorizontalAlignment(SwingConstants.CENTER);
		txtInvalidFileName.setBackground(Color.WHITE);
		txtInvalidFileName.setForeground(Color.RED);
		txtInvalidFileName.setText("Invalid file name!");
		txtInvalidFileName.setBounds(185, 79, 127, 28);
		contentPane.add(txtInvalidFileName);
		txtInvalidFileName.setColumns(10);
		
		fromLabel = new JTextField();
		fromLabel.setBackground(Color.WHITE);
		fromLabel.setBorder(BorderFactory.createEmptyBorder());
		fromLabel.setEditable(false);
		fromLabel.setText("From:");
		fromLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fromLabel.setBounds(354, 212, 86, 20);
		contentPane.add(fromLabel);
		fromLabel.setColumns(10);
		
		toLabel = new JTextField();
		toLabel.setBackground(Color.WHITE);
		toLabel.setBorder(BorderFactory.createEmptyBorder());
		toLabel.setEditable(false);
		toLabel.setText("To:");
		toLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		toLabel.setColumns(10);
		toLabel.setBounds(24, 212, 86, 20);
		contentPane.add(toLabel);
		
		txtPathOptions = new JTextField();
		txtPathOptions.setText("Path options:");
		txtPathOptions.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPathOptions.setEditable(false);
		txtPathOptions.setColumns(10);
		txtPathOptions.setBorder(BorderFactory.createEmptyBorder());
		txtPathOptions.setBackground(Color.WHITE);
		txtPathOptions.setBounds(38, 101, 103, 20);
		contentPane.add(txtPathOptions);
		
		addressFormatOption = new JCheckBox("Output addresses");
		addressFormatOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(addressFormatOption.isSelected()) {
					Graph.returnAddress = true;
				} else {
					Graph.returnAddress = false;
				}
			}
		});
		
		addressFormatOption.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addressFormatOption.setBackground(Color.WHITE);
		addressFormatOption.setBounds(23, 453, 146, 28);
		contentPane.add(addressFormatOption);
		
		btnShowCredits = new JButton("Show credits");
		btnShowCredits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnShowCredits.getText().equals("Show credits")) {
					btnShowCredits.setText("Hide credits");
					creditsLabel.setVisible(true);
				} else {
					btnShowCredits.setText("Show credits");
					creditsLabel.setVisible(false);
				}
				
			}
		});
		
		btnShowCredits.setBounds(722, 45, 121, 23);
		contentPane.add(btnShowCredits);
		
		creditsLabel = new JLabel("Project by: Daniel Townsend");
		creditsLabel.setBounds(652, 72, 209, 20);
		creditsLabel.setVisible(false);
		contentPane.add(creditsLabel);
		
		leftTurnButton = new JRadioButton("Minimize left turns");
		leftTurnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(leftTurnButton.isSelected()) {
					Graph.useDistCost = false;
					distCostButton.setSelected(false);
					Graph.useTimeCost = false;
					timeCostButton.setSelected(false);
					Graph.useLeftTurns = true;
					leftTurnButton.setSelected(true);
					Graph.useRestStops = false;
					restStopButton.setSelected(false);
					chargingStationsButton.setSelected(false);
				}
				
			}
		});
		
		leftTurnButton.setToolTipText("");
		leftTurnButton.setForeground(Color.BLACK);
		leftTurnButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		leftTurnButton.setBackground(Color.WHITE);
		leftTurnButton.setBounds(47, 154, 146, 23);
		contentPane.add(leftTurnButton);
		
		restStopButton = new JRadioButton("Maximize rest stops");
		restStopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(restStopButton.isSelected()) {
					Graph.useDistCost = false;
					distCostButton.setSelected(false);
					Graph.useTimeCost = false;
					timeCostButton.setSelected(false);
					Graph.useLeftTurns = false;
					leftTurnButton.setSelected(false);
					Graph.useRestStops = true;
					restStopButton.setSelected(true);
					chargingStationsButton.setSelected(false);
				}
				
			}
		});
		
		restStopButton.setToolTipText("");
		restStopButton.setForeground(Color.BLACK);
		restStopButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		restStopButton.setBackground(Color.WHITE);
		restStopButton.setBounds(203, 154, 165, 23);
		contentPane.add(restStopButton);
		
		chargingStationsButton = new JRadioButton("Maximize vehicle charging stations");
		chargingStationsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chargingStationsButton.isSelected()) {
					Graph.useDistCost = false;
					distCostButton.setSelected(false);
					Graph.useTimeCost = false;
					timeCostButton.setSelected(false);
					Graph.useLeftTurns = false;
					leftTurnButton.setSelected(false);
					Graph.useRestStops = false;
					restStopButton.setSelected(false);
					chargingStationsButton.setSelected(true);
				}
			}
		});
		
		chargingStationsButton.setToolTipText("");
		chargingStationsButton.setForeground(Color.BLACK);
		chargingStationsButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chargingStationsButton.setBackground(Color.WHITE);
		chargingStationsButton.setBounds(354, 128, 247, 23);
		contentPane.add(chargingStationsButton);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(175, 400, 668, 141);
		contentPane.add(scrollPane_2);
		
		outputTextArea = new JTextArea();
		scrollPane_2.setViewportView(outputTextArea);
		outputTextArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
		outputTextArea.setEditable(false);
		outputTextArea.setBackground(Color.LIGHT_GRAY);
		
		arrowText = new JTextField();
		arrowText.setText("--->");
		arrowText.setFont(new Font("Tahoma", Font.PLAIN, 23));
		arrowText.setEditable(false);
		arrowText.setColumns(10);
		arrowText.setBorder(BorderFactory.createEmptyBorder());
		arrowText.setBackground(Color.WHITE);
		arrowText.setBounds(302, 292, 42, 28);
		contentPane.add(arrowText);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(358, 243, 266, 130);
		contentPane.add(scrollPane_1);
		
		toVertexList = new JList();
		toVertexList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(toVertexList);
		toVertexList.setFont(new Font("Tahoma", Font.PLAIN, 14));
		toVertexList.setBackground(SystemColor.controlHighlight);
		toVertexList.setModel(DLM);
		toVertexList.setSelectedIndex(0);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 245, 268, 132);
		contentPane.add(scrollPane);
		
		
		fromVertexList = new JList();
		fromVertexList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(fromVertexList);
		fromVertexList.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fromVertexList.setBackground(SystemColor.controlHighlight);
		fromVertexList.setModel(DLM);
		fromVertexList.setSelectedIndex(0);
		
		txtEnterFileName = new JTextField();
		txtEnterFileName.setText("Enter file name:");
		txtEnterFileName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtEnterFileName.setEditable(false);
		txtEnterFileName.setColumns(10);
		txtEnterFileName.setBorder(BorderFactory.createEmptyBorder());
		txtEnterFileName.setBackground(Color.WHITE);
		txtEnterFileName.setBounds(8, 32, 115, 20);
		contentPane.add(txtEnterFileName);
		
	}
}
