import bean.UserBean;
import dao.UserDao;
import org.junit.Test;
import util.Md5;

public class UserDaoTest {

    @Test
    public void createUserTest() throws Exception {
        UserBean bean = new UserBean();
        UserDao dao = new UserDao();
        bean.setUserName("ada");
        bean.setUserPassword(Md5.EncoderByMd5("ada"));
        dao.createUser(bean);
        bean.setUserName("basic");
        bean.setUserPassword(Md5.EncoderByMd5("basic"));
        dao.createUser(bean);
        bean.setUserName("c");
        bean.setUserPassword(Md5.EncoderByMd5("c"));
        dao.createUser(bean);
        bean.setUserName("delphi");
        bean.setUserPassword(Md5.EncoderByMd5("delphi"));
        dao.createUser(bean);
        bean.setUserName("pascal");
        bean.setUserPassword(Md5.EncoderByMd5("pascal"));
        dao.createUser(bean);
        bean.setUserName("java");
        bean.setUserPassword(Md5.EncoderByMd5("java"));
        dao.createUser(bean);
        bean.setUserName("c#");
        bean.setUserPassword(Md5.EncoderByMd5("c#"));
        dao.createUser(bean);
        bean.setUserName("ruby");
        bean.setUserPassword(Md5.EncoderByMd5("ruby"));
        dao.createUser(bean);
        bean.setUserName("");
        bean.setUserPassword(Md5.EncoderByMd5(""));
        System.out.println(dao.createUser(bean));
    }

    @Test
    public void getPassWordTest() throws Exception {
        UserDao dao = new UserDao();
        if (!dao.getPassWord("ada").equals(Md5.EncoderByMd5("ada"))) {
            throw new Exception();
        }
        if (!dao.getPassWord("basic").equals(Md5.EncoderByMd5("basic"))) {
            throw new Exception();
        }
        if (!dao.getPassWord("c").equals(Md5.EncoderByMd5("c"))) {
            throw new Exception();
        }
        if (!dao.getPassWord("delphi").equals(Md5.EncoderByMd5("delphi"))) {
            throw new Exception();
        }
        if (!dao.getPassWord("pascal").equals(Md5.EncoderByMd5("pascal"))) {
            throw new Exception();
        }
        if (!dao.getPassWord("java").equals(Md5.EncoderByMd5("java"))) {
            throw new Exception();
        }
        if (!dao.getPassWord("c#").equals(Md5.EncoderByMd5("c#"))) {
            throw new Exception();
        }
        if (!dao.getPassWord("ada").equals(Md5.EncoderByMd5("ada"))) {
            throw new Exception();
        }
        if (!dao.getPassWord("ruby").equals(Md5.EncoderByMd5("ruby"))) {
            throw new Exception();
        }
        if (!dao.getPassWord("").equals(Md5.EncoderByMd5(""))) {
            throw new Exception();
        }
    }

    @Test
    public void getUserTest() {
        String name = "ada";
        UserBean bean = new UserDao().getUser(name);
        System.out.println(bean.getUserId() + "  " + bean.getUserName() + "  " + bean.getUserPassword());
        bean = new UserDao().getUser("");
        System.out.println(bean.getUserId() + "  " + bean.getUserName() + "  " + bean.getUserPassword());
        bean = new UserDao().getUser("abc");
        System.out.println(bean == null);
        bean = new UserDao().getUser("c");
        System.out.println(bean == null);
    }

    @Test
    public void isExistTest() {
        String name = "";
        assert new UserDao().isExist(name);
        name = "ada";
        assert new UserDao().isExist(name);
        name = "add";
        assert !new UserDao().isExist(name);
    }
}
