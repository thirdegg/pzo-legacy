package Base;


public interface Frontend extends Abonent {
    void AutStatus(int otvet, int idchanel);

    void RegStatus(int sessionId, String name, int resultreg);

    void doGet(int st, String str);
}
