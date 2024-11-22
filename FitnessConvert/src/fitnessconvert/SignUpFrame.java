package fitnessconvert;

import database.JSONDatabase;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SignUpFrame extends JFrame {
    public SignUpFrame() {
        setTitle("Sign Up");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Menempatkan frame di tengah layar

        // Menggunakan GridBagLayout untuk tata letak yang lebih fleksibel
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding antara komponen
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Komponen UI
        JLabel lblTitle = new JLabel("Sign Up", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(new Color(34, 112, 147)); // Warna biru tua

        JLabel lblUsername = new JLabel("Username:");
        JTextField txtUsername = new JTextField(20);
        JLabel lblPassword = new JLabel("Password:");
        JPasswordField txtPassword = new JPasswordField(20);

        JButton btnSignUp = new JButton("Daftar");
        btnSignUp.setBackground(new Color(34, 112, 147)); // Warna biru tua
        btnSignUp.setForeground(Color.WHITE);

        JButton btnBack = new JButton("Back");
        btnBack.setBackground(new Color(200, 200, 200));
        btnBack.setForeground(Color.BLACK);

        // Menambahkan komponen ke frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        add(lblUsername, gbc);

        gbc.gridx++;
        add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(lblPassword, gbc);

        gbc.gridx++;
        add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(btnSignUp, gbc);

        gbc.gridy++;
        add(btnBack, gbc);

        // Action Listener untuk tombol Sign Up
        btnSignUp.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Harap isi semua kolom.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                JSONObject db = JSONDatabase.loadDatabase();
                JSONArray users = db.getJSONArray("users");

                for (Object obj : users) {
                    JSONObject user = (JSONObject) obj;
                    if (user.getString("username").equals(username)) {
                        JOptionPane.showMessageDialog(this, "Username sudah ada.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                JSONObject newUser = new JSONObject();
                newUser.put("username", username);
                newUser.put("password", password);
                users.put(newUser);

                JSONDatabase.saveDatabase(db);
                JOptionPane.showMessageDialog(this, "Pendaftaran berhasil!", "Success", JOptionPane.INFORMATION_MESSAGE);
                new LoginFrame().setVisible(true);
                this.dispose();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data pengguna.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action Listener untuk tombol Back
        btnBack.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
    }
}
