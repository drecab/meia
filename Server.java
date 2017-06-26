import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Server extends JFrame {

	private JPanel contentPane;

	
	private static Hero hero = new Hero();

	private static ServerSocket server;
	private static Socket socket;
	private static ObjectInputStream input;
	private static ObjectOutputStream output;
	
	public static void startRunning() throws IOException, ClassNotFoundException
	{
		server = new ServerSocket(4444);
		while(true)
		{
			waitConnection();
			System.out.println("a");
			setupStreams();
			run();
			//close();
		}
	}

	private static void waitConnection() throws IOException
	{
		System.out.println("Waiting...");
		socket = server.accept();
		System.out.println("Connected to "+ socket.getInetAddress().getHostName());
	}
	
	private static void setupStreams() throws IOException
	{
		output = new ObjectOutputStream(socket.getOutputStream());
		output.flush();
		input = new ObjectInputStream(socket.getInputStream());
	}
	
	private static void run() throws IOException, ClassNotFoundException
	{
		System.out.println("Insert damage: ");
		Scanner s = new Scanner(System.in);
		int damage = s.nextInt();
		dealDamage(damage);
		
		takeDamage();
	}
	
	private static void close() throws IOException
	{
		output.close();
		input.close();
		socket.close();
		System.out.println("Closed connection.");
	}
	
	public static void takeDamage() throws ClassNotFoundException, IOException
	{
		int damage = (int) input.readObject();
		hero.deal(damage);
		System.out.println("Client dealt "+damage+" to Server.");
		System.out.println("Server's HP: "+hero.getHP());
	}
	
	public static void dealDamage(int damage) throws IOException
	{
		output.writeObject(damage);
		output.flush();
	}
	
	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		startRunning();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
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
	public Server() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 333, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("ATTACK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.CENTER);
	}

}

