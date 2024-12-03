public class Main {
    public static void main(String[] args) {
        UberManager manager = new UberManager();
        for(int i=0;i<26;i++) {
            manager.testRepublican(i+"-Republican");
            manager.testDemocrat(i+"-Democrat");
        }
    }
}
