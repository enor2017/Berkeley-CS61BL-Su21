public class TriangleDrawer2 {
    public static void main(String[] args) {
        int SIZE = 10;
        for(int row = 0; row < SIZE; ++row){
            for(int col = 0; col <= row; ++col){
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
