//package ru.practicum.shareit.item;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.practicum.shareit.item.dao.ItemDao;
//import ru.practicum.shareit.item.dto.ItemDto;
//import ru.practicum.shareit.item.model.Item;
//import ru.practicum.shareit.user.Users;
//import ru.practicum.shareit.user.dao.UserDao;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class ItemTest {
//
//    private ItemDao itemDao;
//    private static UserDao userDao;
//    private static Users user;
//
//    @BeforeEach
//    public void beforeEach() {
//        itemDao = new ItemDao(userDao,);
//
//
//    }
//
//    @BeforeAll
//    public static void beforeAll() {
//        userDao = new UserDao();
//        user = Users.builder()
//                .id(1)
//                .name("User1")
//                .email("dan22@mail.ru")
//                .build();
//        userDao.addUser(user);
//
//    }
//
//    @Test
//    public void addItemTest() {
//        Item item = Item.builder()
//                .id(1)
//                .name("Item1")
//                .description("Desc Item ")
//                .available(true)
//                .build();
//        Item itemRes = itemDao.addItem(item, user.getId());
//        assertThat(itemRes)
//                .isNotNull()
//                .isEqualTo(itemDao.getItemWithId(itemRes.getId()));
//
//    }
//
//    @Test
//    public void editItemTest() {
//        Item item = Item.builder()
//                .id(1)
//                .name("Item1")
//                .description("Desc Item ")
//                .available(true)
//                .build();
//        itemDao.addItem(item, user.getId());
//        ItemDto itemNew = ItemDto.builder()
//                .id(item.getId())
//                .name("ItemUPDaTw")
//                .description("Desc Item UPDAte ")
//                .available(true)
//                .owner(userDao.getUserById(user.getId()))
//                .build();
//        itemDao.updItem(item.getId(), itemNew, user.getId());
//        assertThat(itemNew.getName())
//                .isNotNull()
//                .isEqualTo(itemDao.getItemWithId(item.getId()).getName());
//        assertThat(itemNew.getDescription())
//                .isNotNull()
//                .isEqualTo(itemDao.getItemWithId(item.getId()).getDescription());
//        assertThat(itemNew.getAvailable())
//                .isNotNull()
//                .isEqualTo(itemDao.getItemWithId(item.getId()).getAvailable());
//        assertThat(itemNew.getOwner())
//                .isNotNull()
//                .isEqualTo(itemDao.getItemWithId(item.getId()).getOwner());
//        assertThat(itemNew.getId())
//                .isEqualTo(itemDao.getItemWithId(item.getId()).getId());
//
//    }
//
//    @Test
//    public void getItemTest() {
//        Item item = Item.builder()
//                .id(1)
//                .name("Item1")
//                .description("Desc Item ")
//                .available(true)
//                .build();
//        itemDao.addItem(item, user.getId());
//        assertThat(itemDao.getItemWithId(item.getId()))
//                .isNotNull()
//                .isEqualTo(item);
//
//    }
//
//    @Test
//    public void getItemWithIdUserTest() {
//        Item item = Item.builder()
//                .id(1)
//                .name("Item1")
//                .description("Desc Item ")
//                .available(true)
//                .build();
//        itemDao.addItem(item, user.getId());
//        assertThat(itemDao.getItemWithIdUser(user.getId()))
//                .isNotNull()
//                .isEqualTo(new ArrayList<>(List.of(item)));
//    }
//}
