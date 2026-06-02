package com.homestaybooking.view;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;

public class CreateDialog extends JDialog {
    private static final Color SURFACE = Color.WHITE;
    private static final Color BORDER = new Color(203, 213, 225);
    private static final Color PRIMARY = new Color(30, 93, 156);
    private static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 12);
    protected final JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));

    public CreateDialog(Frame owner, String title) {
        super(owner, title, true);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(SURFACE);
        form.setBackground(SURFACE);
        form.setBorder(BorderFactory.createEmptyBorder(18, 20, 12, 20));
        add(form, BorderLayout.CENTER);
        setSize(460, 360);
        setLocationRelativeTo(owner);
    }

    protected JTextField text(String label) {
        JTextField field = new JTextField();
        styleField(field);
        form.add(label(label));
        form.add(field);
        return field;
    }

    protected JPasswordField password(String label) {
        JPasswordField field = new JPasswordField();
        styleField(field);
        form.add(label(label));
        form.add(field);
        return field;
    }

    protected JTextArea area(String label) {
        JTextArea field = new JTextArea(4, 20);
        field.setFont(BODY_FONT);
        field.setLineWrap(true);
        field.setWrapStyleWord(true);
        field.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        JScrollPane scrollPane = new JScrollPane(field);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        form.add(label(label));
        form.add(scrollPane);
        return field;
    }

    protected JComboBox<String> combo(String label, String... values) {
        JComboBox<String> field = new JComboBox<>(values);
        field.setFont(BODY_FONT);
        form.add(label(label));
        form.add(field);
        return field;
    }

    protected JButton button(String title) {
        JButton button = new JButton(title);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
        JPanel panel = new JPanel();
        panel.setBackground(SURFACE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 18, 20));
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
        JOptionPane.showMessageDialog(this, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(new Color(51, 65, 85));
        return label;
    }

    private void styleField(JComponent field) {
        field.setFont(BODY_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
    }
}
