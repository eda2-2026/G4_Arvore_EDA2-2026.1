package menu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuUtil {

    private static JFrame frame;

    private MenuUtil() {
    }

    static JFrame getFrame() {
        return frame;
    }

    static void inicializarFrame() {
        frame = criarFrameComIcone();
    }

    static void destruirFrame() {
        if (frame != null) frame.dispose();
    }

    private static JFrame criarFrameComIcone() {
        JFrame f = new JFrame("Restaurante Universitário");
        f.setIconImage(gerarIconeRU());
        f.setUndecorated(true);
        f.setSize(0, 0);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setState(JFrame.ICONIFIED);
        f.setState(JFrame.NORMAL);
        f.setVisible(true);
        return f;
    }

    private static Image gerarIconeRU() {
        int size = 64;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Prato
        g.setColor(new Color(240, 240, 240));
        g.fillOval(4, 4, size - 8, size - 8);

        // Borda do prato
        g.setColor(new Color(150, 150, 150));
        g.setStroke(new BasicStroke(3));
        g.drawOval(4, 4, size - 8, size - 8);

        // Comida (verde e marrom)
        g.setColor(new Color(139, 69, 19)); // Marrom
        g.fillOval(20, 20, 24, 24);
        
        g.setColor(new Color(34, 139, 34)); // Verde
        g.fillOval(12, 12, 16, 16);
        g.fillOval(36, 36, 16, 16);

        g.dispose();
        return img;
    }

    /**
     * Pede uma matrícula ao usuário. Retorna um long,
     * ou -1 se foi deixado em branco/cancelado ou inválido.
     */
    static long pedirMatricula(String mensagem) {
        String matriculaStr = JOptionPane.showInputDialog(frame, mensagem, "Matrícula", JOptionPane.PLAIN_MESSAGE);
        if (matriculaStr == null || matriculaStr.trim().isEmpty()) return -1;
        try {
            return Long.parseLong(matriculaStr.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Matrícula inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    static void exibirTextoRolavel(String texto, String titulo) {
        JTextArea textArea = new JTextArea(texto);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(frame, scrollPane, titulo, JOptionPane.PLAIN_MESSAGE);
    }
}
