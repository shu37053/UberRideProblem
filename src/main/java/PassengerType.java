public enum PassengerType {
    DEMOCRAT {
        @Override
        public <T, R> R accept(Visitor<T, R> visitor, T data) {
            try {
                return visitor.visitDemocrat(data);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    },
    REPUBLICAN {
        @Override
        public <T, R> R accept(Visitor<T, R> visitor, T data) {
            try {
                return visitor.visitRepublican(data);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public abstract <T, R> R accept(Visitor<T, R> visitor, T data);

    public interface Visitor<T, R> {
        R visitDemocrat(T t) throws InterruptedException;

        R visitRepublican(T t) throws InterruptedException;
    }
}
