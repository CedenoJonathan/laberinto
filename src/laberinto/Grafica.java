package laberinto;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

public final class Grafica extends JPanel implements ChangeListener {

    public static int[][] Matriz;
    int Rango;//variable que obtendrá el valor del JSlider
 
    Busqueda buscar;
    JSlider Barrita = new JSlider(1, 9, 5);
    JSpinner xspinner = new JSpinner(new SpinnerNumberModel(19, 0, 19, 1));//Se crea y se le da valor al JSpinner horizontal
    JSpinner yspinner = new JSpinner(new SpinnerNumberModel(19, 0, 19, 1));//Se crea y se le da valor al JSpinner vertical
    JButton btnGenerar = new JButton();//Se instancio el boton que genera la matriz
    JButton btnBuscar = new JButton();// Se instancia el botón que busca una ruta

    public Grafica() {
        this.setLayout(null);//Se establece un layout nulo
        this.setBackground(Color.WHITE);//Se establece el fondo de color blanco
        this.setBounds(0, 0, 800, 600);//Se establece el tamaño del JPanel
        ColocaComponentes();//Se colocan los componentes en el JPanel
        iniciaMatriz();//Se inicia La matriz para que todo su contenido sea 1
        btnBuscar.setEnabled(false);//Se desactiva el boton buscar
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Se crea el tablero
        int x = 200;//Se establece la posicion inicial de x en 200
        int y = 50;//Se establece la poisicion inicial de y en 50
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                g.drawRect(x, y, 26, 26);//se dibuja los cuadros de la matriz en las posiciones 'x' y 'y' con un ancho y un largo de 26 pixeles
                if (getMatriz()[i][j] == 0) {
                    g.setColor(Color.BLACK);
                    g.fill3DRect(x, y, 26, 26, true);//Si el numero en la matriz[i][j] es 0 se dibuja un cuadrado de color negro
                } else if (getMatriz()[i][j] == 25) {
                    g.setColor(Color.BLUE);
                    g.fill3DRect(x, y, 26, 26, true);//Si el numero es 25 ebtonces se diubuja un cuadrado de color azul 
                    g.setColor(Color.BLACK);//Se establece el color en negro nuevamente
                }
                x = x + 26;//La posicion en x aumenta 26 pixeles
            }
            x = 200;//La x regresa a la posicion 200
            y = y + 26;//La posicion en y aumenta 26 pixeles
        }
        g.drawRect(30, 30, 130, 300);//Se dibuja el cuadro de los controles
        x = 200;//x regresa a 200
        y = 50;//y regresa a 50 Esto se hace para poder dibujar el camino

        for (int a = 0; a < 20; a++) {
            for (int b = 0; b < 20; b++) {
                g.drawRect(x, y, 26, 26);//se dibuja los cuadros de la matriz
                if (Busqueda.Matriz[a][b] == 30 || Busqueda.Matriz[a][b] == 25) {// Se dibuja el camino recorrido, y la posicion final
                    g.setColor(Color.BLUE);
                    g.fill3DRect(x, y, 26, 26, true);
                    g.setColor(Color.BLACK);
                }
                x = x + 26;
            }
            x = 200;
            y = y + 26;
        }
    }

    public void ColocaComponentes() {
        btnGenerar.setBounds(50, 50, 90, 25);
        btnGenerar.setFont(new Font("Times New Roman", 1, 12));
        btnGenerar.setText("Generar!");
        btnGenerar.setFocusPainted(false);
        add(btnGenerar);

        //Se manda a generar
        ActionListener accion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnBuscar.setEnabled(true);
                RellenaMatriz();
                buscar = new Busqueda();
                repaint();
                buscar.Imprimir();//Se imprime la matriz por seguridad
                repaint();
            }
        };

        btnGenerar.addActionListener(accion);
        btnBuscar.setBounds(50, 90, 90, 25);
        btnBuscar.setFont(new Font("Times New Roman", 1, 12));
        btnBuscar.setText("Buscar!");
        btnBuscar.setFocusPainted(false);
        this.add(btnBuscar);

        //Se manda a buscar
        ActionListener accion2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                buscar = new Busqueda();
                buscar.BuscarCamino(0, 0);
                repaint(); 
            }
        };
        btnBuscar.addActionListener(accion2);

        //Se dibuja el JSlider
        Barrita.setBounds(40, 150, 100, 40);
        Barrita.setBackground(Color.WHITE);
        Barrita.setMajorTickSpacing(2);
        Barrita.setMinorTickSpacing(1);
        Barrita.setPaintTicks(true);
        Barrita.setFont(new Font("Times New Roman", 1, 12));
        Barrita.setPaintLabels(true);
        Barrita.setSnapToTicks(true);
        add(Barrita);

        //Se agrega el tamaño y la posicion del JSpiiner
        //Además se bloquea para no ingresar valores por el teclado y se pone en color blanco
        xspinner.setBounds(100, 200, 40, 30);
        JFormattedTextField cambiar = ((JSpinner.DefaultEditor) xspinner.getEditor()).getTextField();
        cambiar.setEditable(false);
        cambiar.setBackground(Color.WHITE);

        yspinner.setBounds(100, 240, 40, 30);
        JFormattedTextField cambiar2 = ((JSpinner.DefaultEditor) yspinner.getEditor()).getTextField();
        cambiar2.setEditable(false);
        cambiar2.setBackground(Color.WHITE);
        
        //Se le agregan los eventos a los JSpiner para que se mueva la posicion final
        xspinner.addChangeListener(this);
        yspinner.addChangeListener(this);

        add(xspinner);
        add(yspinner);

        //Etiquetas de Posicion final e inicial
        JLabel Finalx = new JLabel("P. Final x");
        JLabel Finaly = new JLabel("P. Final y");
        Finalx.setBounds(40, 200, 50, 30);
        Finaly.setBounds(40, 235, 50, 30);

        add(Finalx);
        add(Finaly);
        
    }

    //Se inicia la matriz con los valores del JSpinner
    public void iniciaMatriz() {
        int vx, vy;
        vx = (Integer) xspinner.getValue();//Se obtiene el valor del JSpinner para la posicion x
        vy = (Integer) yspinner.getValue();//Se obtiene el valor del JSpinner para la posicion y
        Matriz = new int[20][20];
        for (int i = 0; i < Matriz.length; i++) {
            for (int j = 0; j < Matriz.length; j++) {
                Matriz[i][j] = 1;
            }
        }
        Matriz[vx][vy] = 25;
    }

    //Se limpia la matriz con los nuevos valores del JSpinner
    public void limpiaMatriz() {
        int vx, vy;
        vx = (Integer) xspinner.getValue();//Se obtiene el valor del JSpinner para la posicion x
        vy = (Integer) yspinner.getValue();//Se obtiene el valor del JSpinner para la posicion y
        Matriz = new int[20][20];
        for (int i = 0; i < Matriz.length; i++) {
            for (int j = 0; j < Matriz.length; j++) {
                Matriz[i][j] = 1;
            }
        }
        Matriz[0][0] = 1;
        Matriz[vx][vy] = 25;
    }

    //Se rellena la matriz con la posicion final y 
    public void RellenaMatriz() {
        int vx, vy;
        vx = (Integer) xspinner.getValue();
        vy = (Integer) yspinner.getValue();
        Random randon = new Random();
        Matriz = new int[20][20];
        for (int i = 0; i < Matriz.length; i++) {
            for (int j = 0; j < Matriz.length; j++) {
                Matriz[i][j] = randon.nextInt(Rango = Barrita.getValue()); //Se llena la matriz con numero aleatorios, entre el valor del JSlider y 9
            }
        }
        Matriz[0][0] = 1;//La posicion de inicio siempre será el punto 0,0 por lo que el valor en esa posicion será 1
        Matriz[vx][vy] = 25;//El numero que identifica la salida siempre será el 25
    }

    public int[][] getMatriz() {
        return Matriz;
    }

    //Se lanza el evento de cambio para mover la posicion de la salida
    @Override
    public void stateChanged(ChangeEvent ce) {
        buscar = new Busqueda();
        buscar.LimpiarMatriz();
        limpiaMatriz();
        repaint();
    }

}
 