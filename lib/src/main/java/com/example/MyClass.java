package com.example;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;
public class MyClass {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1,"com.nexuslink.wenavi");
        addFriendVerify(schema);
        new DaoGenerator().generateAll(schema,"app/src/main/java-gen");
    }
    public static void addFriendVerify(Schema schema){
        Entity friendVerify = schema.addEntity("FriendVerify");
        friendVerify.addStringProperty("avatar");
        friendVerify.addStringProperty("nickName");
        friendVerify.addStringProperty("hello");
        friendVerify.addStringProperty("userName");
        friendVerify.addIdProperty();
    }
}
