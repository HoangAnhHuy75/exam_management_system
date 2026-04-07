package user.controls;

import javax.swing.*;
import java.awt.*;

public class AddressInput extends JPanel {

    private JTextField txtStreet;
    private JComboBox<String> cbProvince;
    private JComboBox<String> cbDistrict;

    public AddressInput() {
        setLayout(new GridLayout(3, 2, 5, 5));

        // Label + input
        add(new JLabel("Số nhà:"));
        txtStreet = new JTextField();
        add(txtStreet);

        add(new JLabel("Tỉnh/Thành:"));
        cbProvince = new JComboBox<>(new String[]{
                "TP.HCM", "Hà Nội", "Đà Nẵng"
        });
        add(cbProvince);

        add(new JLabel("Quận/Huyện:"));
        cbDistrict = new JComboBox<>(new String[]{
                "Quận 1", "Quận 2", "Quận 3"
        });
        add(cbDistrict);
    }

    // lấy dữ liệu
    public String getAddress() {
        return txtStreet.getText() + ", "
                + cbDistrict.getSelectedItem() + ", "
                + cbProvince.getSelectedItem();
    }
}