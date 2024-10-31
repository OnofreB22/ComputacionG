import javax.swing.*;

import math.Matrix4x4;
import math.Point4;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Objeto3D extends JPanel{
    private List<Vertice3D> verticesMod;            // Lista para los vertices modificados
    private List<Vertice3D> verticesOriginales;     // Lista para los vertices originales
    private List<Arista> aristas;                   // Lista para las aristas
    private JFrame ventana;
    private double theta;
    private double dx, dy, dz;
    private double centroX, centroY, centroZ;
    private double d;                               // Distancia plano de proyeccion

    public Objeto3D(String fileName) {
        verticesOriginales = new ArrayList<>();
        verticesMod = new ArrayList<>();
        aristas = new ArrayList<>();
        Leer(fileName);

        // inicializar ventana
        ventana = new JFrame("Rotación de Objeto 3D");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.add(this);
        ventana.setSize(800, 600);
        ventana.setVisible(true);

        // Control por teclado
        ventana.setFocusable(true);
        ventana.requestFocusInWindow();
        ventana.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_Q:
                        projection(1.0, 0, 0, 0);
                        break;
                    case KeyEvent.VK_E:
                        projection(-1.0, 0, 0, 0);
                        break;
                    case KeyEvent.VK_W:
                        projection(0, 0, 0, 5);
                        break;
                    case KeyEvent.VK_A:
                        projection(0, 5, 0, 0);
                        break;
                    case KeyEvent.VK_S:
                        projection(0, 0, 0, -5);
                        break;
                    case KeyEvent.VK_D:
                        projection(0, -5, 0, 0);
                        break;
                    case KeyEvent.VK_DOWN:
                        projection(0, 0, -5, 0);
                        break;
                    case KeyEvent.VK_UP:
                        projection(0, 0, 5, 0);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private void Leer(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            // Leer el número de vértices
            line = br.readLine().trim();
            if (line.isEmpty()) throw new NumberFormatException("El número de vértices no puede estar vacío.");
            int numeroVertices = Integer.parseInt(line);

            for (int i = 0; i < numeroVertices; i++) {
                line = br.readLine().trim();
                if (line.isEmpty()) throw new NumberFormatException("La línea de vértice está vacía.");
                String[] coords = line.split("\\s+"); // Usar expresión regular para manejar múltiples espacios
                if (coords.length < 3) throw new NumberFormatException("La línea de vértice no tiene suficientes coordenadas.");
                double x = Double.parseDouble(coords[0]);
                double y = Double.parseDouble(coords[1]);
                double z = Double.parseDouble(coords[2]);
                verticesOriginales.add(new Vertice3D(x, y, z));
                verticesMod.add(new Vertice3D(x, y, z));
            }

            // Leer el número de aristas
            line = br.readLine().trim();
            if (line.isEmpty()) throw new NumberFormatException("El número de aristas no puede estar vacío.");
            int numeroAristas = Integer.parseInt(line);

            for (int i = 0; i < numeroAristas; i++) {
                line = br.readLine().trim();
                if (line.isEmpty()) throw new NumberFormatException("La línea de arista está vacía.");
                String[] indices = line.split("\\s+"); // Usar expresión regular para manejar múltiples espacios
                if (indices.length < 2) throw new NumberFormatException("La línea de arista no tiene suficientes índices.");
                int inicio = Integer.parseInt(indices[0]);
                int fin = Integer.parseInt(indices[1]);
                aristas.add(new Arista(inicio, fin));
            }

            // Leer las coordenadas del centro
            line = br.readLine().trim();
            if (line.isEmpty()) throw new NumberFormatException("Las coordenadas del centro no pueden estar vacías.");
            String[] centroCoords = line.split("\\s+"); // Usar expresión regular para manejar múltiples espacios
            if (centroCoords.length < 3) throw new NumberFormatException("La línea de coordenadas del centro no tiene suficientes valores.");
            centroX = Double.parseDouble(centroCoords[0]);
            centroY = Double.parseDouble(centroCoords[1]);
            centroZ = Double.parseDouble(centroCoords[2]);

            // Leer el plano de proyección
            line = br.readLine().trim();
            if (line.isEmpty()) throw new NumberFormatException("El valor del plano de proyección no puede estar vacío.");
            d = Double.parseDouble(line);

        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);

        // Mover punto de origne al centro de la pantalla
        AffineTransform transform = new AffineTransform();
        transform.translate(getWidth() / 2, getHeight() / 2);
        transform.scale(-1.0, -1.0);
        g2d.setTransform(transform);

        // Dibujar cada arista usando los vértices
        for (Arista arista : aristas) {
            Vertice3D v1 = verticesMod.get(arista.origen);
            Vertice3D v2 = verticesMod.get(arista.fin);
            g2d.draw(new Line2D.Double(v1.x, v1.y, v2.x, v2.y));
        }
    }

    public void projection(double angulo, double dx, double dy, double dz) {
        this.dx += dx;
        this.dy += dy;
        this.dz += dz;
        this.theta += Math.toRadians(angulo);
        double [][] rotarY = {
            { Math.cos(theta),   0,   Math.sin(theta),     0},
            {               0,   1,                 0,     0},
            {-Math.sin(theta),   0,   Math.cos(theta),     0},
            {               0,   0,                 0,     1}
        };
        double [][] perspectiva = {
            {1,      0,     0,     0},
            {0,      1,     0,     0},
            {0,      0,     1,     0},
            {0,      0,   1/d,     0}
        };
        double [][] trasladar0 = {
            {1,      0,     0,    -centroX},
            {0,      1,     0,    -centroY},
            {0,      0,     1,    -centroZ},
            {0,      0,     0,           1}
        };
        double [][] trasladar1 = {
            {1,      0,     0,    centroX+this.dx},
            {0,      1,     0,    centroY+this.dy},
            {0,      0,     1,    centroZ+this.dz},
            {0,      0,     0,                  1}
        };

        Matrix4x4 mPerspectiva = new Matrix4x4(perspectiva);
        Matrix4x4 mRotadoY = new Matrix4x4(rotarY);
        Matrix4x4 trasladarO = new Matrix4x4(trasladar0);
        Matrix4x4 trasladarF = new Matrix4x4(trasladar1);

        for(int i = 0; i < verticesOriginales.size(); i++) {
            Vertice3D verticeOriginal = verticesOriginales.get(i);

            Point4 vectorOriginal = new Point4(verticeOriginal.x, verticeOriginal.y, verticeOriginal.z, 1);
            Point4 vectorTrasladado0 = Matrix4x4.times(trasladarO, vectorOriginal);
            Point4 vectorRotadoY = Matrix4x4.times(mRotadoY, vectorTrasladado0);
            Point4 vectorTrasladado1 = Matrix4x4.times(trasladarF, vectorRotadoY);
            Point4 vectorModificado = Matrix4x4.times(mPerspectiva, vectorTrasladado1);
            double w = vectorModificado.w;
            Point4 vectorPerspectiva = new Point4(vectorModificado.x/w, vectorModificado.y/w, vectorModificado.z/w, 1); // Vector con perspectiva

            Vertice3D verticeModificado = new Vertice3D(vectorPerspectiva.x, vectorPerspectiva.y, vectorPerspectiva.z);

            verticesMod.set(i, verticeModificado);
        }
        ventana.repaint();
    }
}
