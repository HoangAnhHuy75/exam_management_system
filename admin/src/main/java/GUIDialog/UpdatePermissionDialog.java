/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIDialog;

/**
 *
 * @author Hao Nguyen
 */
import BUS.PermissionBUS;
import DTO.PermissionDTO;
import GUI.Panel.PermissionPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Dialog phân quyền cho role theo dạng bảng checkbox Hàng = tên chức năng, Cột
 * = read / create / update / delete
 */
public class UpdatePermissionDialog extends JDialog {

    // ===== Constants =====
    /**
     * Danh sách chức năng (resource) hiển thị trên bảng
     */
    private static final String[][] RESOURCES = {
        {"Ngành", "nganh"},
        {"Tổ hợp", "tohop"},
        {"Ngành - Tổ hợp", "nganh_tohop"},
        {"Thí sinh", "thisinh"},
        {"Điểm thi", "diemthi"},
        {"Điểm cộng", "diemcong"},
        {"Nguyện vọng xét tuyển", "nguyenvong"},
        {"Người dùng", "user"}
    };

    /**
     * Tên các cột quyền
     */
    private static final String[] ACTIONS = {"read", "create", "update", "delete"};

    // ===== Fields =====
    private final PermissionBUS permissionBUS = new PermissionBUS();
    private final PermissionPanel parentPanel;
    private final int roleId;
    private final String roleName;

    /**
     * map key = "ResourceName_action" (e.g. "Ngành_read") value = JCheckBox
     */
    private final Map<String, JCheckBox> checkboxMap = new HashMap<>();

    // ===== UI Components =====
    private JPanel tablePanel;
    private JButton btn_save;
    private JButton btn_cancel;

    // ===== Constructor =====
    public UpdatePermissionDialog(Frame parent, boolean modal,
            PermissionPanel parentPanel,
            int roleId, String roleName) {
        super(parent, modal);
        this.parentPanel = parentPanel;
        this.roleId = roleId;
        this.roleName = roleName;

        setTitle("Phân quyền - " + roleName);
        setSize(750, 520);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents();
        loadCurrentPermissions();
    }

    // ===== Build UI =====
    private void initComponents() {
        setLayout(new BorderLayout(0, 0));

        // ---- Header ----
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        headerPanel.setBackground(new Color(52, 152, 219));
        JLabel titleLabel = new JLabel("Phân quyền cho role: " + roleName);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // ---- Table Panel ----
        tablePanel = buildPermissionTable();
        JScrollPane scrollPane = new JScrollPane(tablePanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // ---- Footer Buttons ----
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        footerPanel.setBackground(new Color(245, 245, 245));
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        // ===== Button Hủy =====
btn_cancel = new JButton("Hủy");
btn_cancel.setPreferredSize(new Dimension(100, 36));
btn_cancel.setFont(new Font("Segoe UI", Font.BOLD, 13));
btn_cancel.setBackground(new Color(231, 76, 60)); // đỏ
btn_cancel.setForeground(Color.WHITE);
btn_cancel.setFocusPainted(false);
btn_cancel.setBorderPainted(false);
btn_cancel.addActionListener(e -> dispose());

// ===== Button Lưu =====
btn_save = new JButton("Xác nhận");
btn_save.setPreferredSize(new Dimension(120, 36));
btn_save.setFont(new Font("Segoe UI", Font.BOLD, 13));
btn_save.setBackground(new Color(46, 204, 113)); // xanh lá
btn_save.setForeground(Color.WHITE);
btn_save.setFocusPainted(false);
btn_save.setBorderPainted(false);
btn_save.addActionListener(e -> savePermissions());
        footerPanel.add(btn_cancel);
        footerPanel.add(btn_save);
        add(footerPanel, BorderLayout.SOUTH);
    }

    /**
     * Xây dựng bảng checkbox thủ công bằng GridBagLayout để kiểm soát hoàn toàn
     * style từng ô.
     */
    private JPanel buildPermissionTable() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 1, 1); // spacing between cells

        // ---- Header row ----
        // Top-left empty cell
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2.5;
        gbc.weighty = 0;
        panel.add(makeHeaderCell("Chức năng"), gbc);

        for (int col = 0; col < ACTIONS.length; col++) {
            gbc.gridx = col + 1;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            panel.add(makeHeaderCell(ACTIONS[col]), gbc);
        }

        // ---- Data rows ----
        for (int row = 0; row < RESOURCES.length; row++) {
            String displayName = RESOURCES[row][0];
            String permissionKey = RESOURCES[row][1];
            Color rowBg = (row % 2 == 0)
                    ? Color.WHITE
                    : new Color(245, 248, 252);

            // Resource label
            gbc.gridx = 0;
            gbc.gridy = row + 1;
            gbc.weightx = 2.5;
            gbc.weighty = 0;
            panel.add(makeResourceCell(displayName, rowBg), gbc);

            // Checkbox cells
            for (int col = 0; col < ACTIONS.length; col++) {

                String action = ACTIONS[col];

                // Nguyện vọng chỉ có read + create
                if (permissionKey.equals("nguyenvong")
                        && (action.equals("update")
                        || action.equals("delete"))) {

                    JPanel emptyPanel = new JPanel();
                    emptyPanel.setBackground(rowBg);
                    emptyPanel.setBorder(
                            BorderFactory.createMatteBorder(
                                    0, 0, 1, 1,
                                    new Color(220, 220, 220)
                            )
                    );

                    gbc.gridx = col + 1;
                    gbc.gridy = row + 1;

                    panel.add(emptyPanel, gbc);

                    continue;
                }

                String key = permissionKey + "." + action;

                JCheckBox cb = new JCheckBox();
                cb.setHorizontalAlignment(SwingConstants.CENTER);
                cb.setBackground(rowBg);
                cb.setFocusPainted(false);

                JPanel cbPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
                cbPanel.setBackground(rowBg);
                cbPanel.setPreferredSize(new Dimension(80, 40));
                cbPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(220, 220, 220)));
                cbPanel.add(cb);

                checkboxMap.put(key, cb);

                gbc.gridx = col + 1;
                gbc.gridy = row + 1;
                gbc.weightx = 1.0;
                panel.add(cbPanel, gbc);
            }
        }

        return panel;
    }

    /**
     * Tạo ô header
     */
    private JPanel makeHeaderCell(String text) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setBackground(new Color(52, 73, 94));
        panel.setPreferredSize(new Dimension(text.equals("Chức năng") ? 200 : 80, 42));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(80, 100, 120)));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

        // Center vertically
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Tạo ô tên chức năng
     */
    private JPanel makeResourceCell(String text, Color bg) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bg);
        panel.setPreferredSize(new Dimension(200, 40));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(0, 12, 0, 8)
        ));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(50, 50, 50));
        panel.add(label, BorderLayout.CENTER);

        return panel;
    }

    // ===== Load current permissions =====
    /**
     * Đọc danh sách permission hiện tại của role từ BUS/DAO rồi tick checkbox
     * tương ứng.
     *
     * Convention tên permission trong DB: "{resource}_{action}" ví dụ:
     * "Ngành_read", "Người dùng_delete"
     *
     * Nếu tên permission trong DB của bạn khác, hãy điều chỉnh logic map ở đây.
     */
    private void loadCurrentPermissions() {

        // lấy permission của role hiện tại
        ArrayList<PermissionDTO> rolePermissions
                = permissionBUS.getPermissionByRole(roleId);
        System.out.println("ROLE ID = " + roleId);
        for (PermissionDTO permission : rolePermissions) {

            String key
                    = permission.getPermissionName();

            JCheckBox cb
                    = checkboxMap.get(key);

            if (cb != null) {

                cb.setSelected(true);
            }
        }
    }

    // ===== Save permissions =====
    private void savePermissions() {
        ArrayList<PermissionDTO> allPermissions = permissionBUS.getAllPermission();

        // Map tên permission → id
        Map<String, Integer> nameToId = new HashMap<>();
        for (PermissionDTO p : allPermissions) {
            nameToId.put(p.getPermissionName(), p.getId());
        }

        // Tổng hợp các permission id được tick
        List<Integer> selectedIds = new ArrayList<>();
        for (Map.Entry<String, JCheckBox> entry : checkboxMap.entrySet()) {
            if (entry.getValue().isSelected()) {
                String permName = entry.getKey(); // e.g. "Ngành_read"
                Integer permId = nameToId.get(permName);
                if (permId != null) {
                    selectedIds.add(permId);
                }
            }
        }

        boolean success = permissionBUS.assignPermissionToRole(roleId, selectedIds);

        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Phân quyền cho role \"" + roleName + "\" thành công!",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            parentPanel.loadData();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Phân quyền thất bại! Vui lòng thử lại.",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
