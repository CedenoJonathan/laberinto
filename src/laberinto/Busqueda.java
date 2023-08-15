package laberinto;

public final class Busqueda {

    int x = 0;
    int y = 0;
    static int[][] Matriz = new int[20][20];//Se cra una matriz

    public Busqueda() {
        RellenaCopia();
    }

    //se rellena esta matriz con los valor de la Matriz principal
    public void RellenaCopia() {
        for (int a = 0; a < 20; a++) {
            for (int b = 0; b < 20; b++) {
                Matriz[a][b] = Grafica.Matriz[a][b];
            }
        }
    }

//Meta=25
//Visitado=30    
//visitado sin salida=40 
//Muro=0    
    public boolean BuscarCamino(int x, int y) {
        try {
            if (Matriz[x][y] == 25) {
                return true;
            }
            if (Matriz[x][y] == 0 || Matriz[x][y] == 30 || Matriz[x][y] == 40) {
                return false;
            }
            Matriz[x][y] = 30;
            boolean resultado;
            resultado = BuscarCamino(x-1, y);

            if (resultado) {
                return true;
            }
            resultado = BuscarCamino(x, y+1);

            if (resultado) {
                return true;
            }
            resultado = BuscarCamino(x+1, y);

            if (resultado) {
                return true;
            }
            resultado = BuscarCamino(x, y-1);

            if (resultado) {
                return true;
            }

            Matriz[x][y] = 40;
        } catch (Exception e) {
            //  System.out.println(e.getMessage());
        }
        return false;
    }

    public int[][] getCamino() {
        return Matriz;
    }

    public void LimpiarMatriz() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Matriz[i][j] = 0;
            }
        }
    }

    public void Imprimir() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.print(Matriz[i][j]);
            }
            System.out.println();
        }
    }
}
