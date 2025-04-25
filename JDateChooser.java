import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class JDateChooser extends JPanel {
    private JTextField dateField;
    private JButton calendarButton;
    private Date selectedDate;
    private String dateFormatString = "yyyy-MM-dd";
    private SimpleDateFormat dateFormat;

    // Added fields to fix the "effectively final" issue
    private int currentMonth;
    private int currentYear;

    public JDateChooser() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        dateFormat = new SimpleDateFormat(dateFormatString);
        selectedDate = new Date();

        dateField = new JTextField(dateFormat.format(selectedDate));
        dateField.setFont(HotelManagementSystem.NORMAL_FONT);
        dateField.setBorder(new CompoundBorder(
                new LineBorder(HotelManagementSystem.SECONDARY_COLOR, 1),
                new EmptyBorder(8, 10, 8, 10)
        ));

        calendarButton = new JButton("...");
        calendarButton.setPreferredSize(new Dimension(30, 0));
        calendarButton.setFocusPainted(false);
        calendarButton.addActionListener(e -> showCalendarDialog());

        add(dateField, BorderLayout.CENTER);
        add(calendarButton, BorderLayout.EAST);
    }

    public void setDateFormatString(String format) {
        this.dateFormatString = format;
        this.dateFormat = new SimpleDateFormat(format);
        updateDateField();
    }

    public void setFont(Font font) {
        super.setFont(font);
        if (dateField != null) {
            dateField.setFont(font);
        }
    }

    private void showCalendarDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Select Date", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 350);
        dialog.setLocationRelativeTo(this);

        JPanel calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel navigationPanel = new JPanel(new BorderLayout());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);

        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);

        JLabel monthYearLabel = new JLabel(new SimpleDateFormat("MMMM yyyy").format(selectedDate));
        monthYearLabel.setFont(HotelManagementSystem.SUBHEADING_FONT);
        monthYearLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton prevButton = new JButton("<");
        prevButton.setFocusPainted(false);

        JButton nextButton = new JButton(">");
        nextButton.setFocusPainted(false);

        navigationPanel.add(prevButton, BorderLayout.WEST);
        navigationPanel.add(monthYearLabel, BorderLayout.CENTER);
        navigationPanel.add(nextButton, BorderLayout.EAST);

        JPanel weekdaysPanel = new JPanel(new GridLayout(1, 7));
        String[] weekdays = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};

        for (String weekday : weekdays) {
            JLabel label = new JLabel(weekday);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            weekdaysPanel.add(label);
        }

        JPanel daysPanel = new JPanel(new GridLayout(6, 7));

        Runnable updateCalendar = new Runnable() {
            @Override
            public void run() {
                daysPanel.removeAll();

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, currentYear);
                cal.set(Calendar.MONTH, currentMonth);
                cal.set(Calendar.DAY_OF_MONTH, 1);

                int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
                int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

                for (int i = 0; i < firstDayOfWeek; i++) {
                    daysPanel.add(new JLabel(""));
                }

                for (int day = 1; day <= daysInMonth; day++) {
                    JButton dayButton = new JButton(String.valueOf(day));
                    dayButton.setFocusPainted(false);

                    Calendar selectedCal = Calendar.getInstance();
                    selectedCal.setTime(selectedDate);

                    if (day == selectedCal.get(Calendar.DAY_OF_MONTH) &&
                            currentMonth == selectedCal.get(Calendar.MONTH) &&
                            currentYear == selectedCal.get(Calendar.YEAR)) {
                        dayButton.setBackground(HotelManagementSystem.PRIMARY_COLOR);
                        dayButton.setForeground(Color.WHITE);
                    }

                    final int selectedDay = day;
                    dayButton.addActionListener(e -> {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(currentYear, currentMonth, selectedDay);
                        selectedDate = newDate.getTime();
                        updateDateField();
                        dialog.dispose();
                    });

                    daysPanel.add(dayButton);
                }

                daysPanel.revalidate();
                daysPanel.repaint();

                monthYearLabel.setText(new SimpleDateFormat("MMMM yyyy").format(
                        new GregorianCalendar(currentYear, currentMonth, 1).getTime()));
            }
        };

        prevButton.addActionListener(e -> {
            if (currentMonth == 0) {
                currentMonth = 11;
                currentYear--;
            } else {
                currentMonth--;
            }
            updateCalendar.run();
        });

        nextButton.addActionListener(e -> {
            if (currentMonth == 11) {
                currentMonth = 0;
                currentYear++;
            } else {
                currentMonth++;
            }
            updateCalendar.run();
        });

        updateCalendar.run();

        calendarPanel.add(navigationPanel, BorderLayout.NORTH);
        calendarPanel.add(weekdaysPanel, BorderLayout.CENTER);
        calendarPanel.add(daysPanel, BorderLayout.SOUTH);

        dialog.add(calendarPanel);
        dialog.setVisible(true);
    }

    private void updateDateField() {
        dateField.setText(dateFormat.format(selectedDate));
    }
}
