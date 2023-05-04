//package cn.tycoding.entity;
//
//import org.apache.ibatis.annotations.Mapper;
//
//import java.util.List;
//
//
//@Mapper //todo 这个interface里面的方法怎么实现？
//public interface UserMapper {
//    /**
//     *查 读取(Retrieve)
//     * fetch data
//     * @return List<User>
//     */
//    List<User> queryAlluser();
//    /**
//     *查 读取(Retrieve)
//     * fetch data by userid
//     * @param  userid
//     * @return User
//     */
//    /**
//     @Select("select * from user where userid = #{userid}")
//     本人喜欢讲SQL语句写在mapper.xml文件中，这样显得代码比较整齐
//     在后期开发中，一旦SQL语句变得繁琐起来，不利于维护
//     但是这样用注解的形式也完全可行，毕竟有些人可能写配置文件可能已经快写吐了
//     */
//    User queryuserbyid( int userid);
//    /**
//     * 增加(Create)
//     * add data by user
//     * @param user
//     * @return int
//     */
//    boolean AddUser(User user);
//    /**
//     * 删除(Delete)
//     * @param id
//     * @return int
//     */
//    boolean DelUser(int id);
//    /**
//     * 更新(Update)
//     * @param user
//     * @return boolean
//     */
//    boolean UpdUser(User user);
//
//    User queryuserbyname(String name);
//
//    }
//
//}