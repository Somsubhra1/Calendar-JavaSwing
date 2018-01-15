import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Main extends JFrame implements ActionListener {

    //Declaring variables:

    private JLabel monthLabel;
    private JButton leftButton, rightButton;
    private JPanel panel;
    private Calendar calendar;
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollPane;

    private Main() {

        //Setting frame:

        setTitle("Calendar");
        setSize(400,200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Setting month label:

        monthLabel = new JLabel();
        monthLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Setting buttons:

        leftButton = new JButton("<");
        rightButton = new JButton(">");

        //Setting panel:

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(leftButton, BorderLayout.WEST);
        panel.add(rightButton, BorderLayout.EAST);
        panel.add(monthLabel, BorderLayout.CENTER);

        //Setting Calendar to table:

        String days[] = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};

        calendar = new GregorianCalendar();

        model = new DefaultTableModel(null, days) {
            @Override
            public boolean isCellEditable(int row, int column) {  //Setting the table not editable and not selectable
                table.setFocusable(false);
                table.setRowSelectionAllowed(false);
                return false;
            }
        };

        table = new JTable(model);
        scrollPane = new JScrollPane(table);

        add(panel,BorderLayout.NORTH); add(scrollPane, BorderLayout.CENTER);

        //Adding ActionListener to buttons:

        leftButton.addActionListener(this); rightButton.addActionListener(this);

        //Initializing the month:

        updateMonth();

        setVisible(true);

    }// End of Constructor

    //Updating month:

    private void updateMonth() {

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        int year = calendar.get(Calendar.YEAR);
        monthLabel.setText(month + " " + year);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK);
        int numberDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int weeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);

        model.setRowCount(0);
        model.setRowCount(weeks);

        int i = startDay - 1;
        for(int j = 1; j <= numberDays; j++) {
            model.setValueAt(j, i/7, i%7);
            i++;
        }
    }// End of method updateMonth

    //Setting ActionLister to change the months:

    @Override
    public void actionPerformed(ActionEvent e) {

        if( e.getSource() == leftButton ) {
            calendar.add(Calendar.MONTH, -1);
            updateMonth();
        }
        else if ( e.getSource() == rightButton ) {
            calendar.add(Calendar.MONTH, +1);
            updateMonth();
        }
    }

    //Main method:

    public static void main(String[] args) {

        new Main();

    }// End of Main method

}// End of Class
