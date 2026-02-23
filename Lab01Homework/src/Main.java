import java.util.Scanner;

public StringBuilder AfisareMatrice(int[][] m) {

    StringBuilder matrice = new StringBuilder();

    for(int i = 0; i < m.length; i++){
        for(int j = 0; j< m[i].length; j++)
            if(m[i][j] == 255)
            {
                matrice.append('⬜');
            }
            else if (m[i][j] == 0)
            {
                matrice.append('⬛');
            }

            matrice.append('\n');
    }

    return matrice;

}

void main(String[] args) {

    long time1 = System.currentTimeMillis();


    int n = Integer.parseInt(args[0]);

    String formaGeometrica = new String(args[1]);

    int[][] matrice = new int[n][n];



        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                if (formaGeometrica.equals("dreptunghi")) {

                    if (i >= n / 4 && i <= n / 3 * 2 && j >= n / 4 && j <= n / 4 * 3)
                        matrice[i][j] = 0;
                    else
                        matrice[i][j] = 255;

                } else if (formaGeometrica.equals("cerc")) {

                    if (((i - n / 2) * (i - n / 2) + (j - n / 2) * (j - n / 2)) <= n / 2)
                        matrice[i][j] = 255;
                    else
                        matrice[i][j] = 0;

                }

        }


        if (n < 100)
        {
            System.out.print(AfisareMatrice(matrice));
        }

        else{

            long time2 = System.currentTimeMillis();

            System.out.print(time2 - time1);

        }



}
