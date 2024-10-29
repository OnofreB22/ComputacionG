import javax.swing.*;

import math.Matrix3x3;
import math.Point3;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Objeto2D extends JPanel {
    private List<Vertice> verticesMod;
    private List<Vertice> verticesOriginales;   // Lista para los vértices originales
    private List<Arista> aristas;               // Lista para las aristas
    private JFrame ventana;
    private double theta;
    private double dx, dy;
    private Point3 centro;


    public Objeto2D(String fileName) {
        verticesOriginales = new ArrayList<>();
        verticesMod = new ArrayList<>();
        aristas = new ArrayList<>();
        Leer(fileName);
        theta = 0;
        dx = 0;
        dy = 0;

        // inicializar ventana
        ventana = new JFrame("Rotación de Objeto");
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
                        transformacion(-2.5, 0, 0);
                        break;
                    case KeyEvent.VK_E:
                        transformacion(2.5, 0, 0);
                        break;
                    case KeyEvent.VK_W:
                        transformacion(0, 0, 10);
                        break;
                    case KeyEvent.VK_S:
                        transformacion(0, 0, -10);
                        break;
                    case KeyEvent.VK_A:
                        transformacion(0, 10, 0);
                        break;
                    case KeyEvent.VK_D:
                        transformacion(0, -10, 0);
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
            int numeroVertices = Integer.parseInt(br.readLine().trim());
            for (int i = 0; i < numeroVertices; i++) {
                String[] coords = br.readLine().trim().split(" ");
                double x = Double.parseDouble(coords[0]);
                double y = Double.parseDouble(coords[1]);
                verticesOriginales.add(new Vertice(x, y));
                verticesMod.add(new Vertice(x, y));
            }
            int numeroAristas = Integer.parseInt(br.readLine().trim());
            for (int i = 0; i < numeroAristas; i++) {
                String[] indices = br.readLine().trim().split(" ");
                int inicio = Integer.parseInt(indices[0]);
                int fin = Integer.parseInt(indices[1]);
                aristas.add(new Arista(inicio, fin));
            }
            this.centro = centroide();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);

        // Dibujar cada arista usando los vértices
        for (Arista arista : aristas) {
            Vertice v1 = verticesMod.get(arista.origen);
            Vertice v2 = verticesMod.get(arista.fin);
            g2d.draw(new Line2D.Double(v1.x, v1.y, v2.x, v2.y));
        }
    }

    public void transformacion(double angulo, double dx, double dy) {
        this.dx += dx;
        this.dy += dy;
        this.theta += Math.toRadians(angulo);

        double[][] rotacion = {
            {Math.cos(theta), -Math.sin(theta), 0},
            {Math.sin(theta),  Math.cos(theta), 0},
            {0,                0,               1}
        };
        double [][] traslacion0 = {
            {1,      0,     -centro.x},
            {0,      1,     -centro.y},
            {0,      0,             1}
        };
        double [][] traslacion1 = {
            {1,      0,      centro.x+this.dx},
            {0,      1,      centro.y+this.dy},
            {0,      0,                     1}
        };
        Matrix3x3 matrizT0 = new Matrix3x3(traslacion0);
        Matrix3x3 matrizT1 = new Matrix3x3(traslacion1);
        Matrix3x3 matrizR = new Matrix3x3(rotacion);

        // Aplicar las transformaciones a cada vertice del objeto
        for(int i = 0; i < verticesOriginales.size(); i++) {
            Vertice verticeOriginal = verticesOriginales.get(i);

            Point3 vectorOriginal = new Point3(verticeOriginal.x, verticeOriginal.y, 1);
            Point3 vectorT = Matrix3x3.times(matrizT0, vectorOriginal);             // Trasladar al centro del plano
            Point3 vectorRotado = Matrix3x3.times(matrizR, vectorT);                // Rotar
            Point3 vectorModificado = Matrix3x3.times(matrizT1, vectorRotado);      // Llevar a posicion final

            Vertice verticeModificado = new Vertice(vectorModificado.x, vectorModificado.y);
            verticesMod.set(i, verticeModificado);
        }
        ventana.repaint();
    }

    public Point3 centroide() {
        double x = 0;
        double y = 0;

        for (Vertice vertice : verticesOriginales) {
            x += vertice.x;
            y += vertice.y;
        }

        x = x/verticesOriginales.size();
        y = y/verticesOriginales.size();

        return new Point3(x, y, 1);
    }
}