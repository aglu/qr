import java.util.ArrayList;
import java.util.List;

import com.qr.DBController.dao.TUserAttributes;
import com.qr.DBController.dao.TUserAuthorizationCode;
import com.qr.DBController.dao.TUserSessions;
import com.qr.DBController.dao.TUsers;
import com.qr.DBController.exceptions.EmptyParamsException;
import com.qr.DBController.exceptions.NotFoundDataExceptions;
import com.qr.DBController.pools.MainPool;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

public class Test1 {

    @Test
    public void main() {
        MainPool.initialize();
        try (Session session = MainPool.getPool()) {
            TUsers user = TUsers.getByPhone(session, "9828").get(0);
            List<TUserAuthorizationCode> codes = TUserAuthorizationCode.checkAuthCode(session, user.getId(), "123");
            List<TUserAttributes> attrs = user.getAttributes();
            System.out.println(user.toString());
            TUserAttributes attr = attrs.stream().findFirst().get();
            attr.setAttrValue(attr.getAttrValue() + "1");
            session.beginTransaction();
            session.update(attr);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }
@Test
    public void main2() {
        MainPool.initialize();
    List<TUserSessions> sessions = new ArrayList<>();
        try (Session session = MainPool.getPool()) {
            sessions = TUserSessions.getBySessionKey(session, "77422CB4D3141676DDB7F3764120909F");
        } catch (EmptyParamsException e) {
            e.printStackTrace();
        } catch (NotFoundDataExceptions notFoundDataExceptions) {
            notFoundDataExceptions.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    ;
    }
}
