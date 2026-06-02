package com.homestaybooking.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.time.LocalDate;

public class CreateDialog extends JDialog {
    protected final JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));

    public CreateDialog(Frame owner, String title) {
        super(owner, title, true);
        setLayout(new BorderLayout(8, 8));
        add(form, BorderLayout.CENTER);
        setSize(420, 320);
        setLocationRelativeTo(owner);
    }

    protected JTextField text(String label) {
        JTextField field = new JTextField();
        form.add(new JLabel(label));
        form.add(field);
        return field;
    }

    protected JPasswordField password(String label) {
        JPasswordField field = new JPasswordField();
        form.add(new JLabel(label));
        form.add(field);
        return field;
    }

    protected JTextArea area(String label) {
        JTextArea field = new JTextArea(4, 20);
        form.add(new JLabel(label));
        form.add(field);
        return field;
    }

    protected JComboBox<String> combo(String label, String... values) {
        JComboBox<String> field = new JComboBox<>(values);
        form.add(new JLabel(label));
        form.add(field);
        return field;
    }

    protected JButton button(String title) {
        JButton button = new JButton(title);
        JPanel panel = new JPanel();
        panel.add(button);
        add(panel, BorderLayout.SOUTH);
        return button;
    }

    protected LocalDate parseDate(JTextField field) {
        if (field.getText().isBlank()) {
            return null;
        }
        return LocalDate.parse(field.getText().trim());
    }

    protected int parseInt(JTextField field) {
        return Integer.parseInt(field.getText().trim());
    }

    protected void showError(Exception exception) {
        JOptionPane.showMessageDialog(this, exception.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
    }
}
