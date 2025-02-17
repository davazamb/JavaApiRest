package org.example;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class AppUI {
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton GuardarButton;
    private JTextField textField2;
    private JButton consultarButton;
    private JTextArea textArea;
    private JTable table1;

    public AppUI() {
        GuardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = new JTable();
                JScrollPane scrollPane = new JScrollPane(table);

                String apiUrl = "http://localhost:8030/usuarios";
                String jsonResponse = getApiData(apiUrl);
                List<Map<String, Object>> data = parseJsonResponse(jsonResponse);
                updateTable(data, table1);

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("AppUI");
        frame.setContentPane(new AppUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private static String getApiData(String apiUrl) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(apiUrl);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al obtener datos de la API";
        }

    }

    private static List<Map<String, Object>> parseJsonResponse(String jsonResponse) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
        return gson.fromJson(jsonResponse, listType);
    }

    private static void updateTable(List<Map<String, Object>> data, JTable table) {
        if (data.isEmpty()) {
            return;
        }

        // Obtener las columnas de la primera fila
        Map<String, Object> firstRow = data.get(0);
        String[] columns = firstRow.keySet().toArray(new String[0]);

        // Crear el modelo de la tabla
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        // Agregar las filas al modelo de la tabla
        for (Map<String, Object> row : data) {
            Object[] rowData = row.values().toArray();
            tableModel.addRow(rowData);
        }

        // Asignar el modelo a la tabla
        table.setModel(tableModel);
    }
}
