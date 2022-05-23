import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.Component;
import java.util.ArrayList;

public class GUI implements ActionListener
{
    private JPanel eventPanel;
    private JTextField zipCodeEntry;
    private JTextField eventEntry;
    private Networking client;
    private JTextArea eventInfo;
    private ArrayList<TicketMaster> eventList;

    public GUI()
    {
        eventInfo = new JTextArea(20, 35);
        eventEntry = new JTextField();
        zipCodeEntry = new JTextField();
        eventList = new ArrayList<TicketMaster>();
        client = new Networking();
        eventPanel = new JPanel();

        setUpGUI();
    }

    private void setUpGUI()
    {
        JFrame frame = new JFrame("Ticket Master");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon image = new ImageIcon("src/ticketmaster.jpg");
        Image imageData = image.getImage();
        Image scaledImage = imageData.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(scaledImage);
        JLabel pictureLabel = new JLabel(image);
        JLabel welcomeLabel = new JLabel("   Events Available");
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.blue);

        JPanel logoWelcomePanel = new JPanel();
        logoWelcomePanel.add(pictureLabel);
        logoWelcomePanel.add(welcomeLabel);

        JPanel eventListPanel = new JPanel();
        eventInfo.setText("events loading...");
        eventInfo.setFont(new Font("Helvetica", Font.PLAIN, 16));
        eventInfo.setWrapStyleWord(true);
        eventInfo.setLineWrap(true);
        eventListPanel.add(eventInfo);

        JLabel zipLabel = new JLabel("Enter Zip Code: ");
        zipCodeEntry = new JTextField(10);
        JButton enterButton = new JButton("Enter");
        JButton resetButton = new JButton("Reset");
        eventPanel.add(zipLabel);
        eventPanel.add(zipCodeEntry);
        eventPanel.add(enterButton);
        eventPanel.add(resetButton);

        frame.add(logoWelcomePanel, BorderLayout.NORTH);
        frame.add(eventListPanel, BorderLayout.CENTER);
        frame.add(eventPanel, BorderLayout.SOUTH);

        enterButton.addActionListener(this);
        resetButton.addActionListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        JButton button = (JButton) (e.getSource());

        String text = button.getText();

        if (text.equals("Enter"))
        {
            loadDisplay();
            loadInformation();
        }
        else if (text.equals("Reset"))
        {
            zipCodeEntry.setText("");
        }
        else if (text.equals("Submit"))
        {
            String selectedEventNum = eventEntry.getText();
            int eventNumInt = Integer.parseInt(selectedEventNum);

            int eventIdx = eventNumInt - 1;
            TicketMaster selectedEvent = eventList.get(eventIdx);

            //loadEventInfo(selectedEvent);
        }
        else if (text.equals("Clear"))
        {
            eventEntry.setText("");
        }
    }

    public void loadDisplay()
    {
        Component[] componentList = eventPanel.getComponents();

        for(Component c : componentList)
        {
            if(c instanceof JLabel || c instanceof JButton)
            {
                eventPanel.remove(c);
            }
        }
        eventPanel.revalidate();
        eventPanel.repaint();

        JLabel eventLabel = new JLabel("Which Event? (Enter 1-" + eventList.size() + "): ");
        eventEntry = new JTextField(10);
        JButton submitButton = new JButton("Enter");
        JButton clearButton = new JButton("Reset");
        eventPanel.add(eventLabel);
        eventPanel.add(eventEntry);
        eventPanel.add(submitButton);
        eventPanel.add(clearButton);

        submitButton.addActionListener(this);
        clearButton.addActionListener(this);
    }

    public void loadInformation()
    {
        String zip = zipCodeEntry.getText();
        eventList = client.getEvents(zip);
        String info = "";
        for (int i = 1; i < eventList.size() + 1; i++)
        {
            info += "";
        }
        eventPanel.add(eventInfo);
        eventInfo.setText(info);
    }
}
