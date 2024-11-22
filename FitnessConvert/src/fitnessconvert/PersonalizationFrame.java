package fitnessconvert;

import org.json.JSONArray;
import org.json.JSONObject;
import database.JSONDatabase;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PersonalizationFrame extends JFrame {
    private final String username;

    public PersonalizationFrame(String username) {
        this.username = username;
        setTitle("Personalization");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        getContentPane().setBackground(new Color(240, 248, 255));

        // Judul
        JLabel lblTitle = new JLabel("Personalization", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(new Color(34, 112, 147));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitle, gbc);

        // Input tinggi dan berat badan
        JLabel lblHeight = new JLabel("Tinggi Badan (cm):");
        lblHeight.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField txtHeight = new JTextField(10);
        restrictInputToPositiveNumbers(txtHeight);

        JLabel lblWeight = new JLabel("Berat Badan (kg):");
        lblWeight.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField txtWeight = new JTextField(10);
        restrictInputToPositiveNumbers(txtWeight);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        add(lblHeight, gbc);
        gbc.gridx = 1;
        add(txtHeight, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(lblWeight, gbc);
        gbc.gridx = 1;
        add(txtWeight, gbc);

        // Tombol Simpan
        JButton btnSave = new JButton("Simpan");
        btnSave.setBackground(new Color(34, 112, 147));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(btnSave, gbc);

        // Tombol Back
        JButton btnBack = new JButton("Back");
        btnBack.setBackground(new Color(200, 200, 200));
        btnBack.setForeground(Color.BLACK);
        btnBack.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy++;
        add(btnBack, gbc);

        // Label pesan status
        JLabel lblMessage = new JLabel("", JLabel.CENTER);
        lblMessage.setFont(new Font("Arial", Font.ITALIC, 14));
        lblMessage.setForeground(Color.RED);
        gbc.gridy++;
        add(lblMessage, gbc);

        // Action Listener untuk tombol Simpan
        btnSave.addActionListener(e -> {
            try {
                String heightText = txtHeight.getText().trim();
                String weightText = txtWeight.getText().trim();

                if (heightText.isEmpty() || weightText.isEmpty()) {
                    lblMessage.setText("Semua kolom harus diisi!");
                    return;
                }

                int height = Integer.parseInt(heightText);
                int weight = Integer.parseInt(weightText);

                if (height <= 0 || weight <= 0) {
                    lblMessage.setText("Masukkan angka positif untuk tinggi dan berat badan.");
                    return;
                }

                savePersonalizationData(height, weight);
                lblMessage.setText("Data berhasil disimpan!");
                lblMessage.setForeground(new Color(0, 128, 0));

                new ConverterFrame(username).setVisible(true);
                this.dispose();

            } catch (NumberFormatException ex) {
                lblMessage.setText("Harap masukkan angka yang valid.");
                lblMessage.setForeground(Color.RED);
            } catch (IOException ex) {
                lblMessage.setText("Gagal menyimpan data.");
                lblMessage.setForeground(Color.RED);
            }
        });

        // Action Listener untuk tombol Back
        btnBack.addActionListener(e -> {
            new LoginFrame().setVisible(true);  // Kembali ke LoginFrame
            this.dispose();
        });
    }

    private void restrictInputToPositiveNumbers(JTextField textField) {
        textField.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
                if (str == null) return;
                if (str.matches("\\d+")) { // Hanya angka
                    super.insertString(offs, str, a);
                }
            }
        });
    }

    private void savePersonalizationData(int height, int weight) throws IOException {
        JSONObject db = JSONDatabase.loadDatabase();
        JSONArray users = db.getJSONArray("users");

        for (Object obj : users) {
            JSONObject user = (JSONObject) obj;
            if (user.getString("username").equals(username)) {
                user.put("height", height);
                user.put("weight", weight);
                JSONDatabase.saveDatabase(db);
                return;
            }
        }
    }
}
