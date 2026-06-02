package com.homestaybooking.view;

import com.homestaybooking.controller.BookingController;
import com.homestaybooking.controller.HomestayController;
import com.homestaybooking.controller.ReviewController;
import com.homestaybooking.controller.RoomController;
import com.homestaybooking.controller.UserController;
import com.homestaybooking.model.domain.Booking;
import com.homestaybooking.model.domain.Homestay;
import com.homestaybooking.model.domain.Room;
import com.homestaybooking.model.domain.User;
import com.homestaybooking.model.enums.UserRole;
import com.homestaybooking.model.repository.BookingRepository;
import com.homestaybooking.model.repository.HomestayRepository;
import com.homestaybooking.model.repository.ReviewRepository;
import com.homestaybooking.model.repository.RoomRepository;
import com.homestaybooking.model.repository.UserRepository;
import com.homestaybooking.model.service.BookingService;
import com.homestaybooking.model.service.HomestayService;
import com.homestaybooking.model.service.ReviewService;
import com.homestaybooking.model.service.RoomService;
import com.homestaybooking.model.service.UserService;
import com.homestaybooking.view.auth.LoginDialog;
import com.homestaybooking.view.auth.RegisterDialog;
import com.homestaybooking.view.booking.CreateBookingDialog;
import com.homestaybooking.view.booking.RoomBookingListView;
import com.homestaybooking.view.homestay.CreateHomestayDialog;
import com.homestaybooking.view.homestay.HomestayDetailView;
import com.homestaybooking.view.review.CreateReviewDialog;
import com.homestaybooking.view.room.CreateRoomDialog;
import com.homestaybooking.view.room.RoomDetailView;
import com.homestaybooking.view.user.UserProfile;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class HomeView extends JFrame {
    private static final Color BACKGROUND = new Color(243, 246, 250);
    private static final Color SURFACE = Color.WHITE;
    private static final Color PRIMARY = new Color(30, 93, 156);
    private static final Color PRIMARY_DARK = new Color(20, 70, 122);
    private static final Color TEXT = new Color(31, 41, 55);
    private static final Color MUTED = new Color(107, 114, 128);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 13);

    private final UserController userController;
    private final HomestayController homestayController;
    private final RoomController roomController;
    private final BookingController bookingController;
    private final ReviewController reviewController;

    private final JPanel contentPanel = new JPanel(new BorderLayout());
    private final JLabel sessionLabel = new JLabel();
    private JButton loginButton;
    private JButton registerButton;
    private JButton profileButton;
    private JButton logoutButton;
    private JButton reloadButton;

    private final DefaultTableModel homestayModel = model("ID", "Tên", "Địa chỉ", "Loại", "Trạng thái");
    private final DefaultTableModel roomModel = model("ID", "Homestay", "Tên phòng", "Loại", "Sức chứa", "Trạng thái");
    private final DefaultTableModel bookingModel = model("ID", "Phòng", "Check-in", "Check-out", "Số đêm", "Trạng thái");
    private final DefaultTableModel userModel = model("ID", "Họ tên", "Email", "Điện thoại", "Vai trò", "Trạng thái", "Số dư");
    private final JTable homestayTable = table(homestayModel);
    private final JTable roomTable = table(roomModel);
    private final JTable bookingTable = table(bookingModel);
    private final JTable userTable = table(userModel);
    private final List<Homestay> homestays = new ArrayList<>();
    private final List<Room> rooms = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();
    private final List<User> users = new ArrayList<>();

    public HomeView() {
        setSystemLookAndFeel();

        UserRepository userRepository = new UserRepository();
        HomestayRepository homestayRepository = new HomestayRepository();
        RoomRepository roomRepository = new RoomRepository();
        BookingRepository bookingRepository = new BookingRepository();
        ReviewRepository reviewRepository = new ReviewRepository(bookingRepository);

        UserService userService = new UserService(userRepository);
        HomestayService homestayService = new HomestayService(homestayRepository, userService);
        RoomService roomService = new RoomService(roomRepository, homestayRepository, homestayService);
        BookingService bookingService = new BookingService(bookingRepository, roomRepository, homestayRepository, userRepository, userService);
        ReviewService reviewService = new ReviewService(reviewRepository, bookingRepository, userService);

        userController = new UserController(userService);
        homestayController = new HomestayController(homestayService);
        roomController = new RoomController(roomService);
        bookingController = new BookingController(bookingService);
        reviewController = new ReviewController(reviewService);

        setTitle("Homestay Booking");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1120, 700);
        setMinimumSize(new Dimension(960, 620));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND);
        contentPanel.setBackground(BACKGROUND);
        add(header(), BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        refreshAll();
    }

    private JPanel header() {
        JLabel title = new JLabel("Homestay Booking");
        title.setFont(TITLE_FONT);
        title.setForeground(Color.WHITE);

        sessionLabel.setForeground(new Color(220, 235, 255));
        sessionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel identity = new JPanel(new GridLayout(0, 1, 0, 4));
        identity.setOpaque(false);
        identity.add(title);
        identity.add(sessionLabel);

        loginButton = button("Đăng nhập", true);
        registerButton = button("Đăng ký", false);
        profileButton = button("Hồ sơ", false);
        logoutButton = button("Đăng xuất", false);
        reloadButton = button("Tải lại", false);
        loginButton.addActionListener(event -> openLogin());
        registerButton.addActionListener(event -> openRegister());
        profileButton.addActionListener(event -> safe(() -> new UserProfile(this, userController, this::refreshAll).setVisible(true)));
        logoutButton.addActionListener(event -> {
            userController.logout();
            refreshAll();
        });
        reloadButton.addActionListener(event -> refreshAll());

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        actions.add(loginButton);
        actions.add(registerButton);
        actions.add(profileButton);
        actions.add(logoutButton);
        actions.add(reloadButton);

        JPanel panel = new JPanel(new BorderLayout(16, 0));
        panel.setBackground(PRIMARY_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        panel.add(identity, BorderLayout.WEST);
        panel.add(actions, BorderLayout.EAST);
        return panel;
    }

    private JPanel authPanel() {
        JLabel title = new JLabel("Vui lòng đăng nhập hoặc đăng ký");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel description = new JLabel("<html><div style='text-align:center;'>Ứng dụng chỉ mở các chức năng sau khi xác thực tài khoản.<br>Menu sẽ thay đổi theo quyền CUSTOMER, OWNER hoặc ADMIN.</div></html>");
        description.setFont(BODY_FONT);
        description.setForeground(MUTED);
        description.setHorizontalAlignment(SwingConstants.CENTER);

        JButton login = button("Đăng nhập", true);
        JButton register = button("Đăng ký tài khoản", false);
        login.addActionListener(event -> openLogin());
        register.addActionListener(event -> openRegister());

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        actions.setOpaque(false);
        actions.add(login);
        actions.add(register);

        JLabel accounts = new JLabel("<html><div style='text-align:center;'>Tài khoản mẫu:<br>admin@homestay.local / 123456<br>owner@homestay.local / 123456<br>customer@homestay.local / 123456</div></html>");
        accounts.setFont(BODY_FONT);
        accounts.setForeground(MUTED);
        accounts.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel card = new JPanel(new GridLayout(0, 1, 0, 16));
        card.setBackground(SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(34, 38, 34, 38)
        ));
        card.add(title);
        card.add(description);
        card.add(actions);
        card.add(accounts);

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 90));
        wrapper.setBackground(BACKGROUND);
        wrapper.add(card);
        return wrapper;
    }

    private JTabbedPane roleTabs(User user) {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabs.setBorder(BorderFactory.createEmptyBorder(8, 18, 18, 18));
        tabs.addTab("Homestay", homestayPanel(user));
        tabs.addTab("Phòng", roomPanel(user));
        tabs.addTab("Booking", bookingPanel(user));
        if (user.role == UserRole.ADMIN) {
            tabs.addTab("Người dùng", userPanel());
        }
        return tabs;
    }

    private JPanel homestayPanel(User user) {
        JTextField keyword = input();
        JButton search = button("Tìm", true);
        JButton create = button("Tạo homestay", false);
        JButton detail = button("Chi tiết", false);
        search.addActionListener(event -> refreshHomestays(keyword.getText()));
        create.addActionListener(event -> safe(() -> new CreateHomestayDialog(this, homestayController, this::refreshAll).setVisible(true)));
        detail.addActionListener(event -> safe(() -> {
            Homestay homestay = selectedHomestay();
            new HomestayDetailView(this, homestay, roomController.byHomestay(homestay.id)).setVisible(true);
        }));
        create.setVisible(user.role == UserRole.CUSTOMER || user.role == UserRole.OWNER || user.role == UserRole.ADMIN);
        JPanel actions = toolbar("Từ khóa", keyword, search, create, detail);
        return withActions(actions, tableScroll(homestayTable));
    }

    private JPanel roomPanel(User user) {
        JTextField keyword = input();
        JButton search = button("Tìm", true);
        JButton create = button("Tạo phòng", false);
        JButton detail = button("Chi tiết", false);
        JButton booking = button("Đặt phòng", false);
        JButton review = button("Đánh giá", false);
        search.addActionListener(event -> refreshRooms(keyword.getText()));
        create.addActionListener(event -> safe(() -> new CreateRoomDialog(this, roomController, this::refreshAll).setVisible(true)));
        detail.addActionListener(event -> safe(() -> {
            Room room = selectedRoom();
            new RoomDetailView(this, room, reviewController.byRoom(room.id)).setVisible(true);
        }));
        booking.addActionListener(event -> safe(() -> {
            Room room = selectedRoom();
            new CreateBookingDialog(this, bookingController, room.id, this::refreshAll).setVisible(true);
        }));
        review.addActionListener(event -> safe(() -> new CreateReviewDialog(this, reviewController, this::refreshAll).setVisible(true)));
        create.setVisible(user.role == UserRole.OWNER || user.role == UserRole.ADMIN);
        booking.setVisible(user.role == UserRole.CUSTOMER || user.role == UserRole.ADMIN);
        review.setVisible(user.role == UserRole.CUSTOMER);
        JPanel actions = toolbar("Từ khóa", keyword, search, create, detail, booking, review);
        return withActions(actions, tableScroll(roomTable));
    }

    private JPanel bookingPanel(User user) {
        JButton mine = button("Booking của tôi", true);
        JButton owner = button("Booking owner/admin", false);
        JButton manage = button("Mở bảng hủy/hoàn tất", false);
        mine.addActionListener(event -> safe(this::refreshMyBookings));
        owner.addActionListener(event -> safe(this::refreshOwnerBookings));
        manage.addActionListener(event -> safe(() -> {
            if (bookings.isEmpty()) {
                if (user.role == UserRole.CUSTOMER) {
                    refreshMyBookings();
                } else {
                    refreshOwnerBookings();
                }
            }
            new RoomBookingListView(this, bookingController, new ArrayList<>(bookings), this::refreshAll).setVisible(true);
        }));
        mine.setVisible(user.role == UserRole.CUSTOMER || user.role == UserRole.ADMIN);
        owner.setVisible(user.role == UserRole.OWNER || user.role == UserRole.ADMIN);
        JPanel actions = toolbar(null, null, mine, owner, manage);
        return withActions(actions, tableScroll(bookingTable));
    }

    private JPanel userPanel() {
        JTextField keyword = input();
        JButton search = button("Tìm người dùng", true);
        JButton lock = button("Khóa", false);
        JButton unlock = button("Mở khóa", false);
        search.addActionListener(event -> safe(() -> refreshUsers(keyword.getText())));
        lock.addActionListener(event -> safe(() -> {
            userController.lockUser(selectedUser().id);
            refreshUsers(keyword.getText());
        }));
        unlock.addActionListener(event -> safe(() -> {
            userController.unlockUser(selectedUser().id);
            refreshUsers(keyword.getText());
        }));
        JPanel actions = toolbar("Từ khóa", keyword, search, lock, unlock);
        return withActions(actions, tableScroll(userTable));
    }

    private JPanel toolbar(String label, JTextField keyword, JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(SURFACE);
        panel.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        if (label != null && keyword != null) {
            JLabel fieldLabel = new JLabel(label);
            fieldLabel.setFont(BODY_FONT);
            fieldLabel.setForeground(MUTED);
            panel.add(fieldLabel);
            panel.add(keyword);
        }
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }

    private JPanel withActions(JPanel actions, JScrollPane content) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panel.add(actions, BorderLayout.NORTH);
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    private JScrollPane tableScroll(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        scrollPane.getViewport().setBackground(SURFACE);
        return scrollPane;
    }

    private void refreshAll() {
        User user = userController.currentUser();
        updateHeader(user);
        renderContent(user);
        clearTables();
        if (user == null) {
            return;
        }
        refreshHomestays("");
        refreshRooms("");
        if (user.role == UserRole.CUSTOMER) {
            safe(this::refreshMyBookings);
        } else {
            safe(this::refreshOwnerBookings);
        }
        if (user.role == UserRole.ADMIN) {
            safe(() -> refreshUsers(""));
        }
    }

    private void updateHeader(User user) {
        if (user == null) {
            sessionLabel.setText("Chưa đăng nhập - cần đăng nhập hoặc đăng ký để sử dụng hệ thống");
            loginButton.setVisible(true);
            registerButton.setVisible(true);
            profileButton.setVisible(false);
            logoutButton.setVisible(false);
            reloadButton.setVisible(false);
        } else {
            sessionLabel.setText(user.fullname + " | " + user.role + " | Số dư: " + formatMoney(user.balance));
            loginButton.setVisible(false);
            registerButton.setVisible(false);
            profileButton.setVisible(true);
            logoutButton.setVisible(true);
            reloadButton.setVisible(true);
        }
    }

    private void renderContent(User user) {
        contentPanel.removeAll();
        contentPanel.add(user == null ? authPanel() : roleTabs(user), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void clearTables() {
        homestays.clear();
        rooms.clear();
        bookings.clear();
        users.clear();
        homestayModel.setRowCount(0);
        roomModel.setRowCount(0);
        bookingModel.setRowCount(0);
        userModel.setRowCount(0);
    }

    private void refreshHomestays(String keyword) {
        homestays.clear();
        homestays.addAll(homestayController.list(keyword));
        homestayModel.setRowCount(0);
        for (Homestay homestay : homestays) {
            homestayModel.addRow(new Object[]{homestay.id, homestay.name, homestay.address, homestay.type, homestay.status});
        }
    }

    private void refreshRooms(String keyword) {
        rooms.clear();
        rooms.addAll(roomController.list(keyword));
        roomModel.setRowCount(0);
        for (Room room : rooms) {
            roomModel.addRow(new Object[]{room.id, room.homestayId, room.name, room.type, room.capacity, room.status});
        }
    }

    private void refreshMyBookings() {
        bookings.clear();
        bookings.addAll(bookingController.mine());
        refreshBookingTable();
    }

    private void refreshOwnerBookings() {
        bookings.clear();
        bookings.addAll(bookingController.ownerBookings());
        refreshBookingTable();
    }

    private void refreshBookingTable() {
        bookingModel.setRowCount(0);
        for (Booking booking : bookings) {
            bookingModel.addRow(new Object[]{booking.id, booking.roomId, booking.checkInDate, booking.checkOutDate, booking.totalNights, booking.status});
        }
    }

    private void refreshUsers(String keyword) {
        users.clear();
        users.addAll(userController.listUsers(keyword));
        userModel.setRowCount(0);
        for (User user : users) {
            userModel.addRow(new Object[]{user.id, user.fullname, user.email, user.phone, user.role, user.status, formatMoney(user.balance)});
        }
    }

    private Homestay selectedHomestay() {
        return selected(homestayTable, homestays);
    }

    private Room selectedRoom() {
        return selected(roomTable, rooms);
    }

    private User selectedUser() {
        return selected(userTable, users);
    }

    private <T> T selected(JTable table, List<T> values) {
        int row = table.getSelectedRow();
        if (row < 0) {
            throw new IllegalStateException("Cần chọn một dòng trước");
        }
        int modelRow = table.convertRowIndexToModel(row);
        return values.get(modelRow);
    }

    private DefaultTableModel model(String... columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private JTable table(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(BODY_FONT);
        table.setRowHeight(34);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(229, 231, 235));
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(TEXT);
        table.setAutoCreateRowSorter(true);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(230, 239, 250));
        header.setForeground(TEXT);
        header.setPreferredSize(new Dimension(0, 36));
        return table;
    }

    private JTextField input() {
        JTextField field = new JTextField(20);
        field.setFont(BODY_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        return field;
    }

    private JButton button(String title, boolean primary) {
        JButton button = new JButton(title);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        if (primary) {
            button.setBackground(PRIMARY);
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(new Color(241, 245, 249));
            button.setForeground(TEXT);
        }
        return button;
    }

    private void openLogin() {
        new LoginDialog(this, userController, this::refreshAll).setVisible(true);
    }

    private void openRegister() {
        new RegisterDialog(this, userController, this::refreshAll).setVisible(true);
    }

    private String formatMoney(long amount) {
        return String.format("%,d VND", amount);
    }

    private void safe(Runnable action) {
        try {
            action.run();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }

    public static void open() {
        SwingUtilities.invokeLater(() -> new HomeView().setVisible(true));
    }
}
