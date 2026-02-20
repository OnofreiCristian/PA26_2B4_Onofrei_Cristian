
void main() {

    String[] languages = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};

    int n = (int) (Math.random() * 1_000_000);

    n = n * 3;
    n = n + 0b10101;
    n = n + 0xFF;
    n = n * 6;
    int m = 0;

    while (n > 9) {

        while (n > 0) {

            int r = n % 10;

            m = m + r;

            n = n / 10;

        }

        n = m;
        m = 0;


    }

    IO.println(n);
    int result = n;
    IO.println( "Willy-nilly, this semester I will learn " + languages[result]);

}
