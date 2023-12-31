package PL;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * This class represents a graphical user interface for SplashGUI.
 */

public class SplashGUI extends JFrame {
	private static final int SPLASH_SCREEN_DURATION = 5000; // 5 seconds

	public SplashGUI() {
		setTitle("Arabic Poetry Encyclopedia");
		setSize(550, 350);
		setUndecorated(true); // No window decorations
		setLocationRelativeTo(null); // Center the frame on the screen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(238, 238, 238));
		
		JPanel panel = new JPanel(new BorderLayout());

		// Create label with big size font

		Font quicksandFont = loadFont("fonts/Quicksand-Light.ttf");

		JLabel titleLabel = new JLabel("Arabic Poetry Encyclopedia");
		titleLabel.setFont(quicksandFont.deriveFont(Font.PLAIN, 36));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(titleLabel, BorderLayout.CENTER);

		// Create label for "Power By Logitech" at the bottom
		JLabel poweredByLabel = new JLabel("Copyright © 2023 - All Rights Reserverd");
		poweredByLabel.setFont((new Font("Arial", Font.PLAIN, 10)));
		poweredByLabel.setHorizontalAlignment(JLabel.CENTER);

		// Set an empty border with a bottom margin of 10 pixels
		poweredByLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		panel.add(poweredByLabel, BorderLayout.SOUTH);

		// Display the frame
		setContentPane(panel);
		setVisible(true);

		// Close the splash screen after a delay
		Timer timer = new Timer(SPLASH_SCREEN_DURATION, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                dispose(); // Close the splash screen
                new Main(); // Open the Main.java window
			}
		});
		timer.setRepeats(false); // Only run once
		timer.start();
	}

	public static void main(String[] args) {
		// Run the splash screen
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SplashGUI();
			}
		});
	}

	private Font loadFont(String fontFileName) {
		try {
			File fontFile = new File(fontFileName);
			return Font.createFont(Font.TRUETYPE_FONT, fontFile);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			return new Font("Arial", Font.PLAIN, 12); // Use default font if loading fails
		}
	}
}