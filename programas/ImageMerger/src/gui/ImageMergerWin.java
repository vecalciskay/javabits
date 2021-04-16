package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileFilter;

import objects.ImageMergerModel;
import objects.ImagePixels;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImageMergerWin extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final Logger theLog = LogManager.getRootLogger();
	private ImageMergerModel model;
	private String lastDir;

	public ImageMergerWin() {
		super("Image Merger");
		model = new ImageMergerModel(600, 400);
		lastDir = "";
		init();

		theLog.info("Created window with model: " + model.toString());
	}

	public void init() {

		init_addTabs();

		JMenuBar menuBar = new JMenuBar();

		init_addMenuFile(menuBar);
		init_addMenuImage(menuBar);
		init_addMenuOptions(menuBar);

		this.setJMenuBar(menuBar);

		this.pack();
	}

	private void init_addMenuOptions(JMenuBar menuBar) {
		JMenu mnu = new JMenu("Options");

		addItemSetDivisions(mnu);

		menuBar.add(mnu);
	}

	private void addItemSetDivisions(JMenu mnu) {
		JMenuItem item = new JMenuItem("Number of divisions");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mnuOptions_SetDivisions();
			}
		});

		mnu.add(item);
	}

	protected void mnuOptions_SetDivisions() {
		SpinnerNumberModel sModel = new SpinnerNumberModel(model.getNumberDivisions(), 
				ImageMergerModel.MIN_NUMBER_DIVISIONS, 
				ImageMergerModel.MAX_NUMBER_DIVISIONS, 1);
		JSpinner spinner = new JSpinner(sModel);
		
		int option = JOptionPane.showOptionDialog(null, spinner, "Enter number of divisions", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (option == JOptionPane.CANCEL_OPTION)
		{
		    return;
		} 
		else if (option == JOptionPane.OK_OPTION)
		{
			int newNumberDivisions = (Integer)spinner.getValue();
		    model.setNumberDivisions(newNumberDivisions);
		}
	}

	private void init_addMenuImage(JMenuBar menuBar) {
		JMenu mnu = new JMenu("Image");

		addItemMerge(mnu);

		menuBar.add(mnu);
	}

	private void addItemMerge(JMenu mnu) {
		JMenuItem item = new JMenuItem("Merge");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.calculateResult();
			}
		});

		mnu.add(item);
	}

	private void init_addMenuFile(JMenuBar menuBar) {
		JMenu mnuFile = new JMenu("File");

		addItemLoadImage1(mnuFile);
		addItemLoadImage2(mnuFile);
		mnuFile.addSeparator();
		addItemExit(mnuFile);

		menuBar.add(mnuFile);
	}

	private void init_addTabs() {
		JTabbedPane tabs = new JTabbedPane();

		ImagePanel panel = new ImagePanel(model.getImage1());
		model.getImage1().addObserver(panel);
		tabs.addTab("Imagen 1", panel);

		panel = new ImagePanel(model.getImage2());
		model.getImage2().addObserver(panel);
		tabs.addTab("Imagen 2", panel);

		panel = new ImagePanel(model.getResult());
		model.getResult().addObserver(panel);
		tabs.addTab("Result", panel);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(tabs, BorderLayout.CENTER);

	}

	private void addItemExit(JMenu mnuFile) {
		JMenuItem item = new JMenuItem("Exit");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mnuFile_Exit();
			}
		});

		mnuFile.add(item);
	}

	protected void mnuFile_Exit() {
		System.exit(DISPOSE_ON_CLOSE);
	}

	private void addItemLoadImage1(JMenu mnuFile) {
		JMenuItem item = new JMenuItem("Load Image 1");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showChooserAndAssignToImage(model.getImage1());
			}
		});

		mnuFile.add(item);
	}

	private void addItemLoadImage2(JMenu mnuFile) {
		JMenuItem item = new JMenuItem("Load Image 2");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showChooserAndAssignToImage(model.getImage2());
			}
		});

		mnuFile.add(item);
	}

	protected void showChooserAndAssignToImage(ImagePixels modelImage) {
		JFileChooser inputFile = new JFileChooser();
		if (!lastDir.equals(""))
			inputFile.setCurrentDirectory(new File(lastDir));

		inputFile.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				String extension = f.getAbsolutePath().substring(f.getAbsolutePath().length() - 4);
				lastDir = f.getParent();
				extension = extension.toLowerCase();
				return extension.equals(".jpg") || extension.equals(".gif") || extension.equals(".png");
			}
		});
		inputFile.showOpenDialog(this);

		if (inputFile.getSelectedFile() == null) {
			JOptionPane.showMessageDialog(this, "You must choose an image");
			return;
		}

		BufferedImage img = null;
		try {
			img = ImageIO.read(inputFile.getSelectedFile());
		} catch (IOException e) {
			;
		}

		modelImage.setImage(img);
		modelImage.setName(inputFile.getSelectedFile().getName());
		theLog.info("Loaded image " + inputFile.getSelectedFile().getAbsolutePath());
	}
}
