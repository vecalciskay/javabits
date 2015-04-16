package gui;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import bll.GameBoard;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.util.Observable;
import java.util.Observer;

public class StatusPanel extends JPanel implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getRootLogger();
	private GameBoard theGame;
	private JLabel lblLblscore;
	private JLabel lbltiempo;
	private long startTime;
	private boolean pleaseStop;

	/**
	 * Create the panel.
	 */
	public StatusPanel() {
		setBackground(SystemColor.textHighlightText);
		setLayout(new BorderLayout(5, 5));
		
		JLabel lblScoreTime = new JLabel("Score / Tiempo");
		lblScoreTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblScoreTime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(lblScoreTime, BorderLayout.NORTH);
		
		lblLblscore = new JLabel("");
		lblLblscore.setForeground(SystemColor.textHighlight);
		lblLblscore.setHorizontalAlignment(SwingConstants.CENTER);
		lblLblscore.setFont(new Font("Tahoma", Font.PLAIN, 60));
		add(lblLblscore, BorderLayout.CENTER);
		
		lbltiempo = new JLabel("");
		lbltiempo.setHorizontalAlignment(SwingConstants.CENTER);
		lbltiempo.setFont(new Font("Tahoma", Font.PLAIN, 40));
		add(lbltiempo, BorderLayout.SOUTH);

		logger.debug("Created swing components in panel status");
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		theGame = (GameBoard) arg0;
		
		lblLblscore.setText(String.valueOf( theGame.getScore()));
		
		if (theGame.getFigures().size() == 0)
			pleaseStop = true;
	}

	public void startTimer() {
		pleaseStop = false;
		Thread timer = new Thread(new Runnable() {

			@Override
			public void run() {
				runTimer();
			}
			
		});
		
		timer.start();
	}
	
	public void stopTimer() {
		pleaseStop = true;
	}

	protected void runTimer() {
		startTime = System.currentTimeMillis();
		
		while(!pleaseStop) {
			long currentTime = System.currentTimeMillis();
			double seconds = (currentTime - startTime) / 1000.0;
			this.lbltiempo.setText(String.format("%1$,.2f", seconds));
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				;
			}
		}
	}
}
