package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.college.cse431_mobile_programming_project.R
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.college.cse431_mobile_programming_project.data.model.DishType
import com.college.cse431_mobile_programming_project.data.model.Restaurant
import com.college.cse431_mobile_programming_project.data.recycler_data.DishTypesRecyclerAdapter
import com.college.cse431_mobile_programming_project.data.recycler_data.RestaurantsRecyclerAdapter
import com.college.cse431_mobile_programming_project.databinding.FragmentMainBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainFragment : Fragment() {

    private val restaurantsList = ArrayList<Restaurant>()
    private val dishTypesList = ArrayList<DishType>()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dishTypesList.add(DishType(1,
            "Breakfast",
            "https://scontent-hbe1-1.xx.fbcdn.net/v/t1.6435-9/67612388_2146645052305267_4052419102541611008_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=8bfeb9&_nc_eui2=AeHAYBHASR_6P4uRnbwAuxOctdEPKGXDpWm10Q8oZcOlacXl-ra998YWVwSXAsqeXuE71quHJKW3IcRzLJ0Ih5e_&_nc_ohc=gmRNjxgWBTUAX_V0kPF&_nc_ht=scontent-hbe1-1.xx&oh=00_AfA4WPCTUewRcIU0uvNT_LkKt5RxHBoNzaEPaVnyPZzHGg&oe=63BC8107"))

        dishTypesList.add(DishType(2,
            "Lunch",
            "https://m3rfh.b-cdn.net/wp-content/uploads/2021/05/%D8%A3%D9%81%D9%83%D8%A7%D8%B1-%D9%84%D9%84%D8%B9%D8%B4%D8%A7%D8%A1.jpg"))

        dishTypesList.add(DishType(3,
            "Dinner",
            "https://m3rfh.b-cdn.net/wp-content/uploads/2021/05/%D8%A3%D9%81%D9%83%D8%A7%D8%B1-%D9%84%D9%84%D8%BA%D8%AF%D8%A7%D8%A1.jpg"))

        dishTypesList.add(DishType(4,
            "Fool & Falafel",
            "https://menu22.com/images/57104171_1325972874233946_5601971753078226944_n.jpg"))

        dishTypesList.add(DishType(5,
            "Syrian",
            "https://www.knoozi.com/wp-content/uploads/2018/02/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9-%D8%B9%D9%85%D9%84-%D8%B4%D8%A7%D9%88%D8%B1%D9%85%D8%A7-%D8%A7%D9%84%D8%AF%D8%AC%D8%A7%D8%AC-%D8%A7%D9%84%D8%B3%D9%88%D8%B1%D9%8A%D8%A9.jpg"))

        dishTypesList.add(DishType(6,
            "Burger",
            "https://shamlola.s3.amazonaws.com/Shamlola_Images/7/src/7d63e3088cd4f45c20dac8671bb3eea1d98a22c5.jpg"))

        dishTypesList.add(DishType(7,
            "Pizza",
            "https://modo3.com/thumbs/fit630x300/51334/1435144381/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9_%D8%B9%D9%85%D9%84_%D8%B9%D8%AC%D9%8A%D9%86%D8%A9_%D8%A7%D9%84%D8%A8%D9%8A%D8%AA%D8%B2%D8%A7_%D8%A7%D9%84%D8%A5%D9%8A%D8%B7%D8%A7%D9%84%D9%8A%D8%A9.jpg"))

        dishTypesList.add(DishType(8,
            "Pasta",
            "https://www.justfood.tv/nawa3emPics/4bCJFR12.7.2019-1.jpg"))

        dishTypesList.add(DishType(9,
            "Chinese",
            "https://lh3.googleusercontent.com/p/AF1QipOhfMj2gQaFgsnyFnoCxntaexHlsNinpEdxDOQ=s680-w680-h510"))

        dishTypesList.add(DishType(10,
            "Crepe",
            "https://www.supermama.me/system/App/Models/Recipe/images/000/106/376/watermarked/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9-%D8%B9%D9%85%D9%84-%D9%83%D8%B1%D9%8A%D8%A8-%D8%A8%D8%A7%D9%84%D9%84%D8%AD%D9%85%D8%A9-%D8%A7%D9%84%D9%85%D9%81%D8%B1%D9%88%D9%85%D8%A9.jpg"))

        dishTypesList.add(DishType(11,
            "Fried Chicken",
            "https://static.toiimg.com/thumb/61589069.cms?width=1200&height=900"))

        dishTypesList.add(DishType(12,
            "Waffles",
            "https://i.pinimg.com/originals/59/a2/af/59a2afb9c78993013ac259f48ed1b169.jpg"))

        dishTypesList.add(DishType(13,
            "Sandwiches",
            "https://lh3.googleusercontent.com/-O9hmerSSvpQ/X_8-Z6UFe0I/AAAAAAAACMI/C1NVYgumLIoiseK5Iek2bEuSQ-nC2tqGACLcBGAsYHQ/s16000/%25D8%25B3%25D8%25A7%25D9%2586%25D8%25AF%25D9%2588%25D8%25AA%25D8%25B4%25D8%25A7%25D8%25AA%2B%25D8%25A7%25D9%2584%25D8%25B4%25D9%258A%25D8%25B4%2B%25D8%25B7%25D8%25A7%25D9%2588%25D9%2588%25D9%2582.jpg"))

        val foolTags = ArrayList<String>()
        foolTags.add("Breakfast")
        foolTags.add("Fool & Falafel")

        val syrianTags = ArrayList<String>()
        syrianTags.add("Lunch")
        syrianTags.add("Syrian")
        syrianTags.add("Shawerma")

        val burgerTags = ArrayList<String>()
        burgerTags.add("Dinner")
        burgerTags.add("Burger")

        val pizzaTags = ArrayList<String>()
        pizzaTags.add("Lunch")
        pizzaTags.add("Dinner")
        pizzaTags.add("Pizza")
        pizzaTags.add("Feteer")

        val pastaTags = ArrayList<String>()
        pastaTags.add("Lunch")
        pastaTags.add("Dinner")
        pastaTags.add("Pasta")

        val chineseTags = ArrayList<String>()
        chineseTags.add("Lunch")
        chineseTags.add("Dinner")
        chineseTags.add("Chinese")

        val crepeTags = ArrayList<String>()
        crepeTags.add("Lunch")
        crepeTags.add("Crepe")
        crepeTags.add("Meat")
        crepeTags.add("Dessert")

        val friedChickenTags = ArrayList<String>()
        friedChickenTags.add("Lunch")
        friedChickenTags.add("Dinner")
        friedChickenTags.add("Fried Chicken")

        val waffleTags = ArrayList<String>()
        waffleTags.add("Breakfast")
        waffleTags.add("Lunch")
        waffleTags.add("Waffle")
        waffleTags.add("Dessert")

        val sandwichTags = ArrayList<String>()
        sandwichTags.add("Lunch")
        sandwichTags.add("Dinner")
        sandwichTags.add("Meat")
        sandwichTags.add("Sandwiches")

        val foolList = ArrayList<Dish>()
        foolList.add(Dish(1,
            "Fool Sandwich",
            5.0f,
            "EGP",
            "Fool sandwich.",
            "A finely made fool sandwich made of the most exquisite of ingredients. The beans were grown in a farm with water taken directly from melting Antarctican glaciers. The bread was baked in the gates of hell. The tahini was created by crushing real human bones for the most exquisite of tastes. Truly a sandwich to fall in love with.",
            "https://scontent-hbe1-1.xx.fbcdn.net/v/t1.6435-9/60127232_1012635668946457_170317722691829760_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=cdbe9c&_nc_eui2=AeFdLYgFf3GwwPiugcfP2t_xF1JFxbvQ96IXUkXFu9D3oma9HP71DkIlRy3rjf41Ssm9Z6dIKtPdki6kiCnZyw4Z&_nc_ohc=fgF6lROFYVgAX8GvVY-&_nc_ht=scontent-hbe1-1.xx&oh=00_AfDwtOOLmna3V4AHN1_u_wJRtfEdQTw1c60twtuXcBBuDA&oe=63BD4CF7"))
        foolList.add(Dish(2,
            "Falafel Sandwich",
            6.0f,
            "EGP",
            "Falafel sandwich.",
            "A falafel sandwich like none you had before. Fried in oil extracted from flowers grown on top of Mount Everest, at the perfect temperature calculated using our latest machine learning model which was sent to us from the future, the falafel present will be perfectly tender as you have never tried before. Using the same beans used in our fool sandwiches, it will keep you drooling at all times, even after finishing the sandwich.",
            "https://pbs.twimg.com/media/ErbexFhXUAAwJLS.jpg"))
        foolList.add(Dish(3,
            "Fries Sandwich",
            7.5f,
            "EGP",
            "Fries sandwich.",
            "Ever heard of the Irish great famine? Do you know why it happened? Because their potato supply was depleted after a virus swept away their entire crop yield. It also wasn't helped by the fact that England was taxing them with all what's left. Lesson is, potatos can be made so well they cause an entire nation to be over-reliant on it that a single virus can kill millions. Our fries are made with potatos that are as good, if not better.",
            "https://scontent.fcai20-2.fna.fbcdn.net/v/t1.18169-9/27752038_1985383591684198_833922425111005850_n.jpg?_nc_cat=104&ccb=1-7&_nc_sid=730e14&_nc_eui2=AeEiBJ50FRPaJrHZP3l8d5MlYKHJsbte3mFgocmxu17eYVXNvMBBTgcPcUeZUn1r1RCmDsYUwY8EiHoZJqSKPMby&_nc_ohc=uwDzW_GbZyEAX_pa3gx&tn=zBlexu2en-P2Fj5j&_nc_ht=scontent.fcai20-2.fna&oh=00_AfAY9WlF-USCb8zGNXvVaEk4dnO-zCt9TPPSNY-5vg7FAA&oe=63BD46AC",
            false))
        foolList.add(Dish(4,
            "Pickles",
            3.0f,
            "EGP",
            "Pickles.",
            "Made from vinegar chemically crafted using what was learned in Thanaweya 'Amma organic chemistry, with salts directly extracted from meteorites, these are the perfect pickles. Here is a funny story, a friend of mine absolutely hates pickles but keeps commenting how I don't like cinnamon and how \"it makes no sense\". The double standards are real.",
            "https://modo3.com/thumbs/fit630x300/149903/1483863074/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9_%D8%B9%D9%85%D9%84_%D9%85%D8%AE%D9%84%D9%84%D8%A7%D8%AA_%D9%85%D8%B4%D9%83%D9%84%D8%A9.jpg"))

        val syrianList = ArrayList<Dish>()
        syrianList.add(Dish(5,
            "Chicken Shawerma Sandwich",
            40.0f,
            "EGP",
            "Chicken shawerma covered in toumeya wrapped in syrian bread.",
            "Remember when Arabic unity was a thing? Back when Egypt and Syria were a single country. When they were intermarrying normally. When we were one people. When they started broadcasting \"Hona Al-Qahira\" from Syria when it was hit in Cairo. Now, everyone is like \"tHeY tOoK oUr JoBs\". No, no one is taking anybody's jobs. This is a thinking only found in seppoland. They only think in race. We are all brothers in Islam.",
            "https://ckreci.com/wp-content/uploads/2020/03/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9-%D8%B9%D9%85%D9%84-%D8%B4%D8%A7%D9%88%D8%B1%D9%85%D8%A7-%D8%A7%D9%84%D9%81%D8%B1%D8%A7%D8%AE-2.jpg"))
        syrianList.add(Dish(6,
            "Meat Shawerma Sandwich",
            45.0f,
            "EGP",
            "Meat shawerma covered in toumeya wrapped in syrian bread.",
            "While we fight over whose work it is, a new faction of zionists is now calling shawerma a Jew dish. Can you believe the audacity? We should stop fighting amongst ourselves and shut them up for good. Egyptian Shawerma is still better than Syrian, though.",
            "https://www.taabkh.com/files/styles/recipe/public/recipe/2016/08/Shawarma-meat.jpg"))
        syrianList.add(Dish(7,
            "Man'ousha",
            20.0f,
            "EGP",
            "A man'ousha with the type of your choice.",
            "A man'ousha with the type of your choice.\nAvailable additives:\nThyme, Roomy Cheese, Smoked Turkey, Mozzarella.",
            "https://i.ytimg.com/vi/JyBSle0Onh8/hqdefault.jpg"))
        syrianList.add(Dish(8,
            "Machine-Grilled Chicken",
            60.0f,
            "EGP",
            "Served with rice, fries, toumey, and pickles.",
            "A machine-grilled chicken. Served with rice, fries, toumeya, and pickles.",
            "https://img.youm7.com/xlarge/20171118030810810.jpg"))

        val burgerList = ArrayList<Dish>()
        burgerList.add(Dish(9,
            "Cheese Burger",
            50.0f,
            "EGP",
            "Beef burger, cheddar cheese, lettuce, tomatoes, pickles, onions, buns.",
            "Beef burger, cheddar cheese, lettuce, tomatoes, pickles, onions, buns.",
            "https://www.foodrepublic.com/wp-content/uploads/2012/03/033_FR11785.jpg"))
        burgerList.add(Dish(10,
            "Chicken Burger",
            40.0f,
            "EGP",
            "Chicken burger, cheddar cheese, lettuce, tomatoes, pickles, onions, buns.",
            "Chicken burger, cheddar cheese, lettuce, tomatoes, pickles, onions, buns.",
            "https://chefjar.com/wp-content/uploads/2022/01/ground-chicken-burgers-1.jpg"))
        burgerList.add(Dish(11,
            "Bacon Mashroom Burger",
            65.0f,
            "EGP",
            "Beef burger, bacon, mushrooms, cheddar cheese, lettuce, tomatoes, pickles, onions, buns.",
            "Beef burger, bacon, mushrooms, cheddar cheese, lettuce, tomatoes, pickles, onions, buns.",
            "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/39a1e890-c0fd-41a4-b492-a5ff0cc6bbf1.jpg"))
        burgerList.add(Dish(12,
            "Burger Burger",
            70.0f,
            "EGP",
            "Cheese burger with burger patties as buns.",
            "Cheese burger with burger patties as buns.",
            "https://healthyrecpesblogs.com/wp-content/uploads/2017/03/bunless-burger-featured-2021-500x375.jpg",
            false))

        val pizzaList = ArrayList<Dish>()
        pizzaList.add(Dish(13,
            "Pepperoni Pizza",
            100.0f,
            "EGP",
            "Pepperoni pizza with extra mozzarella cheese.",
            "Pepperoni pizza with extra mozzarella cheese.",
            "https://www.moulinex.com.eg/medias/?context=bWFzdGVyfHJvb3R8MTQzNTExfGFwcGxpY2F0aW9uL29jdGV0LXN0cmVhbXxoNjIvaDE5LzEzMTExNzcxNTI5MjQ2LmJpbnw3NTkwMmNjYmFhZTUwZjYwNzk0ZmQyNjVmMjEzYjZiNGI3YzU1NGI3ZGNjYjM3YjYxZGY5Y2Y0ZTdjZmZkZmNj"))
        pizzaList.add(Dish(14,
            "Chicken Ranch Pizza",
            120.0f,
            "EGP",
            "Grilled Chicken, Onions, Tomatoes, Ranch Sauce.",
            "Grilled Chicken, Onions, Tomatoes, Ranch Sauce.",
            "https://d257c1zjbj9yqq.cloudfront.net/general-uploads/Open-Graph-Images/General-SEO-1200x1200/_1200x630_crop_center-center_82_none/PR-menu-pizza-ChicBacRanch-1200x1200.png?mtime=1500927407"))
        pizzaList.add(Dish(15,
            "Margherita Pizza",
            100.0f,
            "EGP",
            "Margherita Pizza with extra mozzarella cheese.",
            "Margherita Pizza with extra mozzarella cheese.",
            "https://static.toiimg.com/thumb/56868564.cms?imgsize=1948751&width=800&height=800"))
        pizzaList.add(Dish(16,
            "Cheese Pizza",
            115.0f,
            "EGP",
            "A delicious blend of six different types of cheese.",
            "Gouda, Cheddar, Emmental, Romano, Mozzarella, Edam.",
            "https://img.youm7.com/xlarge/20171118030810810.jpg"))

        val pastaList = ArrayList<Dish>()
        pastaList.add(Dish(17,
            "Chicken Alfredo",
            80.0f,
            "EGP",
            "Penne pasta mixed with grilled chicken and white sauce.",
            "Penne pasta mixed with grilled chicken and white sauce.",
            "https://lmld.org/wp-content/uploads/2013/10/One-Pot-Chicken-Alfredo-Pasta-4-500x375.jpg"))
        pastaList.add(Dish(18,
            "Chicken Crispy Ranch Pasta",
            110.0f,
            "EGP",
            "Penne pasta covered in crispy chicken and ranch sauce.",
            "Penne pasta covered in crispy chicken and ranch sauce.",
            "https://scontent-hbe1-1.xx.fbcdn.net/v/t1.6435-9/76660683_1353687208138226_1194005383643922432_n.jpg?_nc_cat=110&ccb=1-7&_nc_sid=730e14&_nc_eui2=AeEC3R5ZnhoCtbAYW_j_5V46ZueMTFvmUNVm54xMW-ZQ1Zx5HXc102QSPi03CJ1RF2anfAeELSM8gDH5S6QDE-bL&_nc_ohc=d7m5q9xKOBsAX861J2y&_nc_ht=scontent-hbe1-1.xx&oh=00_AfBxVdYVvCgVyUH2GY-p3ZdpRFTqz16v9-Aaf3Ttq6y_cg&oe=63CBC72A",
            false))
        pastaList.add(Dish(19,
            "Bashamel Pasta",
            75.0f,
            "EGP",
            "Bashamel, minced meat, penne pasta.",
            "Baked pasta stuffed with minced meat and covered in premium bashamel sauce",
            "https://www.justfood.tv/big/0Bashamel.jpg"))
        pastaList.add(Dish(20,
            "Spaghetti & Meatballs",
            90.0f,
            "EGP",
            "Spaghetti mixed with meatballs and tomato sauce.",
            "Spaghetti mixed with meatballs and tomato sauce.",
            "https://hips.hearstapps.com/del.h-cdn.co/assets/17/39/2048x1024/landscape-1506456062-delish-spaghetti-meatballs.jpg?resize=1200:*"))

        val chineseList = ArrayList<Dish>()
        chineseList.add(Dish(21,
            "Beef Noodles Soup",
            60.0f,
            "EGP",
            "Beef noodles served in soup with vegetables.",
            "Halal beef meat served in soup along with hand-pulled noodles.",
            "https://www.ahlmasrnews.com/img/large/2020/11/%D9%85%D8%A7%D9%84-%D9%85%D8%AD%D9%85%D8%AF-%D8%B4%D9%8A%D9%81-%D9%85%D8%B5%D8%B1%D9%8A%D8%A9-%D8%B5%D9%8A%D9%86%D9%8A%D8%A9-1604943343-0.jpg"))
        chineseList.add(Dish(22,
            "Stir Fried Noodles",
            70.0f,
            "EGP",
            "Stir fried noodles with beef/chicken and vegetables.",
            "Stir fried noodles served with your choice of beef or chicken along with colored peppers, onion, and tomatoes.",
            "https://scontent-hbe1-1.xx.fbcdn.net/v/t39.30808-6/281628391_407679508030041_1756203971851507300_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=8bfeb9&_nc_eui2=AeHdx6rKCKQ1dEeMNhmNgRouBBXmTMN16DYEFeZMw3XoNo5bEe4gvw_u8uPOvl8b_pOtM_nTjAdHLalsjBQYKBw8&_nc_ohc=oXE1wFm3GGcAX-W9Uzb&_nc_ht=scontent-hbe1-1.xx&oh=00_AfC-4WNE1_Oa-DaVWazZC678yt9QdLYvp9E7GNaruhjG3w&oe=63A9D6AF"))
        chineseList.add(Dish(23,
            "Sweet & Sour Chicken",
            100.0f,
            "EGP",
            "Sweet & Sour Chicken",
            "Sweet and sour chicken made with vinegar, honey, ketchup, sugar, and MSG.",
            "https://copykat.com/wp-content/uploads/2021/01/Chinese-Imperial-Palace-Sweet-and-Sour-Sauce-Pin2.jpg"))
        chineseList.add(Dish(24,
            "Dumplings",
            85.0f,
            "EGP",
            "Dumplings served either dry or with soup.",
            "Dumplings stuffed with meat and vegetables served dry with chilli sauce or in soup.",
            "https://www.aspicyperspective.com/wp-content/uploads/2014/01/potstickers-chinese-dumplings-recipe-100.jpg"))

        val crepeList = ArrayList<Dish>()
        crepeList.add(Dish(25,
            "Koko El-Da3eef",
            45.0f,
            "EGP",
            "Pane Chicken + Nuggets",
            "Pane Chicken + Nuggets",
            "https://rayehgaay.com/image/cache/catalog/demo/mataaam/sandwiches/Crepe/Koko-El-Daef-Crepe-369x369.jpg"))
        crepeList.add(Dish(26,
            "El-Wa7sh",
            55.0f,
            "EGP",
            "Pane Chicken + Crispy + Katyusha + Hot dog",
            "Pane Chicken + Crispy + Katyusha + Hot dog",
            "https://images.deliveryhero.io/image/talabat/Menuitems/El_Wahsh_Crepe_637195409447945787.jpg"))
        crepeList.add(Dish(27,
            "El-Sarookh",
            60.0f,
            "EGP",
            "Pane Chicken + Crispy + Katyusha + Hot Dog + Burger + Kofta + Fries",
            "Pane Chicken + Crispy + Katyusha + Hot Dog + Burger + Kofta + Fries",
            "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/b977b395-2b25-41b9-822c-e9461925ee4e.jpg"))
        crepeList.add(Dish(28,
            "Nutella with Bananas",
            35.0f,
            "EGP",
            "Nutella with bananas",
            "Nutella with bananas",
            "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/e2c7b690-31a2-495b-9cbe-1f83617afcae.jpg"))

        val friedChickenList = ArrayList<Dish>()
        friedChickenList.add(Dish(29,
            "9 Pc Bucket",
            // WHAT ARE THOSE PRICES KFC HAS IT FOR 350 TOO??? AAAAAAAAAAAAAAA
            250.0f,
            "EGP",
            "9 pcs + 1 large coleslaw + 1 large fries + 3 buns + 1L pepsi",
            "Nine pieces of our signature chicken made with our secret recipe offered with a large coleslaw, fries, three buns, and 1L pepsi.",
            "https://media.istockphoto.com/id/477707389/photo/bucket-of-chicken.jpg?s=612x612&w=0&k=20&c=_lQkjaVH0i4ZnlT9ARggbBbeYcuofmcH0Y1HmczQssk="))
        friedChickenList.add(Dish(30,
            "Super Crunchy Sandwich",
            60.0f,
            "EGP",
            "Crispy chicken strips, pickles, mozzarella, mayonnaise",
            "Crispy chicken strips, smoked turkey, pickles, mozzarella, mayonnaise",
            "https://kitchen.sayidaty.net/uploads/small/83/8341e219ec14bc3ad6c4c6bf989c262b_w750_h750.jpg"))
        friedChickenList.add(Dish(31,
            "Zinger",
            70.0f,
            "EGP",
            "Zinger chicken, mozzarella, mushroom, mayonnaise",
            "Zinger chicken, mozzarella, mushroom, mayonnaise",
            "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/84694b2b-2878-445b-9c06-531c5ca59da3.jpg"))
        friedChickenList.add(Dish(32,
            "3 Pc Meal",
            80.0f,
            "EGP",
            "3 pieces with basmati rice, spicy sauce, garlic dip, and coleslaw",
            "3 pieces with basmati rice, spicy sauce, garlic dip, and coleslaw. Suitable for one person.",
            "https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/a97ef558-7faf-447c-90f5-3bd66d006d49.jpg"))

        val waffleList = ArrayList<Dish>()
        waffleList.add(Dish(33,
            "Ice Cream Waffle",
            60.0f,
            "EGP",
            "Waffle covered with vanilla ice cream, strawberries, blueberries, and chocolate sauce.",
            "Waffle covered with vanilla ice cream, strawberries, blueberries, and chocolate sauce.",
            "https://www.prairiefarms.com/wp-content/uploads/2017/04/WaffleSundae.jpg"))
        waffleList.add(Dish(34,
            "Chocolate Waffle",
            40.0f,
            "EGP",
            "Chocolate waffle covered with chocolate sauce and Maltesers.",
            "Chocolate waffle covered with chocolate sauce and Maltesers.",
            "https://miragemargarine.com/wp-content/uploads/2020/10/Chocolate_Waffles_landscape_hero_web-1.jpg"))
        waffleList.add(Dish(35,
            "Brownies with Ice Cream",
            40.0f,
            "EGP",
            "Brownies with ice cream covered with chocolate sauce.",
            "Brownies with ice cream covered with chocolate sauce.",
            "https://www.oetker.com.my/Recipe/Recipes/oetker.com.my/my-en/cakes/image-thumb__66007__RecipeDetailsLightBox/brownies-with-ice-cream.jpg"))
        waffleList.add(Dish(36,
            "Cheese Cake",
            35.0f,
            "EGP",
            "",
            "Cheese cake.",
            "https://sugarspunrun.com/wp-content/uploads/2019/01/Best-Cheesecake-Recipe-2-1-of-1-4.jpg",
            false))

        val sandwichList = ArrayList<Dish>()
        sandwichList.add(Dish(37,
            "Shish Tawook Sandwich",
            45.0f,
            "EGP",
            "Grilled shish tawook with vegetables.",
            "Grilled shish tawook with vegetables.",
            "https://lh3.googleusercontent.com/-O9hmerSSvpQ/X_8-Z6UFe0I/AAAAAAAACMI/C1NVYgumLIoiseK5Iek2bEuSQ-nC2tqGACLcBGAsYHQ/s16000/%25D8%25B3%25D8%25A7%25D9%2586%25D8%25AF%25D9%2588%25D8%25AA%25D8%25B4%25D8%25A7%25D8%25AA%2B%25D8%25A7%25D9%2584%25D8%25B4%25D9%258A%25D8%25B4%2B%25D8%25B7%25D8%25A7%25D9%2588%25D9%2588%25D9%2582.jpg"))
        sandwichList.add(Dish(38,
            "Sausage Sandwich",
            30.0f,
            "EGP",
            "Grilled sausages with vegetables.",
            "Grilled sausages with vegetables.",
            "https://www.supermama.me/system/App/Models/Recipe/images/000/100/771/watermarked/%D8%B3%D8%A7%D9%86%D8%AF%D9%88%D9%8A%D8%B4%D8%A7%D8%AA-%D8%A7%D9%84%D8%B3%D8%AC%D9%82-%D8%A8%D8%A7%D9%84%D9%85%D8%B4%D8%B1%D9%88%D9%85.jpg"))
        sandwichList.add(Dish(39,
            "Fries Sandwich",
            20.0f,
            "EGP",
            "Fries with ketchup, mayonnaise, and lettuce.",
            "Fries with ketchup, mayonnaise, and lettuce.",
            "https://www.mammeto.me/wp-content/uploads/2019/03/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9-%D8%B9%D9%85%D9%84-%D8%B3%D9%86%D8%AF%D9%88%D9%8A%D8%B4%D8%A7%D8%AA-%D8%A8%D8%A7%D9%84%D8%A8%D8%B7%D8%A7%D8%B7%D8%B3.jpg",
            false))
        sandwichList.add(Dish(40,
            "Hawawshi",
            40.0f,
            "EGP",
            "Marinated Meat, tomatoes, pepper, olives, cheese",
            "Marinated Meat, tomatoes, pepper, olives, cheese",
            "https://arbfoods.com/wp-content/uploads/2021/09/%D8%AD%D9%88%D8%A7%D9%88%D8%B4%D9%8A.jpg"))

        restaurantsList.add(Restaurant(1,
            "Fool & Falafel Restaurant",
            4.5f,
            1234,
            "https://menu22.com/images/57104171_1325972874233946_5601971753078226944_n.jpg",
            foolTags,
            foolList))

        restaurantsList.add(Restaurant(2,
            "Syrian Restaurant",
            4.1f,
            3210,
            "https://www.knoozi.com/wp-content/uploads/2018/02/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9-%D8%B9%D9%85%D9%84-%D8%B4%D8%A7%D9%88%D8%B1%D9%85%D8%A7-%D8%A7%D9%84%D8%AF%D8%AC%D8%A7%D8%AC-%D8%A7%D9%84%D8%B3%D9%88%D8%B1%D9%8A%D8%A9.jpg",
            syrianTags,
            syrianList))

        restaurantsList.add(Restaurant(3,
            "Burger Restaurant",
            4.7f,
            923,
            "https://shamlola.s3.amazonaws.com/Shamlola_Images/7/src/7d63e3088cd4f45c20dac8671bb3eea1d98a22c5.jpg",
            burgerTags,
            burgerList))

        restaurantsList.add(Restaurant(4,
            "Pizza Restaurant",
            3.1f,
            210,
            "https://modo3.com/thumbs/fit630x300/51334/1435144381/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9_%D8%B9%D9%85%D9%84_%D8%B9%D8%AC%D9%8A%D9%86%D8%A9_%D8%A7%D9%84%D8%A8%D9%8A%D8%AA%D8%B2%D8%A7_%D8%A7%D9%84%D8%A5%D9%8A%D8%B7%D8%A7%D9%84%D9%8A%D8%A9.jpg",
            pizzaTags,
            pizzaList))

        restaurantsList.add(Restaurant(5,
            "Pasta Restaurant",
            4.1f,
            512,
            "https://www.justfood.tv/nawa3emPics/4bCJFR12.7.2019-1.jpg",
            burgerTags,
            pastaList))

        restaurantsList.add(Restaurant(6,
            "Chinese Restaurant",
            4.2f,
            1024,
            "https://lh3.googleusercontent.com/p/AF1QipOhfMj2gQaFgsnyFnoCxntaexHlsNinpEdxDOQ=s680-w680-h510",
            chineseTags,
            chineseList))

        restaurantsList.add(Restaurant(7,
            "Crepe Restaurant",
            2.9f,
            310,
            "https://www.supermama.me/system/App/Models/Recipe/images/000/106/376/watermarked/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9-%D8%B9%D9%85%D9%84-%D9%83%D8%B1%D9%8A%D8%A8-%D8%A8%D8%A7%D9%84%D9%84%D8%AD%D9%85%D8%A9-%D8%A7%D9%84%D9%85%D9%81%D8%B1%D9%88%D9%85%D8%A9.jpg",
            crepeTags,
            crepeList))

        restaurantsList.add(Restaurant(8,
            "Fried Chicken Restaurant",
            3.9f,
            2312,
            "https://static.toiimg.com/thumb/61589069.cms?width=1200&height=900",
            friedChickenTags,
            friedChickenList))

        restaurantsList.add(Restaurant(9,
            "Waffles Restaurant",
            4.4f,
            715,
            "https://i.pinimg.com/originals/59/a2/af/59a2afb9c78993013ac259f48ed1b169.jpg",
            waffleTags,
            waffleList))

        restaurantsList.add(Restaurant(10,
            "Sandwiches Restaurant",
            4.1f,
            4096,
            "https://lh3.googleusercontent.com/-O9hmerSSvpQ/X_8-Z6UFe0I/AAAAAAAACMI/C1NVYgumLIoiseK5Iek2bEuSQ-nC2tqGACLcBGAsYHQ/s16000/%25D8%25B3%25D8%25A7%25D9%2586%25D8%25AF%25D9%2588%25D8%25AA%25D8%25B4%25D8%25A7%25D8%25AA%2B%25D8%25A7%25D9%2584%25D8%25B4%25D9%258A%25D8%25B4%2B%25D8%25B7%25D8%25A7%25D9%2588%25D9%2588%25D9%2582.jpg",
            sandwichTags,
            sandwichList))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        database = Firebase.database(getString(R.string.realtime_db_url))
        val dbRef = database.reference
        dbRef.child("restaurants").setValue(restaurantsList)


        if (restaurantsList.isNotEmpty()) {
            val restaurantsRecyclerView = binding.restaurantsRecyclerview
            val restaurantsRecyclerAdapter = RestaurantsRecyclerAdapter(restaurantsList)
            restaurantsRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            restaurantsRecyclerView.adapter = restaurantsRecyclerAdapter

            if (binding.restaurantsRecyclerview.id == binding.restaurantsViewSwitcher.nextView.id) {
                binding.restaurantsViewSwitcher.showNext()
            }
        }
        else if (binding.noRestaurantsFoundText.id == binding.restaurantsViewSwitcher.nextView.id) {
            binding.restaurantsViewSwitcher.showNext()
        }

        if (dishTypesList.isNotEmpty()) {
            val dishesRecyclerView = binding.dishTypesRecyclerview
            val dishTypesRecyclerAdapter = DishTypesRecyclerAdapter(dishTypesList)
            dishesRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            dishesRecyclerView.adapter = dishTypesRecyclerAdapter

            if (binding.dishTypesRecyclerview.id == binding.dishesViewSwitcher.nextView.id) {
                binding.dishesViewSwitcher.showNext()
            }
        }
        else if (binding.noDishesFoundText.id == binding.dishesViewSwitcher.nextView.id) {
            binding.dishesViewSwitcher.showNext()
        }


        return view
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).configureBars("Home", false, View.VISIBLE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}