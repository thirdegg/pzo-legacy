package AccountService;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class UserDataSetDAO {
    public SessionFactory sessionFactory;
    FileWriter fw;
    public UserDataSetDAO(SessionFactory session,FileWriter fw) {
        this.sessionFactory = session;
        this.fw=fw;
    }

    public void save(UserDataSet dataSet) {
        Session session = sessionFactory.openSession();
        Transaction trx = session.beginTransaction();
        session.save(dataSet);
        trx.commit();
        session.close();
    }

    public void update(UserDataSet dataSet) {
        // здесь возникает ошибка при сохранении игрока
        Session session = sessionFactory.openSession();
        try{
        Transaction trx = session.beginTransaction();
        session.update(dataSet);
        trx.commit();
        session.close();}
        catch(Exception e){
            e.printStackTrace();
            session.close();
            try {
                String err = "\n";
                for (StackTraceElement ste : e.getStackTrace()) {
                    err += "Class name -" + ste.getClassName() + " Line -" + ste.getLineNumber() + "\n";
                }
                fw.write("error - "+e.toString() + " Message -" + e.getMessage() + err);
                fw.flush();
                fw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public UserDataSet read(long id) {
        Session session = sessionFactory.openSession();
        return (UserDataSet) session.load(UserDataSet.class, id);
    }

    public UserDataSet readByName(String name) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(UserDataSet.class);
        //
        criteria.add(Restrictions.eq("name", name));
        UserDataSet uddss=(UserDataSet)criteria.uniqueResult();
        //
        return uddss;
        //return (UserDataSet) criteria.add(Restrictions.eq("name", name)).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<UserDataSet> readAll() {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return (List<UserDataSet>) criteria.list();
    }
/*  @Size(min = 1, max = 255)
    @Column(name = "login", length = 255 )
    private String login;

    @Size(min = 5,max = 255)
    @Column(name = "password", length = 255 )
    private String password;

    @NotNull
    @Size(max = 255)
    @Pattern(regexp = "^.+@.+\\..+$" )
    @Column(name = "email", nullable = false )
    private String email;*/

}
