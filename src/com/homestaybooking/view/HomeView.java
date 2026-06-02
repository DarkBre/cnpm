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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class HomeView extends JFrame {
    private final UserController userController;
    private final HomestayController homestayController;
    private final RoomController roomController;
    private final BookingController bookingController;
    private final ReviewController reviewController;

    private final JLabel sessionLabel = new JLabel();
    private final DefaultListModel<Homestay> homestayModel = new DefaultListModel<>();
    private final DefaultListModel<Room> roomModel = new DefaultListModel<>();
    private final DefaultListModel<Booking> bookingModel = new DefaultListModel<>();
    private final DefaultListModel<User> userModel = new DefaultListModel<>();
    private final JList<Homestay> homestayList = new JList<>(homestayModel);
    private final JList<Room> roomList = new JList<>(roomModel);
    private final JList<Booking> bookingList = new JList<>(bookingModel);
    private final JList<User> userList = new JList<>(userModel);

    public HomeView() {
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
        setSize(920, 620);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(header(), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Homestay", homestayPanel());
        tabs.addTab("Phong", roomPanel());
        tabs.addTab("Booking", bookingPanel());
        tabs.addTab("User", userPanel());
        add(tabs, BorderLayout.CENTER);
        refreshAll();
    }

    private JPanel header() {
        JButton login = new JButton("Dang nhap");
        JButton register = new JButton("Dang ky");
        JButton profile = new JButton("Ho so");
        JButton logout = new JButton("Dang xuat");
        JButton reload = new JButton("Tai lai");
        login.addActionListener(event -> new LoginDialog(this, userController, this::refreshAll).setVisible(true));
        register.addActionListener(event -> new RegisterDialog(this, userController, this::refreshAll).setVisible(true));
        profile.addActionListener(event -> safe(() -> new UserProfile(this, userController, this::refreshAll).setVisible(true)));
        logout.addActionListener(event -> {
            userController.logout();
            refreshAll();
        });
        reload.addActionListener(event -> refreshAll());
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(sessionLabel);
        panel.add(login);
        panel.add(register);
        panel.add(profile);
        panel.add(logout);
        panel.add(reload);
        return panel;
    }

    private JPanel homestayPanel() {
        JTextField keyword = new JTextField(18);
        JButton search = new JButton("Tim");
        JButton create = new JButton("Tao homestay");
        JButton detail = new JButton("Chi tiet");
        search.addActionListener(event -> refreshHomestays(keyword.getText()));
        create.addActionListener(event -> safe(() -> new CreateHomestayDialog(this, homestayController, this::refreshAll).setVisible(true)));
        detail.addActionListener(event -> safe(() -> {
            Homestay homestay = requireSelected(homestayList);
            new HomestayDetailView(this, homestay, roomController.byHomestay(homestay.id)).setVisible(true);
        }));
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.add(new JLabel("Tu khoa"));
        actions.add(keyword);
        actions.add(search);
        actions.add(create);
        actions.add(detail);
        return withActions(actions, new JScrollPane(homestayList));
    }

    private JPanel roomPanel() {
        JTextField keyword = new JTextField(18);
        JButton search = new JButton("Tim");
        JButton create = new JButton("Tao phong");
        JButton detail = new JButton("Chi tiet");
        JButton booking = new JButton("Dat phong");
        JButton review = new JButton("Danh gia");
        search.addActionListener(event -> refreshRooms(keyword.getText()));
        create.addActionListener(event -> safe(() -> new CreateRoomDialog(this, roomController, this::refreshAll).setVisible(true)));
        detail.addActionListener(event -> safe(() -> {
            Room room = requireSelected(roomList);
            new RoomDetailView(this, room, reviewController.byRoom(room.id)).setVisible(true);
        }));
        booking.addActionListener(event -> safe(() -> {
            Room room = requireSelected(roomList);
            new CreateBookingDialog(this, bookingController, room.id, this::refreshAll).setVisible(true);
        }));
        review.addActionListener(event -> safe(() -> new CreateReviewDialog(this, reviewController, this::refreshAll).setVisible(true)));
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.add(new JLabel("Tu khoa"));
        actions.add(keyword);
        actions.add(search);
        actions.add(create);
        actions.add(detail);
        actions.add(booking);
        actions.add(review);
        return withActions(actions, new JScrollPane(roomList));
    }

    private JPanel bookingPanel() {
        JButton mine = new JButton("Booking cua toi");
        JButton owner = new JButton("Booking owner/admin");
        JButton cancel = new JButton("Mo bang huy/hoan tat");
        mine.addActionListener(event -> safe(this::refreshMyBookings));
        owner.addActionListener(event -> safe(this::refreshOwnerBookings));
        cancel.addActionListener(event -> safe(() -> new RoomBookingListView(this, bookingController, bookingController.mine(), this::refreshAll).setVisible(true)));
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.add(mine);
        actions.add(owner);
        actions.add(cancel);
        return withActions(actions, new JScrollPane(bookingList));
    }

    private JPanel userPanel() {
        JTextField keyword = new JTextField(18);
        JButton search = new JButton("Tim user");
        JButton lock = new JButton("Khoa");
        JButton unlock = new JButton("Mo khoa");
        search.addActionListener(event -> safe(() -> refreshUsers(keyword.getText())));
        lock.addActionListener(event -> safe(() -> {
            userController.lockUser(requireSelected(userList).id);
            refreshUsers(keyword.getText());
        }));
        unlock.addActionListener(event -> safe(() -> {
            userController.unlockUser(requireSelected(userList).id);
            refreshUsers(keyword.getText());
        }));
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.add(new JLabel("Tu khoa"));
        actions.add(keyword);
        actions.add(search);
        actions.add(lock);
        actions.add(unlock);
        return withActions(actions, new JScrollPane(userList));
    }

    private JPanel withActions(JPanel actions, JScrollPane content) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(actions, BorderLayout.NORTH);
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    private void refreshAll() {
        User user = userController.currentUser();
        sessionLabel.setText(user == null ? "Guest" : user.fullname + " | " + user.role + " | Balance: " + user.balance);
        refreshHomestays("");
        refreshRooms("");
        bookingModel.clear();
        userModel.clear();
        if (user != null) {
            safe(this::refreshMyBookings);
            if (user.role == UserRole.ADMIN) {
                safe(() -> refreshUsers(""));
            }
        }
    }

    private void refreshHomestays(String keyword) {
        homestayModel.clear();
        homestayController.list(keyword).forEach(homestayModel::addElement);
    }

    private void refreshRooms(String keyword) {
        roomModel.clear();
        roomController.list(keyword).forEach(roomModel::addElement);
    }

    private void refreshMyBookings() {
        bookingModel.clear();
        bookingController.mine().forEach(bookingModel::addElement);
    }

    private void refreshOwnerBookings() {
        bookingModel.clear();
        bookingController.ownerBookings().forEach(bookingModel::addElement);
    }

    private void refreshUsers(String keyword) {
        userModel.clear();
        userController.listUsers(keyword).forEach(userModel::addElement);
    }

    private <T> T requireSelected(JList<T> list) {
        T value = list.getSelectedValue();
        if (value == null) {
            throw new IllegalStateException("Can chon mot dong truoc");
        }
        return value;
    }

    private void safe(Runnable action) {
        try {
            action.run();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void open() {
        SwingUtilities.invokeLater(() -> new HomeView().setVisible(true));
    }
}
