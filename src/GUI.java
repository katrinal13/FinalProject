import javax.imageio.ImageIO;
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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GUI implements ActionListener, ItemListener
{
    private JPanel eventPanel;
    private JTextField zipCodeEntry;
    private JTextField eventEntry;
    private Networking client;
    private JTextArea eventInfo;
    private ArrayList<TicketMaster> eventList;

    private EventDetails eventDetails;

    private TicketMaster selectedEvent;

    private String seatmapStr;

    private JCheckBox venues;

    private JCheckBox presale;

    public GUI()
    {
        eventInfo = new JTextArea(35, 70);
        eventEntry = new JTextField();
        zipCodeEntry = new JTextField();
        eventList = new ArrayList<TicketMaster>();
        client = new Networking();
        eventPanel = new JPanel();
        eventDetails = null;
        selectedEvent = null;
        seatmapStr = "";
        venues = new JCheckBox();
        presale = new JCheckBox();

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
        frame.add(eventListPanel, BorderLayout.SOUTH);
        frame.add(eventPanel, BorderLayout.CENTER);

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
            selectedEvent = eventList.get(eventIdx);

            loadEventInfo(selectedEvent);

            JButton seatmap = new JButton("Seatmap");

            venues = new JCheckBox("Venues");
            presale = new JCheckBox("Presale");

            seatmap.addActionListener(this);
            venues.addItemListener(this);
            presale.addItemListener(this);

            eventPanel.add(seatmap);
            eventPanel.add(venues);
            eventPanel.add(presale);
        }
        else if (text.equals("Clear"))
        {
            eventEntry.setText("");
        }
        else if (text.equals("Seatmap"))
        {
            if (!eventDetails.getSeatmap().equals(""))
            {
                try {
                    URL imageURL = new URL(eventDetails.getSeatmap());
                    BufferedImage image = ImageIO.read(imageURL);
                    JFrame frame = new JFrame("Seatmap " + eventDetails.getEventName());
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    JLabel movieImage = new JLabel(new ImageIcon(image));
                    frame.add(movieImage);
                    frame.pack();
                    frame.setVisible(true);
                } catch (IOException exc) {
                    System.out.println(exc.getMessage());
                }
            }
            else
            {
                if (seatmapStr.equals(""))
                {
                    seatmapStr = "There is no seatmap for this event.";
                    eventInfo.append(seatmapStr);
                }
            }
        }
    }

    public void loadDisplay()
    {
        Component[] componentList = eventPanel.getComponents();

        for(Component c : componentList)
        {
            if(c instanceof JLabel || c instanceof JButton || c instanceof JTextField)
            {
                eventPanel.remove(c);
            }
        }
        eventPanel.revalidate();
        eventPanel.repaint();

        String zip = zipCodeEntry.getText();
        eventList = client.getEvents(zip);
        String info = "";
        for (int i = 0; i < eventList.size(); i++)
        {
            info += i + 1 + ". " + eventList.get(i).getEventName() + ", " + eventList.get(i).getEventID() + "\n";
        }

        JLabel eventLabel = new JLabel("Which Event? (Enter 1-" + eventList.size() + "): ");
        eventEntry = new JTextField(10);
        JButton submitButton = new JButton("Submit");
        JButton clearButton = new JButton("Clear");
        eventPanel.add(eventLabel);
        eventPanel.add(eventEntry);
        eventPanel.add(submitButton);
        eventPanel.add(clearButton);

        eventPanel.add(eventInfo);
        eventInfo.setText(info);

        submitButton.addActionListener(this);
        clearButton.addActionListener(this);
    }

    public void loadEventInfo(TicketMaster event)
    {
        Component[] componentList = eventPanel.getComponents();

        for(Component c : componentList)
        {
            if(c instanceof JLabel || c instanceof JButton || c instanceof JTextField)
            {
                if (c instanceof JButton)
                {
                    JButton button = (JButton) c;
                    if (!button.getText().equals("Seatmap"))
                    {
                        eventPanel.remove(c);
                    }
                }
                else
                {
                    eventPanel.remove(c);
                }
            }
        }
        eventPanel.revalidate();
        eventPanel.repaint();

        eventDetails = client.getEventDetails(event.getEventID());

        String info = "";
        if (!eventDetails.getInfo().equals(""))
        {
            info += "Info: " + eventDetails.getInfo() + "\n\n";
        }
        if (!eventDetails.getStartLocalDate().equals(""))
        {
            info += "Start Date/Time: " + eventDetails.getStartLocalDate() + " @ " + eventDetails.getStartTime() + "\n\n";
        }
        if (!eventDetails.getEndLocalDate().equals(""))
        {
            info += "End Date/Time: " + eventDetails.getEndLocalDate() + " @ " + eventDetails.getEndTime() + "\n\n";
        }
        if (!eventDetails.getTicketLimit().equals(""))
        {
            info += "Ticket Limit: " + eventDetails.getTicketLimit() + "\n\n";
        }
        if (!eventDetails.getSaleStart().equals(""))
        {
            info += "Sale Start: " + eventDetails.getSaleStart() + "\n\n";
        }
        if (!eventDetails.getSaleEnd().equals(""))
        {
            info += "Sale End: " + eventDetails.getSaleEnd() + "\n\n";
        }
        if (!eventDetails.getURL().equals(""))
        {
            info += "URL: " + eventDetails.getURL() + "\n\n";
        }
        if (!eventDetails.getPleaseNote().equals(""))
        {
            info += "Please Note: " + eventDetails.getPleaseNote() + "\n\n";
        }
        eventInfo.setText(info);
    }

    public void itemStateChanged(ItemEvent e)
    {
        JCheckBox box = (JCheckBox) (e.getSource());
        String text = box.getText();

        if (box.isSelected())
        {
            if (text.equals("Venues"))
            {
                appendVenues();
            }
            if (text.equals("Presale"))
            {
                appendPresale();
            }
        }
        else if (!box.isSelected())
        {
            loadEventInfo(selectedEvent);
            if (!seatmapStr.equals(""))
            {
                eventInfo.append(seatmapStr);
            }
            if (venues.isSelected())
            {
                appendVenues();
            }
            else if (presale.isSelected())
            {
                appendPresale();
            }
        }
    }

    private void appendVenues()
    {
        ArrayList<String[]> venues = eventDetails.getVenues();
        for (String[] arr : venues)
        {
            eventInfo.append(arr[0] + "\n" + arr[1] + "\n" + arr[2] + "\n" + arr[3] + "\n" + arr[4] + "\n\n");
        }
    }

    private void appendPresale()
    {
        if (eventDetails.getPresales().size() == 0)
        {
            eventInfo.append("There are no presales for this event.");
        }
        else
        {
            ArrayList<String[]> presale = eventDetails.getPresales();
            if (presale != null)
            {
                for (String[] arr : presale)
                {
                    for (String str : arr)
                    {
                        if (!str.equals(""))
                        {
                            eventInfo.append(str + "\n");
                        }
                    }
                    eventInfo.append("\n");
                }
            }
        }
    }
}

